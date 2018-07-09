import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.Random;

public class EnemyMovingTank3 extends EnemyTank {
    private boolean gunIsReloaded ;
    private char direction;
    private int directionChoosed;
    private Random randomGenerator;
    public EnemyMovingTank3(int locationX, int locationY, double rotationRequired, Point enemyLocation) {
        super(locationX, locationY, rotationRequired, enemyLocation);
        gunIsReloaded = true ;
        direction = 'R';
        directionChoosed = 0 ;
        randomGenerator = new Random();
    }
    public void updateEnemyLocation(Point enemyLocation1) {
        tankCenterX = getTankLocation().x + 45;
        tankCenterY = getTankLocation().y + 42;

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
        if (checkArea(enemyLocation) != checkArea(getTankLocation()))
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
        if (directionChoosed <= 10)
        {
            switch (direction){
                case 'R' :
                    getTankLocation().x += 6 ;
                    break;
                case 'L' :
                    getTankLocation().x -= 5 ;
                    break;
                case 'U' :
                    getTankLocation().y += 5 ;
                    break;
                case 'D' :
                    getTankLocation().y -= 5 ;
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
