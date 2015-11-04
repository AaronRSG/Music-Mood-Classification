import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Stopwords {
    public ArrayList<String> loadWords(String file) {
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
        ArrayList<String> stopWords = loadWords("C:\\Users\\Aaron\\Documents\\Final Year\\FinalYearProject\\Music-Mood-Classification\\Stopwords.txt");
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
}
