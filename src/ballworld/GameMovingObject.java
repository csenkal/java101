package ballworld;

public class GameMovingObject extends GameObject implements Collidable{

    protected int xSpeed;
    protected int ySpeed;

    public void setMotion (int ndx, int ndy)
    {
        xSpeed = ndx;
        ySpeed = ndy;
    }

    public void moveTo (int x, int y)
    {
        location.setLocation(x, y);
    }

    public void move ()
    {
        location.translate (xSpeed, ySpeed);
    }


    public GameMovingObject(int x, int y, int w, int h) {
        super(x, y, w, h);
    }


    @Override
    public boolean checkCollision(Collidable other) {

        if(this.location.intersects(other.getLocation())){
            onCollision(other);
            return true;
        }
        return false;
    }

    @Override
    public void onCollision(Collidable other) {
        /*
        int speedBoost  =1 ;
        if(other instanceof GameMovingObject){
             speedBoost = ((GameMovingObject)other).xSpeed;
            System.out.println(speedBoost);
        }


         */
        if(Math.abs(this.getLocation().getMinY() - other.getLocation().getMaxY())<5 || Math.abs(this.getLocation().getMaxY() - other.getLocation().getMinY())<5 )
            this.setMotion(xSpeed, ySpeed*-1);
        else
            this.setMotion(xSpeed*-1, ySpeed);
    }
}
