package collabrite.appliance;

import java.io.IOException;

/**
 * Represents a data input
 *
 * @author anil
 */
public interface DataInput<T> extends MapBasedOptions {
    T open() throws IOException;
}
