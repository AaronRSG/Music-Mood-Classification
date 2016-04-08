import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BuildLyrics {
    DataSetInfo dsi = new DataSetInfo();
    String dbFullLyrics = "jdbc:sqlite:Resources\\Databases\\Full-Lyrics.db";

    public ArrayList<SongLyrics> getFullLyrics(String query){
        ArrayList<SongLyrics> fullLyrics = new ArrayList<SongLyrics>();
        try{
            ResultSet rs = dsi.getInfo(dbFullLyrics, query);
            while (rs.next()){
                String track_id = rs.getString("track_id");
                String tag = rs.getString("tag");
                String score = rs.getString("score");
                String mood = rs.getString("mood");
                String lyrics = rs.getString("lyrics");

                SongLyrics sl = new SongLyrics(track_id, tag, score, mood, lyrics);
                fullLyrics.add(sl);
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }
        return fullLyrics;
    }

    public ArrayList<SongLyrics> buildDataSet(int sizeQ1, int sizeQ2, int sizeQ3, int sizeQ4){
        ArrayList<SongLyrics> q1 = getFullLyrics("SELECT * FROM full_lyrics_view WHERE mood IN (\'G1\'," +
                "\'G2\', \'G5\', \'G6\', \'G7\', \'G9\') ORDER BY RANDOM() LIMIT " + sizeQ1); // quadrant - v+a+
        ArrayList<SongLyrics> q2 = getFullLyrics("SELECT * FROM full_lyrics_view WHERE mood IN (\'G8\', " +
                "\'G11\', \'G12\', \'G14\', \'G32\') ORDER BY RANDOM() LIMIT " + sizeQ2); // quadrant - v+a-
        ArrayList<SongLyrics> q3 = getFullLyrics("SELECT * FROM full_lyrics_view WHERE mood IN (\'G25\', " +
                "\'G28\', \'G29\') ORDER BY RANDOM() LIMIT " + sizeQ3); // quadrant v-a+
        ArrayList<SongLyrics> q4 = getFullLyrics("SELECT * FROM full_lyrics_view WHERE mood IN (\'G15\'," +
                "\'G16\', \'G17\', \'G31\') ORDER BY RANDOM() LIMIT " + sizeQ4); //quadrant v-a-

        ArrayList<SongLyrics> dataSet = new ArrayList<SongLyrics>();
        dataSet.addAll(q1);
        dataSet.addAll(q2);
        dataSet.addAll(q3);
        dataSet.addAll(q4);

        return dataSet;
    }

    public ArrayList<SongLyrics> buildGroupDataSet(String mood_group){
        ArrayList<SongLyrics> group_lyrics_pos = getFullLyrics("SELECT * FROM full_lyrics_view WHERE mood IN (\'" +
                                                            mood_group + "\')" + "ORDER BY RANDOM() LIMIT 1000");
        int max_neg = group_lyrics_pos.size();
        ArrayList<SongLyrics> group_lyrics_neg = getFullLyrics("SELECT * FROM full_lyrics_view WHERE mood NOT IN (\'" +
                mood_group + "\')" + "ORDER BY RANDOM() LIMIT " + max_neg);
        ArrayList<SongLyrics> dataSet = new ArrayList<SongLyrics>();
        dataSet.addAll(group_lyrics_pos);
        dataSet.addAll(group_lyrics_neg);
        return dataSet;
    }
}
