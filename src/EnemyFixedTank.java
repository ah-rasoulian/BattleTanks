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
        tankCenterX = getTankLocation().x + 32 ;
        tankCenterY = getTankLocation().y + 90 ;
        this.enemyLocation.x = enemyLocation.x + 50 ;
        this.enemyLocation.y = enemyLocation.y + 50 ;

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
        affineTransform.translate(tankCenterX,tankCenterY);
        affineTransform.rotate(rotationRequired - Math.toRadians(123));
        affineTransform.translate(-15 , -8 );
        shootBullet();
    }
    private void shootBullet (){
        if (System.currentTimeMillis() - startTime >= 2000) {
            bullets.add(new HeavyBullet(enemyLocation, tankCenterX, tankCenterY, rotationRequired ));
            SoundPlayer.playSound("cannon");
            startTime += 2000 ;
        }
    }
    public ArrayList<HeavyBullet> getBullets() {
        return bullets;
    }
}
