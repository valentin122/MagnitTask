package ru.zhigalov;

import ru.zhigalov.dao.Dao;
import ru.zhigalov.operations.Operations;
import ru.zhigalov.service.Config;
import ru.zhigalov.xml.MakeXml;

import javax.sql.rowset.CachedRowSet;
import javax.xml.bind.JAXBException;
import java.sql.SQLException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws SQLException, JAXBException {
        Config config = new Config();
        config.init();
        String pathToFirstResult = config.getValue("path.to.XML.first.result");
        String pathToTransformedXML = config.getValue("path.to.TransformedXML");
        final String CREATE = config.getValue("create");
        final String CLEAN = config.getValue("clean");
        final String INSERT = config.getValue("insert");
        final String SELECT = config.getValue("select");
        final String URL = config.getValue("jdbc.url");
        final String LOGIN = config.getValue("jdbc.user");
        final String PASSWORD = config.getValue("jdbc.pwd");
        final int N = Integer.parseInt(config.getValue("n"));
        long startTime = System.currentTimeMillis();

        Dao dao = new Dao(URL, LOGIN, PASSWORD);
        MakeXml makeXml = new MakeXml();
        Operations operations = new Operations();

        //dao.initConfigConnectionToDb(); //инициация параметров подключения к БД
        List<Integer> listForInsert = operations.createListFromOneToN(N);

        dao.dbCreateAndClear(CREATE, CLEAN);
        double clearTime = System.currentTimeMillis() - startTime;

        dao.dbInsert(INSERT, listForInsert);
        double insertTime = System.currentTimeMillis() - startTime - clearTime ;

        CachedRowSet cRowSet = dao.getData(SELECT);
        double selectTime = System.currentTimeMillis() - startTime - insertTime - clearTime;

        makeXml.generateXml(cRowSet, pathToFirstResult);
        double generateTime = System.currentTimeMillis() - startTime - clearTime - insertTime - selectTime;

        makeXml.xsltTransform(pathToFirstResult, pathToTransformedXML);
        double transformTime = System.currentTimeMillis() - startTime - clearTime - insertTime  - selectTime - generateTime;

        List parsedData = makeXml.xmlParserToArrayList(pathToTransformedXML);

        long sum = operations.getSumOfList(parsedData);
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
        System.out.println(String.format("Sum is %s", sum));
    }

    private void setInitParams() {

    }

}