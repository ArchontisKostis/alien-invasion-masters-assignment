import greenfoot.*;
import java.util.ArrayList;
import java.util.List;

/**
 * GameWorld — abstract base class for gameplay levels.
 *
 * Centralises everything that is identical across levels so subclasses stay thin.
 *
 */
public abstract class GameWorld extends World
{
    // ── UI ────────────────────────────────────────────────────────────────────

    /**
     * The pause/settings overlay actor.
     */
    protected UIManager uiManager;

    // ── Game state ────────────────────────────────────────────────────────────

    /** Remaining lives. Starts at 3; reaching 0 triggers game over. */
    protected int lives = 3;

    /** Level number (1, 2, ...). Set via constructor. */
    protected final int level;

    /** Active player actor. Null while the death/respawn animation plays. */
    protected PlayerCannon player;

    /** HUD life-icon actors. One is removed each time playerHit() is called. */
    protected List<LifeIcon> lifeIcons = new ArrayList<>();

    // ── Music ─────────────────────────────────────────────────────────────────

    /**
     * Background music for this level.
     */
    protected GreenfootSound bgMusic;
    private boolean musicLoopStarted = false;

    // ── Respawn ───────────────────────────────────────────────────────────────

    private int respawnTimer = 0;
    private boolean giveRespawnProtection = false;
    private int respawnX = 400;   // default spawn X
    private int respawnY = 540;   // default spawn Y
    /** Number of acts between death and respawn. ~1.5 s at 60 fps. */
    private static final int RESPAWN_DELAY = 90;
    // Delay before showing Game Over to allow explosion/flash animation to play
    private int gameOverTimer = 0;
    private static final int GAME_OVER_DELAY = 90; // acts (~1.5 s)

    // ── Constructor ───────────────────────────────────────────────────────────

    /**
     * @param width     World width  in pixels (800)
     * @param height    World height in pixels (600)
     * @param cellSize  Cell size (always 1 for pixel-perfect worlds)
     * @param level     Level number shown in the HUD
     */
    public GameWorld(int width, int height, int cellSize, int level)
    {
        super(width, height, cellSize);
        this.level = level;
        // NOTE: do NOT call buildLevel() here.
        // Each subclass calls it from its own constructor after super().
    }

    // ── Greenfoot lifecycle ───────────────────────────────────────────────────

    /**
     * Called by Greenfoot when the Run button is pressed.
     * Start the background music loop here — not in the constructor —
     * so music only plays while the scenario is actually running.
     */
    @Override
    public void started()
    {
        startMusicLoopIfNeeded();
    }

    /**
     * Called by Greenfoot when Stop is pressed or the world is replaced.
     * Always stop music to prevent audio leaking into the next world.
     */
    @Override
    public void stopped()
    {
        if (bgMusic != null) BackgroundMusic.stop(bgMusic);
        musicLoopStarted = false;
    }

    // ── act ───────────────────────────────────────────────────────────────────

    /**
     * Base act loop — handles HUD refresh and respawn countdown.
     * Subclasses that override act() MUST call super.act() first.
     */
    @Override
    public void act()
    {
        // Some world transitions happen while Run is active and may skip started().
        // Ensure level music still begins once gameplay acts are running.
        startMusicLoopIfNeeded();

        // Freeze all world-level logic while the UI overlay is open.
        // UIManager.act() is NOT blocked here — it handles ESC/close itself.
        if (GameSettings.isPaused()) return;

        updateHUD();

        // If a game-over sequence is pending, count it down here so the
        // explosion and flash actors have time to play before we switch worlds.
        if (gameOverTimer > 0) {
            gameOverTimer--;
            if (gameOverTimer == 0) {
                triggerGameOver(false);
            }
            return;
        }

        if (respawnTimer > 0) {
            respawnTimer--;
            if (respawnTimer == 0) {
                spawnPlayer();
                // Respawn is silent; the hit sound already played when the player died.
            }
        }
    }

    // ── UI builder ───────────────────────────────────────────────────────────

    /**
     * Create a UIManager actor and add it to the world at centre (400, 300).
     * Call this LAST in buildLevel() so it is painted on top of everything else
     * (Greenfoot paints actors above objects added earlier, and setPaintOrder
     * is also applied inside UIManager.addedToWorld()).
     */
    protected void buildUI()
    {
        uiManager = new UIManager();
        addObject(uiManager, 400, 300);
    }

