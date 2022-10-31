package ballworld;

import java.awt.*;

public class SquareBall extends Ball{


    public SquareBall(int x, int y, int r) {
        super();
        location = new Rectangle(x-r, y-r, r, r);
    }


    @Override
    public void paint (Graphics g){

        g.setColor (color);
        g.fillRect(location.x, location.y, location.width, location.height);

    }


}