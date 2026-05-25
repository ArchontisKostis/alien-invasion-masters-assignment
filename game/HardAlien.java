import greenfoot.*;

/**
 * HardAlien — top rows (rows 0 & 1), 30 points.
 */
public class HardAlien extends Alien
{
    private static final String FRAME_0 = "aliens/hard_alien_0.png";
    private static final String FRAME_1 = "aliens/hard_alien_1.png";

    public HardAlien()
    {
        frames = new GreenfootImage[]{
            new GreenfootImage(FRAME_0),
            new GreenfootImage(FRAME_1)
        };
        setImage(frames[0]);
        points = 30;
        setClassAggressiveness(1.35);
    }
}
