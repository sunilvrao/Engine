package collabrite.appliance.slot.in;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import collabrite.appliance.ApplianceConstants;
import collabrite.appliance.DataInput;

/**
 * Represents a {@link File} as a type of {@link DataInput}
 *
 * @author anil
 */
public class FileDataInput extends AbstractDataInput<InputStream> implements DataInput<InputStream> {
    protected InputStream fis = null;
    @Override
    public InputStream open() throws IOException {
        return fis;
    }

    @Override
    public void initialize() throws IOException {
        if(fis == null){
            String fileName = (String) options.get(ApplianceConstants.NAME);
            if (fileName == null || fileName.isEmpty())
                throw new IllegalArgumentException("Name is empty");
            File theFile = new File(fileName);
            if(theFile.exists() == false){
                fis = getClass().getClassLoader().getResourceAsStream(fileName);
            } else {
                fis =  new FileInputStream(new File(fileName));   
            }
        }
    }

    @Override
    public void cleanUp() throws IOException {
        if(fis != null){
            safeClose(fis);
        }
    }
}