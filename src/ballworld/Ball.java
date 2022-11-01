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

    public Rectangle region () { return location; }

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
        if ((this.y() < 0) || (this.y() > height))
            this.setMotion (this.xMotion(), -this.yMotion());

    }

    public void Collission2(int X_Second, int Y_Second, int X_First, int Y_First, Ball b1 ){   // Top Obyesini yollarak Ball da kullan yada yeni sınıf aç 

            //this.setMotion(-this.xMotion(), this.yMotion());

        if ((Math.abs(Y_First-Y_Second)<=2*radius()) && (Math.abs(X_First-X_Second)<=2*radius()))
           // this.setMotion(this.xMotion(), -this.yMotion());
            if ((Y_First-Y_Second)>0)
                this.setMotion(this.xMotion(), b1.yMotion());                 //Diğer topların motion ı verebilirim
        if ((Y_First-Y_Second)<0)
                this.setMotion(b1.xMotion(), this.yMotion());

    }

    public void paint (Graphics g)
    {
            g.setColor(color);
            g.fillOval(location.x, location.y, location.width, location.height);

    }
}