package ballworld;

import java.awt.*;

public class Square extends Ball {



    public Square(int x, int y, int r) {
        super(x, y, r);
    }
    public void paint (Graphics g)
    {

        g.setColor (color);
        g.fillRect (location.x, location.y, location.width, location.height);

    }


}
