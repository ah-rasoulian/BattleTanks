public class FriendTank extends MyTank {
    public FriendTank(int locationX, int locationY, double rotationRequired) {
        super(locationX, locationY, rotationRequired);
    }
    public void updateLocation (int locX , int locY){
        obstacleLocation.x = locX ;
        obstacleLocation.y = locY ;
    }
}
