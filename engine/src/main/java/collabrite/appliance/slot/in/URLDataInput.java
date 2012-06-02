package collabrite.appliance.slot.in;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import collabrite.appliance.ApplianceConstants;
import collabrite.appliance.DataInput;

/**
 * Represent a {@link URL} as a {@link DataInput}
 *
 * @author anil
 */
public class URLDataInput extends AbstractDataInput<InputStream> implements DataInput<InputStream> {

    protected URL theURL = null;
    @Override
    public InputStream open() throws IOException {
        return theURL.openStream();
    }

    @Override
    public void initialize() throws IOException {
        String url = (String) options.get(ApplianceConstants.URL);
        if (url == null || url.isEmpty())
            throw new IllegalArgumentException("URL is empty");
        theURL = new URL(url);
    }

    @Override
    public void cleanUp() throws IOException {
        theURL = null;
    }
}