package collabrite.appliance.slot.out;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileDataOutput extends AbstractDataOutput {
    public static final String FILENAME = "fileName";

    protected File file = null;
    protected FileOutputStream fos = null;

    @Override
    public void store(Object data) throws IOException {
        if (data instanceof byte[]) {
            byte[] bytes = (byte[]) data;
            fos.write(bytes);
        } else if (data instanceof String) {
            String str = (String) data;
            fos.write(str.getBytes());
        } else
            throw new IOException(" Unknown type:" + data);
    }

    @Override
    public void initialize() throws IOException {
        if (file == null) {
            String filename = (String) options.get(FILENAME);
            if (filename == null || filename.isEmpty()) {
                throw new IOException("Cannot find option " + FILENAME);
            }
            file = new File(filename);
            fos = new FileOutputStream(file);
        }
    }

    @Override
    public void cleanUp() throws IOException {
        safeClose(fos);
    }
}