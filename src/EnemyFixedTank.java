import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

public class EnemyFixedTank extends Tank {
    private Point enemyLocation ;
    private ArrayList<HeavyBullet> bullets;
    private long startTime ;
    private int tankCenterX ;
    private int tankCenterY ;
    public EnemyFixedTank(int locationX, int locationY, double rotationRequired , Point enemyLocation) {
        super(locationX, locationY, rotationRequired);
        this.enemyLocation = new Point(enemyLocation.x + 50 , enemyLocation.y + 50) ;
        bullets = new ArrayList<>();
        startTime = System.currentTimeMillis();
    }
    public void updateEnemyLocation (Point enemyLocation){
        tankCenterX = getTankLocation().x + 43 ;
        tankCenterY = getTankLocation().y + 70 ;
        this.enemyLocation.x = enemyLocation.x + 50 ;
        this.enemyLocation.y = enemyLocation.y + 50 ;

        if (enemyLocation.x - tankCenterX > 0)
            rotationRequired = Math.atan(((double) (enemyLocation.y - tankCenterY - 11)) / ((double) (enemyLocation.x - tankCenterX + 20)));
        else if (enemyLocation.x - tankCenterX < 0)
            rotationRequired = Math.toRadians(180) + Math.atan(((double) (enemyLocation.y - tankCenterY)) / ((double) (enemyLocation.x - tankCenterX)));
        else {
            if (enemyLocation.y - tankCenterY > 0)
                rotationRequired = Math.toRadians(90);
            else
                rotationRequired = Math.toRadians(-90);
        }
        affineTransform = new AffineTransform();
        affineTransform.translate(tankCenterX,tankCenterY);
        if (Math.toDegrees(rotationRequired) < 74)
            rotationRequired = Math.toRadians(73);
        if (Math.toDegrees(rotationRequired) > 204)
            rotationRequired = Math.toRadians(205);
        affineTransform.rotate(rotationRequired - Math.toRadians(123));
        affineTransform.translate(-25, +14 );
        shootBullet();
    }
    private void shootBullet (){
        if (System.currentTimeMillis() - startTime >= 2000 ) {
            if (shootIsValid()) {
                bullets.add(new HeavyBullet(enemyLocation, tankCenterX , tankCenterY , rotationRequired ));
                SoundPlayer.playSound("enemyShot");
            }
            startTime += 2000 ;
        }
    }
    public ArrayList<HeavyBullet> getBullets() {
        return bullets;
    }
    private boolean shootIsValid ()
    {
        if (!(Math.toDegrees(rotationRequired) >= 74 && Math.toDegrees(rotationRequired) <= 204))
            return false;
        if (checkArea(enemyLocation) != checkArea(getTankLocation()))
            return false;
        return true;
    }
    private int checkArea (Point location)
    {
        int area = 1 ;
        if (location.y >= 720 && location.y <= 1440)
            area += 3 ;
        if (location.y > 1440)
            area += 6 ;

        if (location.x >= GameFrame.GAME_WIDTH && location.x <= GameFrame.GAME_WIDTH * 2)
            area ++ ;
        if (location.x >= GameFrame.GAME_WIDTH * 2)
            area += 2 ;

        return area ;
    }
}
