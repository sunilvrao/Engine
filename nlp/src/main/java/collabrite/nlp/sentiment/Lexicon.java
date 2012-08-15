package collabrite.nlp.sentiment;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a collection of positive,negative, uncertain, strong and weak words
 * @author anil
 */
public class Lexicon {
    protected List<String> positives = new ArrayList<String>();
    protected List<String> negatives = new ArrayList<String>();
    
    protected List<String> uncertainties = new ArrayList<String>();
    protected List<String> strongWords = new ArrayList<String>();
    protected List<String> weakWords = new ArrayList<String>();

    public Lexicon() throws IOException {
        load();
    }

    /**
     * Return if the word is a positive word
     * @param word
     * @return
     */
    public boolean isPositiveWord(String word) {
        return positives.contains(word.toLowerCase());
    }
    
    /**
     * Return if the word is a negative word
     * @param word
     * @return
     */
    public boolean isNegativeWord(String word) {
        return negatives.contains(word.toLowerCase());
    }
    /**
     * Return if the word is an uncertain word
     * @param word
     * @return
     */
    public boolean isUncertainWord(String word) {
        return uncertainties.contains(word.toLowerCase());
    }
    
    /**
     * Return if the word is a strong word
     * @param word
     * @return
     */
    public boolean isStrongWord(String word) {
        return strongWords.contains(word.toLowerCase());
    }
    
    /**
     * Return if the word is a weak word
     * @param word
     * @return
     */
    public boolean isWeakWord(String word) {
        return weakWords.contains(word.toLowerCase());
    }

    private void load() throws IOException {
        positives.addAll(loadFile("lexicon/positive-words.txt"));
        negatives.addAll(loadFile("lexicon/negative-words.txt"));
        uncertainties.addAll(loadFile("lexicon/uncertainty-words.txt"));
        strongWords.addAll(loadFile("lexicon/strong-words.txt"));
        weakWords.addAll(loadFile("lexicon/weak-words.txt"));
    }
    
    private List<String> loadFile(String fileName) throws IOException{
        InputStream is = null;
        DataInputStream in = null;
        BufferedReader br = null;
        List<String> result = new ArrayList<String>();
        
        try {
            is = getClass().getClassLoader().getResourceAsStream(fileName);
            if (is == null)
                throw new RuntimeException("InputStream null");

            in = new DataInputStream(is);
            br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            // Read File Line By Line
            while ((strLine = br.readLine()) != null) {
                strLine = strLine.trim();
                if (strLine != null && strLine.length() > 0 && !strLine.startsWith(";")) {
                    result.add(strLine.toLowerCase());
                }
            }
        } finally {
            safeClose(br);
            safeClose(in);
            safeClose(is);
        }
        return result;
    }

    private void safeClose(InputStream is) {
        if (is != null) {
            try {
                is.close();
            } catch (IOException e) {
            }
        }
    }

    private void safeClose(Reader is) {
        if (is != null) {
            try {
                is.close();
            } catch (IOException e) {
            }
        }
    }
}