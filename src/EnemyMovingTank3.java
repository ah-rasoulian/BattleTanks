import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.Random;

/**
 * @author AmirHosseinRasulian & MohammadHasanRashidi
 */
public class EnemyMovingTank3 extends EnemyTank {
    private boolean gunIsReloaded ;
    private char direction;
    private int directionChoosed;
    private Random randomGenerator;
    public EnemyMovingTank3(int locationX, int locationY, double rotationRequired, Point enemyLocation) {
        super(locationX, locationY, rotationRequired, enemyLocation, 600, 100, 100);
        gunIsReloaded = true ;
        direction = 'R';
        directionChoosed = 0 ;
        randomGenerator = new Random();
    }
    public void updateEnemyLocation(Point enemyLocation1) {
        tankCenterX = getObstacleLocation().x + 45;
        tankCenterY = getObstacleLocation().y + 42;

        enemyLocation.x = enemyLocation1.x + 58;
        enemyLocation.y = enemyLocation1.y + 58;

        if (enemyLocation.x - tankCenterX > 0)
            rotationRequired = Math.atan(((double) (enemyLocation.y - tankCenterY)) / ((double) (enemyLocation.x - tankCenterX)));
        else if (enemyLocation.x - tankCenterX < 0)
            rotationRequired = Math.toRadians(180 - 0) + Math.atan(((double)(enemyLocation.y - tankCenterY)) / ((double) (enemyLocation.x - tankCenterX)));
        else {
            if (enemyLocation.y - tankCenterY > 0)
                rotationRequired = Math.toRadians(90 - 0);
            else
                rotationRequired = Math.toRadians(-90 - 0);
        }
        affineTransform = new AffineTransform();
        affineTransform.translate(tankCenterX, tankCenterY);
        affineTransform.rotate(rotationRequired);
        affineTransform.translate( -15 , - 15);
        shootBullet();
        updateTankLocation();
    }
    private boolean shootIsValid() {
        if (checkArea(enemyLocation) != checkArea(getObstacleLocation()))
            return false;
        return true;
    }
    private void shootBullet() {
        if (System.currentTimeMillis() - startTime > 1000) {
            gunIsReloaded = true;
            startTime += 1000 ;
        }
        if (shootIsValid() && gunIsReloaded) {
            if (System.currentTimeMillis() - startTime >= 500) {
                if (shootIsValid()) {
                    bullets.add(new HeavyBullet(enemyLocation, tankCenterX, tankCenterY, rotationRequired , 120));
                    SoundPlayer.playSound("enemyShot");
                }
                startTime += 1000;
            }
        }
    }
    private void updateTankLocation (){
        if (directionChoosed <= 15)
        {
            switch (direction){
                case 'R' :
                    if (GameState.allowToMove("right" , this))
                        getObstacleLocation().x += 5 ;
                    else
                        directionChoosed = 16 ;
                    break;
                case 'L' :
                    if (GameState.allowToMove("left" , this))
                        getObstacleLocation().x -= 4 ;
                    else
                        directionChoosed = 16 ;
                    break;
                case 'U' :
                    if (GameState.allowToMove("up" , this))
                        getObstacleLocation().y -= 4 ;
                    else
                        directionChoosed = 16;
                    break;
                case 'D' :
                    if (GameState.allowToMove("down" , this))
                        getObstacleLocation().y += 4 ;
                    else
                        directionChoosed = 16;
                    break;
            }
            directionChoosed ++ ;
        }
        else {
            directionChoosed = 0 ;
            switch (randomGenerator.nextInt(4)) {
                case 0:
                    direction = 'R';
                    break;
                case 1:
                    direction = 'L';
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
}
