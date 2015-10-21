import java.sql.*;
import java.util.ArrayList;

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

    public ArrayList<SongLyrics> getFullLyrics(){
        ArrayList<SongLyrics> fullLyrics = new ArrayList<SongLyrics>();
        try{
            ResultSet rs = dsi.getInfo(dbFullLyrics, "SELECT * FROM full_lyrics_view WHERE mood!=\'NULL\'");
            while (rs.next()){
                String track_id = rs.getString("track_id");
                String tag = rs.getString("tag");
                String score = rs.getString("score");
                String mood = rs.getString("mood");
                String lyrics = rs.getString("lyrics");

                SongLyrics sl = new SongLyrics(track_id, tag, score,
                        mood, lyrics);
                fullLyrics.add(sl);
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }
        return fullLyrics;
    }
}
