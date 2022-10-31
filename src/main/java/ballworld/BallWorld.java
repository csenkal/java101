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


public class BallWorld extends JFrame {

    public static void main (String [ ] args)
    {
        BallWorld world = new BallWorld (Color.red);
        world.setVisible (true);
    }

    private static final int FrameWidth = 600;
    private static final int FrameHeight = 400;

    private LinkedList<RectangleBall> recballs;
    private LinkedList<Ball> balls;



    private JPanel mainPanel;



    private BallWorld (Color ballColor) {
        // constructor for new ball world

        recballs = new LinkedList<RectangleBall>();
        balls = new LinkedList<Ball>();
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

                for (RectangleBall aRecBall: recballs) {
                    aRecBall.paint (g);
                }

                for (Ball aBall: balls) {
                    aBall.paint (g);
                }


            }
        };
        mainPanel.setPreferredSize(new Dimension(FrameWidth,FrameHeight));
        this.add(mainPanel);
        this.pack();
        // initialize object data field
        Color[] colors = new Color[]{Color.red, Color.black, Color.blue, Color.pink, Color.cyan};
        for(int i=0; i<3; i++){
            RectangleBall aRecBall = new RectangleBall ((i*10)+10, (i*10)+15, i*5+10);
            aRecBall.setColor (colors[i]);
            aRecBall.setMotion (i+1, i+1);
            recballs.add(aRecBall);

        }

        for(int i=0; i<2; i++){
            Ball aBall = new Ball ((i*30)+10, (i*20)+15, i*10+10);
            aBall.setColor (colors[i]);
            aBall.setMotion (i+5, i+3);
            balls.add(aBall);

        }


        //Köşedeki çarpıya basılınca uygulamanın kapanması için
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Timer start çağrıldığında, her on milisaniyede bir actionPerformed metodu çağrılır
        Timer timer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                for (RectangleBall aRecBall: recballs) {
                    //Topun konumu dx,dy kadar değiştirilir
                    aRecBall.move();
                    //Ekran dışına çıktıysa geri dönmesi sağlanır
                    aRecBall.checkCollision(mainPanel.getWidth(),mainPanel.getHeight());
                }

                for (Ball aBall: balls) {
                    //Topun konumu dx,dy kadar değiştirilir
                    aBall.move();
                    //Ekran dışına çıktıysa geri dönmesi sağlanır
                    aBall.checkCollision(mainPanel.getWidth(),mainPanel.getHeight());
                }

                //Ekrandaki değişikliklerin çizilmesi için repaint in çağrılması gerekir
                mainPanel.repaint();
            }
        });
        timer.start();
    }

}