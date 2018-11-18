package ru.zhigalov;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class Tests {

    final static String CREATE = "CREATE TABLE TEST (field INTEGER PRIMARY KEY)";
    final static String CLEAN = "TRUNCATE TEST";
    final static String INSERT = "INSERT INTO TEST (field) VALUES (?)";
    final static String SELECT = "SELECT field FROM TEST";


    public static int n = 100000;

    static String url = "jdbc:postgresql://127.0.0.1/";
    static String user = "postgres";
    static String pwd = "73173100";

    public static void DbCreate() {
        try (Connection connection = DriverManager.getConnection(url, user, pwd)) {
            try {
                DatabaseMetaData meta = connection.getMetaData();
                ResultSet tables = meta.getTables(null, null, "test", null);
                if (!tables.next()) {
                    try (PreparedStatement statement = connection.prepareStatement(CREATE)) {
                        statement.executeUpdate();
                    }
                }

            } catch (SQLException e) {
                System.out.println("db can't create");
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void DbClean() {
        try (Connection connection = DriverManager.getConnection(url, user, pwd)) {
            try (PreparedStatement statement = connection.prepareStatement(CLEAN)) {
                statement.executeUpdate();
                System.out.println("db cleaning");
            } catch (SQLException e) {
                System.out.println("db cleaning failed");
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void DbInsert() {
        try (Connection connection = DriverManager.getConnection(url, user, pwd)) {

            try (PreparedStatement statement = connection.prepareStatement(INSERT)) {
                connection.setAutoCommit(false);
                for (int i = 1; i <= n; i++) {
                    statement.setInt(1, i);
                    statement.executeUpdate();
                }

                System.out.println("db inserted!");

                connection.setAutoCommit(true);
            } catch (SQLException e) {
                System.out.println("db inserted failed");
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    ResultSet GetData() throws SQLException {
        ResultSet rs = null;

        try (Connection connection = DriverManager.getConnection(url, user, pwd);) {

            try (PreparedStatement statement = connection.prepareStatement(SELECT)) {
                connection.setAutoCommit(false);
                rs = statement.executeQuery();
                if (rs != null) {
                    System.out.println("Select success");
                }

                generateXml(rs);
                connection.commit();
            } catch (SQLException e) {
                System.out.println("Select failed");
                e.printStackTrace();
            } catch (XMLStreamException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            System.out.println("Select failed");
            e.printStackTrace();
        }


        return rs;
    }

        void generateXml (ResultSet rs) throws SQLException, XMLStreamException {
            XmlGenerator xmlGenerator = new XmlGenerator();
            String result = "Xml create fail!";
            try {
                result = xmlGenerator.generateDocument(rs);
            } catch (SQLException e) {
                System.out.println(" Generate XML error!");
                e.printStackTrace();
            } catch (ParserConfigurationException | IOException | TransformerException | SAXException error) {
                error.printStackTrace();
            }
        }

        void xsltTransform ( final String path){

            XsltGenerator xsltGenerator = new XsltGenerator();

            try {
                xsltGenerator.generateXmlWithUseXslt(path);
            } catch (IOException | SAXException | ParserConfigurationException | TransformerException e) {
                e.printStackTrace();
            }
        }

        void xmlParserToArrayListAndSum ( final String path){
            long result = 0;
            XmlFileParserAndSumCounter xmlFileParser = new XmlFileParserAndSumCounter();
            ArrayList<Integer> arrayForSum = xmlFileParser.parseXmlFileToArrayList(path);

            System.out.println("Parse file to array success.");


            result =  sum(arrayForSum);


            System.out.println(String.format("Sum is %s", result));
        }
    public long sum(Collection<Integer> collection) {
        return collection.stream().mapToInt(i -> i).sum();
    }
    }




