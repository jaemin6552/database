package User;


import Kospi.KospiInfo;
import Kospi.KospiTh;

import java.util.HashMap;
import java.util.List;

public class UserInfo {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    String id; //아이디 고유번호

    public UserInfo(String id ,String password, String name, String phone, int money) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.money = money;
    }

    String password; //비밀번호
    String name; //이름
    String phone; //핸드폰번호

    int money; //현금 보유량
    HashMap<String,Wallet> userWallet;


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public HashMap<String, Wallet> getUserWallet() {
        return userWallet;
    }

    public void setUserWallet(HashMap<String, Wallet> userWallet) {
        this.userWallet = userWallet;
    }
}
class Wallet{
    String name; //주식이름
    int figure; //주식 보유량
    int totalPrice; //합계가격
    int crc; //현재가격
    int bought; //구매가

    public Wallet(String name, int figure, int totalPrice, int crc, int bought, String buyDate, double growth) {
        this.name = name;
        this.figure = figure;
        this.totalPrice = totalPrice;
        this.crc = crc;
        this.bought = bought;
        this.buyDate = buyDate;
        this.growth = growth;
    }

    public int getFigure() {
        return figure;
    }

    public void setFigure(int figure) {
        this.figure = figure;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getCrc() {
        return crc;
    }

    public void setCrc(int crc) {
        this.crc = crc;
    }

    public int getBought() {
        return bought;
    }

    public void setBought(int bought) {
        this.bought = bought;
    }

    public String getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(String buyDate) {
        this.buyDate = buyDate;
    }

    public double getGrowth() {
        return growth;
    }

    public void setGrowth(double growth) {
        this.growth = growth;
    }

    String buyDate; //구매한날짜
    double growth; //성장률


}
