package ru.zhigalov.service.xml;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Created by Zhigalov
 * @since 18.11.2018
 */
public class XmlFileParser {
    private static final Logger LOG = LogManager.getLogger(XmlFileParser.class.getName());

    ArrayList parseXmlFileToArrayList(String inputPath)  {
        ArrayList<Integer> arrayAfterParse = new ArrayList();

        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLStreamReader parser = null;
        try {
            parser = factory.createXMLStreamReader(
                    new BufferedInputStream(new FileInputStream(inputPath)));
        } catch (FileNotFoundException | XMLStreamException e) {
            LOG.error("Can't find file to parsing", e);
        }
        try {
            while (parser.hasNext()) {
                int event = parser.next();
                if (event == XMLStreamConstants.START_ELEMENT) {
                    if (parser.getLocalName().equals("entry")) {
                        String intValueInAttribute = parser.getAttributeValue(null, "field");
                        if (intValueInAttribute != null) {
                            arrayAfterParse.add(Integer.parseInt(intValueInAttribute));
                        }
                    }
                }
            }
        } catch (XMLStreamException e) {
            LOG.error("Can't parse file", e);
        }
        return arrayAfterParse;
    }
}