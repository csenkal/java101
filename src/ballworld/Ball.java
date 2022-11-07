package ballworld;

//
//	general purpose reusable bouncing ball abstraction
//	Described in Chapter 5 of
//	Understanding Object-Oriented Programming with Java
//	by Timothy A Budd
//	Published by Addison-Wesley
//
//	see ftp://ftp.cs.orst.edu/pub/budd/java/ReadMe.html
//	for further information
//

import java.awt.*;

public class Ball {
    protected Rectangle location;

    protected double dx;
    protected double dy;
    protected Color color;


    public  Ball (int x, int y, int r)
    {
        location = new Rectangle(x-r, y-r, 2*r, 2*r);
        dx = 0;
        dy = 0;
        color = Color.green;
    }

    // functions that set attributes
    public void setColor (Color newColor)
    { color = newColor;	}

    public void setMotion (double ndx, double ndy)
    { dx = ndx; dy = ndy; }

    // functions that access attributes of ball
    public int radius ()
    { return location.width / 2; }

    public int x ()
    { return location.x + radius(); }

    public int y ()
    { return location.y + radius(); }

    public double xMotion ()
    { return dx; }

    public double yMotion ()
    { return dy; }



    // functions that change attributes of ball
    public void moveTo (int x, int y)
    { location.setLocation(x, y); }




    public void move ()
    {
        location.translate ((int) dx, (int) dy);


    }

    public void checkCollision(int width, int height){

        if ((this.x() < 0) || (this.x() > width))
            this.setMotion (-this.xMotion(), this.yMotion());
        if ((this.y() < 0)  )
            this.setMotion (this.xMotion(), -this.yMotion());
        if (this.y()> height+40)
            this.setMotion(0,0);                        // Top ekran dışına çıkarsa hızını 0 a eşitle (objeyi listden silmek daha mantıklı )

    }

    public void tabCollision(Ball tab,Ball ball){
        if ((ball.y() > (tab.y()-80))){
            if ((ball.x()>(-80+tab.x()) && (ball.x()<(80+tab.x()))))
            ball.setMotion (ball.xMotion(), -ball.yMotion());
    }
        if (tab.x()>1817) {
            System.out.println("Tab ekran dışı");
            tab.moveTo(1780, 900);
        }

    }

    public int Collission2( Ball b1 ,Ball b0){   // b1 = toplar b0= brickler
        //this.setMotion(-this.xMotion(), this.yMotion());

        if ((Math.abs(b0.x()- b1.x())<=2*radius()) && (Math.abs(b1.y()-b0.y())<=2*radius())) {
            b1.setMotion(-b1.xMotion(), -b1.yMotion());    // çarpışmalar fizik dışı
            return 1;
        }
        return 0;
    }


    public void paint (Graphics g)
    {
            g.setColor(color);
            g.fillOval(location.x, location.y, location.width, location.height);

    }
}