package ballworld;

import java.awt.*;

public class RectangleBall extends Ball
{

    public RectangleBall (int x, int y, int r) {
        super (x,y,r);

    }

    @Override
    public void paint (Graphics g)
    {
        g.setColor (color);
        g.fillRect (location.x, location.y, location.width, location.height);
    }


}
