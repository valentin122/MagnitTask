package ru.zhigalov.dao;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;
import java.sql.*;
import java.util.List;

public class Dao {
    private static final Logger LOG = LogManager.getLogger(Dao.class.getName());

    private String url;
    private String user;
    private String pwd;

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
                LOG.error("db can't create", e);
            }
            try (PreparedStatement statement = connection.prepareStatement(clean)) {
                statement.executeUpdate();
                LOG.info("db cleaning");
            } catch (SQLException e) {
                LOG.error("db cleaning failed", e);
            }
        } catch (SQLException e) {
            LOG.error("db connection failed", e);
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
                LOG.info("db inserted!");
            } catch (SQLException e) {
                LOG.error("db inserted failed", e);
                try {
                    connection.rollback();
                } catch (SQLException e1) {
                    LOG.error("rollback failed");
                }
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            LOG.error("db connection failed", e);
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
                    LOG.info("Select success");
                }
            } catch (SQLException e) {
                LOG.error("Select failed", e);
            }
        }
        return cachedRowSet;
    }
}







