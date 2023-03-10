package User;

public class Wallet {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
