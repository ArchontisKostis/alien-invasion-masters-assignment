import greenfoot.*;

/**
 * EasyAlien — bottom row (row 4), 10 points.
 *
 * Squid-shaped alien (lime green).
 *   Frame 0: tentacles pointing straight down
 *   Frame 1: tentacles splaying outward at 45°
 *
 * Source: original code; follows game1_space_invaders_FINAL.md §7.6.
 */
public class EasyAlien extends Alien
{
    private static final String FRAME_0 = "aliens/easy_alien_0.png";
    private static final String FRAME_1 = "aliens/easy_alien_1.png";

    public EasyAlien()
    {
        frames = new GreenfootImage[]{
            new GreenfootImage(FRAME_0),
            new GreenfootImage(FRAME_1)
        };
        setImage(frames[0]);
        points = 10;
        setClassAggressiveness(0.95);
    }
}
