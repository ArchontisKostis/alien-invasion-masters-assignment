import greenfoot.*;

/**
 * PlayerExplosion — 5-frame large explosion for player ship death.
 *
 * - Frame timeline 
 *   Frame 0 (acts 0-3):  White flash — blinding
 *   Frame 1 (acts 4-7):  Yellow-white fireball, hull breaking apart
 *   Frame 2 (acts 8-11): Orange fireball, debris chunks flying outward
 *   Frame 3 (acts 12-15): Red-orange smoke, fragments scattered
 *   Frame 4 (acts 16-19): Dark grey smoke fading, just embers
 *   After act 20: actor removed
 */
public class PlayerExplosion extends Actor
{
    private static final int FRAME_THRESHOLD = 4;  // acts per frame

    private GreenfootImage[] frames;
    private int currentFrame = 0;
    private int frameCounter = 0;

    // ── Constructor ───────────────────────────────────────────────────────────

    public PlayerExplosion()
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
        GreenfootImage[] imgs = new GreenfootImage[5];
        for (int i = 0; i < 5; i++) {
            imgs[i] = new GreenfootImage("ship/explosion_player_" + i + ".png");
        }
        return imgs;
    }
}
