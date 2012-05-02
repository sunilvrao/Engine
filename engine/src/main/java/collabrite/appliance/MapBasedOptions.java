package collabrite.appliance;

import java.util.Map;

/**
 * An interface to represent options
 *
 * @author anil
 */
public interface MapBasedOptions {
    /**
     * Add an Option
     *
     * @param key
     * @param value
     */
    void addOption(String key, Object value);

    /**
     * Remove an Option
     *
     * @param key
     * @return
     */
    boolean removeOption(String key);

    /**
     * Given a {@link Map}, load all data
     *
     * @param theMap
     */
    void loadAllOptions(Map<String, Object> theMap);

    /**
     * Get the Options as a read only {@link Map}
     *
     * @return
     */
    Map<String, Object> options();
}