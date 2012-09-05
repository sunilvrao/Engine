package collabrite.test.voice;

import org.junit.Test;

import collabrite.voice.Transcriber;

/**
 * Unit Test the {@link Transcriber}
 * @author anil
 */
public class TranscriberTestCase {

    @Test
    public void testTranscribe() throws Exception{
        Transcriber transcriber = new Transcriber();
        transcriber.transcribe("transcriber/10001-90210-01803.wav", "transcriber/config.xml");
    }
}
