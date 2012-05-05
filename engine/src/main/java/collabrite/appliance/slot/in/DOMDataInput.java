package collabrite.appliance.slot.in;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.w3c.dom.Document;

import collabrite.appliance.ApplianceConstants;
import collabrite.appliance.DataInput;
import collabrite.appliance.util.DocumentUtil;

/**
 * An implementation of {@link DataInput} that takes in a xml file and returns a DOM representation.
 *
 * @author anil
 */
public class DOMDataInput extends AbstractDataInput<Document> implements DataInput<Document> {

    @Override
    public Document open() throws IOException {
        String fileName = (String) options.get(ApplianceConstants.NAME);
        if (fileName == null || fileName.isEmpty())
            throw new IllegalArgumentException("Name is empty");

        try {
            return DocumentUtil.getDocument(getFile(fileName));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    private InputStream getFile(String fileName) throws IOException{
        File file = new File(fileName);
        if(file.exists() == false)
            return getClass().getClassLoader().getResourceAsStream(fileName);
        return new FileInputStream(file);
    }
}