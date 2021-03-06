import java.awt.*;

/**
 * a class to handle the light Bullets
 * that have a method to update the location of a bullet
 *
 * @author AmirHosseinRasulian & MohammadHasanRashidi
 */
public class LightBullet extends Bullet {

    public LightBullet(GameState state , double speed , int radius) {
        super(state, speed, radius);
        bulletRec = new Rectangle(x, y, 17, 1);
    }
    public LightBullet(Point shootingPoint, int centerOfTankX, int centerOfTankY, double rotationRequired , int radius) {
        super(shootingPoint, centerOfTankX, centerOfTankY, rotationRequired, 0.50, radius);
        bulletRec = new Rectangle(x, y, 17, 1);
    }
    /**
     * updating the point that bullet must draw there by the speed and x = vt formula
     */
    @Override
    public void update() {
        x = pointOfGun.x + (int) (speedX * (System.currentTimeMillis() - startTime));
        y = pointOfGun.y + (int) (speedY * (System.currentTimeMillis() - startTime));
        bulletRec = new Rectangle(x,y,17,1);
    }
}
