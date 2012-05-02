package collabrite.appliance.slot.in;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import collabrite.appliance.ApplianceConstants;
import collabrite.appliance.DataInput;

/**
 * Represents a {@link File} as a type of {@link DataInput}
 *
 * @author anil
 */
public class FileDataInput extends AbstractDataInput<FileInputStream> implements DataInput<FileInputStream> {
    @Override
    public FileInputStream open() throws IOException {
        String fileName = (String) options.get(ApplianceConstants.NAME);
        if (fileName == null || fileName.isEmpty())
            throw new IllegalArgumentException("Name is empty");
        return new FileInputStream(new File(fileName));
    }
}