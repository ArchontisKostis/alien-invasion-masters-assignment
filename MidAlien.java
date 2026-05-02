import greenfoot.*;

/**
 * MidAlien — middle rows (rows 2 & 3), 20 points.
 *
 * Crab-shaped alien (cyan).
 *   Frame 0: claws bent inward/up
 *   Frame 1: claws extended outward
 *
 * NOTE: asset filename is "med_alien_*" (not "mid_alien_*") — matches actual files.
 * Source: original code; follows game1_space_invaders_FINAL.md §7.7.
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
