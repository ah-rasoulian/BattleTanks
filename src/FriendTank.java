import java.awt.geom.AffineTransform;

public class FriendTank extends MyTank {
    private AffineTransform affineTransform;
    public FriendTank(int locationX, int locationY, double rotationRequired) {
        super(locationX, locationY, rotationRequired);
        affineTransform = new AffineTransform();
    }

    public AffineTransform getAffineTransform() {
        affineTransform = new AffineTransform();
        affineTransform.translate(obstacleLocation.x + 50 , obstacleLocation.y + 50);
        affineTransform.rotate(rotationRequired);
        affineTransform.translate(-30 , -31);
        return affineTransform;
    }
}
