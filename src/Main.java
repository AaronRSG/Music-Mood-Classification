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

//        Lyrics l = new Lyrics();
//        ArrayList<SongDetails> details = l.getDetails();
//        try{
//            Features f = new Features();
//            for(SongDetails sd :details.subList(2315,9999)){
//                f.getFeaturesToAdd(sd.getArtist(), sd.getTitle(), 1);
//                System.out.println("Successfully added " + sd.toString() + "\n");
//            }
//        }catch (EchoNestException ex){
//            ex.printStackTrace();
//
//        }

        LyricProcessing lp = new LyricProcessing();
        ArrayList<SongLyrics> lyrics = lp.ProcessLyrics();
        ArrayList<String> lyricTerms = lp.gatherLyricTerms(lyrics);
        for (String t: lyricTerms){
            System.out.println(t);
        }


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

//        ArrayList<SongFeatures> features;
//        try {
//            Features f = new Features();
//            features = f.getFeatures();
//
//            for(SongFeatures sf : features){
//                System.out.println(sf.toString());
//            }
//        } catch (EchoNestException ex) {
//            ex.printStackTrace();
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
