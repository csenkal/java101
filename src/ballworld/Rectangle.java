package ballworld;

import java.awt.*;

class Rectangel extends Ball{
    public Rectangel(int x, int y, int r) {
        super(x, y, r);
    }

    public void paint (Graphics g)
    {

        g.setColor(color);
        g.fillRect(location.x, location.y, location.width, location.height);

    }
}
