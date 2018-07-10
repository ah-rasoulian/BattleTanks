import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.Random;

public class EnemyMovingTank2 extends EnemyTank {
    private boolean gunIsReloaded;
    private char direction;
    private int directionChoosed;
    private Random randomGenerator;

    public EnemyMovingTank2(int locationX, int locationY, double rotationRequired, Point enemyLocation) {
        super(locationX, locationY, rotationRequired, enemyLocation,400,100,100);
        gunIsReloaded = true;
        direction = 'R';
        directionChoosed = 0;
        randomGenerator = new Random();
    }

    public void updateEnemyLocation(Point enemyLocation1) {
        tankCenterX = getObstacleLocation().x + 45;
        tankCenterY = getObstacleLocation().y + 0;

        enemyLocation.x = enemyLocation1.x + 58;
        enemyLocation.y = enemyLocation1.y + 58;

        if (enemyLocation.x - tankCenterX > 0)
            rotationRequired = Math.atan(((double) (enemyLocation.y - tankCenterY)) / ((double) (enemyLocation.x - tankCenterX)));
        else if (enemyLocation.x - tankCenterX < 0)
            rotationRequired = Math.toRadians(180 - 0) + Math.atan(((double) (enemyLocation.y - tankCenterY)) / ((double) (enemyLocation.x - tankCenterX)));
        else {
            if (enemyLocation.y - tankCenterY > 0)
                rotationRequired = Math.toRadians(90 - 0);
            else
                rotationRequired = Math.toRadians(-90);
        }
        affineTransform = new AffineTransform();
        affineTransform.translate(tankCenterX, tankCenterY);
        affineTransform.rotate(rotationRequired + Math.toRadians(90));
        affineTransform.translate(getObstacleLocation().x - tankCenterX, getObstacleLocation().y - tankCenterY);
        shootBullet();
        updateTankLocation();
    }

    private boolean shootIsValid() {
        if (checkArea(enemyLocation) != checkArea(getObstacleLocation()))
            return false;
        return true;
    }

    private void shootBullet() {
        if (System.currentTimeMillis() - startTime > 1500) {
            gunIsReloaded = false;
        }
        if (System.currentTimeMillis() - startTime > 2000) {
            gunIsReloaded = true;
            startTime += 800;
        }
        if (shootIsValid() && gunIsReloaded) {
            bullets.add(new LightBullet(enemyLocation, tankCenterX, tankCenterY, rotationRequired, 10));
            SoundPlayer.playSound("machineGun");
        }
    }

    private void updateTankLocation() {
        if (directionChoosed <= 4) {
            switch (direction) {
                case 'R':
                    getObstacleLocation().x += 3;
                    break;
                case 'L':
                    getObstacleLocation().x -= 3;
                    break;
            }
            directionChoosed++;
        } else {
            directionChoosed = 0;
            switch (randomGenerator.nextInt(2)) {
                case 0:
                    direction = 'R';
                    break;
                case 1:
                    direction = 'L';
                    break;
            }
        }
    }
}
