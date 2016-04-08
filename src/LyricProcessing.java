import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import java.io.File;
import java.util.*;

public class LyricProcessing {
    // Perform word stemming of lyrics as implemented by the Porter Stemmer.
    public String stemWords(String lyrics){
        String result = "";
        String[] words = lyrics.split("\\s+"); // Split lyric terms using spaces.
        Stemmer stemmer = new Stemmer();
        char[] char_arry;

        for(String w: words){ // For each lyric term, an array of characters is required.
            char_arry = w.toLowerCase().toCharArray();
            for (int j = 0; j < char_arry.length; j++){
                stemmer.add(char_arry[j]); // Add each letter of a lyric term to stemmer.
            }
            stemmer.stem();
            result += " " + stemmer.toString();
        }
        return result; // Return stemmed string of lyrics.
    }
    // Perform pre-processing steps and assign mood quadrants for specified dataset.
    public ArrayList<SongLyrics> ProcessLyricsQuadrant(int sizeQ1, int sizeQ2, int sizeQ3, int sizeQ4){
        BuildLyrics l = new BuildLyrics();
        Stopwords st = new Stopwords();
        // Get dataset, size can be used to set balanced or unbalanced classifiers.
        ArrayList<SongLyrics> dataSet = l.buildDataSet(sizeQ1, sizeQ2, sizeQ3, sizeQ4);
        for(SongLyrics song: dataSet){
            String mood = song.getMood();
            String quadrant = song.getQuadrant();
            String lyrics = song.getLyrics();

            // Based on the mood group of each song, a certain quadrant can be assigned.
            if(mood.equalsIgnoreCase("G1") || mood.equalsIgnoreCase("G2") ||
                    mood.equalsIgnoreCase("G5") || mood.equalsIgnoreCase("G6") ||
                    mood.equalsIgnoreCase("G7") || mood.equalsIgnoreCase("G9")) quadrant = "v+a+";
            if(mood.equalsIgnoreCase("G8") || mood.equalsIgnoreCase("G11") ||
                    mood.equalsIgnoreCase("G12") || mood.equalsIgnoreCase("G14") ||
                    mood.equalsIgnoreCase("G32")) quadrant = "v+a-";
            if(mood.equalsIgnoreCase("G25") || mood.equalsIgnoreCase("G28") ||
                    mood.equalsIgnoreCase("G29")) quadrant = "v-a+";
            if(mood.equalsIgnoreCase("G15") || mood.equalsIgnoreCase("G16") ||
                    mood.equalsIgnoreCase("G17") || mood.equalsIgnoreCase("G31")) quadrant = "v-a-";

            song.setQuadrant(quadrant);
            String LyricTagsRemoved = st.removeLyricTags(lyrics); // Remove any HTML tags that may be in lyrics.
            String PunctuationRemoved = LyricTagsRemoved.replaceAll("\\p{Punct}+", ""); // Remove any punctuation.
            String StopWordsRemoved = st.removeWords(PunctuationRemoved); // Perform stopword removal.
            String stemmed = stemWords(StopWordsRemoved); // Perform word stemming.
            song.setLyrics(stemmed);
        }
        return dataSet; // Return datset to be used for classification.
    }

//    Perform pre-processing steps for specified mood group dataset .
    public ArrayList<SongLyrics> ProcessLyricsGroup(String mood_group){
        BuildLyrics l = new BuildLyrics();
        Stopwords st = new Stopwords();
        ArrayList<SongLyrics> dataSet = l.buildGroupDataSet(mood_group);
        for(SongLyrics song: dataSet){
            String lyrics = song.getLyrics();

            String LyricTagsRemoved = st.removeLyricTags(lyrics);
            String PunctuationRemoved = LyricTagsRemoved.replaceAll("\\p{Punct}+", "");
            String StopWordsRemoved = st.removeWords(PunctuationRemoved);
            String stemmed = stemWords(StopWordsRemoved);
            song.setLyrics(stemmed);
        }
        return dataSet;
    }
    //Generate .arff file to be used in one vs rest classification.
    public void oneVSRestClassification(String filename, ArrayList<SongLyrics> songs, String quadrant) throws Exception{
        FastVector fvClassVal = new FastVector(2);
        int i = 0;
        double[] values;

        // Setting attribute to Document allows document classification to be carried out in the Weka GUI.
        Attribute attribute = new Attribute("Document", (FastVector) null);

        fvClassVal.addElement("positive");
        fvClassVal.addElement("negative");
        Attribute classAttribute = new Attribute("@@class@@", fvClassVal);

        FastVector fvWekaAttributes = new FastVector(2);
        fvWekaAttributes.addElement(attribute);
        fvWekaAttributes.addElement(classAttribute);

        Instances ts = new Instances("MyRel", fvWekaAttributes, 1);
        ts.setClassIndex(1);

        for (SongLyrics song: songs){
            values = new double[ts.numAttributes()];
            values[0] = ts.attribute(0).addStringValue(song.getLyrics()); // Represent each song by lyrics.
            // Depending on the classifier being generated, a quadrant will be assigned positive or negative class label.
            if(song.getQuadrant().equals(quadrant))
                values[1] = ts.attribute(1).indexOfValue("positive");
            else
                values[1] = ts.attribute(1).indexOfValue("negative");

            ts.add(new Instance(1.0, values ));
        }

        // Write to .arff.
        ArffSaver as = new ArffSaver();
        as.setInstances(ts);
        as.setFile(new File(filename));
        as.writeBatch();
    }

