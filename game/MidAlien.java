import greenfoot.*;

/**
 * MidAlien — middle rows (rows 2 & 3), 20 points.
 */
public class MidAlien extends Alien
{
    private static final String FRAME_0 = "aliens/med_alien_0.png";
    private static final String FRAME_1 = "aliens/med_alien_1.png";

    public MidAlien()
    {
        frames = new GreenfootImage[]{
            new GreenfootImage(FRAME_0),
            new GreenfootImage(FRAME_1)
        };
        setImage(frames[0]);
        points = 20;
        setClassAggressiveness(1.15);
    }
}
