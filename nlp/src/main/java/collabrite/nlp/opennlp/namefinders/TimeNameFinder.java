package collabrite.nlp.opennlp.namefinders;

import java.io.IOException;
import java.io.InputStream;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.util.Span;


public class TimeNameFinder{
    TokenNameFinderModel model = null;
    NameFinderME nameFinder = null;

    public TimeNameFinder(){
        setUp();
        execute();
    }
     
    public void setUp() {
        InputStream is = getClass().getClassLoader().getResourceAsStream("opennlp/models/namefinder/en-ner-time.bin");
        if(is == null)
            throw new IllegalStateException("Unable to load NameFinder Model en-ner-time.bin");
        
        try {
            model = new TokenNameFinderModel(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        finally{
            if(is != null){
                safeClose(is);
            }
        }
    }
 
    public void execute() {
        nameFinder = new NameFinderME(model);
    }

    public void tearDown() { 
        model = null;
        nameFinder.clearAdaptiveData();
    }
    
    public Span[] load(String[] sentence){
            return nameFinder.find(sentence);
    }

    private void safeClose(InputStream is){
        try {
            is.close();
        } catch (IOException e) {
        }
    }
}