import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Stopwords {
    String file = "Resources\\Stopwords.txt";  // File containing a list of well known stopwords.

    // Load stopwords from file and return as an ArrayList.
    public ArrayList<String> loadWords() {
        ArrayList<String> words = new ArrayList<String>();
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null){
                words.add(line);
            }
            br.close();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return words;
    }

    // Perform check to see if a term is contained in the list of stopwords.
    public boolean isStopWord(String word){
        ArrayList<String> stopWords = loadWords();
        if(stopWords.contains(word.toLowerCase())) return true;
        else return false;
    }
    // Remove any terms in the lyrics of a song if they are featured on the list of stopwords.
    public String removeWords(String lyrics){
        String result = "";
        String[] words = lyrics.split("\\s+");
        for(String w: words){
            if(isStopWord(w) || w.isEmpty()){
                continue;
            }else {
                result += w + " ";
            }
        }
        return result;
    }
    // Remove html tags and other unnecessary data in lyrics.
    public String removeLyricTags(String lyrics){
        String result = "";
        String words[] = lyrics.split("\\s+");
        for(String w: words){
            if(w.contains("<") && w.contains(">")) continue;
            if(w.contains("[") && w.contains("]")) continue;
            if(w.contains("2x") || w.contains("x2")) continue;
            if(w.contains("3x") || w.contains("x3")) continue;
            else result += w + " ";
        }
        return result;
    }
}
