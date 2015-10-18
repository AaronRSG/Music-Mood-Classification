import com.echonest.api.v4.EchoNestAPI;
import com.echonest.api.v4.EchoNestException;
import com.echonest.api.v4.Song;
import com.echonest.api.v4.SongParams;

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
        System.out.printf("%s\n", song.getTitle());
        System.out.printf("   artist: %s\n", song.getArtistName());
        System.out.printf("   dur   : %.3f\n", song.getDuration());
        System.out.printf("   BPM   : %.3f\n", song.getTempo());
        System.out.printf("   Mode  : %d\n", song.getMode());
        System.out.printf("   S hot : %.3f\n", song.getSongHotttnesss());
        System.out.printf("   A hot : %.3f\n", song.getArtistHotttnesss());
        System.out.printf("   A fam : %.3f\n", song.getArtistFamiliarity());
        System.out.printf("   A loc : %s\n", song.getArtistLocation());

    }

    public void searchSongByArtist(String artist, int results)throws EchoNestException{
        SongParams p = new SongParams();
        p.setArtist(artist);
        p.includeAudioSummary();
        p.includeArtistHotttnesss();
        p.includeSongHotttnesss();
        p.includeArtistFamiliarity();
        p.includeArtistLocation();
        p.sortBy("song_hotttnesss", false);

        List<Song> songs = en.searchSongs(p);
        for (Song song : songs){
            dumpSong(song);
            System.out.println();
        }
    }
}
