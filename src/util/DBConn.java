package util;

import java.sql.*;


public class DBConn {

    private static DBConn myConn = new DBConn();


    private static Connection dbConn;
    private static PreparedStatement pStmt;


    private DBConn(){};

    public static synchronized DBConn getMyConn() throws ClassNotFoundException, SQLException {
        if(dbConn == null ){
            String url = "jdbc:oracle:thin:@localhost:1521:xe";
            String user = "JM";
            String pwd = "1234";
            Class.forName("oracle.jdbc.driver.OracleDriver");
            dbConn = DriverManager.getConnection(url,user,pwd);
        }
        return myConn;
    }
    public synchronized PreparedStatement getPStmt(String sql) throws SQLException {
        if(dbConn != null) {
            pStmt = dbConn.prepareStatement(sql);
        }
        return pStmt;
    }

    public synchronized void close(Connection dbConn, PreparedStatement pStmt, ResultSet rs) throws SQLException {
        if(rs != null && (!rs.isClosed())){
            rs.close();
        }
        if(pStmt != null && (!pStmt.isClosed())){
            pStmt.close();
            this.pStmt = null;
           // System.out.println("제거 완료");
        }
        if(dbConn != null && !dbConn.isClosed()) {
                dbConn.close();
               // System.out.println("제거완료~");
        }
    }
    public synchronized void close( PreparedStatement pStmt, ResultSet rs) throws SQLException {
        close(null,pStmt,rs);
    }
    public synchronized void close( PreparedStatement pStmt) throws SQLException {
        close(null,pStmt,null);
    }
}