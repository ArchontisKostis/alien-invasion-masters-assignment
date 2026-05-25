import greenfoot.*;

/**
 * SmoothMover — abstract base class for all actors that need sub-pixel movement.
 *
 * Greenfoot stores actor positions as integers, which causes visible jittering
 * at slow speeds (e.g., aliens creeping across the screen at 1.5 px/act).
 * SmoothMover keeps the "true" position as doubles and only commits the rounded
 * integer to Greenfoot once per act — giving perfectly smooth motion at any speed.
 *
 * - How to use
 *   - Extend SmoothMover instead of Actor.
 *   - Call  move(dx, dy)  inside act() to translate by (dx, dy) pixels.
 *   - Never call setLocation() directly — it desynchronises exactX/exactY.
 *     If you must call setLocation() for a teleport, also update exactX/exactY.
 */
public abstract class SmoothMover extends Actor
{
    /** Exact horizontal position (double precision). */
    protected double exactX;

    /** Exact vertical position (double precision). */
    protected double exactY;

    // ── Movement ──────────────────────────────────────────────────────────────

    /**
     * Move this actor by (dx, dy) pixels.
     * Accumulates into exactX/exactY and rounds to the nearest integer pixel
     * before passing to Greenfoot's setLocation().
     *
     * @param dx  Horizontal displacement (positive = right)
     * @param dy  Vertical displacement   (positive = down)
     */
    protected void move(double dx, double dy)
    {
        exactX += dx;
        exactY += dy;
        setLocation((int) Math.round(exactX), (int) Math.round(exactY));
    }

    // ── Greenfoot lifecycle ───────────────────────────────────────────────────

    /**
     * Synchronise exactX/exactY with Greenfoot's integer position whenever
     * this actor is placed into a world (addObject / world constructor).
     * Without this, the first move() call would start from (0, 0).
     */
    @Override
    public void addedToWorld(World w)
    {
        exactX = getX();
        exactY = getY();
    }
}
