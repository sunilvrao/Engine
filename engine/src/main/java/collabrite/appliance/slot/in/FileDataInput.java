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
    @Override
    public InputStream open() throws IOException {
        String fileName = (String) options.get(ApplianceConstants.NAME);
        if (fileName == null || fileName.isEmpty())
            throw new IllegalArgumentException("Name is empty");
        File theFile = new File(fileName);
        if(theFile.exists() == false){
            return getClass().getClassLoader().getResourceAsStream(fileName);
        }
        return new FileInputStream(new File(fileName));
    }
}