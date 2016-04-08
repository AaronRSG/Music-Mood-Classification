import java.sql.*;
import java.util.ArrayList;

public class BuildLyrics {
    DataSetInfo dsi = new DataSetInfo();
    String dbFullLyrics = "jdbc:sqlite:Resources\\Databases\\Full-Lyrics.db";

    // Create an ArrayList of SongLyrics objects for a given query to the database.
    public ArrayList<SongLyrics> getFullLyrics(String query){
        ArrayList<SongLyrics> fullLyrics = new ArrayList<SongLyrics>();
        try{
            ResultSet rs = dsi.getInfo(dbFullLyrics, query); // Store results of query.
            while (rs.next()){ // For each entry retrieved get various data.
                String track_id = rs.getString("track_id");
                String tag = rs.getString("tag");
                String score = rs.getString("score");
                String mood = rs.getString("mood");
                String lyrics = rs.getString("lyrics");

                // Create a SongLyrics object for particular entry retrieved from database.
                SongLyrics sl = new SongLyrics(track_id, tag, score, mood, lyrics);
                fullLyrics.add(sl);
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }
        return fullLyrics;
    }

    // Create the data set to be used for classification at the quadrant level of granularity.
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

        return dataSet; // Depending on initial sizes passed, different classification strategies can be used.
    }

    // Create the data set to be used for classification at the group level of granularity.
    public ArrayList<SongLyrics> buildGroupDataSet(String mood_group){
        // Get lyrics for mood group defined as positive for a particular binary classifier.
        ArrayList<SongLyrics> group_lyrics_pos = getFullLyrics("SELECT * FROM full_lyrics_view WHERE mood IN (\'" +
                                                            mood_group + "\')" + "ORDER BY RANDOM() LIMIT 1000");
        int max_neg = group_lyrics_pos.size(); // Set the maximum number negative instances to be used, match number of positives.
        // Get lyrics for negative instances from all remaining groups for a particular binary classifier.
        ArrayList<SongLyrics> group_lyrics_neg = getFullLyrics("SELECT * FROM full_lyrics_view WHERE mood NOT IN (\'" +
                mood_group + "\')" + "ORDER BY RANDOM() LIMIT " + max_neg);

        ArrayList<SongLyrics> dataSet = new ArrayList<SongLyrics>();
        dataSet.addAll(group_lyrics_pos);
        dataSet.addAll(group_lyrics_neg);
        return dataSet;
    }
}
