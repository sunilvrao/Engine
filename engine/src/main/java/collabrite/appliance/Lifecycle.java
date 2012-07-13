package collabrite.appliance;

import java.io.IOException;

/**
 * <p>
 * Stages of life
 * </p>
 *
 * @author anil
 */
public interface Lifecycle {
    /**
     * Initialize
     */
    void setUp() throws IOException;

    /**
     * Execute Operations
     */
    void execute();

    /**
     * Tear down/Finish
     */
    void tearDown() throws IOException;
}