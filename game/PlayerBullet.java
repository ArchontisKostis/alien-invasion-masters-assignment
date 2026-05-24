import greenfoot.*;

/**
 * PlayerBullet — the laser bolt fired by PlayerCannon.
 *
 * Travels straight upward at SPEED px/act. Removed when it exits the top
 * of the world (getY() &lt; 0).
 *
 * ── Collision stubs ───────────────────────────────────────────────────────────
 *   Collision checks for Alien and BunkerTile are active.
 *
 * ── Sprite ───────────────────────────────────────────────────────────────────
 *   Loads ship/player_bullet.png from images/ and scales it to 8×28 px.
 *
 * Source: original code; follows game1_space_invaders_FINAL.md §7.3.
 */
public class PlayerBullet extends SmoothMover
{
    private static final double SPEED = 8.0;   // px/act upward

    // ── Constructor ───────────────────────────────────────────────────────────

    public PlayerBullet()
    {
        GreenfootImage img = new GreenfootImage("ship/player_bullet.png");
        img.scale(8, 28);
        setImage(img);
    }

    // ── Act ───────────────────────────────────────────────────────────────────

    @Override
    public void act()
    {
        if (GameSettings.isPaused()) return;
        move(0, -SPEED);

        // Exited top of world — remove
        // Use exactY (sub-pixel position) as Greenfoot may clamp getY() to 0
        // when an actor tries to move outside the world bounds. Relying on
        // exactY ensures the bullet is removed even if the displayed Y is
        // stuck at the top edge.
        if (getY() < 0 || exactY < 0) {
            getWorld().removeObject(this);
            return;
        }

        // ── Collision: Alien ──────────────────────────────────────────────────
        Alien a = (Alien) getOneIntersectingObject(Alien.class);
        if (a != null) {
            ScoreManager.addPoints(a.getPoints());
            getWorld().addObject(new ScorePopup(a.getPoints()), a.getX(), a.getY());
            a.die();                                   // spawns AlienExplosion, plays sound,
                                                       // notifies AlienGrid → may trigger level clear
            getWorld().removeObject(this);
            return;
        }

        // ── Collision: BunkerTile ─────────────────────────────────────────────
        BunkerTile bt = (BunkerTile) getOneIntersectingObject(BunkerTile.class);
        if (bt != null) {
            bt.damage();                               // plays bunker_hit.wav
            getWorld().removeObject(this);
        }
    }

}
