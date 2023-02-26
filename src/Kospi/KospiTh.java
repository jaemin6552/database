package Kospi;

import User.MyDao;

import java.sql.SQLException;
import java.util.HashMap;

import static java.lang.Thread.sleep;

public class KospiTh extends Thread{

    public static boolean isAlive = true;
    MyDao myDao;

    public KospiTh() {

        isAlive = true;
        try {
            myDao = new MyDao();
        } catch (SQLException e) {
            System.out.println("연결실패");
        } catch (ClassNotFoundException e) {
            System.out.println("연결실패");
        }
    }

    @Override
    public  void run() {
        int change;
        while(true){
            try {
                sleep(10000);
                change  = (int)(Math.random() * 3)+1;  //1.상승 2.하락 3.변동없음
                double growth = Math.random()*10;
                switch(change){
                    case 1: case 2: case 3:
                        String tmp = myDao.getRdCospi();
                        System.out.println(tmp);
                        myDao.updateCospi("삼성",growth);
                        break;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println("파국이다~");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            if(isAlive){

            }

        }
    }
}