    //Generate .arff file to be used in multiclass classification.
    public void multiClassClassification(String filename, ArrayList<SongLyrics> songs) throws Exception{
        FastVector fvClassVal = new FastVector(2);
        int i = 0;
        double[] values;

        Attribute attribute = new Attribute("Document", (FastVector) null);
        // In multiclass 4 class labels are required, one for each quadrant.
        fvClassVal.addElement("v+a+");
        fvClassVal.addElement("v+a-");
        fvClassVal.addElement("v-a+");
        fvClassVal.addElement("v-a-");
        Attribute classAttribute = new Attribute("@@class@@", fvClassVal);

        FastVector fvWekaAttributes = new FastVector(2);
        fvWekaAttributes.addElement(attribute);
        fvWekaAttributes.addElement(classAttribute);

        Instances ts = new Instances("MyRel", fvWekaAttributes, 1);
        ts.setClassIndex(1);

        for (SongLyrics song: songs){
            values = new double[ts.numAttributes()];
            values[0] = ts.attribute(0).addStringValue(song.getLyrics());
            // Assign class label based on quadrant of each song.
            if(song.getQuadrant().equals("v+a+")){
                values[1] = ts.attribute(1).indexOfValue("v+a+");
            } else if (song.getQuadrant().equals("v+a-")){
                values[1] = ts.attribute(1).indexOfValue("v+a-");
            } else if (song.getQuadrant().equals("v-a+")){
                values[1] = ts.attribute(1).indexOfValue("v-a+");
            } else if (song.getQuadrant().equals("v-a-")){
                values[1] = ts.attribute(1).indexOfValue("v-a-");
            }

            ts.add(new Instance(1.0, values ));
        }

        ArffSaver as = new ArffSaver();
        as.setInstances(ts);
        as.setFile(new File(filename));
        as.writeBatch();
    }

    // Generate .arff file to be used in one vs rest classification at the group level of granularity.
    public void oneVSRestGroupClassification(String filename, ArrayList<SongLyrics> songs, String group) throws Exception{
        FastVector fvClassVal = new FastVector(2);
        int i = 0;
        double[] values;

        Attribute attribute = new Attribute("Document", (FastVector) null);

        fvClassVal.addElement("positive");
        fvClassVal.addElement("negative");
        Attribute classAttribute = new Attribute("@@class@@", fvClassVal);

        FastVector fvWekaAttributes = new FastVector(2);
        fvWekaAttributes.addElement(attribute);
        fvWekaAttributes.addElement(classAttribute);

        Instances ts = new Instances("MyRel", fvWekaAttributes, 1);
        ts.setClassIndex(1);

        for (SongLyrics song: songs){
            values = new double[ts.numAttributes()];
            values[0] = ts.attribute(0).addStringValue(song.getLyrics());
            // Assign class label based on mood group. This will differ depending on the mood group being used to generate the classifier.
            if(song.getMood().equals(group))
                values[1] = ts.attribute(1).indexOfValue("positive");
            else
                values[1] = ts.attribute(1).indexOfValue("negative");

            ts.add(new Instance(1.0, values ));
        }

        ArffSaver as = new ArffSaver();
        as.setInstances(ts);
        as.setFile(new File(filename));
        as.writeBatch();
    }

}
