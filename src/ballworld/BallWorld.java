package ballworld;

//
//	the Ball World game
//	Described in Chapter 5 of
//	Understanding Object-Oriented Programming with Java
//	by Timothy A Budd
//	Published by Addison-Wesley
//
//	see ftp://ftp.cs.orst.edu/pub/budd/java/ReadMe.html
//	for further information
//

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;


public class BallWorld extends JFrame {
    MouseMotionListener action = new MouseMotionListener() {
        @Override
        public void mouseDragged(MouseEvent e) {

        }

        @Override
        public void mouseMoved(MouseEvent e) {
            newT.moveTo(e.getX(),900);
        }
    };
    public static void main (String [ ] args)
    {
        BallWorld world = new BallWorld (Color.red);
        world.setVisible (true);
    }

    private static final int FrameWidth = 2000;
    private static final int FrameHeight = 1650;



    LinkedList<Ball> Ball_List = new LinkedList<>();

    ArrayList<Brick> Brick_List = new ArrayList<>();

    TAB newT = new TAB(300, 430,60);         // Ekran yüzdesine göre bir değer ekelencek  Curser konumuna göre değişim eklenecek
    private JPanel mainPanel;



    private BallWorld (Color ballColor) {


        // constructor for new ball world

        Scanner sc= new Scanner(System.in);
        System.out.print("Daire sayısı - ");
        int Dsayı= sc.nextInt();

        System.out.print("Dikdörtgen sayısı - ");
        int İsayı= sc.nextInt();



        setTitle ("Balls World");

        //Tüm çizimler mainPanel üzerinde yapılıyor
        mainPanel = new JPanel(){
            @Override
            //repaint çağrıldığında swing bu metodu çağırır
            protected void paintComponent(Graphics g) {
                //Şekillerin köşelerinin düzgün gözükmesini sağlar

                Graphics2D g2d = (Graphics2D) g;
                g2d.setStroke(new BasicStroke(4, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                //Önce tüm ekran temizlenir
                g.clearRect(0,0, mainPanel.getWidth(), mainPanel.getHeight());
                //Sonra top yeni yerinde çizilir
                for (Ball aBall :Ball_List) {
                    aBall.paint(g);

                }
                for (Brick dbrick :Brick_List) {
                    dbrick.paint(g);
                }
                newT.paint(g);

            }
        };

        mainPanel.addMouseMotionListener(action);

        mainPanel.setPreferredSize(new Dimension(FrameWidth,FrameHeight));
        this.add(mainPanel);
        this.pack();
        // initialize object data field
        for (int i=0; i<İsayı;i++) {
            Rectangel aRec= new Rectangel(60, 200, 20);
            aRec.setColor(new Color((int)(Math.random() * 0x1000000)));
            aRec.setMotion(3, 2.0+i);
            Ball_List.add(aRec);
        }
        for (int i=0; i<Dsayı;i++) {
            Ball aBall= new Ball(100, 200, 20);
            aBall.setColor(new Color((int)(Math.random() * 0x1000000)));
            aBall.setMotion(3+i, 2+2*i);
            Ball_List.add(aBall);
        }

        for (int i=0; i<40;i++) {
            Brick brick = new Brick(50+50*i, 50, 10);
            Brick_List.add(brick);

        }


        //Köşedeki çarpıya basılınca uygulamanın kapanması için
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Timer start çağrıldığında, her on milisaniyede bir actionPerformed metodu çağrılır
        Timer timer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Topun konumu dx,dy kadar değiştirilir
                for (int i =0;i<Ball_List.size();i++) {
                    Ball_List.get(i).move();

                    //Ekran dışına çıktıysa geri dönmesi sağlanır
                    Ball_List.get(i).tabCollision(newT,Ball_List.get(i));
                    Ball_List.get(i).checkCollision(mainPanel.getWidth(), mainPanel.getHeight());
                    for (int j =0;j<Brick_List.size();j++) {
                        //Brick_List.get(i).Collission2(Ball_List.get(i),Brick_List.get(j));
                        if (Brick_List.get(i).Collission2(Ball_List.get(i),Brick_List.get(j)) == 1)          // TAB collision ı eklenecek
                            Brick_List.remove(j);
                    }

                }
                //Ekrandaki değişikliklerin çizilmesi için repaint in çağrılması gerekir
                mainPanel.repaint();
            }
        });
        timer.start();
    }

}