    /**
     * Update the background-music volume at runtime.
     * Called by UIManager when the player changes the music volume slider.
     *
     * @param vol  0-100
     */
    public void applyMusicVolume(int vol)
    {
        if (bgMusic == null) return;
        try {
            bgMusic.setVolume(Math.max(0, Math.min(100, vol)));
        } catch (Exception ignored) {}
    }

    // ── Abstract — subclasses must implement ──────────────────────────────────

    /**
     * Set up the complete level: background, player, aliens, bunkers, music.
     * Call this from the SUBCLASS constructor (after super()), not from here.
     */
    protected abstract void buildLevel();

    // ── Shared implementations (may be overridden) ────────────────────────────

    /**
     * Create a new PlayerCannon and place it at the default spawn point (400, 540).
     * Override in a subclass if the level needs a different spawn position.
     */
    protected void spawnPlayer()
    {
        player = new PlayerCannon();
        addObject(player, respawnX, respawnY);
        if (giveRespawnProtection) {
            player.activateRespawnProtection();
            giveRespawnProtection = false;
        }
        // Reset to default for next level
        respawnX = 400;
        respawnY = 540;
    }

    /**
     * Redraw the top 32-px HUD strip on the background image.
     *   Left   — current score (5-digit, zero-padded)
     *   Centre — high score
     *   Right  — level number  (life icons are Actor objects, not drawn here)
     *
     * Tiles the scoreboard background graphics (scaled to 32x32) horizontally
     * across the HUD bar, then draws the HUD text on top.
     *
     * Override to add level-specific HUD elements (e.g., mystery-ship indicator).
     */
    protected void updateHUD()
    {
        GreenfootImage bg = getBackground();
        int hudHeight = 32;
        int tileSize = 32;  // Tiles scaled from 64x64 to 32x32
        int bgWidth = getWidth();

        // Load and scale tile images (64x64 → 32x32)
        GreenfootImage tileStart = new GreenfootImage("ui/score/score_tile_start_left.png");
        tileStart.scale(tileSize, hudHeight);

        GreenfootImage tile0 = new GreenfootImage("ui/score/score_tile_0.png");
        tile0.scale(tileSize, hudHeight);

        GreenfootImage tile2 = new GreenfootImage("ui/score/score_tile_2.png");
        tile2.scale(tileSize, hudHeight);

        GreenfootImage tileEnd = new GreenfootImage("ui/score/score_tile_end_right.png");
        tileEnd.scale(tileSize, hudHeight);

        // Draw left cap
        bg.drawImage(tileStart, 0, 0);

        // Tile the middle section, alternating between tile_0 and tile_2
        int x = tileSize;
        boolean useTile0 = true;
        while (x < bgWidth - tileSize) {
            bg.drawImage(useTile0 ? tile0 : tile2, x, 0);
            x += tileSize;
            useTile0 = !useTile0;  // Alternate tiles
        }

        // Draw right cap
        bg.drawImage(tileEnd, bgWidth - tileSize, 0);

        // Draw HUD text on top
        drawHUDText("SCORE: " + String.format("%05d", ScoreManager.getScore()),  10,  7);
        drawHUDText("HI:    " + String.format("%05d", ScoreManager.getHighScore()), 310, 7);
        drawHUDText("LEVEL: " + level, 600, 7);
    }

    // ── Shared game-event handlers ────────────────────────────────────────────

    /**
     * Call this when an alien bomb hits the PlayerCannon.
     *   1. Remove player from world.
     *   2. Decrement lives and play hit sound.
     *   3. Remove one life icon from the HUD.
     *   4a. If lives > 0: schedule respawn after RESPAWN_DELAY acts.
     *   4b. If lives == 0: trigger game over.
     *
     * Explosion actors (ScreenFlash, PlayerExplosion) are added here once
     * those classes exist — see the commented blocks below.
     */
    public void playerHit()
    {
        if (player != null) {
            // Capture hit location for respawn
            respawnX = player.getX();
            respawnY = player.getY();
            // Spawn 5-frame death explosion at ship's position
            addObject(new PlayerExplosion(), respawnX, respawnY);
            // Brief white screen flash for impact feedback
            addObject(new ScreenFlash(), 400, 300);
            removeObject(player);
            player = null;
        }

        lives--;
        playSound("player_hit.wav");

        // Remove rightmost life icon
        if (!lifeIcons.isEmpty()) {
            removeObject(lifeIcons.remove(lifeIcons.size() - 1));
        }

        if (lives <= 0) {
            // Start a short delay so the death explosion/flash are visible
            gameOverTimer = GAME_OVER_DELAY;
        } else {
            giveRespawnProtection = true;
            respawnTimer = RESPAWN_DELAY;
        }
    }

