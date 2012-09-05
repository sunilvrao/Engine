package collabrite.voice;

import java.net.URL;

import edu.cmu.sphinx.frontend.util.AudioFileDataSource;
import edu.cmu.sphinx.recognizer.Recognizer;
import edu.cmu.sphinx.result.Result;
import edu.cmu.sphinx.util.props.ConfigurationManager;

public class Transcriber {

    public Transcriber(){
        
    }
    
    public void transcribe(String wavFileName, String configFile){
        URL audioURL = getClass().getClassLoader().getResource(wavFileName);
        URL configURL = getClass().getClassLoader().getResource(configFile);
        ConfigurationManager cm = new ConfigurationManager(configURL);
        Recognizer recognizer = (Recognizer) cm.lookup("recognizer");

        /* allocate the resource necessary for the recognizer */
        recognizer.allocate();

        // configure the audio input for the recognizer
        AudioFileDataSource dataSource = (AudioFileDataSource) cm.lookup("audioFileDataSource");
        dataSource.setAudioFile(audioURL, null);

        // Loop until last utterance in the audio file has been decoded, in which case the recognizer will return null.
        Result result;
        while ((result = recognizer.recognize())!= null) {

                String resultText = result.getBestResultNoFiller();
                System.out.println(resultText);
        }
    }
    
}