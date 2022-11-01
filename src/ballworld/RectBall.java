package ballworld;

import java.awt.*;
import java.lang.reflect.Array;

public class RectBall extends Ball{


    public RectBall(int x, int y, int r) {
        super(x, y, r);
    }

    @Override
    public void paint(Graphics g) {
        g.setColor (color);

        g.fillRect (location.x, location.y, location.width, location.height);
    }
}
