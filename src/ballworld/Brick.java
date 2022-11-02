package ballworld;

import java.awt.*;

public class Brick extends Ball{
    public Brick(int x, int y, int r) {
        super(x, y, r);
    }



    public void paint (Graphics g)
    {
        g.setColor(Color.BLACK);
        g.fillRect(location.x, location.y, location.width/2, location.height);

    }
}
