//package ru.zhigalov;

//import java.sql.*;


/*public class DbInsert {
    ResultSet resultSet = null;
    PreparedStatement preparedStatement = null;
    final String CREATE = "CREATE TABLE TEST (field INTEGER PRIMARY KEY)";
    final String CLEAN = "TRUNCATE TEST";
    final String INSERT = "INSERT INTO TEST (field) VALUES (?)";
    final String SELECT = "SELECT field FROM TEST";
    Connection connection;

    int n = 10000;

    String url = "jdbc:postgresql://127.0.0.1/";
    String user = "postgres";
    String pwd = "73173100";
/*
    public DbInsert() throws SQLException {

        try (Connection connection = DriverManager.getConnection(url, user, pwd)) {
            if (connection != null) {
                System.out.println("postgres connected");

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public class DbCreate {
        String exist = ("select * from pg_tables where tablename='TEST'");
        if (exist == null) {
            try (PreparedStatement statement = connection.prepareStatement(CREATE)) {
                statement.executeUpdate();
                System.out.println("db created");
            } catch (SQLException e) {
                System.out.println("db created failed");
                e.printStackTrace();
            }
        }
        try (PreparedStatement statement = connection.prepareStatement(CLEAN)) {
            statement.executeUpdate();
            System.out.println("db cleaning");
        } catch (SQLException e) {
            System.out.println("db cleaning failed");
            e.printStackTrace();
        }

        try (PreparedStatement statement = connection.prepareStatement(INSERT)) {
            for (int i = 1; i <= n; i++) {
                statement.setInt(1, i);
                statement.executeUpdate();
            }
            System.out.println("db inserted!");
        } catch (SQLException e) {
            System.out.println("db inserted failed");
            e.printStackTrace();
        }

        try (PreparedStatement statement = connection.prepareStatement(SELECT)) {
            resultSet = preparedStatement.executeQuery();
            System.out.println("Select success");

            //for debug - need to see result of sql query
            // resultSet.getInt(1) - 1 is column index

            //while (resultSet.next()) {
            //	System.out.println(resultSet.getInt(1));
            //}
        } catch (SQLException e) {
            System.out.println("Select failed");
            e.printStackTrace();
        }
    }




}

*/

