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

        Lyrics l = new Lyrics();
        ArrayList<SongDetails> details = l.getDetails();
        try{
            Features f = new Features();
//            f.getFeatures("DJ Harry", "\'Round the Wheel", 1);
            for(SongDetails sd :details.subList(1835,9999)){
                f.getFeaturesToAdd(sd.getArtist(), sd.getTitle(), 1);
                System.out.println("Successfully added " + sd.toString() + "\n");
            }
        }catch (EchoNestException ex){
            ex.printStackTrace();

        }
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
