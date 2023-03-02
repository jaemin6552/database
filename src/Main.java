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

        while(true){
            System.out.println("1.로그인 2.주식가격확인 3.회원가입 4.게시판 5.종료");
            String id = null;
            String pwd = null;
            int tmp = sc.nextInt();
            switch (tmp){
                case 1:
                    id = sc.next();
                    pwd = sc.next();
                    break;
                case 2:
                    myDao.showCos_Info();
                    isAlive = false;
                    break;
                case 3:
                    id = sc.next();
                    pwd = sc.next();
                    String name = sc.next();
                    String PH = sc.next();
                    myDao.userJoin(id,pwd,name,PH);
                    break;
                case 4:
                    myDao.showUser_Info();
                    break;
                case 5:
                    System.exit(0);
            }
            if(myDao.rogIn(id,pwd)){
                while (myDao.rogIn(id,pwd)){
                    System.out.println("1.내 주식정보 확인 2.주식 가격 확인 3.구매 4.판매 5.게시판 6.로그아웃 7.종료");
                    tmp = sc.nextInt();
                    switch (tmp){
                        case 1:
                            myDao.showMyWallets(id);
                            break;
                        case 2:
                            myDao.showCos_Info();
                            break;
                        case 3:
                            break;
                        case 4:
                            break;
                        case 5:
                            break;
                        case 6:
                            id = null;
                            pwd = null;
                            break;
                        case 7:
                            System.exit(0);

                    }
                }
            }
        }

    }
}