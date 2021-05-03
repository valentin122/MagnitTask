package ru.zhigalov.dao;


import ru.zhigalov.service.Config;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;
import java.sql.*;
import java.util.List;

public class Dao {

    private String url;
    private String user;
    private String pwd;
    private int n;

    public Dao() {
    }

    public Dao(String url, String user, String password) {
        this.setUrl(url);
        this.setLogin(user);
        this.setPassword(password);
    }

    public void setUrl(String url) {
        this.url = url;
    }
    public void setLogin(String login) {
        this.user = login;
    }
    public void setPassword(String password){
        this.pwd = password;
    }

    public void dbCreateAndClear(String create, String clean) {
        try (Connection connection = DriverManager.getConnection(url, user, pwd)) {
            try {
                DatabaseMetaData meta = connection.getMetaData();
                ResultSet tables = meta.getTables(null, null, "test", null);
                if (!tables.next()) {
                    try (PreparedStatement statement = connection.prepareStatement(create)) {
                        statement.executeUpdate();
                    }
                }
            } catch (SQLException e) {
                System.out.println("db can't create");
                e.printStackTrace();
            }
            try (PreparedStatement statement = connection.prepareStatement(clean)) {
                statement.executeUpdate();
              //  System.out.println("db cleaning");
            } catch (SQLException e) {
                System.out.println("db cleaning failed");
                e.printStackTrace();
            }
        } catch (SQLException e) {
            System.out.println("db cleaning failed");
            e.printStackTrace();
        }
    }


    public void dbInsert(String insert, List<Integer> input) {
        try (Connection connection = DriverManager.getConnection(url, user, pwd)) {
            try (PreparedStatement statement = connection.prepareStatement(insert)) {
                connection.setAutoCommit(false);
                for (int i : input) {
                    statement.setInt(1, i);
                    statement.addBatch();
                }
                statement.executeBatch();
                connection.commit();
                // System.out.println("db inserted!");
            } catch (SQLException e) {
              //  System.out.println("db inserted failed");
                try {
                    connection.rollback();
                } catch (SQLException e1) {
                 //   System.out.println("rollback failed");
                    e.printStackTrace();
                }
                e.printStackTrace();
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
         //   System.out.println("db connection failed");
            e.printStackTrace();
        }
    }

    public CachedRowSet getData(String select) throws SQLException {
        ResultSet rs;
        RowSetFactory factory = RowSetProvider.newFactory();
        CachedRowSet cachedRowSet = factory.createCachedRowSet();

        try (Connection connection = DriverManager.getConnection(url, user, pwd)) {
            try (PreparedStatement statement = connection.prepareStatement(select)) {

                rs = statement.executeQuery();
                cachedRowSet.populate (rs);
                if (cachedRowSet != null) {
                    //System.out.println("Select success");
                }
            } catch (SQLException e) {
              //  System.out.println("Select failed");
                e.printStackTrace();
            }
        }
        return cachedRowSet;
    }
}







