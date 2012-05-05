package collabrite.test.appliance;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import collabrite.appliance.DataInput;
import collabrite.appliance.DataOutput;
import collabrite.appliance.SlotUnit;

/**
 * Test {@link SlotUnit}
 * @author anil
 */
public class TestSlotUnit implements SlotUnit {
    private static final long serialVersionUID = 1L;
    
    protected DataInput<?> input;
    protected DataOutput output;
    protected boolean finished = false;

    @Override
    public void setUp() { 
    }

    @Override
    public void execute() {
        try {            
            InputStream is = (InputStream) input.open();
            BufferedReader in = new BufferedReader(new InputStreamReader(is));
            String line = null;
            while((line = in.readLine()) != null) {
                output.store(line.getBytes());
            }
            if(is != null) is.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        finished = true;
    }

    @Override
    public void tearDown() {
    }

    @Override
    public void setDataInput(DataInput<?> dataInput) {
        input = dataInput;
    }

    @Override
    public DataInput<?> getDataInput() {
        return input;
    }

    @Override
    public void setDataOutput(DataOutput dataOutput) {
        output = dataOutput;
    }

    @Override
    public DataOutput getDataOutput() { 
        return output;
    }

    @Override
    public boolean finishedExecution() {
        return finished;
    }
}