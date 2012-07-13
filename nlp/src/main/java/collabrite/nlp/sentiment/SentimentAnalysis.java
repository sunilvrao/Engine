package collabrite.nlp.sentiment;

import java.io.IOException;

import collabrite.nlp.opennlp.namefinders.SentenceDetectorHelper;
import collabrite.nlp.opennlp.namefinders.TokenizerHelper;

/**
 * Class that can peform Sentiment Analysis
 *
 * @author anil
 */
public class SentimentAnalysis {

    protected Lexicon lexicon;

    public SentimentAnalysis() {
        try {
            lexicon = new Lexicon();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Given a text, determine if the sentiment is positive
     *
     * @param text
     * @return
     */
    public Sentiment analyse(String text) {
        Sentiment sentiment = new Sentiment();

        SentenceDetectorHelper sentenceHelper = new SentenceDetectorHelper();
        TokenizerHelper tokenizerHelper = new TokenizerHelper();

        String[] sentences = sentenceHelper.load(text);
        for (String sentence : sentences) {
            String[] tokens = tokenizerHelper.load(sentence);
            for (String token : tokens) {
                token = token.trim();
                if (lexicon.isPositiveWord(token)) {
                    sentiment.positiveCount++;
                } else if (lexicon.isNegativeWord(token)) {
                    sentiment.negativeCount++;
                }
            }
        }
        return sentiment;
    }

    public class Sentiment {
        int positiveCount = 0;
        int negativeCount = 0;

        public int getPositiveCount() {
            return positiveCount;
        }

        public int getNegativeCount() {
            return negativeCount;
        }

        public boolean overallPositive() {
            return positiveCount > negativeCount;
        }

        public boolean overallNegative() {
            return negativeCount > positiveCount;
        }
        
        @Override
        public String toString() {
            return "Sentiment [positiveCount=" + positiveCount + ", negativeCount=" + negativeCount + "]";
        }
    }
}