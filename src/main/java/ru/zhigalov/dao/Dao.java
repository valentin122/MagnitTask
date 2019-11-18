package ru.zhigalov.dao;


import ru.zhigalov.service.Config;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;
import java.sql.*;

public class Dao {

    private String url;
    private String user;
    private String pwd;
    private int n;

    final String CREATE = "CREATE TABLE TEST (field INTEGER PRIMARY KEY)";
    final String CLEAN = "TRUNCATE TEST";
    final String INSERT = "INSERT INTO TEST (field) VALUES (?)";
    final String SELECT = "SELECT field FROM TEST";

    public void initConfigConnectionToDb() {
        setUrl();
        setLogin();
        setPassword();
        setN();
    }
    public void setUrl(){
        this.url = Config.getValue("jdbc.url");
    }
    public void setLogin(){
        this.user = Config.getValue("jdbc.user");
    }
    public void setPassword(){
        this.pwd = Config.getValue("jdbc.pwd");
    }
    public void setN() {
        this.n = Integer.parseInt((Config.getValue("n")));
    }

    public void dbCreateAndClear() {
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


    public void dbInsert() {
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

    public CachedRowSet getData() throws SQLException {
        ResultSet rs;
        RowSetFactory factory = RowSetProvider.newFactory();
        CachedRowSet cachedRowSet = factory.createCachedRowSet();

        try (Connection connection = DriverManager.getConnection(url, user, pwd)) {
            try (PreparedStatement statement = connection.prepareStatement(SELECT)) {

                rs = statement.executeQuery();
                cachedRowSet.populate (rs);
                if (cachedRowSet != null) {
                    System.out.println("Select success");
                }
            } catch (SQLException e) {
                System.out.println("Select failed");
                e.printStackTrace();
            }
        }
        return cachedRowSet;
    }
}







