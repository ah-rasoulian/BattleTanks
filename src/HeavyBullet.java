import java.awt.*;

/**
 * a class to handle the heavy Bullets
 * that have a method to update the location of a bullet
 *
 * @author AmirHosseinRasulian & MohammadHasanRashidi
 */
public class HeavyBullet extends Bullet {

    public HeavyBullet(GameState state , double speed , int radius) {
        super(state, speed, radius);
        bulletRec = new Rectangle(x, y, 23, 9);
    }

    public HeavyBullet(Point shootingPoint, int centerOfTankX, int centerOfTankY, double rotationRequired , int radius) {
        super(shootingPoint, centerOfTankX, centerOfTankY, rotationRequired, 0.50, radius);
        bulletRec = new Rectangle(x, y, 23, 9);
    }

    /**
     * updating the point that bullet must draw there by the speed and x = vt formula
     */
    @Override
    public void update() {
        x = pointOfGun.x + (int) (speedX * (System.currentTimeMillis() - startTime));
        y = pointOfGun.y + (int) (speedY * (System.currentTimeMillis() - startTime));
        bulletRec.setLocation(x, y);
    }
}
