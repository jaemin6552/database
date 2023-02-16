import Kospi.KospiTh;
import User.UserInfo;
import util.DBConn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        //Connection conn = DBConn.getDbConn();
        Scanner sc = new Scanner(System.in);
        KospiTh demonKospiTh = new KospiTh();
        demonKospiTh.setDaemon(true);
        demonKospiTh.start();
        int value;
        byte count;
        UserInfo userInfo = new UserInfo("JM","qkqh","7000","01065523739",1000000);

        while(true){
            System.out.println("1.구매 2.판매 3.주식및소지금확인 4.종료");
            int tmp = sc.nextInt();
            switch (tmp){
                case 1:

                    System.out.print("구매 : SAMSUNG = 100 LG = 101 SK = 102 AHNLAB= 103 KAKAO = 104 NAVER = 105 COUPANG = 106 AMZN = 107 HYUNDAI = 108 APPLE = 109\r");
                    value = sc.nextInt();
                    count = sc.nextByte();
                    userInfo.buyKOSPI(value,count,demonKospiTh);
                    break;
                case 2:
                    System.out.print("판매 : SAMSUNG = 100 LG = 101 SK = 102 AHNLAB= 103 KAKAO = 104 NAVER = 105 COUPANG = 106 AMZN = 107 HYUNDAI = 108 APPLE = 109\r");
                    value = sc.nextInt();
                    count = sc.nextByte();
                    userInfo.sellKOSPI(value,count,demonKospiTh);
                    break;
                case 3:
                    userInfo.getWalletInfo();
                    break;
                case 4:
                    System.exit(0);
            }
        }

    }
}