package collabrite.test.nlp;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import collabrite.nlp.sentiment.Lexicon;

/**
 * Unit Test the {@link Lexicon}
 * @author anil
 */
public class LexiconUnitTestCase {

    @Test
    public void testLexicon() throws Exception{
        Lexicon lex = new Lexicon();
        assertTrue(lex.isPositiveWord("good"));
        assertTrue(lex.isPositiveWord("Good"));
        assertTrue(lex.isPositiveWord("GOOD"));
        
        assertFalse(lex.isNegativeWord("good"));
        assertFalse(lex.isNegativeWord("Good"));
        assertFalse(lex.isNegativeWord("GOOD"));
        
        assertTrue(lex.isNegativeWord("bad"));
        assertTrue(lex.isNegativeWord("dirty"));
        assertTrue(lex.isNegativeWord("awful"));
    }
}