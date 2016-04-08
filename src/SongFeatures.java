public class SongFeatures {
    private String track_id;
    private String artist;
    private String title;
    private double duration;
    private double danceability;
    private double end_of_fade_in;
    private double energy;
    private double key;
    private double key_confidence;
    private double loudness;
    private double mode;
    private double mode_confidence;
    private double song_hotness;
    private double start_of_fade_out;
    private double tempo;
    private double time_signature;

    public String toString(){
        return "[ track_id = " + track_id +
                ", artist = " + artist +
                ", title = " + title +
                ", duration = " + duration +
                ", danceability = " + danceability +
                ", end_of_fade_in = " + end_of_fade_in +
                ", energy = " + energy +
                ", key = " + key +
                ", key_confidence = " + key_confidence +
                ", loudness = " + loudness +
                ", mode = " + mode +
                ", mode_confidence = " + mode_confidence +
                ", song_hotness = " + song_hotness +
                ", start_of_fade_out = " + start_of_fade_out +
                ", tempo = " + tempo +
                ", time_signature = " + time_signature + " ]";
    }

    public String getTrack_id(){
        return track_id;
    }
    public String getArtist(){
        return artist;
    }
    public String getTitle(){
        return title;
    }
    public double getDuration(){
        return duration;
    }
    public double getDanceability(){
        return danceability;
    }
    public double getEnd_of_fade_in(){
        return end_of_fade_in;
    }
    public double getEnergy(){
        return energy;
    }
    public double getKey(){
        return key;
    }
    public double getKey_confidence(){
        return key_confidence;
    }
    public double getLoudness(){
        return loudness;
    }
    public double getMode(){
        return mode;
    }
    public double getMode_confidence(){
        return mode_confidence;
    }
    public double getSong_hotness(){
        return song_hotness;
    }
    public double getStart_of_fade_out(){
        return start_of_fade_out;
    }
    public double getTempo(){
        return tempo;
    }
    public double getTime_signature(){
        return time_signature;
    }

    public SongFeatures(String track_id, String artist, String title,
                        double duration, double danceability, double end_of_fade_in,
                        double energy, double key, double key_confidence,
                        double loudness, double mode, double mode_confidence,
                        double song_hotness, double start_of_fade_out, double tempo,
                        double time_signature){

        super();
        this.track_id = track_id;
        this.artist = artist;
        this.title = title;
        this.duration = duration;
        this.danceability = danceability;
        this.end_of_fade_in = end_of_fade_in;
        this.energy = energy;
        this.key = key;
        this.key_confidence = key_confidence;
        this.loudness = loudness;
        this.mode = mode;
        this.mode_confidence = mode_confidence;
        this.song_hotness = song_hotness;
        this.start_of_fade_out = start_of_fade_out;
        this.tempo = tempo;
        this.time_signature = time_signature;
    }
}
