import java.awt.*;

public class Obstacle {
    protected Point obstacleLocation;
    protected Rectangle obstacleRec;
    private boolean impact;
    private String obstacleName ;
    public Obstacle(int locationX, int locationY, int width, int height, boolean impact , String obstacleName) {
        obstacleLocation = new Point(locationX, locationY);
        obstacleRec = new Rectangle(locationX, locationY, width, height);
        this.impact = impact;
        this.obstacleName = obstacleName;
    }

    public Point getObstacleLocation() {
        return obstacleLocation;
    }

    public void setObstacleLocation(Point obstacleLocation) {
        this.obstacleLocation = obstacleLocation;
    }

    public Rectangle getObstacleRec() {
        return obstacleRec;
    }

    public void setObstacleRec(Rectangle obstacleRec) {
        this.obstacleRec = obstacleRec;
    }

    public boolean isImpact() {
        return impact;
    }

    public String getObstacleName() {
        return obstacleName;
    }
}
