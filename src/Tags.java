import java.sql.*;

public class Tags {
    DataSetInfo dsi = new DataSetInfo();
    String dbLastFM = "jdbc:sqlite:C:\\Users\\Aaron\\Documents\\Final Year\\FinalYearProject\\MSD\\lastfm_subset\\lastfm_tags.db";
    String dbSongs = "jdbc:sqlite:C:\\Users\\Aaron\\Documents\\Final Year\\FinalYearProject\\MSD\\MillionSongSubset\\AdditionalFiles\\subset_track_metadata.db";

    public void getTags(){
        try{
            Connection connection = DriverManager.getConnection(dbLastFM);
            ResultSet srs = dsi.getInfo(dbSongs, "SELECT track_id, title FROM songs");

            while(srs.next()){
                String track_id = srs.getString("track_id");
                String title = srs.getString("title");
                String lfmQuery = "SELECT tags.tag, tid_tag.val FROM tid_tag, tids, tags WHERE tags.ROWID=tid_tag.tag AND tid_tag.tid=tids.ROWID AND tids.tid=?";
                PreparedStatement ps = connection.prepareStatement(lfmQuery);
                ps.setString(1, track_id);
                ResultSet rs = ps.executeQuery();
                while(rs.next()){
                    String tag = rs.getString("tag");
                    String value = rs.getString("val");
                    System.out.println(track_id + " - " + title + " - " + tag + " - " + value);
                }
                System.out.println("***************************************");
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }

    }
}
