import java.awt.*;

/**
 * a class to handle the heavy Bullets
 * that have a method to update the location of a bullet
 * @author AmirHosseinRasulian
 */
public class HeavyBullet extends Bullet{

    public HeavyBullet (GameState state)
    {
        super(state ,0.50 , 140);
    }
    public HeavyBullet (Point shootingPoint, int centerOfTankX , int centerOfTankY , double rotationRequired ){
        super(shootingPoint,centerOfTankX ,centerOfTankY ,rotationRequired , 0.50 , 50);
    }

    /**
     * updating the point that bullet must draw there by the speed and x = vt formula
     */
    @Override
    public void update() {
        x = pointOfGun.x + (int)( speedX * (System.currentTimeMillis() - startTime));
        y = pointOfGun.y + (int)( speedY * (System.currentTimeMillis() - startTime));
    }
}
