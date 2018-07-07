import java.awt.*;

/**
 * this is a class that simulates a bullet from 2 kind lightBullet and heavyBullet
 * that they are 2 classes that extends this class
 * this class have the data of a bullet
 * @author AmirHossein Rasulian
 */
public abstract class Bullet
{
    protected Point pointOfShut ;
    protected Point pointOfGun ;
    protected double speed ;
    protected double speedX ;
    protected double speedY ;
    protected int x ;
    protected int y ;
    protected double rotationRequired ; // -90 degree to 270 degree
    protected double startTime ;
    protected double distant ;

    public Bullet (GameState state , double speed , double radius)
    {
        this.speed = speed ;
        startTime = System.currentTimeMillis() ;

        pointOfShut = state.getShootingPoint() ;
        rotationRequired = state.getRotationRequired() ;

        int centerOfTankX = state.tankLocationX + 50 ;
        int centerOfTankY = state.tankLocationY + 50 ;

        distant = Math.sqrt((pointOfShut.x - centerOfTankX)*(pointOfShut.x - centerOfTankX) + (pointOfShut.y - centerOfTankY)*(pointOfShut.y - centerOfTankY));

        speedX = speed * ( (double)(pointOfShut.x - centerOfTankX) ) / distant ;
        speedY = speed * ( (double)(pointOfShut.y - centerOfTankY) ) / distant;

        pointOfGun = new Point(centerOfTankX + (int)(radius * speedX) , centerOfTankY + (int)(radius * speedY));

        x = pointOfGun.x ;
        y = pointOfGun.y ;

    }

    public abstract void update();

    public int getX()
    {
        return x;
    }

    public int getY(){
        return y ;
    }

    public double getRotationRequired(){
        return rotationRequired;
    }
}
