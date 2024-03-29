package collabrite.nlp.opennlp.namefinders;

import java.io.IOException;
import java.io.InputStream;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.util.Span;

/**
 * Base class for NameFinder modules
 *
 * @author anil
 */
public abstract class AbstractNameFinder {
    protected TokenNameFinderModel model = null;
    protected NameFinderME nameFinder = null;

    public AbstractNameFinder() {
        String modelName = getModelName();
        InputStream is = getClass().getClassLoader().getResourceAsStream(modelName);
        if (is == null)
            throw new IllegalStateException("Unable to load NameFinder Model:" + modelName);

        try {
            model = new TokenNameFinderModel(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (is != null) {
                safeClose(is);
            }
        }
        nameFinder = new NameFinderME(model);
    }

    /**
     * Clean up
     */
    public void tearDown() {
        model = null;
        nameFinder.clearAdaptiveData();
    }

    /**
     * Given a set of sentences, detect names
     *
     * @param sentence
     * @return
     */
    public Span[] load(String[] sentence) {
        return nameFinder.find(sentence);
    }

    /**
     * Return the name of the model file that the module should load
     *
     * @return
     */
    protected abstract String getModelName();

    protected void safeClose(InputStream is) {
        try {
            is.close();
        } catch (IOException e) {
        }
    }
}