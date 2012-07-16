package collabrite.appliance;

import java.io.IOException;

import collabrite.appliance.ex.ProcessingException;

/**
 * Used for transforming data before storage
 * @author anil
 */
public interface DataTransform {
    /**
     * Take an input object and transform it
     * @param transform
     * @return
     * @throws ProcessingException
     */
    Object transform(Object transform) throws IOException;
}