import java.awt.*;
import java.awt.geom.AffineTransform;

public class Tank
{
    private Point tankLocation ;
    protected AffineTransform affineTransform;
    protected double rotationRequired ;

    public Tank (int locationX , int locationY , double rotationRequired)
    {
        tankLocation = new Point(locationX,locationY);
        this.rotationRequired = rotationRequired ;
    }
    public Point getTankLocation() {
        return tankLocation;
    }

    public double getRotationRequired() {
        return rotationRequired;
    }

    public void setRotationRequired(double rotationRequired) {
        this.rotationRequired = rotationRequired;
    }
}
