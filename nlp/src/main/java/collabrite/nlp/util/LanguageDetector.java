package collabrite.nlp.util;

import org.apache.tika.language.LanguageIdentifier;

/**
 * Use Apache Tika to detect the language
 * of a text
 * @author anil
 */
public class LanguageDetector {
    
    protected String content = null;
    
    protected LanguageIdentifier identifier = null;

    public LanguageDetector(String content) {
        this.content = content;
        identifier = new LanguageIdentifier(content);
    }
    
    public String getLanguage(){
        return identifier.getLanguage();
    }
}