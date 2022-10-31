package ballworld;

import java.awt.*;

public interface Collidable {
    boolean checkCollision(Collidable other);
    Rectangle getLocation();
    void onCollision(Collidable other);
}
