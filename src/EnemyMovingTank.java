import java.awt.*;
import java.awt.geom.AffineTransform;

public class EnemyMovingTank extends EnemyTank {
    private boolean gunIsReloaded ;
    public EnemyMovingTank(int locationX, int locationY, double rotationRequired, Point enemyLocation) {
        super(locationX, locationY, rotationRequired, enemyLocation);
    }
    public void updateEnemyLocation(Point enemyLocation) {
        gunIsReloaded = true ;
        tankCenterX = getTankLocation().x + 45;
        tankCenterY = getTankLocation().y + 47;
        this.enemyLocation.x = enemyLocation.x + 50;
        this.enemyLocation.y = enemyLocation.y + 50;

        if (enemyLocation.x - tankCenterX > 0)
            rotationRequired = Math.atan(((double) (enemyLocation.y - tankCenterY)) / ((double) (enemyLocation.x - tankCenterX)));
        else if (enemyLocation.x - tankCenterX < 0)
            rotationRequired = Math.toRadians(180 - 0) + Math.atan(((double)(enemyLocation.y - tankCenterY)) / ((double) (enemyLocation.x - tankCenterX)));
        else {
            if (enemyLocation.y - tankCenterY > 0)
                rotationRequired = Math.toRadians(90);
            else
                rotationRequired = Math.toRadians(-90);
        }
        affineTransform = new AffineTransform();
        affineTransform.translate(tankCenterX, tankCenterY);
        affineTransform.rotate(rotationRequired - Math.toRadians(20));
        affineTransform.translate(-25, -20);
        shootBullet();
    }
    private boolean shootIsValid() {
        if (checkArea(enemyLocation) != checkArea(getTankLocation()))
            return false;
        return true;
    }
    private void shootBullet() {
        if (System.currentTimeMillis() - startTime > 1500)
            gunIsReloaded = false ;
        if (System.currentTimeMillis() - startTime > 2000) {
            gunIsReloaded = true;
            startTime += 1000 ;
        }
        if (shootIsValid() && gunIsReloaded) {
            bullets.add(new LightBullet(enemyLocation, tankCenterX, tankCenterY, rotationRequired));
            SoundPlayer.playSound("machineGun");
        }
    }
}
