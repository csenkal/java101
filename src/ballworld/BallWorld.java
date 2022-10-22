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
import java.util.Iterator;
import java.util.LinkedList;


public class BallWorld extends JFrame {

    public static void main (String [ ] args)
    {
        BallWorld world = new BallWorld (Color.red);
        world.setVisible (true);
        LinkedList<Ball> aBall = new LinkedList<>();
        Ball b1 = new Ball(10, 15, 20);
        Ball b2 = new Ball(12, 17, 22);
        Ball b3 = new Ball(14, 19, 24);
        Ball b4 = new Ball(16, 21, 26);
        Ball b5 = new Ball(18, 23, 28);
        aBall.add(b1);
        aBall.add(b2);
        aBall.add(b3);
        aBall.add(b4);
        aBall.add(b5);

    }


    private static final int FrameWidth = 600;
    private static final int FrameHeight = 400;
    private Ball aBall;

    private JPanel mainPanel;



    private BallWorld (Color ballColor) {
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
                aBall.paint (g);
            }
        };
        mainPanel.setPreferredSize(new Dimension(FrameWidth,FrameHeight));
        this.add(mainPanel);
        this.pack();
        // initialize object data field
        aBall = new Ball (10, 15, 20);
        aBall.setColor (ballColor);
        aBall.setMotion (1.0, 1.0);

        //Köşedeki çarpıya basılınca uygulamanın kapanması için
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Timer start çağrıldığında, her on milisaniyede bir actionPerformed metodu çağrılır
        Timer timer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Topun konumu dx,dy kadar değiştirilir
                aBall.move();
                //Ekran dışına çıktıysa geri dönmesi sağlanır
                aBall.checkCollision(mainPanel.getWidth(),mainPanel.getHeight());
                //Ekrandaki değişikliklerin çizilmesi için repaint in çağrılması gerekir
                mainPanel.repaint();
            }
        });
        timer.start();
    }

}