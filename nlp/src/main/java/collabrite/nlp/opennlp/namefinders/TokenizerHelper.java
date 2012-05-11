package collabrite.nlp.opennlp.namefinders;

import java.io.IOException;
import java.io.InputStream;

import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

/**
 * Tokenize sentences
 *
 * @author anil
 */
public class TokenizerHelper {
    protected Tokenizer tokenizer = null;
    protected TokenizerModel model = null;

    public TokenizerHelper() {
        InputStream is = getClass().getClassLoader().getResourceAsStream("opennlp/models/namefinder/en-token.bin");
        if (is == null)
            throw new IllegalStateException("Unable to load NameFinder Model en-token.bin");

        try {
            model = new TokenizerModel(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (is != null) {
                safeClose(is);
            }
        }
        tokenizer = new TokenizerME(model);
    }

    public void tearDown() {
        model = null;
    }

    /**
     * Given a sentence, break into tokens
     *
     * @param sentence
     * @return
     */
    public String[] load(String sentence) {
        return tokenizer.tokenize(sentence);
    }

    private void safeClose(InputStream is) {
        try {
            is.close();
        } catch (IOException e) {
        }
    }
}