import greenfoot.*;
import java.util.List;

/**
 * Alien — abstract base class for all alien types (EasyAlien, MidAlien, HardAlien).
 */
public abstract class Alien extends Actor
{
    private static final int ANIM_INTERVAL_ACTS = 12;

    // ── Position bookkeeping ─────────────────────────────────────────────────

    /** Starting X of this alien when the grid has zero offset. */
    protected double baseX;

    /** Current Y — modified by dropOneRow(). */
    protected double baseY;

    // ── Scoring ──────────────────────────────────────────────────────────────

    /** Points awarded when this alien is killed. Set by subclass constructor. */
    protected int points;

    // ── Animation ────────────────────────────────────────────────────────────

    /** [frame0, frame1] loaded by subclass constructor. */
    protected GreenfootImage[] frames;

    /** Which frame is currently displayed (0 or 1). */
    protected int animFrame = 0;

    /** Counts acts to pace body animation. */
    private int animTick = 0;

    /** Per-class aggressiveness baseline (Easy < Mid < Hard by default). */
    private double classAggressiveness = 1.0;

    // ── Public API ────────────────────────────────────────────────────────────

    /**
     * Called by AlienGrid every act.
     * Repositions this alien based on the formation's cumulative horizontal offset.
     *
     * @param offsetX  Cumulative px moved from the starting X position (can be negative).
     */
    public void updateGridPosition(double offsetX)
    {
        setLocation((int)(baseX + offsetX), (int)baseY);

        if (frames != null && frames.length > 1) {
            animTick++;
            if (animTick >= ANIM_INTERVAL_ACTS) {
                animTick = 0;
                animFrame = 1 - animFrame;
                setImage(frames[animFrame]);
            }
        }
    }

    /**
     * Called by AlienGrid whenever the entire formation drops one row.
     * Moves the alien down by {@code amount} pixels AND toggles the animation
     * frame — the authentic Space Invaders technique where aliens animate on
     * each descent rather than on a fixed timer.
     *
     * @param amount  Pixels to drop (typically 20 px per bounce).
     */
    public void dropOneRow(int amount)
    {
        baseY += amount;
        setLocation(getX(), (int)baseY);
        animFrame = 1 - animFrame;
        setImage(frames[animFrame]);
    }

    /**
     * Set the pixel origin (top-left of the formation, i.e., offsetX = 0).
     * Called once after addObject() in the level's buildLevel() method.
     */
    public void setBasePosition(double x, double y)
    {
        baseX = x;
        baseY = y;
    }

    /** @return Points awarded to the player when this alien is killed. */
    public int getPoints()
    {
        return points;
    }

    /**
     * Set this alien type's baseline aggressiveness.
     * Values greater than 1 shoot more often in weighted selection.
     */
    public void setClassAggressiveness(double value)
    {
        classAggressiveness = Math.max(0.1, value);
    }

    /**
     * Aggressiveness score used by AlienGrid to weight shooter choice.
     * difficultyMultiplier lets each level globally scale hostility.
     */
    public double getAggressiveness(double difficultyMultiplier)
    {
        return classAggressiveness * Math.max(0.1, difficultyMultiplier);
    }

    /**
     * Kill this alien:
     *   1. Spawn a 4-frame AlienExplosion at this location.
    *   2. Play alien_explode.mp3.
     *   3. Notify AlienGrid so it can decrement its counter and check the win condition.
     *   4. Remove self from the world.
     *
     * Called by PlayerBullet on intersection.
     */
    public void die()
    {
        World w = getWorld();
        int x = getX(), y = getY();

        // 1. Spawn explosion animation
        w.addObject(new AlienExplosion(), x, y);

        // 2. Sound
        GameWorld.playSound("alien_explode.mp3");

        // 3. Notify AlienGrid
        @SuppressWarnings("unchecked")
        List<AlienGrid> grids = (List<AlienGrid>) w.getObjects(AlienGrid.class);
        if (!grids.isEmpty()) {
            grids.get(0).alienKilled();
        }

        // 4. Remove self
        w.removeObject(this);
    }
}
