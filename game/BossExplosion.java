import greenfoot.*;

/**
 * BossExplosion — 4-frame explosion used for the Level 3 boss death sequence.
 *
 * Uses the boss explosion sprite set from images/boss.
 */
public class BossExplosion extends Actor
{
    private static final int FRAME_THRESHOLD = 3;

    private GreenfootImage[] frames;
    private int currentFrame = 0;
    private int frameCounter = 0;

    public BossExplosion()
    {
        frames = loadFrames();
        setImage(frames[0]);
    }

    @Override
    public void act()
    {
        frameCounter++;
        if (frameCounter >= FRAME_THRESHOLD) {
            frameCounter = 0;
            currentFrame++;
            if (currentFrame >= frames.length) {
                World w = getWorld();
                if (w != null) {
                    w.removeObject(this);
                }
                return;
            }
            setImage(frames[currentFrame]);
        }
    }

    private static GreenfootImage[] loadFrames()
    {
        GreenfootImage[] imgs = new GreenfootImage[4];
        for (int i = 0; i < 4; i++) {
            imgs[i] = new GreenfootImage("boss/boss_explosion_" + i + ".png");
        }
        return imgs;
    }
}