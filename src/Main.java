import com.echonest.api.v4.EchoNestException;

import java.util.*;

public class Main {
    public static void main(String args[]){
        String dbFeatures = "jdbc:sqlite:C:\\Users\\Aaron\\Documents\\Final Year\\FinalYearProject\\SQLite\\track_features.db";
        String FeatureTable = "CREATE TABLE features " +
                "(track_id            TEXT    PRIMARY KEY NOT NULL, " +
                "artist_name          TEXT                NOT NULL, " +
                "title                TEXT                NOT NULL, " +
                "duration             DOUBLE              NOT NULL, " +
                "danceability         DOUBLE              NOT NULL, " +
                "end_of_fade_in       DOUBLE              NOT NULL, " +
                "energy               DOUBLE              NOT NULL, " +
                "key                  DOUBLE              NOT NULL, " +
                "key_confidence       DOUBLE              NOT NULL, " +
                "loudness             DOUBLE              NOT NULL, " +
                "mode                 DOUBLE              NOT NULL, " +
                "mode_confidence      DOUBLE              NOT NULL, " +
                "song_hotness         DOUBLE              NOT NULL, " +
                "start_of_fade_out    DOUBLE              NOT NULL, " +
                "tempo                DOUBLE              NOT NULL, " +
                "time_signature       DOUBLE              NOT NULL) ";

//        DataSetInfo dsi = new DataSetInfo();
//        dsi.createTable(dbFeatures, FeatureTable);

//        *******************************Features*****************************************

//        Lyrics l = new Lyrics();
//        ArrayList<SongDetails> details = l.getDetails();
//        try{
//            Features f = new Features();
//            for(SongDetails sd :details.subList(2943,details.size())){
//                f.getFeaturesToAdd(sd.getArtist(), sd.getTitle(), 1);
//                System.out.println("Successfully added " + sd.toString() + "\n");
//            }
//        }catch (EchoNestException ex){
//            //Do Nothing. Invalid entries not entered.
//        }
        ArrayList<SongFeatures> features;
        try {
            Features f = new Features();
            features = f.getFeatures();
            int i = 0;

            f.featureARFF(features);
//            for(SongFeatures sf : features){
//                i++;
//                System.out.println(sf.toString());
//            }
//            System.out.println(i);
        } catch (EchoNestException ex) {
            ex.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
//        ***********************Lyrics**************************************
//
//        LyricProcessing lp = new LyricProcessing();
//        ArrayList<SongLyrics> lyrics = lp.ProcessLyrics();
//        ArrayList<String> lyricTerms = lp.gatherLyricTerms(lyrics);
//        for (String t: lyricTerms){
//            System.out.println(t);
//        }

//        try{
//            lp.docClassification(lyrics, "v+a+");
//        }catch (Exception e){
//            e.printStackTrace();
//        }




//        List<String> classVal = new ArrayList<String>();
//        for(SongLyrics sl: lyrics){
//            String quadrant = sl.getQuadrant();
//            if(!classVal.contains(quadrant)) classVal.add(quadrant);
//        }
//
//        for(String q: classVal){
//            System.out.println(q);
//        }
//        try {
//            lp.createBinaryClassification("MyClassifier.arff", lyrics, classVal);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }


//        Lyrics l = new Lyrics();
//        ArrayList<SongLyrics> lyrics = l.getFullLyrics();
//        for (SongLyrics sl: lyrics){
//            System.out.println(sl.toString());
//        }

//        l.getLyrics();
//        l.getFullLyrics();

//        Tags t = new Tags();
//        t.getTags();
//        try {
//            Features f = new Features();
//            f.getFeatures("Nadine Renee", "Next Time", 5);
//        } catch (EchoNestException e) {
//            e.printStackTrace();
//        }
    }
}
