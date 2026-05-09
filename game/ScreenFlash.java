import greenfoot.*;

/**
 * ScreenFlash — draws a white semi-transparent overlay on the world background
 * for 5 acts (~83 ms), then restores the original background.
 *
 * Creates the "screen flash" hit-feedback effect when the player is killed.
 * This is a pure visual actor: it has no collision behaviour and no visible sprite.
 * The effect is baked directly onto the world background image.
 *
 * ── Usage ────────────────────────────────────────────────────────────────────
 *   addObject(new ScreenFlash(), 400, 300);   // position doesn't matter
 *
 * Source: original code; follows game1_space_invaders_FINAL.md §7.14.
 */
public class ScreenFlash extends Actor
{
    private static final int LIFETIME = 5;   // acts (≈ 83 ms at 60 fps)

    private int lifeTimer = LIFETIME;
    private GreenfootImage originalBackground;

    // ── Greenfoot lifecycle ───────────────────────────────────────────────────

    @Override
    public void addedToWorld(World w)
    {
        // Invisible actor — 1×1 transparent image so Greenfoot is satisfied
        setImage(new GreenfootImage(1, 1));

        // Save the current background so we can restore it after the flash
        originalBackground = new GreenfootImage(w.getBackground());

        // Draw the white overlay onto the live background
        GreenfootImage flash = new GreenfootImage(w.getWidth(), w.getHeight());
        flash.setColor(new Color(255, 255, 255, 180));   // semi-transparent white
        flash.fill();
        w.getBackground().drawImage(flash, 0, 0);
    }

    // ── act ───────────────────────────────────────────────────────────────────

    @Override
    public void act()
    {
        lifeTimer--;
        if (lifeTimer <= 0) {
            // Restore the original background (removes the flash overlay)
            getWorld().setBackground(originalBackground);
            getWorld().removeObject(this);
        }
    }
}
