import java.awt.*;

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

    public Bullet (GameState state , double speed , int radius)
    {
        this.speed = speed ;
        startTime = System.currentTimeMillis() ;

        pointOfShut = state.getShootingPoint() ;
        rotationRequired = state.getRotationRequired() ;

        int centerOfTankX = state.tankLocationX + 42 ;
        int centerOfTankY = state.tankLocationY + 42 ;

        if ( Math.toDegrees(rotationRequired) >= 0 && Math.toDegrees(rotationRequired) < 90)
            pointOfGun = new Point(centerOfTankX + (int)(radius*Math.cos(rotationRequired)),centerOfTankY  + (int)(radius*Math.sin(rotationRequired))) ;
        else if (Math.toDegrees(rotationRequired) >= 90 && Math.toDegrees(rotationRequired) < 180)
            pointOfGun = new Point(centerOfTankX + (int)(radius*Math.cos(rotationRequired)) , centerOfTankY + (int)(radius*Math.sin(rotationRequired))) ;
        else if (Math.toDegrees(rotationRequired) >= 180 && Math.toDegrees(rotationRequired) < 270)
            pointOfGun = new Point(centerOfTankX + (int)(radius*Math.cos(rotationRequired)) , centerOfTankY + (int)(radius*Math.sin(rotationRequired))) ;
        else
            pointOfGun = new Point(centerOfTankX + (int)(radius*Math.cos(-rotationRequired)) , centerOfTankY - (int)(radius*Math.sin(-rotationRequired))) ;

        x = pointOfGun.x ;
        y = pointOfGun.y ;
        distant = Math.sqrt((pointOfShut.x - pointOfGun.x)*(pointOfShut.x - pointOfGun.x) + (pointOfShut.y - pointOfGun.y)*(pointOfShut.y - pointOfGun.y));

        speedX = speed * ( (double)(pointOfShut.x - pointOfGun.x) ) / distant ;
        speedY = speed * ( (double)(pointOfShut.y - pointOfGun.y) ) / distant;

    }

    public abstract void update();

    public abstract int getX();

    public abstract int getY();

    public abstract double getRotationRequired();
}
