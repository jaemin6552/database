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
        setCospi();
        setWallet();
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
            //System.out.println("????????? ???????????????");
            rs = pstmt.executeQuery();
            //System.out.println("????????????");
            while(rs.next()){
                System.out.println(rs.getString("COSPI_ID") + " "
                        +rs.getString("USER_ID") + " "
                        + rs.getInt("WRITE_NUM") + " "
                        + rs.getString("TITLE") + " "
                        + rs.getString("CONTENT"));
            }
        }catch (SQLException e){
            System.out.println("??????");
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
                        return true;
                    }
                    System.out.println("??????????????? ???????????? ????????????.");
                    return false;
                }
            }

        System.out.println("?????? ????????? ?????????.");
        return false;
    }
    public void showMyWallets(String id) throws SQLException {
        setWallet();
        setUser_Info();

        System.out.println(id + "?????? ?????? ?????? ??????");
        for(String key : userMap.get(id).getUserWallet().keySet()){
            System.out.println("???????????? : " + userMap.get(id).getUserWallet().get(key).getName());
            System.out.println("????????? : " + userMap.get(id).getUserWallet().get(key).getFigure());
            System.out.println("??????????????? : " + userMap.get(id).getUserWallet().get(key).getBuyDate());
            System.out.println("???????????? : " + userMap.get(id).getUserWallet().get(key).getCrc());
            System.out.println("????????? : " + userMap.get(id).getUserWallet().get(key).getBought());
            System.out.println("?????? : " + userMap.get(id).getUserWallet().get(key).getTotalPrice());
            System.out.println("?????? : " + userMap.get(id).getUserWallet().get(key).getGrowth());
            System.out.println("================================================================");
        }
        System.out.println(id + "?????? ????????? : " + userMap.get(id).getMoney());
    }
    ///////////////////////////////////////////////////////////////////////////////////
    public void buyCospi(String user_id,String cospi_id,int figure) throws SQLException {
        PreparedStatement pstmt = null;
        int buyCost = cospiMap.get(cospi_id).getPrice() * figure;

        if(userMap.get(user_id).getMoney() < buyCost) {
            System.out.println("???????????? ???????????????.");
            return;
        }

        if(userMap.get(user_id).getUserWallet().containsKey(cospi_id)){
            try {
                pstmt = myDB.getPStmt("UPDATE WALLETS" +
                        " SET FIGURE = ?,TOTAL_PRICE = (SELECT COSPI_PRICE FROM INFO_COSPI WHERE COSPI_ID = ?) * ?, " +
                        "GROWTH = ?, BOUGHT = ?, BUY_DATE = SYSDATE " +
                        " WHERE COSPI_ID = ? AND USER_ID = ?");
                int count = figure + userMap.get(user_id).getUserWallet().get(cospi_id).getFigure();
                int prevCost = userMap.get(user_id).getUserWallet().get(cospi_id).getBought() * count;
                int nowCost = userMap.get(user_id).getUserWallet().get(cospi_id).getCrc() * count;
                int benefit = ((userMap.get(user_id).getUserWallet().get(cospi_id).getBought() * userMap.get(user_id).getUserWallet().get(cospi_id).getFigure()) +
                        (cospiMap.get(cospi_id).getPrice() * figure )) / count;
                pstmt.setInt(1,count);
                pstmt.setString(2,cospi_id);
                pstmt.setInt(3,count);
                pstmt.setInt(4,nowCost - prevCost);
                pstmt.setInt(5,benefit);
                pstmt.setString(6,cospi_id);
                pstmt.setString(7,user_id);
                pstmt.executeUpdate();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }finally {
                myDB.close(pstmt);
            }
        }else{
            try {
                pstmt = myDB.getPStmt("INSERT INTO WALLETS VALUES(?,?,?,(SELECT COSPI_PRICE FROM INFO_COSPI WHERE COSPI_ID = ?) * ?,SYSDATE,?,?,?)");
                pstmt.setString(1, user_id);
                pstmt.setString(2, cospi_id);
                pstmt.setInt(3, figure);
                pstmt.setString(4, cospi_id);
                pstmt.setInt(5, figure);
                pstmt.setInt(6, cospiMap.get(cospi_id).getPrice());
                pstmt.setInt(7, cospiMap.get(cospi_id).getPrice());
                pstmt.setInt(8, 0);
                pstmt.executeUpdate();

            }catch (SQLException e){
                e.printStackTrace();
            }finally {
                myDB.close(pstmt);
            }
        }
        setWallet();
        updateUser(buyCost,user_id);
    }
    public void sellCospi(String user_id,String cospi_id,int figure) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int sellCost = cospiMap.get(cospi_id).getPrice() * figure;
        if(!userMap.get(user_id).getUserWallet().containsKey(cospi_id)){
            System.out.println("?????????????????? ???????????????.");
            return;
        }
        if(userMap.get(user_id).getUserWallet().get(cospi_id).getFigure() < figure){
            System.out.println("??????????????? ???????????????.");
            return;
        } else if(userMap.get(user_id).getUserWallet().get(cospi_id).getFigure() == figure){
            try {
                pstmt = myDB.getPStmt("DELETE FROM WALLETS " +
                       " WHERE USER_ID = ? AND COSPI_ID = ?");
                pstmt.setString(1,user_id);
                pstmt.setString(2,cospi_id);
                userMap.get(user_id).getUserWallet().remove(cospi_id);
                pstmt.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }finally {
                myDB.close(pstmt);
            }
        } else{
            try {
                int rest = userMap.get(user_id).getUserWallet().get(cospi_id).getFigure() - figure;
                pstmt = myDB.getPStmt("UPDATE WALLETS " +
                        "SET FIGURE = ?" +
                        "WHERE USER_ID = ? AND COSPI_ID = ?");
                pstmt.setInt(1,rest);
                pstmt.setString(2,user_id);
                pstmt.setString(3,cospi_id);
                pstmt.executeUpdate();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }finally {
                myDB.close(pstmt);
            }
        }
        setWallet();
        updateUser(-sellCost,user_id);

    }
    //////////////////////////////////////////////////////////////////////////////////////////////////
    public boolean userJoin(String user_id,String user_pwd,String name,String PH) throws SQLException {
        for(int i = 0; i<userName.size(); i++){

            if(user_id.equals(userName.get(i))){
                System.out.println("???????????? ???????????????");
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
        }
        setUser_Info();
        return true;
    }
    public void setUser_Info() throws SQLException {
        PreparedStatement pstmt =null;
        ResultSet rs = null;
        try {
            pstmt = myDB.getPStmt("SELECT * FROM USER_TABLE");
            rs = pstmt.executeQuery();
            while(rs.next()){
                if(userMap.containsKey(rs.getString("USER_ID"))) {
                    userMap.put(rs.getString("USER_ID"),
                            new UserInfo(rs.getString("USER_ID"),
                                    rs.getNString("USER_PWD"),
                                    rs.getString("NAME"),
                                    rs.getString("PH"),
                                    rs.getInt("MONEY"),
                                    userMap.get(rs.getString("USER_ID")).getUserWallet()));
                    }else {
                    userMap.put(rs.getString("USER_ID"),
                            new UserInfo(rs.getString("USER_ID"),
                                    rs.getNString("USER_PWD"),
                                    rs.getString("NAME"),
                                    rs.getString("PH"),
                                    rs.getInt("MONEY"),
                                    new HashMap<>()));
                }
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

    public void showCos_Info() throws SQLException { //?????? ?????? ?????????????????????

        PreparedStatement pstmt = myDB.getPStmt("SELECT ROWNUM, INFO_COSPI.* FROM INFO_COSPI ");
        ResultSet rs =  pstmt.executeQuery();
        //System.out.printf("[     ??????     ]" + " [  ??????  ]" + "[ ????????? ]" + "[ ???????????? ]\n");
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

    public void updateUser(int money,String user_id) throws SQLException {
        PreparedStatement pstmt = null;
        try {
            pstmt = myDB.getPStmt("UPDATE USER_TABLE SET MONEY = ?" +
                    "WHERE USER_ID = ?");
            pstmt.setInt(1,userMap.get(user_id).getMoney() - money);
            pstmt.setString(2,user_id);
            pstmt.executeUpdate();
        }catch (SQLException e){
                e.printStackTrace();
        }finally {
            setUser_Info();
            myDB.close(pstmt);
        }
    }
    public void updateCospi(String cosId,double growth) throws SQLException {
        PreparedStatement pstmt = null;
        try {
            pstmt = myDB.getPStmt("UPDATE INFO_COSPI SET COSPI_PRICE = ?," +
                    "GROWTH_F = ?," +
                    "GROWTH_I = ?" +
                    "WHERE COSPI_ID = ?");
            int price = cospiMap.get(cosId).getPrice() + (int)(cospiMap.get(cosId).getPrice() * growth * 0.05);
            pstmt.setInt(1,price);
            pstmt.setDouble(2,growth);
            int tmp = price - cospiMap.get(cosId).getPrice();
            pstmt.setInt(3,tmp);
            pstmt.setString(4,cosId);
            int re = pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("??????????????????");
        }finally {
            myDB.close(pstmt);
        }
        setCospi();
        updateWallet(cosId);

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
            System.out.println("getRdNum????????????");
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
            return "getRdID????????????";
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

            myDB.close(pstmt);
        }
        setWallet();
    }


}
