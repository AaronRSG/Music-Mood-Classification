import com.echonest.api.v4.*;

import java.sql.*;
import java.util.List;

public class Features {
    private EchoNestAPI en;
    private String API_KEY = "LH8NKVEQPJK33XZDV";
    String dbFeatures = "jdbc:sqlite:C:\\Users\\Aaron\\Documents\\Final Year\\FinalYearProject\\SQLite\\track_features.db";
    String SQL_INSERT = "INSERT INTO features VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    public Features()throws EchoNestException {
        en = new EchoNestAPI(API_KEY);
        en.setTraceSends(true);
        en.setTraceRecvs(false);
    }

    public void insertFeatures(Song song) throws EchoNestException, SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = DriverManager.getConnection(dbFeatures);
            statement = connection.prepareStatement(SQL_INSERT);
            statement.setString(1, song.getID());
            statement.setString(2, song.getArtistName());
            statement.setString(3, song.getTitle());
            statement.setDouble(4, song.getDuration());
            statement.setDouble(5, song.getDanceability());
            statement.setDouble(6, song.getAnalysis().getEndOfFadeIn());
            statement.setDouble(7, song.getEnergy());
            statement.setDouble(8, song.getKey());
            statement.setDouble(9, song.getAnalysis().getKeyConfidence());
            statement.setDouble(10, song.getLoudness());
            statement.setDouble(11,song.getMode());
            statement.setDouble(12,song.getAnalysis().getModeConfidence());
            statement.setDouble(13,song.getSongHotttnesss());
            statement.setDouble(14,song.getAnalysis().getStartOfFadeOut());
            statement.setDouble(15,song.getTempo());
            statement.setDouble(16,song.getTimeSignature());
            statement.executeUpdate();
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    public void searchSongByArtist(String artist, String title, int results)throws EchoNestException{
        SongParams p = new SongParams();
        p.setArtist(artist);
        p.setTitle(title);
        p.includeAudioSummary();
        p.includeArtistHotttnesss();
        p.includeSongHotttnesss();
        p.includeArtistFamiliarity();
        p.includeArtistLocation();
        p.sortBy("tempo", false);
        p.add("results", results);

        List<Song> songs = en.searchSongs(p);
        try{
            for (Song song : songs){
                insertFeatures(song);
                System.out.println();
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }
}
