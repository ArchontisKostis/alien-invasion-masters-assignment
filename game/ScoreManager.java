/**
 * ScoreManager — static score tracker shared across all worlds and levels.
 *
 * No instance is ever created; every field and method is static.
 * Call ScoreManager.addPoints(), ScoreManager.getScore(), etc. from anywhere.
 *
 * ── Lifetime ─────────────────────────────────────────────────────────────────
 *   score     resets to 0 when the player starts a new game (press R).
 *   highScore persists for the entire JVM session — it is NOT reset on restart.
 *
 * ── Usage ────────────────────────────────────────────────────────────────────
 *   ScoreManager.addPoints(20);              // alien killed
 *   int s = ScoreManager.getScore();         // draw on HUD
 *   ScoreManager.reset();                    // new game
 *
 * Source: original code; follows game1_space_invaders_FINAL.md §12.
 */
public class ScoreManager
{
    private static int score     = 0;
    private static int highScore = 0;

    /** Prevent instantiation — this is a pure static utility class. */
    private ScoreManager() {}

    // ── Mutation ──────────────────────────────────────────────────────────────

    /**
     * Reset current score to zero.
     * Call this when the player restarts a new game, NOT between levels
     * (score carries through Level 1 → Level 2).
     */
    public static void reset()
    {
        score = 0;
    }

    /**
     * Add pts to the current score.
     * Automatically updates highScore if the result exceeds it.
     *
     * @param pts  Points to add (should be &ge; 0).
     */
    public static void addPoints(int pts)
    {
        score += pts;
        if (score > highScore) {
            highScore = score;
        }
    }

    // ── Query ─────────────────────────────────────────────────────────────────

    /** @return The player's current score. */
    public static int getScore()     { return score; }

    /** @return The all-time high score for this JVM session. */
    public static int getHighScore() { return highScore; }
}
