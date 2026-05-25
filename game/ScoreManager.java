/**
 * ScoreManager — static score tracker shared across all worlds and levels.
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
