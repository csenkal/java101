package ballworld;

import java.awt.*;

public class TAB extends Ball{
    public TAB(int x, int y, int r) {
        super(x, y, r);
    }

    public void paint (Graphics g)
    {
        g.setColor(Color.BLACK);
        g.fillRect(location.x, location.y, 140, 30);

    }
}
