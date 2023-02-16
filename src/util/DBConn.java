package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBConn {
    private static Connection dbConn;
    private static PreparedStatement pStmt;
    private DBConn(){}
    public static PreparedStatement getPStmt(String sql) throws SQLException {
        if(pStmt != null && (!pStmt.isClosed())){
            pStmt.close();
            pStmt = null;
            System.out.println("제거 완료");
        }
        pStmt = dbConn.prepareStatement(sql);
        return pStmt;
    }

    public static Connection getDbConn() throws ClassNotFoundException, SQLException {

        if(dbConn ==null){
            String url = "jdbc:oracle:thin:@localhost:1521:xe";
            String user = "JM";
            String pwd = "1234";
            Class.forName("oracle.jdbc.driver.OracleDriver");
            dbConn = DriverManager.getConnection(url,user,pwd);
        }
        return dbConn;
    }
    public static void close() throws SQLException {
        if(pStmt != null && (!pStmt.isClosed())){
            pStmt.close();
            pStmt = null;
            System.out.println("제거 완료");
        }
        if(dbConn != null && !dbConn.isClosed()) {
                dbConn.close();
                dbConn = null;
                System.out.println("제거완료~");
        }

    }


}
