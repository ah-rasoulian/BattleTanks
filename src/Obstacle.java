import java.awt.*;

/**
 * @author AmirHosseinRasulian & MohammadHasanRashidi
 */
public class Obstacle {
    protected Point obstacleLocation;
    protected Rectangle obstacleRec;
    private boolean impact;
    private String obstacleName;
    private int prize;

    public Obstacle(int locationX, int locationY, int width, int height, boolean impact, String obstacleName, int prize) {
        obstacleLocation = new Point(locationX, locationY);
        obstacleRec = new Rectangle(locationX, locationY, width, height);
        this.impact = impact;
        this.obstacleName = obstacleName;
        this.prize = prize;
    }

    public Point getObstacleLocation() {
        return obstacleLocation;
    }

    public Rectangle getObstacleRec() {
        return obstacleRec;
    }

    public boolean isImpact() {
        return impact;
    }

    public String getObstacleName() {
        return obstacleName;
    }

    public int getPrize() {
        return prize;
    }
}
