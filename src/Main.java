import com.echonest.api.v4.EchoNestException;

import java.util.ArrayList;

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

        Lyrics l = new Lyrics();
        ArrayList<SongLyrics> dataSet = l.buildDataSet();
        for(SongLyrics song: dataSet){
            String mood = song.getMood();
            String quadrant = song.getQuadrant();
            if(song.getMood().equalsIgnoreCase("G1") || song.getMood().equalsIgnoreCase("G2") ||
                    song.getMood().equalsIgnoreCase("G5") || song.getMood().equalsIgnoreCase("G6") ||
                    song.getMood().equalsIgnoreCase("G7") || song.getMood().equalsIgnoreCase("G9")) quadrant = "v+a+";
            if(song.getMood().equalsIgnoreCase("G8") || song.getMood().equalsIgnoreCase("G11") ||
                    song.getMood().equalsIgnoreCase("G12") || song.getMood().equalsIgnoreCase("G14") ||
                    song.getMood().equalsIgnoreCase("G32")) quadrant = "v+a-";
            if(song.getMood().equalsIgnoreCase("G25") || song.getMood().equalsIgnoreCase("G28") ||
                    song.getMood().equalsIgnoreCase("G29")) quadrant = "v-a+";
            if(song.getMood().equalsIgnoreCase("G15") || song.getMood().equalsIgnoreCase("G16") ||
                    song.getMood().equalsIgnoreCase("G17") || song.getMood().equalsIgnoreCase("G31")) quadrant = "v-a-";
            song.setQuadrant(quadrant);

            System.out.println(song.getTrack_id() + " " + song.getMood() + " " + song.getQuadrant());

        }

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
