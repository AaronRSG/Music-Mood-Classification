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
    public String removeLyricTags(String lyrics){
        String result = "";
        String words[] = lyrics.split("\\s+");
        for(String w: words){
            if(w.contains("<") && w.contains(">")) continue;
            if(w.contains("[") && w.contains("]")) continue;
            if(w.contains("2x") || w.contains("x2")) continue;
            else result += w + " ";
        }
        return result;
    }

    public String stemWords(String lyrics){
        String result = "";
        String[] words = lyrics.split("\\s+");
        Stemmer stemmer = new Stemmer();
        char[] char_arry;
        for(int i = 0; i < lyrics.length(); i++){
            for(String w: words){
                char_arry = w.toLowerCase().toCharArray();
                for (int j = 0; j < char_arry.length; j++){
                    stemmer.add(char_arry[j]);
                }
                stemmer.stem();
                result = stemmer.toString();
            }
        }
        return result;
    }
}
