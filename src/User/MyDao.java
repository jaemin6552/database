package User;

import util.DBConn;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyDao {
    DBConn myDB = DBConn.getMyConn();
    HashMap<String,UserInfo> userMap;
    public MyDao() throws SQLException, ClassNotFoundException {
        userMap = new HashMap<>();
    }
    public void setUser_Info() throws SQLException {
        PreparedStatement pstmt =null;
        ResultSet rs = null;
        try {
            pstmt = myDB.getPStmt("SELECT * FROM USER_TABLE");
            rs = pstmt.executeQuery();
            while(rs.next()){
                userMap.put(rs.getString("USER_ID"),new UserInfo(rs.getString("USER_ID"),rs.getNString("USER_PWD"),rs.getString("NAME"),rs.getString("PH"),rs.getInt("MONEY") ) );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            myDB.close(pstmt,rs);
        }
    }
    public void setWallet() throws SQLException {
        PreparedStatement pstmt =null;
        ResultSet rs = null;
        try {
            pstmt = myDB.getPStmt("SELECT * FROM WALLETS");
            rs = pstmt.executeQuery();

            while(rs.next()){
                HashMap<String,Wallet> tmpMap = new HashMap<>();
                    tmpMap.put(rs.getString("COSPI_ID"),
                            new Wallet(rs.getString("COSPI_ID"),
                                    rs.getInt("FIGURE"),
                                    rs.getInt("TOTAL_PRICE"),
                                    rs.getInt("CRC"),
                                    rs.getInt("BOUGHT"),
                                    rs.getString("BUY_DATE"),
                                    rs.getDouble("GROWTH") ));
                    userMap.get(rs.getString("USER_ID")).setUserWallet(tmpMap);
                    System.out.println(tmpMap);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            myDB.close(pstmt,rs);
        }
    }

    public  void showCos_Info() throws SQLException { //주식 정보 보여주는메서드

        PreparedStatement pstmt = myDB.getPStmt("SELECT ROWNUM, INFO_COSPI.* FROM INFO_COSPI ");
        ResultSet rs =  pstmt.executeQuery();
        //System.out.printf("[     이름     ]" + " [  가격  ]" + "[ 성장률 ]" + "[ 성장가격 ]\n");
        while(rs.next()){
            System.out.printf("%s %s %d %.2f %d %d\n",rs.getString("ROWNUM"),
                    rs.getString("COSPI_ID"),
                    rs.getInt("COSPI_PRICE"),
                    rs.getDouble("GROWTH_F"),
                    rs.getInt("GROWTH_I"),
                    rs.getInt("COSPI_NUM"));
        }
        myDB.close(pstmt,rs);
    }
    public  void showUser_Info() throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = myDB.getPStmt("SELECT * FROM USER_TABLE");
            rs = pstmt.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getString("USER_ID")+ " "
                       + rs.getString("NAME")+ " "
                       + rs.getInt("MONEY") + " "
                       + rs.getString("PH"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        myDB.close(pstmt, rs);

    }

    public  void updateCospi(String cosId,double growth)  {
        PreparedStatement pstmt = null;
        List<String>name = getBringUser(cosId);
        try {
            pstmt = myDB.getPStmt("UPDATE INFO_COSPI SET COSPI_PRICE = ?, " +
                    "GROWTH_F = ?," +
                    "GROWTH_I = ?" +
                    "WHERE COSPI_ID = ?");
            int nowPrice = getCosPrice(cosId);
            System.out.println(nowPrice);
            int price = nowPrice + (int)(nowPrice * growth);
            pstmt.setInt(1,price);
            pstmt.setDouble(2,growth);
            int tmp = price - nowPrice;
            pstmt.setInt(3,tmp);
            pstmt.setString(4,cosId);
            int re = pstmt.executeUpdate();
            for(int i = 0; i<name.size(); i++) {
               // updateWallet(cosId, nowPrice,name.get(i));
            }
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
    public synchronized int getRdNum() throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int count = 0;
        try {
            pstmt = myDB.getPStmt("SELECT COUNT(*) AS total FROM INFO_COSPI");
            rs = pstmt.executeQuery();
            while (rs.next()){
                count = rs.getInt(1);
            }
            int random = (int)(Math.random() * count) + 101;
            return random;

        }catch (SQLException e){
            System.out.println("getRdNum에러발생");
            return -1;
        }finally {
            myDB.close(pstmt,rs);
        }

    }

    public String getRdID() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int random = getRdNum();
        try {
            pstmt = myDB.getPStmt("SELECT COSPI_ID FROM INFO_COSPI WHERE COSPI_NUM = ?");
            pstmt.setInt(1,random);
            rs = pstmt.executeQuery();
            rs.next(); //rs.next를 한번해줘야함
            String tmp = rs.getString("COSPI_ID");
            return tmp;
        }catch (SQLException e){
            return "getRdID에러발생";
        }finally {
            myDB.close(pstmt,rs);
        }
    }
    public int getCosPrice(String name) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

            pstmt = myDB.getPStmt("SELECT COSPI_PRICE FROM INFO_COSPI WHERE COSPI_ID = ?");
            pstmt.setString(1,name);
            rs = pstmt.executeQuery();
            rs.next();
            int tmp = rs.getInt("COSPI_PRICE");
            myDB.close(pstmt,rs);
            return tmp;

    }
    public void updateWallet(String cosId,int price,String name) throws SQLException {
        PreparedStatement pstmt = null;

        try {
            pstmt = myDB.getPStmt("UPDATE WALLETS SET CRC = ?" +
                    "GROWTH = ?," +
                    "TOTAL_PRICE = ?" +
                    "WHERE COSPI_ID = ? AND USER_ID = ?");

            pstmt.setInt(1,price);
           // pstmt.setDouble(2,);
            pstmt.setString(4,cosId);
            pstmt.setString(5,name);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            myDB.close(pstmt);
        }
    }
    public List<String> getBringUser(String cosId){
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<String> name = new ArrayList<>();
        try {
            pstmt = myDB.getPStmt("SELECT USER_ID FROM WALLTES WHERE COSPI_ID = ?");
            pstmt.setString(1,cosId);
            rs = pstmt.executeQuery();
            while (rs.next()){
                name.add(rs.getString("USER_ID"));
            }
            return name;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


}
