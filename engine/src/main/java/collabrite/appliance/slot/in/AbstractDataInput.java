package collabrite.appliance.slot.in;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import collabrite.appliance.DataInput;

/**
 * Base class for all {@link DataInput}
 *
 * @author anil
 */
public abstract class AbstractDataInput<T> implements DataInput<T> {

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

    @Override
    public abstract T open() throws IOException;
}