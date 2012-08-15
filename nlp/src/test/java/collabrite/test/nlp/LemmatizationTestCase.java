package collabrite.test.nlp;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import collabrite.nlp.sentiment.Lemmatization;

/**
 * Unit test the {@link Lemmatization}
 * @author anil
 */
public class LemmatizationTestCase {
    
    @Test
    public void testLemmatization() throws Exception{
        Lemmatization lem = new Lemmatization();
        assertEquals("match", lem.lemmatize("matches"));
        assertEquals("buzz", lem.lemmatize("buzzes"));
        assertEquals("rebel", lem.lemmatize("rebellion"));
        assertEquals("order", lem.lemmatize("orderly"));
    }

}