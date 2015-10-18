import com.echonest.api.v4.*;

import java.util.List;

public class Features {
    private EchoNestAPI en;
    private String API_KEY = "LH8NKVEQPJK33XZDV";

    public Features()throws EchoNestException {
        en = new EchoNestAPI(API_KEY);
        en.setTraceSends(true);
        en.setTraceRecvs(false);
    }

    public void dumpSong(Song song) throws EchoNestException {
        System.out.println(song.getTitle());
        System.out.println("    Track ID : " + song.getID());
        System.out.println("    Artist : " + song.getArtistName());
        System.out.println("    Duration : " + song.getDuration());
        System.out.println("    Danceability : " + song.getDanceability());
        System.out.println("    End of Fade In : " + song.getAnalysis().getEndOfFadeIn());
        System.out.println("    Energy : " + song.getEnergy());
        System.out.println("    Key : " + song.getKey());
        System.out.println("    Key Confidence : " + song.getAnalysis().getKeyConfidence());
        System.out.println("    Loudness" + song.getLoudness());
        System.out.println("    Mode : " + song.getMode());
        System.out.println("    Mode Confidence : " + song.getAnalysis().getModeConfidence());
        System.out.println("    Song Hotness : " + song.getSongHotttnesss());
        System.out.println("    Start of Fade Out : " + song.getAnalysis().getStartOfFadeOut());
        System.out.println("    Tempo : " + song.getTempo());
        System.out.println("    Time Signature : " + song.getTimeSignature());


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
        for (Song song : songs){
            dumpSong(song);
            System.out.println();
        }
    }
}
