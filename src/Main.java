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
                    System.out.print("아이디 : ");
                    id = sc.next();
                    System.out.print("비밀번호 : ");
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
                    myDao.showBoard_Info();
                    break;
                case 5:
                    System.exit(0);
            }
            if(myDao.rogIn(id,pwd)){
                while (myDao.rogIn(id,pwd)){
                    System.out.println("1.내 주식정보 확인 2.주식 가격 확인 3.구매 4.판매 5.게시판 6.로그아웃 7.종료");
                    String cos_id = null;
                    int figure = 0;
                    tmp = sc.nextInt();
                    switch (tmp){
                        case 1:
                            myDao.showMyWallets(id);
                            break;
                        case 2:
                            myDao.showCos_Info();
                            break;
                        case 3:
                            System.out.print("사려는 주식이름,갯수 순서대로 입력 : ");
                            cos_id = sc.next();
                            figure = sc.nextInt();
                            myDao.buyCospi(id,cos_id,figure);
                            break;
                        case 4:
                            System.out.print("팔려는 주식이름,갯수 순서대로 입력 : ");
                            cos_id = sc.next();
                            figure = sc.nextInt();
                            myDao.sellCospi(id,cos_id,figure);
                            break;
                        case 5:
                            System.out.println("1.게시판 보기 2.게시판 검색 3.글쓰기 4.글삭제 5.댓글 쓰기 6.댓글 삭제 7.댓글 확인" );
                            int sel = sc.nextInt();
                            switch(sel){
                                case 1:
                                    myDao.showBoard_Info();
                                    break;
                                case 2:
                                    System.out.print("검색할 id : ");
                                    String name = sc.next();
                                    myDao.UsearchBoard_Info(name);
                                    break;
                                case 3:
                                    System.out.print("cospi_id : ");
                                    String cospi_id = sc.next();
                                    System.out.print("user_id : ");
                                    String user_id = sc.next();
                                    System.out.print("제목 : ");
                                    String title = sc.next();
                                    System.out.print("게시글 : ");
                                    String content = sc.next();
                                    myDao.insertBoard_Info(cospi_id, user_id, title, content);
                                    break;
                                case 4:
                                    System.out.print("삭제할 글 번호 : ");
                                    int write_num = sc.nextInt();
                                    myDao.deleteBoard_Info(write_num);
                                    break;
                                case 5:
                                    System.out.println("댓글 달기");
                                    System.out.print("user_id : ");
                                    String user_id1 = sc.next();
                                    System.out.print("글 번호 : ");
                                    int write_num1 = sc.nextInt();
                                    System.out.print("댓글 : ");
                                    String comments_letter = sc.next();
                                    myDao.insertComments_Info(user_id1, write_num1, comments_letter);
                                    break;
                                case 6:
                                    System.out.println("댓글 삭제");
                                    System.out.print("삭제할 댓글 번호 : ");
                                    int comments_num = sc.nextInt();
                                    myDao.deleteComments_Info(comments_num);
                                    break;
                                case 7:
                                    myDao.showComments_Info();
                                    break;
                            }
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