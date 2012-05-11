package collabrite.test.nlp;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import collabrite.nlp.sentiment.SentimentAnalysis;
import collabrite.nlp.sentiment.SentimentAnalysis.Sentiment;

/**
 * {@link SentimentAnalysis} unit test case
 *
 * @author anil
 */
public class SentimentAnalysisTestCase {
    private String iphonePositive = "iPhone is marvelous. I like its design. Best on the planet.";
    private String winNegative = "My Windows Machine keeps crashing. I hate it. Give me something I can love";

    @Test
    public void testSentiments() throws Exception {
        SentimentAnalysis sa = new SentimentAnalysis();
        Sentiment positive = sa.analyse(iphonePositive);
        assertTrue(positive.getPositiveCount() > positive.getNegativeCount());
        System.out.println("Positive=" + positive);

        Sentiment negative = sa.analyse(winNegative);
        assertTrue(negative.getPositiveCount() < negative.getNegativeCount());
        System.out.println("Negative=" + negative);
    }
}