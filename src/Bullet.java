import java.awt.*;
import java.io.Serializable;

/**
 * this is a class that simulates a bullet from 2 kind lightBullet and heavyBullet
 * that they are 2 classes that extends this class
 * this class have the data of a bullet
 *
 * @author AmirHosseinRasulian & MohammadHasanRashidi
 */
public abstract class Bullet implements Serializable {
    protected Point pointOfShut;
    protected Point pointOfGun;
    protected double speed;
    protected double speedX;
    protected double speedY;
    protected int x;
    protected int y;
    protected double rotationRequired; // -90 degree to 270 degree
    protected double startTime;
    protected double distant;
    protected Rectangle bulletRec;

    public Bullet(GameState state, double speed, double radius) {
        this.speed = speed;
        startTime = System.currentTimeMillis();

        pointOfShut = state.getShootingPoint();
        rotationRequired = state.getRotationRequired();

        int centerOfTankX = state.getMyTank().getObstacleLocation().x + 50;
        int centerOfTankY = state.getMyTank().getObstacleLocation().y + 50;

        distant = Math.sqrt((pointOfShut.x - centerOfTankX) * (pointOfShut.x - centerOfTankX) + (pointOfShut.y - centerOfTankY) * (pointOfShut.y - centerOfTankY));

        speedX = this.speed * ((double) (pointOfShut.x - centerOfTankX)) / distant;
        speedY = this.speed * ((double) (pointOfShut.y - centerOfTankY)) / distant;

        pointOfGun = new Point(centerOfTankX + (int) (radius * speedX), centerOfTankY + (int) (radius * speedY));

        x = pointOfGun.x;
        y = pointOfGun.y;

    }

    public Bullet(Point shootingPoint, int centerOfTankX, int centerOfTankY, double rotationRequired, double speed, double radius) {
        this.speed = speed;
        startTime = System.currentTimeMillis();

        pointOfShut = shootingPoint;
        this.rotationRequired = rotationRequired;


        distant = Math.sqrt((pointOfShut.x - centerOfTankX) * (pointOfShut.x - centerOfTankX) + (pointOfShut.y - centerOfTankY) * (pointOfShut.y - centerOfTankY));

        speedX = this.speed * ((double) (pointOfShut.x - centerOfTankX)) / distant;
        speedY = this.speed * ((double) (pointOfShut.y - centerOfTankY)) / distant;

        pointOfGun = new Point(centerOfTankX + (int) (radius * speedX), centerOfTankY + (int) (radius * speedY));

        x = pointOfGun.x;
        y = pointOfGun.y;

    }

    public abstract void update();

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public double getRotationRequired() {
        return rotationRequired;
    }

    public Rectangle getBulletRec() {
        return bulletRec;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public void setSpeed(double speed,int centerOfTankX, int centerOfTankY) {

        this.speed = speed;
        speedX = this.speed * ((double) (pointOfShut.x - centerOfTankX)) / distant;
        speedY = this.speed * ((double) (pointOfShut.y - centerOfTankY)) / distant;
    }
}
