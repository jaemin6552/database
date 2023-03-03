package User;


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

    public UserInfo(String id ,String password, String name, String phone, int money,HashMap<String,Wallet> userWallet ) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.money = money;
        this.userWallet = userWallet;
    }

    String password; //비밀번호
    String name; //이름
    String phone; //핸드폰번호

    int money; //현금 보유량

    public HashMap<String, Wallet> getUserWallet() {
        return userWallet;
    }


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


}
