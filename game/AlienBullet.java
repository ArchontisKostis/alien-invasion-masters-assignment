import greenfoot.*;

/**
 * AlienBullet — the zigzag bomb fired by the alien formation.
 *
 * ── Movement ──────────────────────────────────────────────────────────────────
 *   Falls straight down at 3.5 px/act. Removed when it exits the bottom.
 *
 * ── Animation ────────────────────────────────────────────────────────────────
 *   Two-frame zigzag flicker (threshold 2 = 30 fps flicker):
 *     Frame 0: leans left  — aliens/alien_bomp_0.png
 *     Frame 1: mirror of frame 0 (leans right)
 *
 * ── Collisions ────────────────────────────────────────────────────────────────
 *   PlayerCannon → GameWorld.playerHit()  (full death sequence)
 *   BunkerTile   → BunkerTile.damage()   (damages bunker, plays bunker_hit.wav)
 *
 * Source: original code; follows game1_space_invaders_FINAL.md §7.9.
 */
public class AlienBullet extends SmoothMover
{
    private static final double SPEED          = 3.5;
    private static final int    ANIM_THRESHOLD = 2;    // acts per frame (30 fps flicker)

    private GreenfootImage[] frames;
    private int animFrame   = 0;
    private int animCounter = 0;

    // ── Constructor ───────────────────────────────────────────────────────────

    public AlienBullet()
    {
        frames = buildFrames();
        setImage(frames[0]);
    }

    // ── act ───────────────────────────────────────────────────────────────────

    @Override
    public void act()
    {
        if (GameSettings.isPaused()) return;
        move(0, SPEED);

        // Zigzag animation
        animCounter++;
        if (animCounter >= ANIM_THRESHOLD) {
            animCounter = 0;
            animFrame   = 1 - animFrame;
            setImage(frames[animFrame]);
        }

        // Exited bottom of world
        // Use exactY (sub-pixel position) as Greenfoot may clamp getY() to world bounds.
        if (getY() > 610 || exactY > 610) {
            getWorld().removeObject(this);
            return;
        }

        // Hit player cannon
        PlayerCannon p = (PlayerCannon) getOneIntersectingObject(PlayerCannon.class);
        if (p != null) {
            if (!p.isInvulnerable() && !GameSettings.isCheatInvincible()) {
                ((GameWorld) getWorld()).playerHit();
            }
            getWorld().removeObject(this);
            return;
        }

        // Hit bunker tile
        BunkerTile bt = (BunkerTile) getOneIntersectingObject(BunkerTile.class);
        if (bt != null) {
            bt.damage();
            getWorld().removeObject(this);
        }
    }

    // ── Image factory ─────────────────────────────────────────────────────────

    private static GreenfootImage[] buildFrames()
    {
        GreenfootImage f0 = new GreenfootImage("aliens/alien_bomp_0.png");

        // Frame 1 = horizontal mirror of frame 0
        GreenfootImage f1 = new GreenfootImage(f0);
        f1.mirrorHorizontally();

        return new GreenfootImage[]{ f0, f1 };
    }
}
