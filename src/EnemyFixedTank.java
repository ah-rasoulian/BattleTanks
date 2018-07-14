import java.awt.*;
import java.awt.geom.AffineTransform;

/**
 * @author AmirHosseinRasulian & MohammadHasanRashidi
 */
public class EnemyFixedTank extends EnemyTank {

    public EnemyFixedTank(int locationX, int locationY, double rotationRequired, Point enemyLocation, int prize , int tankNumber) {
        super(locationX, locationY, rotationRequired, enemyLocation, 500, 120, 100, prize , tankNumber);
    }

    public void updateEnemyLocation(Point enemyLocation) {
        tankCenterX = getObstacleLocation().x + 43;
        tankCenterY = getObstacleLocation().y + 70;
        this.enemyLocation.x = enemyLocation.x + 50;
        this.enemyLocation.y = enemyLocation.y + 50;

        if (enemyLocation.x - tankCenterX > 0)
            rotationRequired = Math.atan(((double) (enemyLocation.y - tankCenterY)) / ((double) (enemyLocation.x - tankCenterX)));
        else if (enemyLocation.x - tankCenterX < 0)
            rotationRequired = Math.toRadians(180) + Math.atan(((double) (enemyLocation.y - tankCenterY)) / ((double) (enemyLocation.x - tankCenterX)));
        else {
            if (enemyLocation.y - tankCenterY > 0)
                rotationRequired = Math.toRadians(90);
            else
                rotationRequired = Math.toRadians(-90);
        }
        affineTransform = new AffineTransform();
        affineTransform.translate(tankCenterX, tankCenterY);
        if (Math.toDegrees(rotationRequired) < 74)
            rotationRequired = Math.toRadians(73);
        if (Math.toDegrees(rotationRequired) > 204)
            rotationRequired = Math.toRadians(205);
        affineTransform.rotate(rotationRequired - Math.toRadians(123));
        affineTransform.translate(-25, +14);
        shootBullet();
    }

    private boolean shootIsValid() {
        if (!(Math.toDegrees(rotationRequired) >= 74 && Math.toDegrees(rotationRequired) <= 204))
            return false;
        if (checkArea(enemyLocation) != checkArea(getObstacleLocation()))
            return false;
        return true;
    }

    private void shootBullet() {
        if (System.currentTimeMillis() - startTime >= 2000) {
            if (shootIsValid()) {
                bullets.add(new HeavyBullet(enemyLocation, tankCenterX, tankCenterY, rotationRequired, 100));
                SoundPlayer.playSound("enemyShot");
            }
            startTime += 2000;
        }
    }
}
