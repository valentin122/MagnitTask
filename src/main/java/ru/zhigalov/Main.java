package ru.zhigalov;

import javax.xml.stream.XMLStreamException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException, XMLStreamException {
        long startTime = System.currentTimeMillis();
        Tests tests = new Tests();
        tests.DbCreate();
        tests.DbClean();
        tests.DbInsert();
        ResultSet rs = tests.GetData();
        tests.generateXml(rs);
        tests.xsltTransform("1.xml");

        //  Parse file to arraylist and get sum
        tests.xmlParserToArrayListAndSum("2.xml");
        double timeSpent = (System.currentTimeMillis() - startTime);
        System.out.println("программа выполнялась " + (timeSpent / 1000) + " секунд");
    }
}