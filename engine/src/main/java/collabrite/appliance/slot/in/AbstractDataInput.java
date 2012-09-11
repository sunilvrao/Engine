package collabrite.appliance.slot.in;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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

    public void setOptions(Map<String, Object> theMap) {
        options.putAll(theMap);
    }

    @Override
    public Map<String, Object> options() {
        return Collections.unmodifiableMap(options);
    }

    @Override
    public abstract T open() throws IOException;

    protected void safeClose(OutputStream fos) {
        if (fos != null) {
            try {
                fos.close();
            } catch (IOException e) {
            }
        }
    }

    protected void safeClose(InputStream is) {
        if (is != null) {
            try {
                is.close();
            } catch (IOException e) {
            }
        }
    }
}