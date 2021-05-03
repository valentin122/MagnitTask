package ru.zhigalov.xml;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.xml.sax.SAXException;
import ru.zhigalov.entity.Entries;
import ru.zhigalov.entity.Entry;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MakeXml {
    private static final Logger LOG = LogManager.getLogger(MakeXml.class.getName());

    public void generateXml(ResultSet rs, String fullPath) throws JAXBException, SQLException {
        final String COLUMN_FIELD = "field";

        Entries entries = new Entries();

        while (rs.next()) {
            entries.addEntry(
                    new Entry(rs.getInt(COLUMN_FIELD)));
        }
        JAXBContext context = JAXBContext.newInstance(Entries.class);
        Marshaller jaxbMarshaller = context.createMarshaller();

        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        jaxbMarshaller.marshal(entries, new File(fullPath));
    }

    public void xsltTransform(String pathInput, String pathOutput) {
        XsltGenerator xsltGenerator = new XsltGenerator();
        try {
            xsltGenerator.generateXmlWithUseXslt(pathInput, pathOutput);
        } catch (IOException | SAXException | ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
    }

    public List<Integer> xmlParserToArrayList(final String path) {
        //TODO: make try catch
        XmlFileParser xmlFileParser = new XmlFileParser();
        ArrayList<Integer> arrayForSum = xmlFileParser.parseXmlFileToArrayList(path);
        LOG.info("Parse file to array success.");
        return arrayForSum;
    }
}
