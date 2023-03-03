package User;


import Board.Board;
import Board.Comments;
import Kospi.CospiInfo;
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

    List<String> userName;
    HashMap<String,Board> boardMap;
    HashMap<String, Comments> commentsMap;
    HashMap<String,CospiInfo> cospiMap;


    public MyDao() throws SQLException, ClassNotFoundException {
        userMap = new HashMap<>();
        cospiMap = new HashMap<>();
        userName = new ArrayList<>();
        setUser_Info();
        setWallet();
        setCospi();
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////

    public void insertBoard_Info(String cospi_id,String user_id,String title, String content) throws SQLException {
        PreparedStatement pstmt = null;
        try{
            pstmt = myDB.getPStmt("INSERT INTO BOARD VALUES(?,?,(SELECT NVL(MAX(WRITE_NUM),0) FROM BOARD) + 1,?,?)");
            pstmt.setString(1,cospi_id);
            pstmt.setString(2,user_id);
            pstmt.setString(3, title);
            pstmt.setString(4,content);
            pstmt.executeUpdate();

        }catch (SQLException e){
            throw new SQLException(e);
        }
        myDB.close(pstmt);
    }

    public void deleteBoard_Info(int write_num) throws SQLException{
        PreparedStatement pstmt = null;
        try{
            pstmt = myDB.getPStmt("DELETE FROM BOARD WHERE WRITE_NUM = ?");
            pstmt.setInt(1, write_num);
            pstmt.executeUpdate();
        }catch (SQLException e){
            throw new SQLException(e);
        }
        myDB.close(pstmt);
    }

    public void showBoard_Info() throws SQLException{
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            pstmt = myDB.getPStmt("SELECT * FROM BOARD");
            //System.out.println("쿼리문 날리기직전");
            rs = pstmt.executeQuery();
            //System.out.println("쿼리날림");
            while(rs.next()){
                System.out.println(rs.getString("COSPI_ID") + " "
                        +rs.getString("USER_ID") + " "
                        + rs.getInt("WRITE_NUM") + " "
                        + rs.getString("TITLE") + " "
                        + rs.getString("CONTENT"));
                System.out.println("값출력 보드");
            }
        }catch (SQLException e){
            System.out.println("실패");
        }
        myDB.close(pstmt, rs);
    }

    public void showComments_Info()throws SQLException{
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = myDB.getPStmt("SELECT * FROM COMMENTS");
            rs = pstmt.executeQuery();
            while(rs.next()){
                System.out.println(rs.getString("USER_ID") + " "
                        + rs.getInt("WRITE_NUM") + " "
                        + rs.getString("COMMENTS_LETTER") + " "
                        + rs.getInt("COMMENTS_NUM"));
            }
        }catch (SQLException e){
            throw new SQLException(e);
        }
        myDB.close(pstmt, rs);
    }

    public void insertComments_Info(String user_id, int write_num, String comments_letter)throws SQLException{
        PreparedStatement pstmt = null;
        try{
            pstmt = myDB.getPStmt("INSERT INTO COMMENTS VALUES(?,?,?,(SELECT NVL(MAX(COMMENTS_NUM),0) FROM COMMENTS) + 1)");
            pstmt.setString(1,user_id);
            pstmt.setInt(2,write_num);
            pstmt.setString(3,comments_letter);
            pstmt.executeUpdate();
        }catch (SQLException e){
            throw new SQLException(e);
        }
        myDB.close(pstmt);
    }

    public void deleteComments_Info(int comments_num) throws SQLException{
        PreparedStatement pstmt = null;
        try{
            pstmt = myDB.getPStmt("DELETE FROM COMMENTS WHERE COMMENTS_NUM = ?");
            pstmt.setInt(1, comments_num);
            pstmt.executeUpdate();
        }catch (SQLException e){
            throw new SQLException(e);
        }
        myDB.close(pstmt);
    }

    public void setComments_Info()throws SQLException{
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = myDB.getPStmt("SELECT * FROM COMMENTS");
            rs = pstmt.executeQuery();
            while (rs.next()) {
                commentsMap.put(rs.getString("USER_ID"),new Comments(rs.getString("USER_ID"),rs.getInt("WRITE_NUM"),rs.getString("COMMENTS_LETTER"),rs.getInt("COMMENTS_NUM")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            myDB.close(pstmt, rs);
        }
    }

    public void searchBoard_Info()throws SQLException{
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            pstmt = myDB.getPStmt("SELECT * FROM BOARD WHERE WRITE_NUM > (SELECT COUNT(WRITE_NUM) - 5 FROM BOARD)");
            rs = pstmt.executeQuery();
            while(rs.next()){
                System.out.println(rs.getString("COSPI_ID") + " "
                        + rs.getString("USER_ID") + " "
                        + rs.getInt("WRITE_NUM") + " "
                        + rs.getString("TITLE") + " "
                        + rs.getString("CONTENT"));
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        myDB.close(pstmt,rs);
    }

    public void UsearchBoard_Info(String user_id)throws SQLException{
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            pstmt = myDB.getPStmt("SELECT * FROM BOARD WHERE USER_ID = ?");
            pstmt.setString(1, user_id);
            rs = pstmt.executeQuery();
            while(rs.next()){
                System.out.println(rs.getString("COSPI_ID") + " "
                        + rs.getString("USER_ID") + " "
                        + rs.getInt("WRITE_NUM") + " "
                        + rs.getString("TITLE") + " "
                        + rs.getString("CONTENT"));
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        myDB.close(pstmt,rs);
    }



    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public boolean rogIn(String user_id,String user_pwd){
        if(user_id == null) return false;

            for (int i = 0; i < userName.size(); i++) {
                if (user_id.equals(userName.get(i))) {
                    if (user_pwd.equals(userMap.get(userName.get(i)).getPassword())) {
                        System.out.println("로그인성공");
                        return true;
                    }
                    System.out.println("비밀번호가 올바르지 않습니다.");
                    return false;
                }
            }

        System.out.println("없는 아이디 입니다.");
        return false;
    }
    public void showMyWallets(String id){
        try {
            setWallet();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println(id + "님의 주식 보유 현황");
        for(String key : userMap.get(id).getUserWallet().keySet()){
            System.out.println("주식이름 : " + userMap.get(id).getUserWallet().get(key).getName());
            System.out.println("보유량 : " + userMap.get(id).getUserWallet().get(key).getFigure());
            System.out.println("최근구매일 : " + userMap.get(id).getUserWallet().get(key).getBuyDate());
            System.out.println("현재가격 : " + userMap.get(id).getUserWallet().get(key).getCrc());
            System.out.println("평단가 : " + userMap.get(id).getUserWallet().get(key).getBought());
            System.out.println("총액 : " + userMap.get(id).getUserWallet().get(key).getTotalPrice());
            System.out.println("손익 : " + userMap.get(id).getUserWallet().get(key).getGrowth());
        }
    }
    ///////////////////////////////////////////////////////////////////////////////////
    public void buyCospi(String user_id,String cospi_id,int figure) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        if(userMap.get(user_id).getUserWallet().containsKey(cospi_id)){
            try {
                pstmt = myDB.getPStmt("UPDATE WALLETS" +
                        " SET FIGURE = ?,TOTAL_PRICE = (SELECT COSPI_PRICE FROM INFO_COSPI WHERE COSPI_ID = ?) * ? " +
                        " WHERE COSPI_ID = ? AND USER_ID = ?");
                pstmt.setInt(1,figure);
                pstmt.setString(2,cospi_id);
                pstmt.setInt(3,figure);
                pstmt.setString(4,cospi_id);
                pstmt.setString(5,user_id);
                pstmt.executeUpdate();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }finally {
                myDB.close(pstmt);
            }
        }else{
            pstmt = myDB.getPStmt("INSERT INTO WALLETS VALUES(?,?,?,(SELECT COSPI_PRICE FROM INFO_COSPI WHERE COSPI_ID = ?) * ?,SYSDATE)");
        }
    }
    public void sellCospi(String user_id,String cospi_id){

    }
    //////////////////////////////////////////////////////////////////////////////////////////////////
    public boolean userJoin(String user_id,String user_pwd,String name,String PH) throws SQLException {
        for(int i = 0; i<userName.size(); i++){

            if(user_id.equals(userName.get(i))){
                System.out.println("아이디가 중복입니다");
                return false;
            }
        }
        PreparedStatement pstmt =null;
        ResultSet rs = null;
        try {
            pstmt = myDB.getPStmt("INSERT INTO USER_TABLE VALUES(?,?,?,?,1000000)");
            pstmt.setString(1,user_id);
            pstmt.setString(2,user_pwd);
            pstmt.setString(3,name);
            pstmt.setString(4,PH);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            myDB.close(pstmt);
            setUser_Info();
        }

        return true;
    }
    public void setUser_Info() throws SQLException {
        PreparedStatement pstmt =null;
        ResultSet rs = null;
        try {
            pstmt = myDB.getPStmt("SELECT * FROM USER_TABLE");
            rs = pstmt.executeQuery();
            while(rs.next()){
                userMap.put(rs.getString("USER_ID"),new UserInfo(rs.getString("USER_ID"),rs.getNString("USER_PWD"),rs.getString("NAME"),rs.getString("PH"),rs.getInt("MONEY") ) );
                userName.add(rs.getString("USER_ID"));
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
                    userMap.get(rs.getString("USER_ID")).getUserWallet().put(rs.getString("COSPI_ID"),
                            new Wallet(rs.getString("COSPI_ID"),
                                    rs.getInt("FIGURE"),
                                    rs.getInt("TOTAL_PRICE"),
                                    rs.getInt("CRC"),
                                    rs.getInt("BOUGHT"),
                                    rs.getString("BUY_DATE"),
                                    rs.getDouble("GROWTH") ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            myDB.close(pstmt,rs);
        }
    }
    public void setCospi() throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = myDB.getPStmt("SELECT * FROM INFO_COSPI");
            rs = pstmt.executeQuery();
            while(rs.next()){
                cospiMap.put(rs.getString("COSPI_ID"),
                        new CospiInfo(rs.getString("COSPI_ID"),
                                rs.getInt("COSPI_NUM"),
                                rs.getInt("COSPI_PRICE"),
                                rs.getInt("GROWTH_I"),
                                rs.getDouble("GROWTH_F")));

            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            myDB.close(pstmt,rs);
        }
    }

    public void showCos_Info() throws SQLException { //주식 정보 보여주는메서드

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


    public void updateCospi(String cosId,double growth) throws SQLException {
        PreparedStatement pstmt = null;
        try {
            pstmt = myDB.getPStmt("UPDATE INFO_COSPI SET COSPI_PRICE = ?," +
                    "GROWTH_F = ?," +
                    "GROWTH_I = ?" +
                    "WHERE COSPI_ID = ?");
            int price = cospiMap.get(cosId).getPrice() + (int)(cospiMap.get(cosId).getPrice() * growth * 0.1);
            pstmt.setInt(1,price);
            pstmt.setDouble(2,growth);
            int tmp = price - cospiMap.get(cosId).getPrice();
            pstmt.setInt(3,tmp);
            pstmt.setString(4,cosId);
            int re = pstmt.executeUpdate();

            if(re>=1){
                System.out.println("주식업데이트성공!");
            } else {
                System.out.println("잘못된 값 입력입니다.");
            }
        } catch (SQLException e) {
            System.out.println("업데이트실패");
        }finally {
            myDB.close(pstmt);
            setCospi();
            updateWallet(cosId);
        }




    }
    public int getRdNum() throws SQLException {
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
            rs.next();
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
    public void updateWallet(String cosId) throws SQLException {
        PreparedStatement pstmt = null;
        int growth = 0;
        try {
            pstmt = myDB.getPStmt("UPDATE WALLETS SET CRC = ?," +
                    "GROWTH = ?," +
                    "TOTAL_PRICE = ?" +
                    "WHERE COSPI_ID = ? AND USER_ID = ?");
            for(int i = 0; i<userName.size(); i++) {
                    if (userMap.get(userName.get(i)).getUserWallet().containsKey(cosId)) {
                        pstmt.setInt(1, cospiMap.get(cosId).getPrice());
                        growth = cospiMap.get(cosId).getPrice() - userMap.get(userName.get(i)).getUserWallet().get(cosId).getBought();
                        pstmt.setInt(2, growth);
                        pstmt.setInt(3, cospiMap.get(cosId).getPrice() * userMap.get(userName.get(i)).getUserWallet().get(cosId).getFigure());
                        pstmt.setString(4, cosId);
                        pstmt.setString(5, userName.get(i));
                        pstmt.executeUpdate();
                    }
                }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            setWallet();
            myDB.close(pstmt);
        }
    }


}
