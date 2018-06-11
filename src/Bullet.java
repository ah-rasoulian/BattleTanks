
import java.awt.*;

public class Bullet {

    private Point pointOfShut ;
    private Point pointOfGun ;
    private double angle ; // angle of the line between gun and shutPoint by +x axis
    private int x ;
    private int y ;
    private double rotationRequired ; // -90 degree to 270 degree

    private double speed = 0.35 ;
    private double speedX ;
    private double speedY ;
    private double startTime ;

    public Bullet (GameState state)
    {
        startTime = System.currentTimeMillis() ;

        pointOfShut = new Point(state.getMouseX() , state.getMouseY() ) ;
        rotationRequired = state.getRotationRequired() ;

        int centerOfTankX = state.tankLocationX + 30 ;
        int centerOfTankY = state.tankLocationY + 30 ;
        int radius = 83 ;
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

        speedX = Math.abs( speed * Math.cos(angle) ) ;
        speedY = Math.abs( speed * Math.sin(angle) );

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
        if (pointOfShut.x >= pointOfGun.x)
            x = pointOfGun.x + (int)( speedX * (System.currentTimeMillis() - startTime));
        else
            x = pointOfGun.x - (int)( speedX * (System.currentTimeMillis() - startTime));

        if (pointOfShut.y >= pointOfGun.y)
            y = pointOfGun.y + (int)( speedY * (System.currentTimeMillis() - startTime));
        else
            y = pointOfGun.y - (int)( speedY * (System.currentTimeMillis() - startTime));
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
