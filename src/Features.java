import com.echonest.api.v4.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Features {
    private EchoNestAPI en;
    private String API_KEY = "LH8NKVEQPJK33XZDV";
    String dbFeatures = "jdbc:sqlite:C:\\Users\\Aaron\\Documents\\Final Year\\FinalYearProject\\SQLite\\track_features.db";
    String SQL_INSERT = "INSERT INTO features VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    DataSetInfo dsi = new DataSetInfo();

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

    public void getFeaturesToAdd(String artist, String title, int results)throws EchoNestException{
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

    public ArrayList<SongFeatures> getFeatures(){
        ArrayList<SongFeatures> features = new ArrayList<SongFeatures>();
        try{
            ResultSet rs = dsi.getInfo(dbFeatures, "SELECT * FROM features");
            while (rs.next()){
                String track_id = rs.getString("track_id");
                String artist = rs.getString("artist_name");
                String title = rs.getString("title");
                double duration = rs.getDouble("duration");
                double danceability = rs.getDouble("danceability");
                double end_of_fade_in = rs.getDouble("end_of_fade_in");
                double energy = rs.getDouble("energy");
                double key = rs.getDouble("key");
                double key_confidence = rs.getDouble("key_confidence");
                double loudness = rs.getDouble("loudness");
                double mode = rs.getDouble("mode");
                double mode_confidence = rs.getDouble("mode_confidence");
                double song_hotness = rs.getDouble("song_hotness");
                double start_of_fade_out = rs.getDouble("start_of_fade_out");
                double tempo = rs.getDouble("tempo");
                double time_signature = rs.getDouble("time_signature");

                SongFeatures sf = new SongFeatures(track_id, artist, title,
                        duration, danceability, end_of_fade_in, energy, key,
                        key_confidence, loudness, mode, mode_confidence, song_hotness,
                        start_of_fade_out, tempo, time_signature);

                features.add(sf);
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }
        return features;
    }
}
