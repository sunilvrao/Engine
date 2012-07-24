package collabrite.test.nlp;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import collabrite.nlp.util.LanguageDetector;

/**
 * Unit test the {@link LanguageDetector}
 * @author anil
 */
public class LanguageDetectorTestCase {

    @Test
    public void testLanguage() throws Exception{
        LanguageDetector detector = new LanguageDetector("Chicago is the capital of Illinois. Anil lives here.");
        assertEquals("en", detector.getLanguage());
        
        detector = new LanguageDetector("Boisson merci");
        assertEquals("fi", detector.getLanguage()); 

        detector = new LanguageDetector("Guten Tag. Auf Wiedersehen.");
        assertEquals("de", detector.getLanguage());
        
        detector = new LanguageDetector("Buen provecho! Buenas tardes. Que tenga un buen día");
        assertEquals("es", detector.getLanguage());
    }
}