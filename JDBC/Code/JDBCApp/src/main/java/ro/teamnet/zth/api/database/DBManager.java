package ro.teamnet.zth.api.database;

import java.sql.*;

import static ro.teamnet.zth.api.database.DBProperties.PASS;
import static ro.teamnet.zth.api.database.DBProperties.USER;

/**
 * Created by Ovidiu.Busuioc on 7/13/2017.
 */
public class DBManager {
    private DBManager() {
        throw new UnsupportedOperationException();
    }

    private static final String CONNECTION_STRING= "jdbc:oracle:thin:@" + DBProperties.IP + ":" + DBProperties.PORT + ":xe";

    private static void registerDriver(){
        try {
            Class.forName(DBProperties.DRIVER_CLASS);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static Connection getConnection(){
        registerDriver();
        try {
            return  DriverManager.getConnection(CONNECTION_STRING,USER,PASS);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean checkConnection(Connection connection){
        String querry = "SELECT 1 FROM DUAL";
        Statement stm = null;
        try {
            stm = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            return  stm.execute(querry);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
