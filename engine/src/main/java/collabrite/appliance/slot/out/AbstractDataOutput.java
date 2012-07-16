package collabrite.appliance.slot.out;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import collabrite.appliance.DataInput;
import collabrite.appliance.DataOutput;
import collabrite.appliance.DataTransform;

/**
 * Base class for all {@link DataInput}
 *
 * @author anil
 */
public abstract class AbstractDataOutput implements DataOutput {
    
    protected DataTransform dataTransform = null;

    protected Map<String, Object> options = new HashMap<String, Object>();
    
    // Set the initialized flag
    protected boolean initialized = false;

    @Override
    public void addOption(String key, Object value) {
        options.put(key, value);
    }

    @Override
    public boolean removeOption(String key) {
        return options.remove(key) != null;
    } 

    /**
     * Set the data transform
     */
    public void setDataTransform(DataTransform dataTransform) {
        this.dataTransform = dataTransform;
    }

    @Override
    public void setOptions(Map<String, Object> theMap) {
        options.putAll(theMap);
    }

    @Override
    public Map<String, Object> options() {
        return Collections.unmodifiableMap(options);
    }
    
    protected void safeClose(OutputStream fos){
        if(fos != null){
            try {
                fos.close();
            } catch (IOException e) {
            }
        }
    }
    protected void safeClose(InputStream is){
        if(is != null){
            try {
                is.close();
            } catch (IOException e) {
            }
        }
    }
}