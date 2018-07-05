import java.awt.*;

public class LightBullet extends Bullet {

    public LightBullet (GameState state)
    {
        super(state , 0.90 , 67);
    }
    @Override
    public void update() {
        x = pointOfGun.x + (int)( speedX * (System.currentTimeMillis() - startTime));
        y = pointOfGun.y + (int)( speedY * (System.currentTimeMillis() - startTime));
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public double getRotationRequired() {
        return rotationRequired;
    }
}
