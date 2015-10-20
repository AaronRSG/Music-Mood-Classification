/**
 * Created with IntelliJ IDEA.
 * User: Aaron
 * Date: 20/10/15
 * Time: 11:03
 * To change this template use File | Settings | File Templates.
 */
public class SongDetails {
    private String artist;
    private String title;

    public String toString(){
        return "[ artist = " + artist +
                ", title = " + title + " ]";
    }

    public String getArtist(){
        return artist;
    }

    public String getTitle(){
        return title;
    }

    public SongDetails(String artist, String title){
        super();
        this.artist = artist;
        this.title = title;
    }
}
