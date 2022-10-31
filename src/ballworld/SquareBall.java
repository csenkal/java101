package ballworld;

import java.awt.*;
import java.util.LinkedList;

public class SquareBall extends Ball{
    public LinkedList<SquareBall> sBalls;

    public SquareBall(int x, int y, int r) {
        super(x, y, r);
        /* for(SquareBall sBall: sBalls) {
            sBall.move();
        } */

    }
    public void paint (Graphics g)
    {
        g.setColor (color);
        g.fillRect(location.x, location.y, location.width, location.height);
    }

}
