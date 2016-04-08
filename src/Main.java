import java.util.*;

public class Main {
    public static void main(String args[]){
        String dbFeatures = "jdbc:sqlite:Resources\\Databases\\track_features.db";
//      Used to create the audio feature table, not required to run.
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

//        Example of how the table of features was created.
//        DataSetInfo dsi = new DataSetInfo();
//        dsi.createTable(dbFeatures, FeatureTable);

//      Generate the datasets used for the various classification experiments carried out using the Weka GUI.
        LyricProcessing lp = new LyricProcessing();

//      ------------------------ One vs Rest Classification Quadrants ------------------------------
        ArrayList<SongLyrics> q1_lyrics = lp.ProcessLyricsQuadrant(1000, 266, 267, 267);
        ArrayList<SongLyrics> q2_lyrics = lp.ProcessLyricsQuadrant(266, 1000, 267, 267);
        ArrayList<SongLyrics> q3_lyrics = lp.ProcessLyricsQuadrant(266, 267, 1000, 267);
        ArrayList<SongLyrics> q4_lyrics = lp.ProcessLyricsQuadrant(266, 267, 267, 1000);

//      ------------------------ Multiclass Classification ------------------------------
        ArrayList<SongLyrics> multi_lyrics = lp.ProcessLyricsQuadrant(1000, 1000, 1000, 1000);

//      ------------------------ One vs Rest Classification Groups ------------------------------
        ArrayList<SongLyrics> G2_ly = lp.ProcessLyricsGroup("G2");
        ArrayList<SongLyrics> G5_ly = lp.ProcessLyricsGroup("G5");
        ArrayList<SongLyrics> G6_ly = lp.ProcessLyricsGroup("G6");
        ArrayList<SongLyrics> G7_ly = lp.ProcessLyricsGroup("G7");
        ArrayList<SongLyrics> G8_ly = lp.ProcessLyricsGroup("G8");
        ArrayList<SongLyrics> G12_ly = lp.ProcessLyricsGroup("G12");
        ArrayList<SongLyrics> G14_ly = lp.ProcessLyricsGroup("G14");
        ArrayList<SongLyrics> G32_ly = lp.ProcessLyricsGroup("G32");
        ArrayList<SongLyrics> G28_ly = lp.ProcessLyricsGroup("G28");
        ArrayList<SongLyrics> G29_ly = lp.ProcessLyricsGroup("G29");
        ArrayList<SongLyrics> G15_ly = lp.ProcessLyricsGroup("G15");
        ArrayList<SongLyrics> G16_ly = lp.ProcessLyricsGroup("G16");
        ArrayList<SongLyrics> G17_ly = lp.ProcessLyricsGroup("G17");

//      ------------------------ Generate .arff Files ------------------------------
        try{
//      ------------------------ Quadrants ------------------------------
            lp.oneVSRestClassification("VPlusAPlus.arff", q1_lyrics, "v+a+");
            lp.oneVSRestClassification("VPlusAMinus.arff", q2_lyrics, "v+a-");
            lp.oneVSRestClassification("VMinusAPlus.arff", q3_lyrics, "v-a+");
            lp.oneVSRestClassification("VMinusAMinus.arff", q4_lyrics, "v-a-");
            lp.multiClassClassification("MultiClass.arff", multi_lyrics);
//      ------------------------ V+A+ Groups ------------------------------
            lp.oneVSRestGroupClassification("G2.arff", G2_ly, "G2");
            lp.oneVSRestGroupClassification("G5.arff", G5_ly, "G5");
            lp.oneVSRestGroupClassification("G6.arff", G6_ly, "G6");
            lp.oneVSRestGroupClassification("G7.arff", G7_ly, "G7");
//      ------------------------ V+A- Groups ------------------------------
            lp.oneVSRestGroupClassification("G8.arff", G8_ly, "G8");
            lp.oneVSRestGroupClassification("G12.arff", G12_ly, "G12");
            lp.oneVSRestGroupClassification("G14.arff", G14_ly, "G14");
            lp.oneVSRestGroupClassification("G32.arff", G32_ly, "G32");
//      ------------------------ V-A+ Groups ------------------------------
            lp.oneVSRestGroupClassification("G28.arff", G28_ly, "G28");
            lp.oneVSRestGroupClassification("G29.arff", G29_ly, "G29");
//      ------------------------ V-A- Groups ------------------------------
            lp.oneVSRestGroupClassification("G15.arff", G15_ly, "G15");
            lp.oneVSRestGroupClassification("G16.arff", G16_ly, "G16");
            lp.oneVSRestGroupClassification("G17.arff", G17_ly, "G17");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