    /**
     * Transition to the between-levels screen.
     * Awards 500 pt level-clear bonus plus 100 pt per remaining life.
     */
    public void triggerLevelClear()
    {
        stopMusic();
        // Play a short level-clear cue only; avoid using full music files
        playFirstAvailableSound("level_clear.wav");
        ScoreManager.addPoints(500 + lives * 100);
        Greenfoot.setWorld(new LevelClearWorld(level));
    }

    /**
     * Transition to the Game Over / Victory screen.
     *
     * @param win  true = player cleared all aliens; false = lives exhausted or
     *             aliens reached the invasion line.
     */
    public void triggerGameOver(boolean win)
    {
        stopMusic();
        // Play only short cues here; don't accidentally kick off long music tracks
        playFirstAvailableSound(win ? "level_clear.wav" : "game_over.wav");
        Greenfoot.setWorld(new GameOverWorld(ScoreManager.getScore(), win));
    }

    // ── Helper: build the HUD life-icon row ──────────────────────────────────

    /**
     * Create LifeIcon actors and place them top-right, spaced 30 px apart.
     * Call once from buildLevel() after the background is set (so the HUD
     * black bar doesn't paint over the icons).
     */
    protected void buildLifeIcons()
    {
        for (int i = 0; i < lives; i++) {
            LifeIcon icon = new LifeIcon();
            addObject(icon, 762 - i * 30, 16);
            lifeIcons.add(icon);
        }
    }

    // ── Sound / music helpers ─────────────────────────────────────────────────

    /**
     * Load a GreenfootSound by filename.
     * Returns null (silently) if the file is not in the sounds/ folder yet,
     * so development can continue without every asset in place.
     *
     * @param filename  e.g. "music_level1.wav"
     * @return GreenfootSound, or null if the file is missing.
     */
    protected static GreenfootSound loadSound(String filename)
    {
        try {
            return new GreenfootSound(filename);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Play a one-shot sound effect with SFX volume setting.
     * Silently skips if the file is missing — no NullPointerException.
     *
     * @param filename  e.g. "laser.wav"
     */
    public static void playSound(String filename)
    {
        try {
            GreenfootSound sound = new GreenfootSound(filename);
            sound.setVolume(Math.max(0, Math.min(100, GameSettings.getSfxVolume())));
            sound.play();
        } catch (Exception e) {
            // Sound file not yet in sounds/ folder — skip silently
        }
    }

    /**
     * Play the first sound in the list that actually exists on disk.
     * Respects the current SFX volume setting.
     * Useful for cues that have no dedicated effect file in sounds/ yet.
     */
    protected static void playFirstAvailableSound(String... filenames)
    {
        for (String filename : filenames) {
            try {
                GreenfootSound sound = new GreenfootSound(filename);
                sound.setVolume(Math.max(0, Math.min(100, GameSettings.getSfxVolume())));
                sound.play();
                return;
            } catch (Exception ignored) {}
        }
    }

    /** Stop background music (null-safe). */
    protected void stopMusic()
    {
        if (bgMusic != null) BackgroundMusic.stop(bgMusic);
        musicLoopStarted = false;
    }

    private void startMusicLoopIfNeeded()
    {
        if (musicLoopStarted || bgMusic == null) return;

        try {
            BackgroundMusic.playLoop(bgMusic, GameSettings.getMusicVolume());
            musicLoopStarted = true;
        } catch (Exception e) {
            System.out.println("GameWorld: failed to play bgMusic for level " + level + ": " + e.getMessage());
        }
    }

    // ── Private helpers ───────────────────────────────────────────────────────

    /** Draw white text on the HUD background at (x, y). */
    private void drawHUDText(String text, int x, int y)
    {
        getBackground().drawImage(
            new GreenfootImage(text, 18, Color.WHITE, new Color(0, 0, 0, 0)),
            x, y);
    }
}
