/**
 * @author AmirHosseinRasulian & MohammadHasanRashidi
 */

public class MyTank extends Tank {

    private Integer numberOfHeavyBullets;
    private Integer numberOfLightBullets;

    public MyTank(int locationX, int locationY, double rotationRequired) {
        super(locationX, locationY, rotationRequired, 1000, 90, 80 , "myTank",0);
        numberOfHeavyBullets = 50;
        numberOfLightBullets = 300;
    }

    public Integer getNumberOfHeavyBullets() {
        return numberOfHeavyBullets;
    }

    public Integer getNumberOfLightBullets() {
        return numberOfLightBullets;
    }

    public void setNumberOfHeavyBullets(Integer numberOfHeavyBullets) {
        this.numberOfHeavyBullets = numberOfHeavyBullets;
    }

    public void setNumberOfLightBullets(Integer numberOfLightBullets) {
        this.numberOfLightBullets = numberOfLightBullets;
    }

    public void decreaseHeavyBullets() {
        numberOfHeavyBullets--;
    }

    public void decreaseLightBullets() {
        numberOfLightBullets--;
    }

    public void relief() {
        health = 1000;
    }

}
