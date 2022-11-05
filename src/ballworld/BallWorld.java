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
import java.util.List;


public class BallWorld extends JFrame {



    class MyMouseListener implements MouseMotionListener{

        @Override
        public void mouseDragged(MouseEvent e) {

        }

        @Override
        public void mouseMoved(MouseEvent e) {

            System.out.println(e.getX() +","+e.getY());
        }
    }
    static class PaddSquare implements SquarePad{



        @Override
        public void pad() {

        }



    }

    public static void main (String [ ] args)
    {
        BallWorld world = new BallWorld (Color.red);
        world.setVisible (true);
    }

    private static final int FrameWidth = 600;
    private static final int FrameHeight = 400;

    private List<Ball> balls;


    private JPanel mainPanel;



    private BallWorld (Color ballColor) {
        // constructor for new ball world

        balls = new ArrayList<Ball>();
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

                for (Ball aBall: balls) {
                    aBall.paint (g);
                }


            }
        };

        MyMouseListener mml = new MyMouseListener();
        mainPanel.addMouseMotionListener(mml);
        PaddSquare pd = new PaddSquare();
        if(pd instanceof PaddSquare){
        mainPanel.addMouseMotionListener((MouseMotionListener) pd);
        }
       /*  MyMouseListener squarePad = new MyMouseListener(); {


            }
        }; */


        mainPanel.setPreferredSize(new Dimension(FrameWidth,FrameHeight));
        this.add(mainPanel);
        this.pack();
        // initialize object data field
        Color[] colors = new Color[]{Color.red, Color.black, Color.blue, Color.pink, Color.cyan};
        Ball aBall;
        for(int i=0; i<5; i++){
            if(i%2==0)
                aBall = new SquareBall ((i*10)+10, (i*10)+15, i*5+10);
            else
                aBall = new Ball ((i*10)+10, (i*10)+15, i*5+10);
            aBall.setColor (colors[i]);
            aBall.setMotion (i+1, i+1);

            balls.add(aBall);

        }



        //Köşedeki çarpıya basılınca uygulamanın kapanması için
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Timer start çağrıldığında, her on milisaniyede bir actionPerformed metodu çağrılır
        Timer timer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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