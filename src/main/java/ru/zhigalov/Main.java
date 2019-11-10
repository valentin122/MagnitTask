package ru.zhigalov;

import ru.zhigalov.entity.Entries;
import ru.zhigalov.entity.Entry;
import ru.zhigalov.entity.XmlUsage;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException, JAXBException {

        XmlUsage xmlUsage = new XmlUsage();
        try {
            xmlUsage.getEntity();
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        JAXBContext jaxbContext = JAXBContext.newInstance(XmlUsage.Entry.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        Entries entries = new Entries();
        entries.entry.add(new Entry(1));
        entries.entry.add(new Entry(2));
        jaxbMarshaller.marshal(
                entries,
                System.out
        );

      /*  long startTime = System.currentTimeMillis();

        Dao dao = new Dao();
        MakeXml makeXml = new MakeXml();

        Config.init(); //инициация параметров из файла properties

        dao.initConfigConnectionToDb(); //инициация параметров подключения к БД
        dao.dbCreateAndClear();
        dao.dbInsert();

        RowSet cRowSet = (RowSet) dao.getData();

        try {
            MakeXml.generateXml(cRowSet);
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }

        makeXml.xsltTransform("1.xml");
        makeXml.xmlParserToArrayListAndSum("2.xml");

        double timeSpent = (System.currentTimeMillis() - startTime);
        System.out.println("программа выполнялась " + (timeSpent / 1000) + " секунд");*/

    }

}