import java.sql.*;

public class Lyrics {
    DataSetInfo dsi = new DataSetInfo();
    String dbLyrics = "jdbc:sqlite:C:\\Users\\Aaron\\Documents\\Final Year\\FinalYearProject\\MSD\\MusiXMatch\\mxm_dataset.db";
    String dbSongs = "jdbc:sqlite:C:\\Users\\Aaron\\Documents\\Final Year\\FinalYearProject\\MSD\\MillionSongSubset\\AdditionalFiles\\subset_track_metadata.db";

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
}
