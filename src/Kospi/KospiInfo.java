package Kospi;

public class KospiInfo {
    int unique;
    byte number; //주식갯수
    int price;

    public KospiInfo(int unique, byte number) {
        this.unique = unique;
        this.number = number;
    }
    public KospiInfo(int unique, int price) {
        this.unique = unique;
        this.price = price;
    }

    public int getNumber() {
        return number;
    }

    public int getPrice() {
        return price;
    }
}
