package collabrite.appliance.slot.out;

import java.io.IOException;

import collabrite.appliance.DataTransform;

/**
 * Use a Database data output. This requires a {@link DataTransform}
 *
 * @author anil
 */
public class DBDataOutput extends AbstractDataOutput {

    @Override
    public void initialize() throws IOException {
        if (dataTransform == null)
            throw new IOException("Data Transform not available");
        this.initialized = true;
    }

    @Override
    public void store(Object data) throws IOException {
        if (!initialized) {
            initialize();
        }
        dataTransform.transform(data);
    }

    @Override
    public void cleanUp() throws IOException {
    }
}