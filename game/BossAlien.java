import greenfoot.*;

/**
 * BossAlien — Level 3 boss target.
 */
public class BossAlien extends Actor
{
    private static final int MAX_HP = 140;
    private static final int MIN_X = 90;
    private static final int MAX_X = 710;
    private static final int SHOOT_MIN = 32;
    private static final int SHOOT_RANGE = 22;
    private static final int SCALE = 2; // sprite scale multiplier

    private int hp = MAX_HP;
    private int direction = 1;
    private double speed = 1.6;
    private int shootTimer = 0;
    private int shootInterval = SHOOT_MIN;
    private int volleyCount = 0;
    private boolean defeated = false; // true once death sequence completed
    private boolean dying = false;    // true while death/explosion sequence runs
    private int deathTimer = 0;
    private static final int DEATH_DURATION = 24; // acts to show one full explosion before win
    private double logicalX = 0; // movement logic X separate from visual shake
    private int baseY = 0;       // base Y position (visual shake applied relative to this)

    // hit feedback
    private int hitTimer = 0;
    private static final int HIT_DURATION = 12; // acts to flicker and shake after hit
    private static final int SHAKE_MAG = 6;     // pixels
    private GreenfootImage[] frames;
    private int frameIndex = 0;
    private int frameTimer = 0;
    private static final int FRAME_DELAY = 8;

    public BossAlien()
    {
        loadFrames();
        if (frames != null && frames.length > 0) {
            setImage(frames[0]);
        }
        randomizeShootInterval();
    }

    @Override
    protected void addedToWorld(World world)
    {
        // initialise logical position once placed in the world
        logicalX = getX();
        baseY = getY();
    }

    @Override
    public void act()
    {
        if (GameSettings.isPaused()) return;
        if (defeated) return;

        if (dying) {
            handleDeathSequence();
            return;
        }

        // Normal behaviour
        moveHorizontal();
        tickShooting();
        animate();
        applyHitEffects();
        checkPlayerHit();
    }

    public int getHp()
    {
        return hp;
    }

    public int getMaxHp()
    {
        return MAX_HP;
    }

    private void moveHorizontal()
    {
        int nextX = (int)Math.round(logicalX + speed * direction);
        if (nextX >= MAX_X) {
            nextX = MAX_X;
            direction = -1;
        } else if (nextX <= MIN_X) {
            nextX = MIN_X;
            direction = 1;
        }
        logicalX = nextX;

        // visual position will be set in applyHitEffects() which may add a small shake offset
        // but default to logical position and baseY
        setLocation((int)Math.round(logicalX), baseY);
    }

    private void tickShooting()
    {
        shootTimer++;
        if (shootTimer < shootInterval) return;

        shootTimer = 0;
        volleyCount++;
        randomizeShootInterval();

        World w = getWorld();
        if (w == null) return;

        // Main shot (offsets scaled with sprite)
        int mainYOffset = 26 * SCALE;
        int sideXOffset = 36 * SCALE;
        int sideYOffset = 24 * SCALE;
        w.addObject(new AlienBullet(), getX(), getY() + mainYOffset);

        // Every third volley, fire side shots too.
        if (volleyCount % 3 == 0) {
            w.addObject(new AlienBullet(), getX() - sideXOffset, getY() + sideYOffset);
            w.addObject(new AlienBullet(), getX() + sideXOffset, getY() + sideYOffset);
        }

        GameWorld.playSound("alien_shoot.wav");
    }

    private void randomizeShootInterval()
    {
        shootInterval = SHOOT_MIN + Greenfoot.getRandomNumber(SHOOT_RANGE + 1);
    }

    private void checkPlayerHit()
    {
        PlayerBullet pb = (PlayerBullet) getOneIntersectingObject(PlayerBullet.class);
        if (pb == null) return;

        World w = getWorld();
        if (w == null) return;

        w.removeObject(pb);
        // Each player bullet deals 2 damage to the boss
        hp = Math.max(0, hp - 2);

        // start hit feedback
        hitTimer = HIT_DURATION;

        if (hp <= 0) {
            startDeathSequence(w);
        }
    }

    private void startDeathSequence(World w)
    {
        if (dying || defeated) return;
        dying = true;
        deathTimer = DEATH_DURATION;
        // play a heavier explosion loop or single cue
        GameWorld.playSound("alien_explode.wav");
        // show one centered boss explosion sprite sequence
        w.addObject(new BossExplosion(), (int)Math.round(logicalX), baseY);
        // ensure boss stops shooting/moving (dying checked in act())
    }

    private void handleDeathSequence()
    {
        World w = getWorld();
        if (w == null) return;

        deathTimer--;
        if (deathTimer <= 0) {
            // score popup, then trigger win after the single explosion has played
            int x = (int)Math.round(logicalX);
            int y = baseY;
            ScoreManager.addPoints(2000);
            w.addObject(new ScorePopup(2000), x, y);
            // mark fully finished and remove self
            defeated = true;
            // remove this actor before triggering world change
            w.removeObject(this);
            if (w instanceof Level3World) {
                ((Level3World) w).onBossDefeated();
            }
        }
    }
    private void loadFrames()
    {
        try {
            String[] names = new String[] {"boss/boss_alien_0.png", "boss/boss_alien_1.png"};
            frames = new GreenfootImage[names.length];
            for (int i = 0; i < names.length; i++) {
                GreenfootImage orig = new GreenfootImage(names[i]);
                // make a private copy before scaling to avoid mutating any cached image
                GreenfootImage scaled = new GreenfootImage(orig);
                int nw = orig.getWidth() * SCALE;
                int nh = orig.getHeight() * SCALE;
                scaled.scale(nw, nh);
                frames[i] = scaled;
            }
        } catch (Exception ex) {
            frames = null;
        }
    }

    private void animate()
    {
        if (frames == null || frames.length == 0) return;
        frameTimer++;
        if (frameTimer < FRAME_DELAY) return;
        frameTimer = 0;
        frameIndex = (frameIndex + 1) % frames.length;
        // create a copy so we can adjust transparency when hit
        GreenfootImage img = new GreenfootImage(frames[frameIndex]);
        setImage(img);
    }

    private void applyHitEffects()
    {
        // If hit recently, flicker and apply small shake
        if (hitTimer > 0) {
            // flicker transparency
            GreenfootImage cur = getImage();
            if (cur != null) {
                int alpha = (hitTimer % 2 == 0) ? 120 : 255;
                cur.setTransparency(alpha);
            }

            // small random shake around logical position
            int dx = Greenfoot.getRandomNumber(SHAKE_MAG * 2 + 1) - SHAKE_MAG;
            int dy = Greenfoot.getRandomNumber(SHAKE_MAG * 2 + 1) - SHAKE_MAG;
            setLocation((int)Math.round(logicalX) + dx, baseY + dy);

            hitTimer--;
            if (hitTimer <= 0) {
                // restore normal image transparency and position
                GreenfootImage cur2 = getImage();
                if (cur2 != null) cur2.setTransparency(255);
                setLocation((int)Math.round(logicalX), baseY);
            }
        } else {
            // ensure image is fully opaque and at logical position
            GreenfootImage cur = getImage();
            if (cur != null) cur.setTransparency(255);
            setLocation((int)Math.round(logicalX), baseY);
        }
    }
}
