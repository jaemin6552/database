package User;

import util.DBConn;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MyDao {
    DBConn myDB = DBConn.getMyConn();

    public MyDao() throws SQLException, ClassNotFoundException {
    }
    public synchronized void showCos_Info() throws SQLException { //주식 정보 보여주는메서드

        PreparedStatement pstmt = myDB.getPStmt("SELECT ROWNUM, INFO_COSPI.* FROM INFO_COSPI ");
        ResultSet rs =  pstmt.executeQuery();
        System.out.printf("[     이름     ]" + " [  가격  ]" + "[ 성장률 ]" + "[ 성장가격 ]\n");
        while(rs.next()){
            System.out.printf("%s %10s %10d %5d\n",rs.getString("ROWNUM"),
                    rs.getString("COSPI_ID"),
                    rs.getInt("COSPI_PRICE"),
                    rs.getInt("GROWTH_F"));
        }
        myDB.close(pstmt,rs);
    }
    public synchronized void showUser_Info() throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = myDB.getPStmt("SELECT * FROM USER_TABLE");
            rs = pstmt.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getString("USER_ID")+ " "
                       + rs.getString("USER_NAME")+ " "
                       + rs.getInt("MONEY"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        myDB.close(pstmt, rs);

    }

    public synchronized void updateCospi(String name,double growth)  {
        PreparedStatement pstmt = null;
        try {
            pstmt = myDB.getPStmt("UPDATE INFO_COSPI SET COSPI_PRICE = " +
                    "(SELECT COSPI_PRICE FROM INFO_COSPI WHERE COSPI_ID = ? ) * " + growth +
                    "WHERE COSPI_ID = ?");
            pstmt.setString(1,name);
            pstmt.setString(2,name);
            int re = pstmt.executeUpdate();
            if(re>=1){
                System.out.println("주식업데이트성공!");
            } else {
                System.out.println("잘못된 값 입력입니다.");
            }
          //  DBConn.getDbConn().commit();
        } catch (SQLException e) {
            System.out.println("업데이트실패");
        }

        try {
            myDB.close(pstmt);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public synchronized String getRdCospi() throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int count = 0;
        try {
            pstmt = myDB.getPStmt("SELECT COUNT(*) FROM INFO_COSPI");
            rs = pstmt.executeQuery();
            System.out.println("카운트");
            count = rs.getInt("COUNT(*)");
            System.out.println("카운터" + "  " + count);
            int random = (int)(Math.random() * count) + 101;

            pstmt = myDB.getPStmt("SELECT COSPI_ID FROM INFO_COSPI WHERE COSPI_NUM = ?");
            pstmt.setInt(1,random);
            rs = pstmt.executeQuery();
            String tmp = rs.getString("COSPI_ID");
            myDB.close(pstmt,rs);
            return tmp;
        }catch (SQLException e){
            System.out.println("카운터 없음");
            return "1";
        }

    }


}
