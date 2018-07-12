import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class multiplayDatas
{
    private Point freindTankLoc ;
    private ArrayList<Bullet> friendBullets;
    private HashMap <Integer , Point > enemysLocations ;
    public multiplayDatas ()
    {
        freindTankLoc = new Point();
        friendBullets = new ArrayList<>();
        enemysLocations = new HashMap<>();
    }

    public Point getFreindTankLoc() {
        return freindTankLoc;
    }

    public void setFreindTankLoc(Point freindTankLoc) {
        this.freindTankLoc = freindTankLoc;
    }

    public ArrayList<Bullet> getFriendBullets() {
        return friendBullets;
    }

    public void setFriendBullets(ArrayList<Bullet> friendBullets) {
        this.friendBullets = friendBullets;
    }

    public HashMap<Integer, Point> getEnemysLocations() {
        return enemysLocations;
    }

    public void setEnemysLocations(HashMap<Integer, Point> enemysLocations) {
        this.enemysLocations = enemysLocations;
    }
}
