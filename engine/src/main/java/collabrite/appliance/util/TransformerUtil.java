package collabrite.appliance.util;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.stax.StAXSource;

import collabrite.appliance.ex.ConfigurationException;
import collabrite.appliance.ex.ParsingException;

/**
 * Utility to deal with JAXP Transformer
 *
 * @author anil
 */
public class TransformerUtil {
    /**
     * Get the Default Transformer
     *
     * @return
     * @throws ConfigurationException
     */
    public static Transformer getTransformer() throws ConfigurationException {
        Transformer transformer;
        try {
            transformer = TransformerFactory.newInstance().newTransformer();
        } catch (TransformerConfigurationException e) {
            throw new ConfigurationException(e);
        } catch (TransformerFactoryConfigurationError e) {
            throw new ConfigurationException(e);
        }
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        transformer.setOutputProperty(OutputKeys.INDENT, "no");
        return transformer;
    }

    /**
     * Use the transformer to transform
     *
     * @param transformer
     * @param stax
     * @param result
     * @throws ParsingException
     */
    public static void transform(Transformer transformer, StAXSource stax, DOMResult result) throws ParsingException {
        try {
            transformer.transform(stax, result);
        } catch (TransformerException e) {
            throw new ParsingException(e);
        }
    }
}