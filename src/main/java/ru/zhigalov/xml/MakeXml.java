package ru.zhigalov.xml;

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

public class MakeXml {

    public static void generateXml(ResultSet rs, String path) throws JAXBException, SQLException {
        final String COLUMN_FIELD = "field";

        Entries entries = new Entries();

        String name = "1.xml";
        String fullPath = path + name;

        while (rs.next()) {
            entries.addEntry(
                    new Entry(rs.getInt(COLUMN_FIELD)));
        }

        JAXBContext context = JAXBContext.newInstance(Entries.class);
        Marshaller jaxbMarshaller = context.createMarshaller();

        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        jaxbMarshaller.marshal(entries, new File(fullPath));
    }

    public void xsltTransform(final String path) {

        XsltGenerator xsltGenerator = new XsltGenerator();
        try {
            xsltGenerator.generateXmlWithUseXslt(path);
        } catch (IOException | SAXException | ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
    }

    public void xmlParserToArrayListAndSum(final String path) {
        XmlFileParser xmlFileParser = new XmlFileParser();
        ArrayList<Integer> arrayForSum = xmlFileParser.parseXmlFileToArrayList(path);
        System.out.println("Parse file to array success.");

        long sum = 0;
        for (int i : arrayForSum) {
            sum = sum + i;

        }
        long sum1 = sum;
        System.out.println(String.format("Sum is %s", sum1));

    }
}
