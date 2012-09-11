package collabrite.appliance.slot.out;

import java.io.IOException;
import java.io.PrintStream;

import collabrite.appliance.DataOutput;

/**
 * An implementation of {@link DataOutput} that just spits the byte[] into System.out
 *
 * @author anil
 */
public class SystemOutDataOutput extends AbstractDataOutput implements DataOutput {
    private PrintStream out = System.out;

    @Override
    public void store(Object data) throws IOException {
        if (data instanceof byte[]) {
            out.write((byte[]) data);
        } else if (data instanceof String) {
            out.write(((String) data).getBytes());
        }
    }

    @Override
    public void initialize() throws IOException {
    }

    @Override
    public void cleanUp() throws IOException {
        out = null;
    }
}