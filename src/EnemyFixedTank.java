import java.awt.*;
import java.awt.geom.AffineTransform;

public class EnemyFixedTank extends EnemyTank {

    public EnemyFixedTank(int locationX, int locationY, double rotationRequired, Point enemyLocation) {
        super(locationX, locationY, rotationRequired, enemyLocation);
    }
    public void updateEnemyLocation(Point enemyLocation) {
        tankCenterX = getTankLocation().x + 43;
        tankCenterY = getTankLocation().y + 70;
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
        if (checkArea(enemyLocation) != checkArea(getTankLocation()))
            return false;
        return true;
    }
    private void shootBullet() {
        if (System.currentTimeMillis() - startTime >= 2000) {
            if (shootIsValid()) {
                bullets.add(new HeavyBullet(enemyLocation, tankCenterX, tankCenterY, rotationRequired));
                SoundPlayer.playSound("enemyShot");
            }
            startTime += 2000;
        }
    }
}
