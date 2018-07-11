import java.awt.geom.AffineTransform;

/**
 * @author AmirHosseinRasulian & MohammadHasanRashidi
 */
public class Tank extends Obstacle {
    protected double rotationRequired;
    protected int health;
    protected AffineTransform affineTransform;
    protected boolean destroy;

    public Tank(int locationX, int locationY, double rotationRequired , int health, int width, int height , String obstacleName) {
        super(locationX, locationY, width, height,true , obstacleName);
        this.rotationRequired = rotationRequired;
        this.health = health;
    }

    public double getRotationRequired() {
        return rotationRequired;
    }

    public void setRotationRequired(double rotationRequired) {
        this.rotationRequired = rotationRequired;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void decreaseHealth(int damage) {
        health -= damage;
    }

    public void setDestroy(boolean destroy) {
        this.destroy = destroy;
    }

}
