package collabrite.appliance.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPathException;

import org.w3c.dom.DOMConfiguration;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import collabrite.appliance.ex.ConfigurationException;
import collabrite.appliance.ex.ParsingException;
import collabrite.appliance.ex.ProcessingException;

/**
 * Utility dealing with DOM
 *
 * @author Anil
 */
public class DocumentUtil {

    /**
     * Check whether a node belongs to a document
     *
     * @param doc
     * @param node
     * @return
     */
    public static boolean containsNode(Document doc, Node node) {
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element elem = (Element) node;
            NodeList nl = doc.getElementsByTagNameNS(elem.getNamespaceURI(), elem.getLocalName());
            if (nl != null && nl.getLength() > 0)
                return true;
            else
                return false;
        }
        throw new UnsupportedOperationException();
    }

    /**
     * Create a new document
     *
     * @return
     * @throws ParserConfigurationException
     */
    public static Document createDocument() throws ConfigurationException {
        DocumentBuilderFactory factory = getDocumentBuilderFactory();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new ConfigurationException(e);
        }
        return builder.newDocument();
    }

    /**
     * Create a document with the root element of the form &lt;someElement xmlns="customNamespace"
     *
     * @param baseNamespace
     * @return
     * @throws ProcessingException
     */
    public static Document createDocumentWithBaseNamespace(String baseNamespace, String localPart) throws ProcessingException {
        try {
            DocumentBuilderFactory factory = getDocumentBuilderFactory();
            DocumentBuilder builder = factory.newDocumentBuilder();
            return builder.getDOMImplementation().createDocument(baseNamespace, localPart, null);
        } catch (DOMException e) {
            throw new ProcessingException(e);
        } catch (ParserConfigurationException e) {
            throw new ProcessingException(e);
        }
    }

    /**
     * Parse a document from the string
     *
     * @param docString
     * @return
     * @throws IOException
     * @throws SAXException
     * @throws ParserConfigurationException
     */
    public static Document getDocument(String docString) throws ConfigurationException, ParsingException, ProcessingException {
        return getDocument(new StringReader(docString));
    }

    /**
     * Parse a document from a reader
     *
     * @param reader
     * @return
     * @throws ParsingException
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     */
    public static Document getDocument(Reader reader) throws ConfigurationException, ProcessingException, ParsingException {
        try {
            DocumentBuilderFactory factory = getDocumentBuilderFactory();
            DocumentBuilder builder = factory.newDocumentBuilder();
            return builder.parse(new InputSource(reader));
        } catch (ParserConfigurationException e) {
            throw new ConfigurationException(e);
        } catch (SAXException e) {
            throw new ParsingException(e);
        } catch (IOException e) {
            throw new ProcessingException(e);
        }
    }

    /**
     * Get Document from a file
     *
     * @param file
     * @return
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     */
    public static Document getDocument(File file) throws ConfigurationException, ProcessingException, ParsingException {
        DocumentBuilderFactory factory = getDocumentBuilderFactory();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            return builder.parse(file);
        } catch (ParserConfigurationException e) {
            throw new ConfigurationException(e);
        } catch (SAXException e) {
            throw new ParsingException(e);
        } catch (IOException e) {
            throw new ProcessingException(e);
        }
    }

    /**
     * Get Document from an inputstream
     *
     * @param is
     * @return
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     */
    public static Document getDocument(InputStream is) throws ConfigurationException, ProcessingException, ParsingException {
        DocumentBuilderFactory factory = getDocumentBuilderFactory();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            return builder.parse(is);
        } catch (ParserConfigurationException e) {
            throw new ConfigurationException(e);
        } catch (SAXException e) {
            throw new ParsingException(e);
        } catch (IOException e) {
            throw new ProcessingException(e);
        }
    }

    /**
     * Marshall a document into a String
     *
     * @param signedDoc
     * @return
     * @throws TransformerFactoryConfigurationError
     * @throws TransformerException
     */
    public static String getDocumentAsString(Document signedDoc) throws ProcessingException, ConfigurationException {
        Source source = new DOMSource(signedDoc);
        StringWriter sw = new StringWriter();

        Result streamResult = new StreamResult(sw);
        // Write the DOM document to the stream
        Transformer xformer = TransformerUtil.getTransformer();
        try {
            xformer.transform(source, streamResult);
        } catch (TransformerException e) {
            throw new ProcessingException(e);
        }

        return sw.toString();
    }

    /**
     * Marshall a DOM Element as string
     *
     * @param element
     * @return
     * @throws TransformerFactoryConfigurationError
     * @throws TransformerException
     */
    public static String getDOMElementAsString(Element element) throws ProcessingException, ConfigurationException {
        Source source = new DOMSource(element);
        StringWriter sw = new StringWriter();

        Result streamResult = new StreamResult(sw);
        // Write the DOM document to the file
        Transformer xformer = TransformerUtil.getTransformer();
        try {
            xformer.transform(source, streamResult);
        } catch (TransformerException e) {
            throw new ProcessingException(e);
        }

        return sw.toString();
    }

    /**
     * <p>
     * Get an element from the document given its {@link QName}
     * </p>
     * <p>
     * First an attempt to get the element based on its namespace is made, failing which an element with the localpart ignoring
     * any namespace is returned.
     * </p>
     *
     * @param doc
     * @param elementQName
     * @return
     */
    public static Element getElement(Document doc, QName elementQName) {
        NodeList nl = doc.getElementsByTagNameNS(elementQName.getNamespaceURI(), elementQName.getLocalPart());
        if (nl.getLength() == 0) {
            nl = doc.getElementsByTagNameNS("*", elementQName.getLocalPart());
            if (nl.getLength() == 0)
                nl = doc.getElementsByTagName(elementQName.getPrefix() + ":" + elementQName.getLocalPart());
            if (nl.getLength() == 0)
                return null;
        }
        return (Element) nl.item(0);
    }

    /**
     * Stream a DOM Node as an input stream
     *
     * @param node
     * @return
     * @throws TransformerFactoryConfigurationError
     * @throws TransformerException
     */
    public static InputStream getNodeAsStream(Node node) throws ConfigurationException, ProcessingException {
        return getSourceAsStream(new DOMSource(node));
    }

    /**
     * Get the {@link Source} as an {@link InputStream}
     *
     * @param source
     * @return
     * @throws ConfigurationException
     * @throws ProcessingException
     */
    public static InputStream getSourceAsStream(Source source) throws ConfigurationException, ProcessingException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Result streamResult = new StreamResult(baos);
        // Write the DOM document to the stream
        Transformer transformer = TransformerUtil.getTransformer();
        try {
            transformer.transform(source, streamResult);
        } catch (TransformerException e) {
            throw new ProcessingException(e);
        }

        return new ByteArrayInputStream(baos.toByteArray());
    }

    /**
     * Stream a DOM Node as a String
     *
     * @param node
     * @return
     * @throws ProcessingException
     * @throws TransformerFactoryConfigurationError
     * @throws TransformerException
     */
    public static String getNodeAsString(Node node) throws ConfigurationException, ProcessingException {
        Source source = new DOMSource(node);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        Result streamResult = new StreamResult(baos);
        // Write the DOM document to the stream
        Transformer transformer = TransformerUtil.getTransformer();
        try {
            transformer.transform(source, streamResult);
        } catch (TransformerException e) {
            throw new ProcessingException(e);
        }

        return new String(baos.toByteArray());
    }

    /**
     * Given a document, return a Node with the given node name and an attribute with a particular attribute value
     *
     * @param document
     * @param nsURI
     * @param nodeName
     * @param attributeName
     * @param attributeValue
     * @return
     * @throws XPathException
     * @throws TransformerFactoryConfigurationError
     * @throws TransformerException
     */
    public static Node getNodeWithAttribute(Document document, final String nsURI, String nodeName, String attributeName,
            String attributeValue) throws XPathException, TransformerFactoryConfigurationError, TransformerException {
        NodeList nl = document.getElementsByTagNameNS(nsURI, nodeName);
        int len = nl != null ? nl.getLength() : 0;

        for (int i = 0; i < len; i++) {
            Node n = nl.item(i);
            if (n.getNodeType() != Node.ELEMENT_NODE)
                continue;
            Element el = (Element) n;
            String attrValue = el.getAttributeNS(nsURI, attributeName);
            if (attributeValue.equals(attrValue))
                return el;
            // Take care of attributes with null NS
            attrValue = el.getAttribute(attributeName);
            if (attributeValue.equals(attrValue))
                return el;
        }
        return null;
    }

    /**
     * DOM3 method: Normalize the document with namespaces
     *
     * @param doc
     * @return
     */
    public static Document normalizeNamespaces(Document doc) {
        DOMConfiguration docConfig = doc.getDomConfig();
        docConfig.setParameter("namespaces", Boolean.TRUE);
        doc.normalizeDocument();
        return doc;
    }

    /**
     * Get a {@link Source} given a {@link Document}
     *
     * @param doc
     * @return
     */
    public static Source getXMLSource(Document doc) {
        return new DOMSource(doc);
    }

    /**
     * Get the document as a string while ignoring any exceptions
     *
     * @param doc
     * @return
     */
    public static String asString(Document doc) {
        String str = null;

        try {
            str = getDocumentAsString(doc);
        } catch (Exception ignore) {
        }
        return str;
    }

    /**
     * Log the nodes in the document
     *
     * @param doc
     */
    public static void logNodes(Document doc) {
        visit(doc, 0);
    }

    public static Node getNodeFromSource(Source source) throws ProcessingException, ConfigurationException {
        try {
            Transformer transformer = TransformerUtil.getTransformer();
            DOMResult result = new DOMResult();
            transformer.transform(source, result);
            return result.getNode();
        } catch (TransformerException te) {
            throw new ProcessingException(te);
        }
    }

    public static Document getDocumentFromSource(Source source) throws ProcessingException, ConfigurationException {
        try {
            Transformer transformer = TransformerUtil.getTransformer();
            DOMResult result = new DOMResult();
            transformer.transform(source, result);
            return (Document) result.getNode();
        } catch (TransformerException te) {
            throw new ProcessingException(te);
        }
    }

    private static void visit(Node node, int level) {
        // Visit each child
        NodeList list = node.getChildNodes();
        for (int i = 0; i < list.getLength(); i++) {
            // Get child node
            Node childNode = list.item(i);
            // Visit child node
            visit(childNode, level + 1);
        }
    }

    /**
     * Create a namespace aware Document builder factory
     *
     * @return
     */
    private static DocumentBuilderFactory getDocumentBuilderFactory() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        factory.setXIncludeAware(true);
        return factory;
    }
}