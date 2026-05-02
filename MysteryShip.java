import greenfoot.*;

/**
 * MysteryShip — the bonus target in Level 2.
 *
 * ── Spawn & Movement ──────────────────────────────────────────────────────────
 *   Spawns every 20–35 seconds at Y=30, random X position.
 *   Travels horizontally at 2.5 px/act (constant direction).
 *   Removed when it exits the sides of the world.
 *
 * ── Animation ─────────────────────────────────────────────────────────────────
 *   2-frame blink cycle:
 *     Frame 0: ship visible (alpha 255)
 *     Frame 1: ship dim (alpha 100) — creates a pulsing/flickering effect
 *   Swaps every 3 acts (~20 fps).
 *
 * ── Hidden Score ──────────────────────────────────────────────────────────────
 *   Each MysteryShip instance has a random hidden value: 50, 100, or 150 pts.
 *   When destroyed by player bullet, ScorePopup reveals the value (brief 1s float).
 *   The player never knows what they'll get until the ship dies.
 *
 * ── Sprite ────────────────────────────────────────────────────────────────────
 *   Loads raw/mystery_ship_0.png / raw/mystery_ship_1.png directly.
 *
 * ── Sound ─────────────────────────────────────────────────────────────────────
 *   mystery_ship_loop.wav plays while ship is on screen (looping).
 *   alien_explode.wav is used when the ship is destroyed.
 *   Stops when the ship exits or is destroyed.
 *
 * Source: original code; follows game1_space_invaders_FINAL.md §7.10.
 */
public class MysteryShip extends SmoothMover
{
    private static final double SPEED           = 2.5;
    private static final int    ANIM_THRESHOLD  = 3;    // acts per frame
    private static final int[]  HIDDEN_SCORES   = { 50, 100, 150 };

    private GreenfootImage[] frames;
    private int animFrame      = 0;
    private int animCounter    = 0;
    private int hiddenScore    = 0;
    private int direction      = 1;   // +1 right, -1 left
    private GreenfootSound loopSound = null;

    // ── Constructor ───────────────────────────────────────────────────────────

    public MysteryShip()
    {
        frames = buildFrames();
        setImage(frames[0]);

        // Random hidden score value
        hiddenScore = HIDDEN_SCORES[Greenfoot.getRandomNumber(HIDDEN_SCORES.length)];

        // Random initial direction
        direction = (Greenfoot.getRandomNumber(2) == 0) ? 1 : -1;

        // Start looping mystery ship sound
        try {
            loopSound = new GreenfootSound("mystery_ship_loop.wav");
            loopSound.playLoop();
        } catch (Exception ignored) {}
    }

    // ── act ───────────────────────────────────────────────────────────────────

    @Override
    public void act()
    {
        move(direction * SPEED, 0);

        // Blink animation
        animCounter++;
        if (animCounter >= ANIM_THRESHOLD) {
            animCounter = 0;
            animFrame = 1 - animFrame;
            setImage(frames[animFrame]);
        }

        // Exited left or right side
        if (getX() < -50 || getX() > 850) {
            stopSound();
            getWorld().removeObject(this);
            return;
        }

        // Hit by player bullet
        PlayerBullet pb = (PlayerBullet) getOneIntersectingObject(PlayerBullet.class);
        if (pb != null) {
            // Spawn explosion
            World w = getWorld();
            w.addObject(new AlienExplosion(), getX(), getY());

            // Award points with floating label
            ScoreManager.addPoints(hiddenScore);
            w.addObject(new ScorePopup(hiddenScore), getX(), getY());

            // Play a real available cue for the bonus target's destruction.
            GameWorld.playSound("alien_explode.wav");

            // Stop looping sound
            stopSound();

            // Remove both ship and bullet
            w.removeObject(pb);
            w.removeObject(this);
        }
    }

    // ── Image factory ─────────────────────────────────────────────────────────

    private static GreenfootImage[] buildFrames()
    {
        GreenfootImage f0 = new GreenfootImage("raw/mystery_ship_0.png");
        GreenfootImage f1 = new GreenfootImage("raw/mystery_ship_1.png");

        return new GreenfootImage[]{ f0, f1 };
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    /** Stop the looping mystery ship sound. */
    private void stopSound()
    {
        if (loopSound != null) {
            try {
                loopSound.stop();
            } catch (Exception ignored) {}
        }
    }

    public void removedFromWorld()
    {
        stopSound();
    }

    /** @return The hidden point value (50, 100, or 150). */
    public int getHiddenScore()
    {
        return hiddenScore;
    }
}
