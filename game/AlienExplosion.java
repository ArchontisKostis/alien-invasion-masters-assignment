import greenfoot.*;

/**
 * AlienExplosion — 4-frame explosion played when any alien is killed.
 */
public class AlienExplosion extends Actor
{
    private static final int FRAME_THRESHOLD = 3;  // acts per frame

    private GreenfootImage[] frames;
    private int currentFrame = 0;
    private int frameCounter = 0;

    // ── Constructor ───────────────────────────────────────────────────────────

    public AlienExplosion()
    {
        frames = loadFrames();
        setImage(frames[0]);
    }

    // ── act ───────────────────────────────────────────────────────────────────

    @Override
    public void act()
    {
        frameCounter++;
        if (frameCounter >= FRAME_THRESHOLD) {
            frameCounter = 0;
            currentFrame++;
            if (currentFrame >= frames.length) {
                getWorld().removeObject(this);
                return;
            }
            setImage(frames[currentFrame]);
        }
    }

    // ── Image factory ─────────────────────────────────────────────────────────

    private static GreenfootImage[] loadFrames()
    {
        GreenfootImage[] imgs = new GreenfootImage[4];
        for (int i = 0; i < 4; i++) {
            imgs[i] = new GreenfootImage("aliens/explosion_alien_" + i + ".png");
        }
        return imgs;
    }
}
