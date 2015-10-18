import com.echonest.api.v4.EchoNestException;

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
//        l.getLyrics();
//        l.getFullLyrics();

//        Tags t = new Tags();
//        t.getTags();
        try {
            Features f = new Features();
            f.searchSongByArtist("Nadine Renee", "Next Time", 5);
        } catch (EchoNestException e) {
            e.printStackTrace();
        }
    }
}
