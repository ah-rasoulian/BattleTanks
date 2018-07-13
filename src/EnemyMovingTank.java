import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.Random;

/**
 * @author AmirHosseinRasulian & MohammadHasanRashidi
 */
public class EnemyMovingTank extends EnemyTank {
    private boolean gunIsReloaded ;
    private int directionChoosed ;
    private char direction ;
    Random randomGenerator ;
    public EnemyMovingTank(int locationX, int locationY, double rotationRequired, Point enemyLocation, int prize , int tankNumber) {
        super(locationX, locationY, rotationRequired, enemyLocation,600,90,90,prize , tankNumber);
        gunIsReloaded = true ;
        randomGenerator = new Random();
        direction = 'R' ;
        directionChoosed = 0 ;
    }
    public void updateEnemyLocation(Point enemyLocation1) {
        tankCenterX = getObstacleLocation().x + 50;
        tankCenterY = getObstacleLocation().y + 45;
        enemyLocation.x = enemyLocation1.x + 58;
        enemyLocation.y = enemyLocation1.y + 58;

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
        affineTransform.rotate(rotationRequired);
        affineTransform.translate(-25, -20);
        shootBullet();
        updateTankLocation();

    }
    private boolean shootIsValid() {
        if (checkArea(enemyLocation) != checkArea(getObstacleLocation()))
            return false;
        return true;
    }
    private void shootBullet() {
        if (System.currentTimeMillis() - startTime > 2000) {
            gunIsReloaded = true;
            startTime += 1000 ;
        }
        if (shootIsValid() && gunIsReloaded) {
            if (System.currentTimeMillis() - startTime >= 1000) {
                if (shootIsValid()) {
                    bullets.add(new HeavyBullet(enemyLocation, tankCenterX, tankCenterY, rotationRequired , 120));
                    SoundPlayer.playSound("enemyShot");
                }
                startTime += 2000;
            }
        }
    }
    private void updateTankLocation () {
        if (!GameState.isMultiPlay() || GameState.getServer() != null) {
            if (directionChoosed <= 10) {
                switch (direction) {
                    case 'R':
                        if (GameState.allowToMove("right", this))
                            getObstacleLocation().x += 4;
                        else directionChoosed = 11;
                        break;
                    case 'L':
                        if (GameState.allowToMove("left", this))
                            getObstacleLocation().x -= 4;
                        else
                            directionChoosed = 11;
                        break;
                    case 'U':
                        if (GameState.allowToMove("up", this))
                            getObstacleLocation().y -= 4;
                        else
                            directionChoosed = 11;
                        break;
                    case 'D':
                        if (GameState.allowToMove("down", this))
                            getObstacleLocation().y += 4;
                        else
                            directionChoosed = 11;
                        break;
                }
                directionChoosed++;
            } else {
                directionChoosed = 0;
                switch (randomGenerator.nextInt(4)) {
                    case 0:
                        direction = 'L';
                        break;
                    case 1:
                        direction = 'R';
                        break;
                    case 2:
                        direction = 'U';
                        break;
                    case 3:
                        direction = 'D';
                        break;
                }
            }
        }
        else {
//            obstacleLocation = GameState.friendMultiPlayDatas.getEnemysLocations().get(tankNumber);
            obstacleLocation = new Point(500,500);
        }
    }
}
