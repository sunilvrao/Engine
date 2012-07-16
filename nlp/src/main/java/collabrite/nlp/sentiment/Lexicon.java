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
 * Represents a collection of positive and negative words
 * @author anil
 */
public class Lexicon {
    protected List<String> positives = new ArrayList<String>();
    protected List<String> negatives = new ArrayList<String>();

    public Lexicon() throws IOException {
        load();
    }

    public boolean isPositiveWord(String word) {
        return positives.contains(word.toLowerCase());
    }

    public boolean isNegativeWord(String word) {
        return negatives.contains(word.toLowerCase());
    }

    private void load() throws IOException {
        InputStream is = null;
        DataInputStream in = null;
        BufferedReader br = null;
        try {
            is = getClass().getClassLoader().getResourceAsStream("lexicon/positive-words.txt");
            if (is == null)
                throw new RuntimeException("InputStream null");

            in = new DataInputStream(is);
            br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            // Read File Line By Line
            while ((strLine = br.readLine()) != null) {
                strLine = strLine.trim();
                if (strLine != null && strLine.length() > 0 && !strLine.startsWith(";")) {
                    positives.add(strLine.toLowerCase());
                }
            }
        } finally {
            safeClose(br);
            safeClose(in);
            safeClose(is);
        }
        try {
            is = getClass().getClassLoader().getResourceAsStream("lexicon/negative-words.txt");
            if (is == null)
                throw new RuntimeException("InputStream null");

            in = new DataInputStream(is);
            br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            // Read File Line By Line
            while ((strLine = br.readLine()) != null) {
                strLine = strLine.trim();
                if (strLine != null && strLine.length() > 0 && !strLine.startsWith(";")) {
                    negatives.add(strLine.toLowerCase());
                }
            }
        } finally {
            safeClose(br);
            safeClose(in);
            safeClose(is);
        }

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