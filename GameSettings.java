import greenfoot.*;

/**
 * GameSettings — static settings store shared across every class.
 *
 * All fields are private-static with public get/set accessors.
 * No instances are ever created.
 *
 * ── Settings ──────────────────────────────────────────────────────────────────
 *   musicVolume      0-100 (default 70)
 *   sfxVolume        0-100 (default 80)  [future use — Greenfoot has no per-clip
 *                                         volume for one-shot sounds]
 *   difficulty       0=Easy, 1=Normal, 2=Hard (default 1)
 *   paused           true while the UI overlay is open
 *
 * ── Cheats ────────────────────────────────────────────────────────────────────
 *   cheatInvincible  player cannot die when true
 *   cheatRapidFire   fire cooldown reduced to 2 acts when true
 *
 * ── Difficulty multipliers ────────────────────────────────────────────────────
 *   getSpeedMultiplier()        Used by AlienGrid to scale marching speed.
 *   getBombIntervalMultiplier() Used by AlienGrid to scale bomb frequency
 *                               (smaller = more frequent bombs).
 *
 * Source: original code; follows game1_space_invaders_FINAL.md §5.
 */
public class GameSettings
{
    // ── Volume ────────────────────────────────────────────────────────────────

    private static int musicVolume = 70;   // 0-100
    private static int sfxVolume   = 80;   // 0-100 (informational; passed to future SFX system)

    // ── Difficulty ────────────────────────────────────────────────────────────

    /** 0 = Easy, 1 = Normal, 2 = Hard */
    private static int difficulty = 1;

    // ── Pause ─────────────────────────────────────────────────────────────────

    private static boolean paused = false;

    // ── Cheats ────────────────────────────────────────────────────────────────

    private static boolean cheatInvincible = false;
    private static boolean cheatRapidFire  = false;

    // ── Prevent instantiation ─────────────────────────────────────────────────

    private GameSettings() {}

    // ── Volume accessors ──────────────────────────────────────────────────────

    public static int getMusicVolume() { return musicVolume; }

    public static void setMusicVolume(int vol)
    {
        musicVolume = Math.max(0, Math.min(100, vol));
    }

    public static int getSfxVolume() { return sfxVolume; }

    public static void setSfxVolume(int vol)
    {
        sfxVolume = Math.max(0, Math.min(100, vol));
    }

    // ── Difficulty accessors ──────────────────────────────────────────────────

    public static int getDifficulty() { return difficulty; }

    public static void setDifficulty(int d)
    {
        difficulty = Math.max(0, Math.min(2, d));
    }

    public static String getDifficultyName()
    {
        switch (difficulty) {
            case 0: return "Easy";
            case 2: return "Hard";
            default: return "Normal";
        }
    }

    /**
     * Alien march speed multiplier based on difficulty.
     * Easy: 0.70×, Normal: 1.00×, Hard: 1.35×
     */
    public static double getSpeedMultiplier()
    {
        switch (difficulty) {
            case 0: return 0.70;
            case 2: return 1.35;
            default: return 1.00;
        }
    }

    /**
     * Bomb interval multiplier based on difficulty.
     * Values < 1.0 mean more frequent bombs.
     * Easy: 1.40×, Normal: 1.00×, Hard: 0.70×
     */
    public static double getBombIntervalMultiplier()
    {
        switch (difficulty) {
            case 0: return 1.40;
            case 2: return 0.70;
            default: return 1.00;
        }
    }

    // ── Pause accessor ────────────────────────────────────────────────────────

    public static boolean isPaused() { return paused; }

    public static void setPaused(boolean p) { paused = p; }

    // ── Cheat accessors ───────────────────────────────────────────────────────

    public static boolean isCheatInvincible() { return cheatInvincible; }

    public static void setCheatInvincible(boolean v) { cheatInvincible = v; }

    public static boolean isCheatRapidFire() { return cheatRapidFire; }

    public static void setCheatRapidFire(boolean v) { cheatRapidFire = v; }

    /** Toggle invincibility cheat; returns new state. */
    public static boolean toggleCheatInvincible()
    {
        cheatInvincible = !cheatInvincible;
        return cheatInvincible;
    }

    /** Toggle rapid-fire cheat; returns new state. */
    public static boolean toggleCheatRapidFire()
    {
        cheatRapidFire = !cheatRapidFire;
        return cheatRapidFire;
    }
}
