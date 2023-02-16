package User;


import Kospi.KospiInfo;
import Kospi.KospiTh;

import java.util.HashMap;
import java.util.List;

public class UserInfo {
    String id; //아이디
    String password; //비밀번호
    String num; //고유번호
    String phone; //핸드폰번호

    int money; //현금 보유량
    HashMap<Integer, KospiInfo> Wallet; //주식 보유량

    public UserInfo(String id, String password, String num, String phone, int money) {
        this.id = id;
        this.password = password;
        this.num = num;
        this.phone = phone;
        this.money = money;
        Wallet = new HashMap<>();
    }

    public void buyKOSPI(int key,byte num,KospiTh kospiTh){
        int price = kospiTh.getInfoKospi().get(key).getPrice();
        if(this.money > price) {
            if (Wallet.get(key) == null) {
                Wallet.put(key, new KospiInfo(key, num));
            }
            else {
                Wallet.put(key, new KospiInfo(key, Wallet.get(key).getNumber() + num));
            }
            this.money -=price * num;
        } else System.out.println(" 돈이 부족합니다! 구매 불가능!! ");
    }
    public void sellKOSPI(int key,int num,KospiTh kospiTh){
        int price = kospiTh.getInfoKospi().get(key).getPrice();
        if(Wallet.get(key).getNumber() - num >= 0 ){
            if(Wallet.get(key).getNumber() - num == 0) {
                Wallet.remove(key);
            } else Wallet.put(key,new KospiInfo(key,Wallet.get(key).getNumber() - num));
            this.money += price * num;
        } else System.out.println("판매하려는 수량을 보유하고 있지않습니다.");
    }

    public void getWalletInfo() {
        for(Integer key : Wallet.keySet()){
            System.out.println("보유 주식 : " + key);
            System.out.println("보유 량 : " + Wallet.get(key).getNumber());
        }
        System.out.println("소지금 : "+ this.money);
    }

    public int getMoney() {
        return money;
    }
}
