package ru.zhigalov;

import javax.xml.stream.XMLStreamException;
import java.sql.*;

public class Dao {

    String url;
    String user;
    String pwd;
    int n;

    final String CREATE = "CREATE TABLE TEST (field INTEGER PRIMARY KEY)";
    final String CLEAN = "TRUNCATE TEST";
    final String INSERT = "INSERT INTO TEST (field) VALUES (?)";
    final String SELECT = "SELECT field FROM TEST";

    void setUrl(){
        this.url = Settings.getValue("jdbc.url");
    }
    void setLogin(){
        this.user = Settings.getValue("jdbc.user");
    }
    void setPassword(){
        this.pwd = Settings.getValue("jdbc.pwd");
    }
    void setN(){
        this.n = Integer.parseInt((Settings.getValue("n")));
    }

    public void DbCreateAndClear() {

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
            try (PreparedStatement statement = connection.prepareStatement(CLEAN)) {
                statement.executeUpdate();
                System.out.println("db cleaning");
            } catch (SQLException e) {
                System.out.println("db cleaning failed");
                e.printStackTrace();
            }
        } catch (SQLException e) {
            System.out.println("db cleaning failed");
            e.printStackTrace();
        }
    }


    public void DbInsert() {
        try (Connection connection = DriverManager.getConnection(url, user, pwd)) {
            try (PreparedStatement statement = connection.prepareStatement(INSERT)) {
                connection.setAutoCommit(false);
                for (int i = 1; i <= n; i++) {
                    statement.setInt(1, i);
                    statement.addBatch();
                }
                statement.executeBatch();
                System.out.println("db inserted!");

                connection.setAutoCommit(true);
            } catch (SQLException e) {
                System.out.println("db inserted failed");
                try {
                    connection.rollback();
                } catch (SQLException e1) {
                    System.out.println("rollback failed");
                    e.printStackTrace();
                }
                e.printStackTrace();
            }
        } catch (SQLException e) {
            System.out.println("db connection failed");
            e.printStackTrace();
        }
    }

    ResultSet GetData() throws SQLException {
        ResultSet rs = null;
        try (Connection connection = DriverManager.getConnection(url, user, pwd)) {
            try (PreparedStatement statement = connection.prepareStatement(SELECT)) {
                rs = statement.executeQuery();
                if (rs != null) {
                    System.out.println("Select success");
                }
                try {
                    MakeXml.generateXml(rs);
                } catch (XMLStreamException e) {
                    e.printStackTrace();
                }
            } catch (SQLException e) {
                System.out.println("Select failed");
                e.printStackTrace();
            }
        }
        return rs;
    }
}





