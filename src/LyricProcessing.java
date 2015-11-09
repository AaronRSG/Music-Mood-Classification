import java.util.ArrayList;

public class LyricProcessing {
    public ArrayList<SongLyrics> ProcessLyrics(){
        Lyrics l = new Lyrics();
        Stopwords st = new Stopwords();
        ArrayList<SongLyrics> dataSet = l.buildDataSet();
        for(SongLyrics song: dataSet){
            String mood = song.getMood();
            String quadrant = song.getQuadrant();
            String lyrics = song.getLyrics();
            if(mood.equalsIgnoreCase("G1") || mood.equalsIgnoreCase("G2") ||
                    mood.equalsIgnoreCase("G5") || mood.equalsIgnoreCase("G6") ||
                    mood.equalsIgnoreCase("G7") || mood.equalsIgnoreCase("G9")) quadrant = "v+a+";
            if(mood.equalsIgnoreCase("G8") || mood.equalsIgnoreCase("G11") ||
                    mood.equalsIgnoreCase("G12") || mood.equalsIgnoreCase("G14") ||
                    mood.equalsIgnoreCase("G32")) quadrant = "v+a-";
            if(mood.equalsIgnoreCase("G25") || mood.equalsIgnoreCase("G28") ||
                    mood.equalsIgnoreCase("G29")) quadrant = "v-a+";
            if(mood.equalsIgnoreCase("G15") || mood.equalsIgnoreCase("G16") ||
                    mood.equalsIgnoreCase("G17") || mood.equalsIgnoreCase("G31")) quadrant = "v-a-";
            song.setQuadrant(quadrant);
            String LyricTagsRemoved = st.removeLyricTags(lyrics);
            String PunctuationRemoved = LyricTagsRemoved.replaceAll("\\p{Punct}+", "");
            String StopWordsRemoved = st.removeWords(PunctuationRemoved);
            String stemmed = st.stemWords(StopWordsRemoved);
            song.setLyrics(stemmed);
            System.out.println(song.getLyrics());
        }
        return dataSet;
    }

}
