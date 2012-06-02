package collabrite.appliance;

import java.io.IOException;

/**
 * Represents a data input
 *
 * @author anil
 */
public interface DataInput<T> extends MapBasedOptions {
    /**
     * Initialize the data input
     * @throws IOException
     */
    void initialize() throws IOException;
    
    /**
     * Open the data input
     * @return
     * @throws IOException
     */
    T open() throws IOException;

    /**
     * Clean up any state or resources
     * @throws IOException
     */
    void cleanUp() throws IOException;
}
