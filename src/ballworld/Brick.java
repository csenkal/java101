package ballworld;

import java.awt.*;

public class Brick extends Ball {
    public Brick(int x, int y, int r) {

        super(x, y, r);

    }

    public void setAsGoalKeeper ()
    {
        location.width = 100;
        location.height=20;


    }




    public void paint (Graphics g)
    {

        g.setColor (color);
        g.fillRect (location.x, location.y, location.width, location.height);

    }

}
