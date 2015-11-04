import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Aaron
 * Date: 03/11/15
 * Time: 18:38
 * To change this template use File | Settings | File Templates.
 */
public class Stopwords {
    protected Set<String> words = new HashSet<String>();
    protected static Stopwords stopwords;
    static {
        if(stopwords == null){
            stopwords = new Stopwords();
        }
    }

    public void addWord(String word){
        if(word.trim().length() > 0){
            words.add(word.trim().toLowerCase());
        }
    }

    public Stopwords(){
        words = new HashSet();
        try{
            FileReader fr = new FileReader("C:\\Users\\Aaron\\Documents\\Final Year\\FinalYearProject\\Music-Mood-Classification\\Stopwords.txt");
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null){
                line = line.trim();
                addWord(line);
            }
            addWord("Hello");
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
