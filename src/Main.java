import com.echonest.api.v4.EchoNestException;

public class Main {
    public static void main(String args[]){
        String dbFeatures = "jdbc:sqlite:C:\\Users\\Aaron\\Documents\\Final Year\\FinalYearProject\\SQLite\\track_features.db";
        String FeatureTable = "CREATE TABLE features " +
                              "(track_id            TEXT    PRIMARY KEY NOT NULL, " +
                              "artist_name          TEXT                NOT NULL, " +
                              "title                TEXT                NOT NULL, " +
                              "duration             FLOAT               NOT NULL, " +
                              "danceability         FLOAT               NOT NULL, " +
                              "end_of_fade_in       FLOAT               NOT NULL, " +
                              "energy               FLOAT               NOT NULL, " +
                              "key                  INT                 NOT NULL, " +
                              "key_confidence       FLOAT               NOT NULL, " +
                              "loudness             FLOAT               NOT NULL, " +
                              "mode                 INT                 NOT NULL, " +
                              "mode_confidence      FLOAT               NOT NULL, " +
                              "song_hotness         FLOAT               NOT NULL, " +
                              "start_of_fade_out    FLOAT               NOT NULL, " +
                              "tempo                FLOAT               NOT NULL, " +
                              "time_signature       INT                 NOT NULL) ";

        DataSetInfo dsi = new DataSetInfo();
        dsi.createTable(dbFeatures, FeatureTable);

//        Lyrics l = new Lyrics();
//        l.getLyrics();
//        l.getFullLyrics();

//        Tags t = new Tags();
//        t.getTags();
//        try {
//            Features f = new Features();
//            f.searchSongByArtist("Nadine Renee", "Next Time", 5);
//        } catch (EchoNestException e) {
//            e.printStackTrace();
//        }
    }
}
