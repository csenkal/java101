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


public class BallWorld extends JFrame {

    public static void main (String [ ] args)
    {
        BallWorld world = new BallWorld (Color.red);
        world.setVisible (true);
    }

    private static final int FrameWidth = 600;
    private static final int FrameHeight = 400;
    private Ball aBall;

    private JPanel mainPanel;


    private int width;
    private int height;
    private BallWorld (Color ballColor) {
        // constructor for new ball world
        // resize our frame
        //setSize (FrameWidth, FrameHeight);
        setTitle ("Ball World");

        mainPanel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
               //super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setStroke(new BasicStroke(4, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g.clearRect(0,0,width,height);

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

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        Timer timer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                width = mainPanel.getWidth();
                height = mainPanel.getHeight();
                aBall.move();

                if ((aBall.x() < 0) || (aBall.x() > width))
                    aBall.setMotion (-aBall.xMotion(), aBall.yMotion());
                if ((aBall.y() < 0) || (aBall.y() > height))
                    aBall.setMotion (aBall.xMotion(), -aBall.yMotion());

                mainPanel.repaint();
                //repaint();
            }
        });
        timer.start();
    }

}