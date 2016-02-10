import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Lyrics {
    DataSetInfo dsi = new DataSetInfo();
    String dbLyrics = "jdbc:sqlite:C:\\Users\\Aaron\\Documents\\Final Year\\FinalYearProject\\MSD\\MusiXMatch\\mxm_dataset.db";
    String dbSongs = "jdbc:sqlite:C:\\Users\\Aaron\\Documents\\Final Year\\FinalYearProject\\MSD\\MillionSongSubset\\AdditionalFiles\\subset_track_metadata.db";
    String dbFullLyrics = "jdbc:sqlite:C:\\Users\\Aaron\\Documents\\Final Year\\FinalYearProject\\Full-Lyrics.db";

    public void getLyrics(){
        try{
            Connection connection = DriverManager.getConnection(dbLyrics);
            ResultSet srs = dsi.getInfo(dbSongs, "SELECT track_id, artist_name, title, year FROM songs");
            while (srs.next()){
                String track_id = srs.getString("track_id");
                String artist_name = srs.getString("artist_name");
                String title = srs.getString("title");
                int year = srs.getInt("year");
                String sql = "SELECT word, count FROM lyrics WHERE track_id=?";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, track_id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()){
                    String lyric = rs.getString("word");
                    System.out.println(track_id + artist_name + " - " + title + " - " + year);
                }
                System.out.println("****************************************");
            }
        } catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    public ArrayList<SongDetails> getDetails(){
        ArrayList<SongDetails> details = new ArrayList<SongDetails>();
        try{
            ResultSet rs = dsi.getInfo(dbSongs, "SELECT artist_name, title FROM songs");
            while (rs.next()){
                String artist = rs.getString("artist_name");
                String title = rs.getString("title");

                SongDetails sd = new SongDetails(artist, title);
                if(!sd.getTitle().equals("")){
                    details.add(sd);
                }
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }
        return details;
    }

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

    public ArrayList<SongLyrics> buildDataSet(){
        ArrayList<SongLyrics> q1 = getFullLyrics("SELECT * FROM full_lyrics_view WHERE mood IN (\'G1\'," +
                                                "\'G2\', \'G5\', \'G6\', \'G7\', \'G9\') ORDER BY RANDOM() LIMIT 200"); // quadrant - v+a+
        ArrayList<SongLyrics> q2 = getFullLyrics("SELECT * FROM full_lyrics_view WHERE mood IN (\'G8\', " +
                                                "\'G11\', \'G12\', \'G14\', \'G32\') ORDER BY RANDOM() LIMIT 0"); // quadrant - v+a-
        ArrayList<SongLyrics> q3 = getFullLyrics("SELECT * FROM full_lyrics_view WHERE mood IN (\'G25\', " +
                                                "\'G28\', \'G29\') ORDER BY RANDOM() LIMIT 0"); // quadrant v-a+
        ArrayList<SongLyrics> q4 = getFullLyrics("SELECT * FROM full_lyrics_view WHERE mood IN (\'G15\'," +
                                                "\'G16\', \'G17\', \'G31\') ORDER BY RANDOM() LIMIT 0"); //quadrant v-a-

        ArrayList<SongLyrics> dataSet = new ArrayList<SongLyrics>();
        dataSet.addAll(q1);
        dataSet.addAll(q2);
        dataSet.addAll(q3);
        dataSet.addAll(q4);

        return dataSet;
    }
}
