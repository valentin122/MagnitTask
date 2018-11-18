package ru.zhigalov;

public class Main {

    public static void main(String[] args)  {

        long startTime = System.currentTimeMillis();
        DbInsert run = new DbInsert();
        run.DbCreate();
        run.DbClean();
        run.DbInsert();
        run.GetData();
        run.xsltTransform("1.xml");
        run.xmlParserToArrayListAndSum("2.xml");

        double timeSpent = (System.currentTimeMillis() - startTime);
        System.out.println("программа выполнялась " + (timeSpent / 1000) + " секунд");
    }
}