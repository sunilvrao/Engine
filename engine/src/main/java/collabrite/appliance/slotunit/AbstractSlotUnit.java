package collabrite.appliance.slotunit;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import collabrite.appliance.DataInput;
import collabrite.appliance.DataOutput;
import collabrite.appliance.MapBasedOptions;
import collabrite.appliance.SlotUnit;

public abstract class AbstractSlotUnit implements SlotUnit, MapBasedOptions {

    public static final String DELAY = "DELAY";

    private static final long serialVersionUID = 1L;

    protected Map<String, Object> options = new HashMap<String, Object>();
    protected boolean finishedExecutionFlag;

    protected DataInput<?> dataInput = null;
    protected DataOutput dataOutput = null;

    @Override
    public void setUp() throws IOException {
        if (dataInput != null) {
            dataInput.initialize();
        }
        if (dataOutput != null) {
            dataOutput.initialize();
        }
    }

    public abstract void execute();

    @Override
    public void tearDown() throws IOException {
        if (dataInput != null) {
            dataInput.cleanUp();
        }
        if (dataOutput != null) {
            dataOutput.cleanUp();
        }
    }

    @Override
    public void setDataInput(DataInput<?> dataInput) {
        this.dataInput = dataInput;
    }

    @Override
    public DataInput<?> getDataInput() {
        return this.dataInput;
    }

    @Override
    public void setDataOutput(DataOutput dataOutput) {
        this.dataOutput = dataOutput;
    }

    @Override
    public DataOutput getDataOutput() {
        return this.dataOutput;
    }

    @Override
    public boolean finishedExecution() {
        return finishedExecutionFlag;
    }

    @Override
    public void addOption(String key, Object value) {
        options.put(key, value);
    }

    @Override
    public boolean removeOption(String key) {
        try {
            options.remove(key);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void setOptions(Map<String, Object> theMap) {
        options.putAll(theMap);
    }

    @Override
    public Map<String, Object> options() {
        return Collections.unmodifiableMap(options);
    }
}