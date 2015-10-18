import com.sun.rowset.CachedRowSetImpl;

import java.sql.*;

public class DataSetInfo {
    public void createTable(String dbURL, String query){
        Connection connection = null;
        Statement statement = null;
        try{
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(dbURL);
            statement = connection.createStatement();
            statement.executeQuery(query);
            statement.close();
        } catch (SQLException ex){
            ex.printStackTrace();
        } catch (ClassNotFoundException ex){
            ex.printStackTrace();
        }
        System.out.println("The table has been created successfully.");
    }

    public ResultSet getInfo(String dbURL, String query) throws SQLException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        CachedRowSetImpl cachedRowSet = null;
        try{
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(dbURL);
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            cachedRowSet = new CachedRowSetImpl();
            cachedRowSet.populate(resultSet);
        }catch (SQLException ex){
            ex.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try{
                if(resultSet != null){
                    resultSet.close();
                }
                if(statement != null){
                    statement.close();
                }
                if(connection != null){
                    connection.close();
                }
            }catch (SQLException ex){
                ex.printStackTrace();
            }
        }
        return cachedRowSet;
    }
}
