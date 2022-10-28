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

    public Ball (int x, int y, int r)
    {
        location = new Rectangle(x-r, y-r, 2*r, 2*r);
        dx = 0;
        dy = 0;
        color = Color.blue;
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

    public double getMass() {
        return 2 * radius() * radius() * radius() / 1000f;
    }





    public void checkCollision(int width, int height){
        if ((this.x()-radius() < 0) || (this.x()+radius() > width))
            this.setMotion (-this.xMotion(), this.yMotion());
        if ((this.y()-radius() < 0) || (this.y()+radius() > height))
            this.setMotion (this.xMotion(), -this.yMotion());
    }


    public static void intersect(Ball a, Ball b) {
        //ref http://gamedev.stackexchange.com/questions/20516/ball-collisions-sticking-together
        double xDist, yDist;
        xDist = a.x() - b.x();
        yDist = a.y() - b.y();
        double distSquared = xDist * xDist + yDist * yDist;
        // Check the squared distances instead of the the distances, same
        // result, but avoids a square root.
        if (distSquared <= (a.radius() + b.radius()) * (a.radius() + b.radius())) {

            System.out.println("!!! COLLISION !!!!");

            double speedXocity = b.xMotion() - a.yMotion();
            double speedYocity = b.xMotion() - a.yMotion();
            double dotProduct = xDist * speedXocity + yDist * speedYocity;
            // Neat vector maths, used for checking if the objects moves towards
            // one another.
            if (dotProduct > 0) {

                double collisionScale = dotProduct / distSquared;
                double xCollision = xDist * collisionScale;
                double yCollision = yDist * collisionScale;
                // The Collision vector is the speed difference projected on the
                // Dist vector,
                // thus it is the component of the speed difference needed for
                // the collision.
                double combinedMass = a.getMass() + b.getMass();
                double collisionWeightA = 2 * b.getMass() / combinedMass;
                double collisionWeightB = 2 * a.getMass() / combinedMass;
                a.dx += (collisionWeightA * xCollision);
                a.dy += (collisionWeightA * yCollision);

                b.dx -= (collisionWeightB * xCollision);
                b.dy -= (collisionWeightB * yCollision);


            }


        }}










/*
    public void checkCollisionBall(int xcoordinateball1, int ycoordinateball1,int xcoordinateball2,int ycoordinateball2)
    {
        if ( || (this.x() > width))
            this.setMotion (-this.xMotion(), this.yMotion());
        if ((this.y() < 0) || (this.y() > height))
            this.setMotion (this.xMotion(), -this.yMotion());
    }
*/

    public void paint (Graphics g)
    {
        g.setColor (color);
        g.fillOval (location.x, location.y, location.width, location.height);

    }
}