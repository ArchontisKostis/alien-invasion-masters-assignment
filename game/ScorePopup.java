import greenfoot.*;

/**
 * ScorePopup — floating "+N" label that rises and fades after an alien is killed.
 */
public class ScorePopup extends Actor
{
    private static final int    LIFETIME  = 30;     // acts alive
    private static final double RISE_SPEED = 0.6;  // px/act upward

    private final int value;
    private int lifeTimer = LIFETIME;
    private double exactY;

    // ── Constructor ───────────────────────────────────────────────────────────

    public ScorePopup(int value)
    {
        this.value = value;
        updateImage(255);
    }

    @Override
    public void addedToWorld(World w)
    {
        exactY = getY();
    }

    // ── act ───────────────────────────────────────────────────────────────────

    @Override
    public void act()
    {
        if (GameSettings.isPaused()) return;
        lifeTimer--;

        // Fade: alpha decreases linearly from 255 → 0
        int alpha = (int)(255.0 * lifeTimer / LIFETIME);
        updateImage(Math.max(0, alpha));

        // Rise
        exactY -= RISE_SPEED;
        setLocation(getX(), (int)exactY);

        if (lifeTimer <= 0) {
            getWorld().removeObject(this);
        }
    }

    // ── Image ─────────────────────────────────────────────────────────────────

    private void updateImage(int alpha)
    {
        // Choose colour based on point value
        Color col;
        if (value >= 100)       col = new Color(255, 100, 255, alpha);  // magenta — Boss / high-value
        else if (value >= 30)   col = new Color(255, 220,   0, alpha);  // gold — HardAlien
        else if (value >= 20)   col = new Color(  0, 220, 220, alpha);  // cyan — MidAlien
        else                    col = new Color(100, 255,  80, alpha);  // green — EasyAlien

        GreenfootImage img = new GreenfootImage(
            "+" + value, 18, col, new Color(0, 0, 0, 0));
        setImage(img);
    }
}
