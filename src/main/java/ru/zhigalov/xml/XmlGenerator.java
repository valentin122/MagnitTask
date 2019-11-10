package ru.zhigalov.xml;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

class XmlGenerator {
    String generateDocument(ResultSet resultSet) throws SQLException, IOException,
        TransformerException, XMLStreamException {
        List<String> dbQueryResult = new ArrayList<>();
        String line;
        final String COLUMN_FIELD = "field";
        while (resultSet.next()) {
            dbQueryResult.add(resultSet.getString(COLUMN_FIELD));
        }

        final XMLOutputFactory factory = XMLOutputFactory.newFactory();
        XMLStreamWriter writer = factory.createXMLStreamWriter(new FileOutputStream("0.xml"), "UTF-8");
        writer.writeStartDocument("UTF-8", "1.0");
        writer.writeStartElement("entries");
        for (String aDbQueryResult : dbQueryResult) {
            writer.writeStartElement("entry");
            writer.writeStartElement("field");
            writer.writeCharacters(aDbQueryResult);
            writer.writeEndElement();
            writer.writeEndElement();
        }
        writer.writeEndElement();
        writer.writeEndDocument();
        writer.close();

        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        transformer.transform(new StreamSource(
                        new BufferedInputStream(new FileInputStream("0.xml"))),
                new StreamResult(new FileOutputStream("1.xml"))
        );

        StringBuilder stringBuilder = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader("1.xml"))) {
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
              //  stringBuilder.append(System.lineSeparator());
            }
            return stringBuilder.toString();
        }
    }
}