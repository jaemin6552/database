import Kospi.KospiTh;
import User.MyDao;
import User.UserInfo;
import util.DBConn;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import static Kospi.KospiTh.isAlive;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Scanner sc = new Scanner(System.in);
        MyDao myDao = new MyDao();
        KospiTh demonKospiTh = new KospiTh();
        demonKospiTh.setDaemon(true);
        demonKospiTh.start();
        myDao.setUser_Info();
        myDao.setWallet();
        while(true){
            System.out.println("1.구매 2.판매 3.주식및소지금확인 4.종료");
            isAlive = true;
            int tmp = sc.nextInt();
            switch (tmp){
                case 1:

                    myDao.showCos_Info();
                    break;
                case 2:
                    myDao.showUser_Info();
                    isAlive = false;
                    break;
                case 3:
                    break;
                case 4:
                    System.exit(0);
            }
        }

    }
}