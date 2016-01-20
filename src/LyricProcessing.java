import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Reorder;

import java.io.File;
import java.util.*;
import java.io.PrintWriter;

public class LyricProcessing {
    public ArrayList<SongLyrics> ProcessLyrics(){
        Lyrics l = new Lyrics();
        Stopwords st = new Stopwords();
        ArrayList<SongLyrics> dataSet = l.buildDataSet();
        for(SongLyrics song: dataSet){
            String mood = song.getMood();
            String quadrant = song.getQuadrant();
            String lyrics = song.getLyrics();
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
            String LyricTagsRemoved = st.removeLyricTags(lyrics);
            String PunctuationRemoved = LyricTagsRemoved.replaceAll("\\p{Punct}+", "");
            String StopWordsRemoved = st.removeWords(PunctuationRemoved);
            String stemmed = st.stemWords(StopWordsRemoved);
            song.setLyrics(stemmed);
//            System.out.println(song.getLyrics());
        }
        return dataSet;
    }
    public ArrayList<String> gatherLyricTerms(ArrayList<SongLyrics> songs){
        ArrayList<String> terms = new ArrayList<String>();
        for(SongLyrics song: songs){
            for(String term: song.getLyrics().split(" ")){
                terms.add(term);
            }
        }
        Set<String> uniqueTerms = new HashSet<String>();
        uniqueTerms.addAll(terms);
        terms.clear();
        terms.addAll(uniqueTerms);
        Collections.sort(terms, String.CASE_INSENSITIVE_ORDER);
        return terms;
    }

    public void docClassification(ArrayList<SongLyrics> songs, String quadrant) throws Exception{
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
            if(song.getQuadrant().equals(quadrant))
                values[1] = ts.attribute(1).indexOfValue("positive");
            else
                values[1] = ts.attribute(1).indexOfValue("negative");

            ts.add(new Instance(1.0, values ));
        }

        ArffSaver as = new ArffSaver();
        as.setInstances(ts);
        as.setFile(new File("myDocTest.arff"));
        as.writeBatch();
    }

}
