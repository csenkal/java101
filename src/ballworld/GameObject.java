package ballworld;

import java.awt.*;

public class GameObject {
    protected Rectangle location;
    protected Color color = Color.black;

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }



    public GameObject(int x, int y, int w, int h) {
        location = new Rectangle(x, y, w, h);
    }

    public int x() {
        return location.x;
    }

    public int y() {
        return location.y;
    }

    public Rectangle getLocation() {
        return location;
    }

    public void paint(Graphics g) {
        g.setColor(color);
        g.fillRect(location.x, location.y, location.width, location.height);
    }

    public int[] checkCollision(int width, int height){
        int[] retVal = new int[]{1,1,1,1};
        if (x() < 0)
            retVal[0] = -1;
        if(x() + location.width > width)
            retVal[1] = -1;
        if (y() < 0)
            retVal[2]  = -1;
        if(y() + location.height > height)
            retVal[3]  = -1;

        return retVal;
    }
}
