package collabrite.appliance;

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
    void setUp();

    /**
     * Execute Operations
     */
    void execute();

    /**
     * Tear down/Finish
     */
    void tearDown();
}