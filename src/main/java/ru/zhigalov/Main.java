package ru.zhigalov;


import java.sql.SQLException;

public class Main {




    public static void main(String[] args) throws SQLException {

        long startTime = System.currentTimeMillis();

        Dao dao = new Dao();
        MakeXml makeXml = new MakeXml();

        dao.DbCreateAndClear();
        dao.DbInsert();
        dao.GetData();

        makeXml.xsltTransform("1.xml");
        makeXml.xmlParserToArrayListAndSum("2.xml");



        double timeSpent = (System.currentTimeMillis() - startTime);
        System.out.println("программа выполнялась " + (timeSpent / 1000) + " секунд");

    }

}