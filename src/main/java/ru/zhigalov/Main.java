package ru.zhigalov;


import javax.sql.RowSet;
import javax.xml.stream.XMLStreamException;
import java.sql.SQLException;

public class Main {



    public static void main(String[] args) throws SQLException {

        long startTime = System.currentTimeMillis();

        Dao dao = new Dao();
        MakeXml makeXml = new MakeXml();

        Settings.load();
        dao.setUrl();
        dao.setLogin();
        dao.setPassword();
        dao.setN();

        dao.DbCreateAndClear();
        dao.DbInsert();

        RowSet crset = (RowSet) dao.GetData();

        try {
            MakeXml.generateXml(crset);
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }

        makeXml.xsltTransform("1.xml");
        makeXml.xmlParserToArrayListAndSum("2.xml");



        double timeSpent = (System.currentTimeMillis() - startTime);
        System.out.println("программа выполнялась " + (timeSpent / 1000) + " секунд");

    }

}