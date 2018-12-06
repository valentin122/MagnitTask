package ru.zhigalov;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MakeXml {

    static void generateXml(ResultSet rs) throws XMLStreamException {
        XmlGenerator xmlGenerator = new XmlGenerator();

        try {
            xmlGenerator.generateDocument(rs);
        } catch (SQLException e) {
            System.out.println(" Generate XML error!");
            e.printStackTrace();
        } catch (IOException | TransformerException error) {
            error.printStackTrace();
        }
    }

    void xsltTransform(final String path) {

        XsltGenerator xsltGenerator = new XsltGenerator();
        try {
            xsltGenerator.generateXmlWithUseXslt(path);
        } catch (IOException | SAXException | ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
    }

    void xmlParserToArrayListAndSum(final String path) {
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
