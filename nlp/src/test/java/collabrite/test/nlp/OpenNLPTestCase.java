package collabrite.test.nlp;

import opennlp.tools.util.Span;

import org.junit.Test;

import collabrite.nlp.opennlp.namefinders.PersonNameFinder;
import collabrite.nlp.opennlp.namefinders.SentenceDetectorHelper;
import collabrite.nlp.opennlp.namefinders.TokenizerHelper;

/**
 * Some basic tests with OpenNLP
 *
 * @author anil
 */
public class OpenNLPTestCase {
    String text = "Anil S lives in Chicago. He is passionate about technology. Anil is friend of Sunil. Sunil lives in Barrington."
            + " They met George and Mike sometime ago.";

    @Test
    public void testNameFinder() throws Exception {

        PersonNameFinder pnf = new PersonNameFinder();

        SentenceDetectorHelper sentenceHelper = new SentenceDetectorHelper();
        TokenizerHelper tokenizerHelper = new TokenizerHelper();

        String[] sentences = sentenceHelper.load(text);
        for (String sentence : sentences) {
            System.out.println("Sentence=" + sentence);
            String[] tokens = tokenizerHelper.load(sentence);
            for (String token : tokens) {
                System.out.println("Token=" + token);
            }
            Span[] names = pnf.load(tokens);
            for (Span name : names) {
                System.out.println("Name=" + name);
            }
        }
        pnf.tearDown();
        sentenceHelper.tearDown();
        tokenizerHelper.tearDown();
    }
}