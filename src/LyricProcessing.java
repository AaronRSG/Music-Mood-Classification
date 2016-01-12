import weka.core.Attribute;
import weka.core.FastVector;
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

    public void createBinaryClassification(){
        FastVector atts = new FastVector();
        List<Instances> instances = new ArrayList<Instances>();
    }

//    public void createBinaryClassification(String filename, ArrayList<SongLyrics> songs, List<String> classVal) throws Exception{
//        int nclasses = classVal.size();
//        ArrayList<Attribute> atts = new ArrayList<Attribute>(27+nclasses);
//        List<String> valuesx = new ArrayList<String>();
//        valuesx.add("1");
//        valuesx.add("0");
//        for(int x = 0;x < nclasses; x++){
//            atts.add(new Attribute("@"+classVal.get(x), valuesx));
//        }
//        atts.add(new Attribute("lyrics", (ArrayList<String>)null));
//        Instances data = new Instances("TestInstances", atts, 0);
//
//        for(SongLyrics sl : songs){
//            double[] instanceValue1 = new double[data.numAttributes()];
//            for (int x = 0; x < nclasses; x++){
//                if(x != classVal.indexOf(sl.getQuadrant())) instanceValue1[x] = 1;
//                else instanceValue1[classVal.indexOf(sl.getQuadrant())] = 0;
//            }
//            String lyrics = sl.getLyrics();
//            instanceValue1[nclasses] = data.attribute(nclasses).addStringValue(lyrics);
//            data.add(new DenseInstance(1.0, instanceValue1));
//        }
//        Reorder reorder = new Reorder();
//        reorder.setAttributeIndices("2,1,3-last");
//        reorder.setInputFormat(data);
//        Instances newData = Filter.useFilter(data, reorder);
//
//        // save in ARFF file
//        ArffSaver saver = new ArffSaver();
//        saver.setInstances(newData);
//        saver.setFile(new File("./"+filename));
//        saver.setDestination(new File("./"+filename));
//        saver.writeBatch();
//    }

}
