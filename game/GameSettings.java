import greenfoot.*;

/**
 * GameSettings — static settings store shared across every class.
 *
 * All fields are private-static with public get/set accessors.
 * No instances are ever created.
 *
 * ── Settings ──────────────────────────────────────────────────────────────────
 *   musicVolume      0-100 (default 70)
 *   sfxVolume        0-100 (default 80)  [Greenfoot does not support per-clip
 *                                         volume for one-shot sounds]
 *   paused           true while the UI overlay is open
 *
 * ── Cheats ────────────────────────────────────────────────────────────────────
 *   cheatInvincible  player cannot die when true
 *
 * Source: original code; follows game1_space_invaders_FINAL.md §5.
 */
public class GameSettings
{
    // ── Volume ────────────────────────────────────────────────────────────────

    private static int musicVolume = 70;   // 0-100
    private static int sfxVolume   = 80;   // 0-100

    // ── Pause ─────────────────────────────────────────────────────────────────

    private static boolean paused = false;

    // ── Cheats ────────────────────────────────────────────────────────────────

    private static boolean cheatInvincible = false;

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

    // ── Pause accessor ────────────────────────────────────────────────────────

    public static boolean isPaused() { return paused; }

    public static void setPaused(boolean p) { paused = p; }

    // ── Cheat accessors ───────────────────────────────────────────────────────

    public static boolean isCheatInvincible() { return cheatInvincible; }

    public static void setCheatInvincible(boolean v) { cheatInvincible = v; }

    /** Toggle invincibility cheat; returns new state. */
    public static boolean toggleCheatInvincible()
    {
        cheatInvincible = !cheatInvincible;
        return cheatInvincible;
    }
}
