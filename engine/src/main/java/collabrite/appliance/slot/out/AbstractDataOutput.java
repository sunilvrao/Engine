package collabrite.appliance.slot.out;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import collabrite.appliance.DataInput;
import collabrite.appliance.DataOutput;

/**
 * Base class for all {@link DataInput}
 *
 * @author anil
 */
public abstract class AbstractDataOutput implements DataOutput {

    protected Map<String, Object> options = new HashMap<String, Object>();

    @Override
    public void addOption(String key, Object value) {
        options.put(key, value);
    }

    @Override
    public boolean removeOption(String key) {
        return options.remove(key) != null;
    }

    @Override
    public void loadAllOptions(Map<String, Object> theMap) {
        options.putAll(theMap);
    }

    @Override
    public Map<String, Object> options() {
        return Collections.unmodifiableMap(options);
    }
}