package Kospi;

public class CospiInfo {

    String name;

    int unique;

    int price;

    int growth_I;

    double growth_f;

    public CospiInfo(String name, int unique, int price, int growth_I, double growth_f) {
        this.name = name;
        this.unique = unique;
        this.price = price;
        this.growth_I = growth_I;
        this.growth_f = growth_f;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUnique() {
        return unique;
    }

    public void setUnique(int unique) {
        this.unique = unique;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getGrowth_I() {
        return growth_I;
    }

    public void setGrowth_I(int growth_I) {
        this.growth_I = growth_I;
    }

    public double getGrowth_f() {
        return growth_f;
    }

    public void setGrowth_f(double growth_f) {
        this.growth_f = growth_f;
    }
}
