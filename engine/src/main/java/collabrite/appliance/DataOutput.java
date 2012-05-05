package collabrite.appliance;

import java.io.IOException;

/**
 * Interface for Data Output
 *
 * @author anil
 */
public interface DataOutput extends MapBasedOptions {
    void store(Object data) throws IOException;
}