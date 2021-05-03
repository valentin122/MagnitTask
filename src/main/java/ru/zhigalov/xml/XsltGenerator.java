package ru.zhigalov.xml;

import java.io.*;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.IOException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/**
 * Created by zhigalov
 * @since 18.11.2018
 */
class XsltGenerator {

    void generateXmlWithUseXslt(String pathInput, String pathOutput) throws IOException, TransformerException, SAXException, ParserConfigurationException {
        File stylesheet = new File("Change.xslt");
        File datafile = new File(pathInput);

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder2 = factory.newDocumentBuilder();
        Document document2 = builder2.parse(datafile);

        TransformerFactory tFactory = TransformerFactory.newInstance();
        StreamSource styleSource = new StreamSource(stylesheet);

        Transformer transformer = tFactory.newTransformer(styleSource);

        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount","2");
        transformer.setOutputProperty(OutputKeys.STANDALONE,"yes");

        DOMSource source = new DOMSource(document2);
        StreamResult result = new StreamResult(System.out);

        transformer.transform(source, result);

        transformer.transform(source, new StreamResult(new FileOutputStream(pathOutput)));
    }
}