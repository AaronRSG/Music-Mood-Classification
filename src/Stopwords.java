import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Stopwords {
    String file = "Resources\\Stopwords.txt";

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

    public boolean isStopWord(String word){
        ArrayList<String> stopWords = loadWords();
        if(stopWords.contains(word.toLowerCase())) return true;
        else return false;
    }
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
