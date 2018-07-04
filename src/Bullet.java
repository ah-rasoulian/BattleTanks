
import java.awt.*;

public class Bullet {

    private Point pointOfShut ;
    private Point pointOfGun ;
    private double angle ; // angle of the line between gun and shutPoint by +x axis
    private int x ;
    private int y ;
    private double rotationRequired ; // -90 degree to 270 degree

    private double speed = 0.50 ;
    private double speedX ;
    private double speedY ;
    private double startTime ;
    private double distant ;

    public Bullet (GameState state)
    {
        startTime = System.currentTimeMillis() ;

        pointOfShut = state.getShootingPoint() ;
        rotationRequired = state.getRotationRequired() ;

        int centerOfTankX = state.tankLocationX + 42 ;
        int centerOfTankY = state.tankLocationY + 42 ;
        int radius = 80 ;
        if ( Math.toDegrees(rotationRequired) >= 0 && Math.toDegrees(rotationRequired) < 90)
            pointOfGun = new Point(centerOfTankX + (int)(radius*Math.cos(rotationRequired)),centerOfTankY  + (int)(radius*Math.sin(rotationRequired))) ;
        else if (Math.toDegrees(rotationRequired) >= 90 && Math.toDegrees(rotationRequired) < 180)
            pointOfGun = new Point(centerOfTankX + (int)(radius*Math.cos(rotationRequired)) , centerOfTankY + (int)(radius*Math.sin(rotationRequired))) ;
        else if (Math.toDegrees(rotationRequired) >= 180 && Math.toDegrees(rotationRequired) < 270)
            pointOfGun = new Point(centerOfTankX + (int)(radius*Math.cos(rotationRequired)) , centerOfTankY + (int)(radius*Math.sin(rotationRequired))) ;
        else
            pointOfGun = new Point(centerOfTankX + (int)(radius*Math.cos(-rotationRequired)) , centerOfTankY - (int)(radius*Math.sin(-rotationRequired))) ;

            angle = Math.atan( ((double)( pointOfGun.y - pointOfShut.y )) / ((double)( pointOfShut.x - pointOfGun.y )) );

        x = pointOfGun.x ;
        y = pointOfGun.y ;
        distant = Math.sqrt((pointOfShut.x - pointOfGun.x)*(pointOfShut.x - pointOfGun.x) + (pointOfShut.y - pointOfGun.y)*(pointOfShut.y - pointOfGun.y));

        speedX = speed * ( (double)(pointOfShut.x - pointOfGun.x) ) / distant ;
        speedY = speed * ( (double)(pointOfShut.y - pointOfGun.y) ) / distant;

        if ( pointOfGun.x - centerOfTankX > 0 )
            rotationRequired = Math.atan( ( (double)(pointOfGun.y - centerOfTankY) ) / ( (double)(pointOfGun.x - centerOfTankX) ) );
        else if ( state.getMouseX() - centerOfTankX < 0 )
            rotationRequired = Math.toRadians(180) + Math.atan( ( (double)(pointOfGun.y - centerOfTankY) ) / ( (double)(pointOfGun.x - centerOfTankX) ) );
        else
        {
            if (state.getMouseY() - centerOfTankY > 0)
                rotationRequired = Math.toRadians(90);
            else
                rotationRequired = Math.toRadians(-90);
        }

    }
    //updating the point that bullet must draw there by the speed and x = vt formula
    public void update() {
        x = pointOfGun.x + (int)( speedX * (System.currentTimeMillis() - startTime));

        y = pointOfGun.y + (int)( speedY * (System.currentTimeMillis() - startTime));
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public double getRotationRequired() {
        return rotationRequired;
    }
}
