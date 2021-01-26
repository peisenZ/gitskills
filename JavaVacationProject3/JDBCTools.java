package JavaVacationProject3;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * @author Peisen Zhang
 * @version 15.0
 * @date 2021/1/22 8:26
 */
public class JDBCTools {

    private  static String driver = null;
    private  static String url = null;
    private  static String userName = null;
    private  static String passWord = null;

    static {
        try {
            InputStream in = JDBCTools.class.getClassLoader().getResourceAsStream("db.properties");
            Properties properties = new Properties();
            properties.load(in);

            driver = properties.getProperty("driver");
            url =properties.getProperty("url");
            userName =properties.getProperty("userName");
            passWord = properties.getProperty("passWord");
            Class.forName(driver);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url,userName,passWord);
    }
    public static void release(Connection connection, Statement statement, ResultSet resultSet) throws SQLException {
        if(resultSet!=null) {
          resultSet.close();
        }
        if(statement!=null){
            statement.close();
        }
        if(connection!=null){
            connection.close();
        }
    }
}
