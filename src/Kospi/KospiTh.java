package Kospi;

import java.util.HashMap;

import static java.lang.Thread.sleep;

public class KospiTh extends Thread{
    final static Integer SAMSUNG = 100;
    final static Integer LG = 101;
    final static Integer SK = 102;
    final static Integer  AHNLAB= 103;
    final static Integer KAKAO = 104;
    final static Integer NAVER = 105;
    final static Integer COUPANG = 106;
    final static Integer AMZN = 107;
    final static Integer HYUNDAI = 108;
    final static Integer APPLE = 109;
    HashMap<Integer, KospiInfo> infoKospi;

    int state;

    public HashMap<Integer, KospiInfo> getInfoKospi() {
        return infoKospi;
    }
    public KospiTh() {
        infoKospi = new HashMap<>();
        infoKospi.put(SAMSUNG,new KospiInfo(SAMSUNG,90000));
        infoKospi.put(LG,new KospiInfo(LG,35000));
        infoKospi.put(SK,new KospiInfo(SK,12000));
        infoKospi.put(AHNLAB,new KospiInfo(AHNLAB,5000));
        infoKospi.put(KAKAO,new KospiInfo(KAKAO,10000));
        infoKospi.put(NAVER,new KospiInfo(NAVER,50000));
        infoKospi.put(COUPANG,new KospiInfo(COUPANG,25000));
        infoKospi.put(AMZN,new KospiInfo(AMZN,60000));
        infoKospi.put(HYUNDAI,new KospiInfo(HYUNDAI,45000));
        infoKospi.put(APPLE,new KospiInfo(APPLE,55000));
    }

    public void setState(int state) {
        this.state = state;
    }

    @Override
    public void run() {
        int change;
        while(state == 0){
            try {
                sleep(10000);
                change = (int)(Math.random() * 3)+1;  //1.상승 2.하락 3.변동없음
                int tmp = (int)(Math.random()*10) + 100;
                switch(change){
                    case 1:
                        infoKospi.get(tmp).price += infoKospi.get(tmp).price/1000 * (int)(Math.random() *30) + 1;
                        break;
                    case 2:
                        infoKospi.get(tmp).price -= infoKospi.get(tmp).price/1000 * (int)(Math.random() *30) + 1;
                        break;
                    case 3:
                        break;
                }
                System.out.print("아마존 : " + infoKospi.get(AMZN).price + " " +
                "안랩 : " +infoKospi.get(AHNLAB).price + " " +
                "엘지 : " +infoKospi.get(LG).price+ " " +
                "에스케이 : " +infoKospi.get(SK).price + " " +
                "애플 : " +infoKospi.get(APPLE).price + " " +
                "쿠팡 : " +infoKospi.get(COUPANG).price + " " +
                "현대 : " +infoKospi.get(HYUNDAI).price + " " +
                "카카오 : " +infoKospi.get(KAKAO).price + " " +
                "네이버 : " +infoKospi.get(NAVER).price + " " +
                "삼성 : " +infoKospi.get(SAMSUNG).price + "\r");
                System.out.println();
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println("파국이다~");
            }

        }
    }
}
