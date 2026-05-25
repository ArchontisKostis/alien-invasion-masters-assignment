import greenfoot.*;

/**
 * PlayerCannon — the player's laser cannon ship.
 */
public class PlayerCannon extends SmoothMover
{
    // ── Constants ─────────────────────────────────────────────────────────────

    private static final int    SPEED          = 4;    // px/act horizontal
    private static final int    FIRE_COOLDOWN     = 20;   // acts between shots
    private static final int    ANIM_THRESHOLD    = 3;    // acts per animation frame
    private static final int    X_MIN             = 24;   // left boundary
    private static final int    X_MAX             = 776;  // right boundary
    private static final int    INVULN_DURATION   = 90;   // ~1.5s at 60 fps
    private static final int    FLICKER_INTERVAL  = 4;    // acts per flicker step
    private static final int    FLICKER_ALPHA_DIM = 80;   // dim alpha while flickering

    // ── Animation ─────────────────────────────────────────────────────────────

    private final GreenfootImage[] frames = new GreenfootImage[2];
    private int animFrame   = 0;
    private int animCounter = 0;

    // ── Firing ────────────────────────────────────────────────────────────────

    private int     fireCooldownTimer = 0;
    private boolean canFire           = true;

    // ── Respawn protection ───────────────────────────────────────────────────

    private int  invulnTimer = 0;
    private int  flickerTick = 0;
    private boolean flickerVisible = true;

    // ── Constructor ───────────────────────────────────────────────────────────

    public PlayerCannon()
    {
        frames[0] = loadFrame("ship/player_ship_0.png");
        frames[1] = loadFrame("ship/player_ship_1.png");
        updateDisplayImage();
    }

    // ── Act ───────────────────────────────────────────────────────────────────

    @Override
    public void act()
    {
        if (GameSettings.isPaused()) return;
        handleMovement();
        handleFiring();
        animateEngine();
        tickCooldown();
        tickInvulnerability();
        updateDisplayImage();
    }

    // ── Private — input ───────────────────────────────────────────────────────

    private void handleMovement()
    {
        if (Greenfoot.isKeyDown("left")  && exactX > X_MIN) move(-SPEED, 0);
        if (Greenfoot.isKeyDown("right") && exactX < X_MAX) move( SPEED, 0);
    }

    private void handleFiring()
    {
        if (Greenfoot.isKeyDown("space") && canFire) {
            fire();
        }
    }

    private void fire()
    {
        // Spawn bullet just above the cannon's barrel
        getWorld().addObject(new PlayerBullet(), getX(), getY() - 16);
        GameWorld.playSound("laser.mp3");
        canFire           = false;
        fireCooldownTimer = FIRE_COOLDOWN;
    }

    // ── Private — animation ───────────────────────────────────────────────────

    /**
     * Alternate between frame 0 (dark nozzles) and frame 1 (cyan glow).
     * Swaps every ANIM_THRESHOLD acts → 20 fps at 60 fps sim speed.
     */
    private void animateEngine()
    {
        animCounter++;
        if (animCounter >= ANIM_THRESHOLD) {
            animCounter = 0;
            animFrame   = 1 - animFrame;    // toggle 0 ↔ 1
        }
    }

    private void tickInvulnerability()
    {
        if (invulnTimer <= 0) return;

        invulnTimer--;
        flickerTick++;
        if (flickerTick >= FLICKER_INTERVAL) {
            flickerTick = 0;
            flickerVisible = !flickerVisible;
        }

        if (invulnTimer == 0) {
            flickerVisible = true;
        }
    }

    private void updateDisplayImage()
    {
        GreenfootImage img = new GreenfootImage(frames[animFrame]);
        if (invulnTimer > 0) {
            img.setTransparency(flickerVisible ? 255 : FLICKER_ALPHA_DIM);
        }
        setImage(img);
    }

    // ── Private — fire cooldown ───────────────────────────────────────────────

    private void tickCooldown()
    {
        if (!canFire) {
            fireCooldownTimer--;
            if (fireCooldownTimer <= 0) canFire = true;
        }
    }

    public void activateRespawnProtection()
    {
        invulnTimer = INVULN_DURATION;
        flickerTick = 0;
        flickerVisible = true;
    }

    public boolean isInvulnerable()
    {
        return invulnTimer > 0;
    }

    // ── Private — image factory ───────────────────────────────────────────────

    private static GreenfootImage loadFrame(String filename)
    {
        GreenfootImage img = new GreenfootImage(filename);
        img.scale(64, 36);
        return img;
    }
}
