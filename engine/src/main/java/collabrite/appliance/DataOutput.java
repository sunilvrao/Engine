package collabrite.appliance;

import java.io.IOException;

/**
 * Interface for Data Output
 *
 * @author anil
 */
public interface DataOutput extends MapBasedOptions {
    /**
     * Initialize the data output
     * @throws IOException
     */
    void initialize() throws IOException;
    /**
     * Store the data
     * @param data
     * @throws IOException
     */
    void store(Object data) throws IOException;
    /**
     * Clean up any state or resources
     * @throws IOException
     */
    void cleanUp() throws IOException;
}