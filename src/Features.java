import com.echonest.api.v4.*;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffSaver;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Features {
    private EchoNestAPI en;
    private String API_KEY = "LH8NKVEQPJK33XZDV";
    String dbFeatures = "jdbc:sqlite:Resources\\Databases\\track_features.db";
    String SQL_INSERT = "INSERT INTO features VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    DataSetInfo dsi = new DataSetInfo();

    public Features()throws EchoNestException {
        en = new EchoNestAPI(API_KEY);
        en.setTraceSends(true);
        en.setTraceRecvs(false);
    }

    // Insert songs and corresponding features in to database storing all songs and feature values.
    public void insertFeatures(Song song) throws EchoNestException, SQLException {
        Connection connection;
        PreparedStatement statement;
        try{
            connection = DriverManager.getConnection(dbFeatures);
            statement = connection.prepareStatement(SQL_INSERT);
            // Specify features to be retrieved from EchoNest.
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
            statement.executeUpdate(); // Update the database and add new entry.
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    // Retrieve audio features of a song based on the artist name and song title.
    public void getFeaturesToAdd(String artist, String title, int results)throws EchoNestException{
        SongParams p = new SongParams();
        p.setArtist(artist);
        p.setTitle(title);
        // Specify the parameters to be included in response from EchoNest.
        p.includeAudioSummary();
        p.includeArtistHotttnesss();
        p.includeSongHotttnesss();
        p.includeArtistFamiliarity();
        p.includeArtistLocation();
        p.sortBy("tempo", false);
        p.add("results", results);

        List<Song> songs = en.searchSongs(p);
        try{ // Insert song into Database.
            for (Song song : songs){
                insertFeatures(song);
                System.out.println();
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    // Retrieved previously stored entries from the database and create an ArrayList of objects.
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

                // Create a new SongFeatures object for each song retrieved.
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

    // Generate arff file to be used for classification of music mood using audio features.
    public void featureARFF(ArrayList<SongFeatures> features) throws Exception{
        FastVector fvClassVal = new FastVector(2);
        FastVector fvWekaAttributes = new FastVector(14); // Define the number of parameters used for classification.
        int i = 0;
        double[] values;

        // Set class attribute and labels
        fvClassVal.addElement("positive");
        fvClassVal.addElement("negative");
        Attribute classAttribute = new Attribute("@@class@@", fvClassVal);

        // Define the names of the attributes.
        fvWekaAttributes.addElement(new Attribute("duration"));
        fvWekaAttributes.addElement(new Attribute("danceability"));
        fvWekaAttributes.addElement(new Attribute("end_of_fade_in"));
        fvWekaAttributes.addElement(new Attribute("energy"));
        fvWekaAttributes.addElement(new Attribute("key"));
        fvWekaAttributes.addElement(new Attribute("key_confidence"));
        fvWekaAttributes.addElement(new Attribute("loudness"));
        fvWekaAttributes.addElement(new Attribute("mode"));
        fvWekaAttributes.addElement(new Attribute("mode_confidence"));
        fvWekaAttributes.addElement(new Attribute("song_hotness"));
        fvWekaAttributes.addElement(new Attribute("start_of_fade_out"));
        fvWekaAttributes.addElement(new Attribute("tempo"));
        fvWekaAttributes.addElement(new Attribute("time_signature"));
        fvWekaAttributes.addElement(classAttribute);

        Instances ts = new Instances("MyRel", fvWekaAttributes, 14);
        ts.setClassIndex(13);

        // Add each song represented by it's audio features to .arff file.
        for(SongFeatures song: features){
            values = new double[ts.numAttributes()];

            values[0] = song.getDuration();
            values[1] = song.getDanceability();
            values[2] = song.getEnd_of_fade_in();
            values[3] = song.getEnergy();
            values[4] = song.getKey();
            values[5] = song.getKey_confidence();
            values[6] = song.getLoudness();
            values[7] = song.getMode();
            values[8] = song.getMode_confidence();
            values[9] = song.getSong_hotness();
            values[10] = song.getStart_of_fade_out();
            values[11] = song.getTempo();
            values[12] = song.getTime_signature();

            ts.add(new Instance(1.0, values));
        }

        // Write to .arff.
        ArffSaver as = new ArffSaver();
        as.setInstances(ts);
        as.setFile(new File("feature.arff"));
        as.writeBatch();
    }
}
