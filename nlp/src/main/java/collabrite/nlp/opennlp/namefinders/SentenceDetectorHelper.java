package collabrite.nlp.opennlp.namefinders;

import java.io.IOException;
import java.io.InputStream;

import opennlp.tools.sentdetect.SentenceDetector;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;

public class SentenceDetectorHelper {
    protected SentenceModel model = null;
    protected SentenceDetector sentenceDetector = null;

    public SentenceDetectorHelper() {
        InputStream is = getClass().getClassLoader().getResourceAsStream("opennlp/models/namefinder/en-sent.bin");
        if (is == null)
            throw new IllegalStateException("Unable to load NameFinder Model en-sent.bin");

        try {
            model = new SentenceModel(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (is != null) {
                safeClose(is);
            }
        }
        sentenceDetector = new SentenceDetectorME(model);
    }

    public void tearDown() {
        model = null;
    }

    public String[] load(String text) {
        return sentenceDetector.sentDetect(text);
    }

    private void safeClose(InputStream is) {
        try {
            is.close();
        } catch (IOException e) {
        }
    }
}
