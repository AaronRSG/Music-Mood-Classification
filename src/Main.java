import com.echonest.api.v4.EchoNestException;

public class Main {
    public static void main(String args[]){
//        Lyrics l = new Lyrics();
//        l.getLyrics();
//        l.getFullLyrics();

//        Tags t = new Tags();
//        t.getTags();
        try {
            Features f = new Features();
            f.searchSongByArtist("Nadine Renee", "Next Time", 5);
        } catch (EchoNestException e) {
            e.printStackTrace();
        }
    }
}
