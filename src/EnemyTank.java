import java.awt.*;
import java.util.ArrayList;

/**
 * @author AmirHosseinRasulian & MohammadHasanRashidi
 */
public class EnemyTank extends Tank {
    protected Point enemyLocation;
    protected ArrayList<Bullet> bullets;
    protected long startTime;
    protected int tankCenterX;
    protected int tankCenterY;
    protected int tankNumber ;
    public EnemyTank(int locationX, int locationY, double rotationRequired, Point enemyLocation, int health, int width, int height, int prize , int tankNumber) {
        super(locationX, locationY, rotationRequired, health, width, height , "enemy",prize);
        this.enemyLocation = new Point(enemyLocation.x + 60, enemyLocation.y + 60);
        this.tankNumber = tankNumber;
        bullets = new ArrayList<>();
        startTime = System.currentTimeMillis();
    }

    public ArrayList<Bullet> getBullets() {
        return bullets;
    }

    protected int checkArea(Point location) {
        int area = 1;
        if (location.y >= 720 && location.y <= 1440)
            area += 3;
        if (location.y > 1440)
            area += 6;

        if (location.x >= GameFrame.GAME_WIDTH && location.x <= GameFrame.GAME_WIDTH * 2)
            area++;
        if (location.x >= GameFrame.GAME_WIDTH * 2)
            area += 2;

        return area;
    }


}
