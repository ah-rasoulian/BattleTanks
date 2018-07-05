
public class HeavyBullet extends Bullet{

    public HeavyBullet (GameState state)
    {
        super(state ,0.50 , 80);
    }
    //updating the point that bullet must draw there by the speed and x = vt formula
    public void update() {
        x = pointOfGun.x + (int)( speedX * (System.currentTimeMillis() - startTime));
        y = pointOfGun.y + (int)( speedY * (System.currentTimeMillis() - startTime));
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public double getRotationRequired() {
        return rotationRequired;
    }
}
