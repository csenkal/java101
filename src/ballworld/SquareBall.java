package ballworld;

import java.awt.*;

public class SquareBall extends Ball{
    public SquareBall(int x, int y, int r) {
        super(x, y, r);

    }

    public void paint (Graphics g)
    {
        g.setColor (color);
        g.fillRect (location.x, location.y, location.width, location.height);
    }


}
