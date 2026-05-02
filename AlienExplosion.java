import greenfoot.*;

/**
 * AlienExplosion — 4-frame explosion played when any alien is killed.
 *
 * ── Frame timeline (FRAME_THRESHOLD = 3 acts each, total 12 acts ≈ 0.2 s) ───
 *   Frame 0 (acts 0-2):  Small bright white/yellow starburst — initial flash
 *   Frame 1 (acts 3-5):  Expanding yellow-white ring, fragments appear
 *   Frame 2 (acts 6-8):  Orange debris cloud, ring breaking apart
 *   Frame 3 (acts 9-11): Fading red embers, nearly gone
 *   After act 12: actor removed from world
 *
 * ── Sprites ──────────────────────────────────────────────────────────────────
 *   Loads aliens/explosion_alien_0.png → aliens/explosion_alien_3.png directly.
 *
 * Source: original code; follows game1_space_invaders_FINAL.md §7.11.
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
