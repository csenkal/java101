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
import java.util.LinkedList;
import java.util.Random;


public class ShapeWorld extends JFrame {

    private static final int FrameWidth = 600;
    private static final int FrameHeight = 400;
    //private Ball aBall;
    LinkedList<Ball> Balls = new LinkedList<>();
    LinkedList<Square> Squares = new LinkedList<>();
    //ArrayList LinkedList olarak değiştirildi
    private JPanel mainPanel;

    Ball collisionchecker;



    public ShapeWorld(Color ballColor) {
        // constructor for new ball world

        setTitle ("Ball World");

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


                for(Ball ball :Balls)
                {
                    ball.paint(g);
                }

                for(Square squares :Squares)
                {
                    squares.paint(g);
                }



                //aBall.paint (g);
            }
        };
        mainPanel.setPreferredSize(new Dimension(FrameWidth,FrameHeight));
        this.add(mainPanel);
        this.pack();
        // initialize object data field

        for(int x=0;x<3;x++)
        {
            Random rand = new Random();
            Color color = new Color(rand.nextInt(0xFFFFFF));
            //her top için ayrı bir random renk üretiliyor
            Balls.add(new Ball(100,150,20));
            Balls.get(x).setColor(color);
            Balls.get(x).setMotion((x+1)*3,(x+1)*3);

        }




        for(int x=0;x<2;x++)
        {
            Random rand = new Random();
            Color color = new Color(rand.nextInt(0xFFFFFF));
            //her top için ayrı bir random renk üretiliyor
            Squares.add(new Square(400,100,15));
            Squares.get(x).setColor(color);
            Squares.get(x).setMotion((x+0.5)*5,(x+0.5)*2);


        }











        //Köşedeki çarpıya basılınca uygulamanın kapanması için
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Timer start çağrıldığında, her on milisaniyede bir actionPerformed metodu çağrılır
        Timer timer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Topun konumu dx,dy kadar değiştirilir

                for(Ball ball :Balls)
                {
                    ball.move();
                    ball.checkCollision(mainPanel.getWidth(),mainPanel.getHeight());
                }


                for(Square squares :Squares)
                {
                    squares.move();
                    squares.checkCollision(mainPanel.getWidth(),mainPanel.getHeight());
                }


                for (int i = 0; i < Balls.size(); i++) {
                    for (int j = 0; j < Balls.size(); j++) {
                        if (i < j) {

                            collisionchecker.intersect(Balls.get(i), Balls.get(j));
                        }
                    }
                }





               // Balls.get(0).checkCollisionBall(Balls.get(0).x(),Balls.get(0).y(), Balls.get(1).x(),Balls.get(1).y());
                //System.out.println(Balls.get(0).x());





                //Ekran dışına çıktıysa geri dönmesi sağlanır

                //Ekrandaki değişikliklerin çizilmesi için repaint in çağrılması gerekir
                mainPanel.repaint();
            }
        });
        timer.start();
    }

}