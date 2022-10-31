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

public class Ball extends GameMovingObject {



    public Ball (int x, int y, int r)
    {
        super(x, y, r, r);

        color = Color.blue;
    }

    // functions that set attributes

    public void paint(Graphics g) {
        g.setColor(color);
        g.fillOval(location.x, location.y, location.width, location.height);

    }


    // functions that access attributes of ball
    public int radius ()
    { return location.width / 2; }






}