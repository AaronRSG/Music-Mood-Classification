/**
 * Created with IntelliJ IDEA.
 * User: Aaron
 * Date: 21/10/15
 * Time: 13:30
 * To change this template use File | Settings | File Templates.
 */
public class SongLyrics {
    private String track_id;
    private String tag;
    private String score;
    private String mood;
    private String lyrics;
    private String quadrant;

    public String toString(){
        return "[ track_id = " + track_id +
                ", tag = " + tag +
                ", score = " + score +
                ", mood = " + mood +
                ", lyrics = " + lyrics;
    }

    public String getTrack_id(){
        return track_id;
    }
    public String getTag(){
        return tag;
    }
    public String getScore(){
        return score;
    }
    public String getMood(){
        return mood;
    }
    public String getLyrics(){
        return lyrics;
    }
    public String getQuadrant(){
        return quadrant;
    }
    public void setQuadrant(String q){
        this.quadrant = q;
    }

    public SongLyrics(String track_id, String tag, String score,
                      String mood, String lyrics){
        super();
        this.track_id = track_id;
        this.tag = tag;
        this.score = score;
        this.mood = mood;
        this.lyrics = lyrics;
        this.quadrant = "";
    }
}
