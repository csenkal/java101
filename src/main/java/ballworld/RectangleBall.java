package ballworld;

import java.awt.*;

public class RectangleBall extends Ball
{

    public RectangleBall (int x, int y, int r) {
        super (x,y,r);

    }

    @Override
    public void setColor (Color newColor)
    { super.color = newColor;	}

    @Override
    public void setMotion (double ndx, double ndy)
    { super.dx = ndx; super.dy = ndy; }

    @Override
    public int radius ()
    { return super.location.width / 2; }

    @Override
    public int x ()
    { return super.location.x + radius(); }

    @Override
    public int y ()
    { return super.location.y + radius(); }

    @Override
    public double xMotion ()
    { return super.dx; }

    @Override
    public double yMotion ()
    { return super.dy; }

    @Override
    public Rectangle region () { return super.location; }

    @Override
    public void moveTo (int x, int y)
    { super.location.setLocation(x, y); }


    @Override
    public void move ()
    {
        super.location.translate ((int) dx, (int) dy);


    }
    @Override
    public void checkCollision(int width, int height){
        if ((super.x() < 0) || (super.x() > width))
            super.setMotion (-super.xMotion(), super.yMotion());
        if ((super.y() < 0) || (super.y() > height))
            super.setMotion (super.xMotion(), -super.yMotion());
    }
    @Override
    public void paint (Graphics g)
    {
        g.setColor (color);
        g.fillRect (location.x, location.y, location.width, location.height);
    }


}
