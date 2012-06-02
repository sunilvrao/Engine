package collabrite.nlp.util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class FileUtils {

    public static BufferedReader getFileAsBufferedReader(InputStream is) throws IOException{
        DataInputStream in = new DataInputStream(is);
        return new BufferedReader(new InputStreamReader(in)); 
    }

    public static void safeClose(InputStream is) {
        if (is != null) {
            try {
                is.close();
            } catch (IOException e) {
            }
        }
    }

    public static void safeClose(Reader is) {
        if (is != null) {
            try {
                is.close();
            } catch (IOException e) {
            }
        }
    } 
}