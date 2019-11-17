package ru.zhigalov;

import ru.zhigalov.dao.Dao;
import ru.zhigalov.servise.Config;
import ru.zhigalov.xml.MakeXml;

import javax.sql.rowset.CachedRowSet;
import javax.xml.bind.JAXBException;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException, JAXBException {

        long startTime = System.currentTimeMillis();

        Dao dao = new Dao();
        MakeXml makeXml = new MakeXml();
        Config config = new Config();

        config.init(); //инициация параметров из файла properties
        String path = config.getValue("path"); //позволяет задавать в файле properties путь к создаваемым файлам

        dao.initConfigConnectionToDb(); //инициация параметров подключения к БД

        dao.dbCreateAndClear();
        double clearTime = System.currentTimeMillis() - startTime;

        dao.dbInsert();
        double insertTime = System.currentTimeMillis() - clearTime - startTime;

        CachedRowSet cRowSet = dao.getData();
        double selectTime = System.currentTimeMillis() - insertTime - startTime;

        MakeXml.generateXml(cRowSet, path);
        double generateTime = System.currentTimeMillis() - startTime - clearTime - insertTime - selectTime;

        makeXml.xsltTransform("1.xml");
        double transformTime = System.currentTimeMillis() - startTime - clearTime - insertTime  - selectTime - generateTime;

        makeXml.xmlParserToArrayListAndSum("2.xml");

        double parseTime = System.currentTimeMillis() - startTime - clearTime - insertTime  - selectTime - generateTime - transformTime;
        double timeSpent = (System.currentTimeMillis() - startTime);
        System.out.println(
                "clearTime " + clearTime / 1000 + System.lineSeparator() +
                "insertTime " + insertTime / 1000 + System.lineSeparator() +
                "selectTime " + selectTime / 1000 + System.lineSeparator() +
                "generateTime " + generateTime / 1000 + System.lineSeparator() +
                "transformTime " + transformTime / 1000 + System.lineSeparator() +
                "parseTime " + parseTime / 1000);

        System.out.println("программа выполнялась " + (timeSpent / 1000) + " секунд");
    }

}