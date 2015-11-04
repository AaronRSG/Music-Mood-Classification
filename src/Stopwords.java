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
}
