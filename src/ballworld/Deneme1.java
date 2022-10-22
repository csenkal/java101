package ballworld;

import java.sql.SQLOutput;
import java.util.LinkedList;

public class Deneme1 {
    public static void main(String[] args) {
        LinkedList<Ball> aball = new LinkedList<Ball>();
        Ball b1 = new Ball(10, 15, 20);
        Ball b2 = new Ball(12,17,22);
        Ball b3 = new Ball(14,19,24);
        Ball b4 = new Ball(16,21,26);
        Ball b5 = new Ball(18,23,28);
        aball.add(b1);
        aball.add(b2);
        aball.add(b3);
        aball.add(b4);
        aball.add(b5);
        System.out.println(aball);
        }


}
