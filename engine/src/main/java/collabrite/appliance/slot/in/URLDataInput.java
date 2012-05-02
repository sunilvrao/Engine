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

    @Override
    public InputStream open() throws IOException {
        String url = (String) options.get(ApplianceConstants.URL);
        if (url == null || url.isEmpty())
            throw new IllegalArgumentException("URL is empty");
        URL theURL = new URL(url);
        return theURL.openStream();
    }
}