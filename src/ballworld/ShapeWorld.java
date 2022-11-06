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
import java.awt.event.*;
import java.util.Formatter;
import java.util.LinkedList;
import java.util.Random;
import java.awt.MouseInfo;



public class ShapeWorld extends JFrame implements MouseMotionListener {

    private static final int FrameWidth = 600;
    private static final int FrameHeight = 400;
    //private Ball aBall;
    LinkedList<Ball> Balls = new LinkedList<>();
    LinkedList<Brick> Bricks = new LinkedList<>();
    //LinkedList<Square> Squares = new LinkedList<>();
    //ArrayList LinkedList olarak değiştirildi
    private JPanel mainPanel;

    int xcoordinateMouse;
    int ycoordinateMouse;


    Ball collisionchecker;



    public ShapeWorld(Color ballColor) {
        // constructor for new ball world
        // addMouseListener(new MouseListen());
        super();
        addMouseMotionListener(this);

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

                for(Brick brick :Bricks)
                {
                    brick.paint(g);

                }



                g.setColor(Color.BLACK);
                g.setFont(new Font("Courier New", Font.PLAIN, 12));
                StringBuilder sb = new StringBuilder();
                Formatter formatter = new Formatter(sb);
                formatter.format("Mouse X "+ xcoordinateMouse);
                formatter.format("  Mouse Y "+ ycoordinateMouse);

                g.drawString(sb.toString(), 5, 20);


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
            Balls.add(new Ball((x+1)*100,(x+1)*50,20));
            Balls.get(x).setColor(color);
            Balls.get(x).setMotion((x+1)*3,(x+1)*3);

        }


        Bricks.add((new Brick(240,400,40)));
        Bricks.get(0).setAsGoalKeeper();
        Bricks.get(0).setColor(Color.green);


        for(int x=1;x<8;x++)
        {

            Bricks.add((new Brick((x+1)*60,75,15)));
            Bricks.get(x).setColor(Color.BLUE);

        }


/*

        for(int x=0;x<2;x++)
        {
            Random rand = new Random();
            Color color = new Color(rand.nextInt(0xFFFFFF));
            //her top için ayrı bir random renk üretiliyor
            Balls.add(new Square((x+3)*100,(x+3)*50,20));
            Balls.get(x).setColor(color);
            Balls.get(x).setMotion((x+3)*3,(x+3)*3);


        }

*/









        //Köşedeki çarpıya basılınca uygulamanın kapanması için
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Timer start çağrıldığında, her on milisaniyede bir actionPerformed metodu çağrılır
        Timer timer = new Timer(40, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Topun konumu dx,dy kadar değiştirilir

                for(Ball ball :Balls)
                {
                    ball.move();
                    ball.checkCollision(mainPanel.getWidth(),mainPanel.getHeight());
                }





                for (int i = 0; i < Balls.size(); i++) {
                    for (int j = 0; j < Balls.size(); j++) {
                        if (i < j) {

                            if(collisionchecker.intersect(Balls.get(i), Balls.get(j)) == "true")
                            {
                                Balls.remove(i);

                            }

                        }
                    }
                }





                for (int i = 0; i < Balls.size(); i++) {
                    for (int j = 0; j < Bricks.size(); j++) {
                         {

                            if(collisionchecker.intersect(Balls.get(i), Bricks.get(j)) == "true")
                            {


                                Balls.get(i).dy = -Balls.get(i).dy;


                                //  ilk brick goalkeeper olduğu için silinmiyor.
                                if(j!=0)
                                Bricks.remove(j);

                            }


                        }
                    }
                }

                if(Bricks.size()>0) {
                    Bricks.get(0).moveTo(xcoordinateMouse, 350);
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








    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {





        xcoordinateMouse = e.getX();
        ycoordinateMouse = e.getY();
        System.out.println(e.getX()+"X"+ e.getY()+ "Y");
        setTitle ("Ball Click World");



    }
}