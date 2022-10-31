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
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;


public class BallWorld extends JFrame {

    public static void main (String [ ] args)
    {
        BallWorld world = new BallWorld (Color.red);
        world.setVisible (true);
    }

    private static final int FrameWidth = 1920;
    private static final int FrameHeight = 1000;

    private List<Ball> balls;
    private List<Brick> bricks;


    private GameMovingObject pad = new GameMovingObject(100,390, 300, 100);

    private JPanel mainPanel;



    private BallWorld (Color ballColor) {
        // constructor for new ball world



        balls = new LinkedList<>();
        bricks = new LinkedList<>();

        for(int i = 0; i<50;i++){
            Brick b = new Brick(i*30 +i+30,100,30,50);
            bricks.add(b);
        }


        setTitle ("Ball World");

        //Tüm çizimler mainPanel üzerinde yapılıyor
        mainPanel = new JPanel(){
            @Override
            //repaint çağrıldığında swing bu metodu çağırır
            protected void paintComponent(Graphics g) {
                //Şekillerin köşelerinin düzgün gözükmesini sağlar
                final Graphics2D g2d = (Graphics2D) g;
                g2d.setStroke(new BasicStroke(4, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);


                //Önce tüm ekran temizlenir
                g.clearRect(0,0, mainPanel.getWidth(), mainPanel.getHeight());
                //Sonra top yeni yerinde çizilir

                balls.forEach(ball->ball.paint(g));
                bricks.forEach(brick -> brick.paint(g));

                pad.paint(g);
            }
        };
        mainPanel.setPreferredSize(new Dimension(FrameWidth,FrameHeight));
        mainPanel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                double velocity = Math.abs(pad.getLocation().getX()-e.getX());
                pad.setMotion((int)velocity,0);
                //System.out.println(velocity);
                pad.moveTo(e.getX(),mainPanel.getHeight()-100);

            }
        });


        this.add(mainPanel);
        this.pack();
        // initialize object data field
        Random random = new Random(System.currentTimeMillis());

        for(int i=0; i<5; i++){
            int x = random.nextInt(mainPanel.getWidth());
            int y = random.nextInt(mainPanel.getHeight());
            int r = 30;
            Ball aBall = i%2==0 ? new Ball (x, y, r)  : new SquareBall (x, y, r);
            aBall.setColor (new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
            aBall.setMotion (1, 1);

            balls.add(aBall);

        }


        //Köşedeki çarpıya basılınca uygulamanın kapanması için
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        Runnable r = new Runnable() {
            @Override
            public void run() {

                while(true){

                    try {
                        Thread.sleep(16);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    LinkedList<Ball> disposed  = new LinkedList<>();
                    balls.forEach(ball -> {

                        ball.move();
                        int[] state = ball.checkCollision(mainPanel.getWidth(), mainPanel.getHeight());
                        ball.setMotion(ball.xSpeed*state[0]*state[1], ball.ySpeed*state[2]*state[3]);
                        if(state[3]!=1)
                            disposed.add(ball);
                        ball.checkCollision(pad);

                        Iterator<Brick> it = bricks.iterator();
                        while(it.hasNext()){
                            boolean collided = ball.checkCollision(it.next());
                            if(collided) {
                                it.remove();
                                break;
                            }
                        }




                    });
                    balls.removeAll(disposed);
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            mainPanel.repaint();
                        }
                    });
                }


            }
        };
        Thread t = new Thread(r);
        t.start();
//        Timer timer = new Timer(1, null);
//        timer.setCoalesce(false);
//        //Timer start çağrıldığında, her on milisaniyede bir actionPerformed metodu çağrılır
//        timer.addActionListener(event-> {
//                LinkedList<Ball> disposed  = new LinkedList<>();
//                balls.forEach(ball -> {
//
//                    ball.move();
//                    int[] state = ball.checkCollision(mainPanel.getWidth(), mainPanel.getHeight());
//                    ball.setMotion(ball.xSpeed*state[0]*state[1], ball.ySpeed*state[2]*state[3]);
//                    if(state[3]!=1)
//                        disposed.add(ball);
//                    ball.checkCollision(pad);
//
//                    Iterator<Brick> it = bricks.iterator();
//                    while(it.hasNext()){
//                        boolean collided = ball.checkCollision(it.next());
//                        if(collided) {
//                            it.remove();
//                            break;
//                        }
//                    }
//
//
//
//
//                });
//                balls.removeAll(disposed);
//                mainPanel.repaint();
//            }
//        );
//
//        timer.start();
    }

}