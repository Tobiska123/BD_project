package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Locale;
import java.util.TimeZone;

public class DbConnectionHandler {
    private String url;
    private String user;
    private String passwd;

    public DbConnectionHandler(String url, String user, String passwd){
        this.url = url;
        this.user = user;
        this.passwd = passwd;
    }

    public Connection getNewConnection() throws SQLException{
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            System.out.println("Oracle JDBC Driver is not found");
            e.printStackTrace();
        }
        TimeZone timeZone = TimeZone.getTimeZone("GMT+7");
        TimeZone.setDefault(timeZone);
        Locale.setDefault(Locale.ENGLISH);
        return DriverManager.getConnection(url, user, passwd);
    }
}
