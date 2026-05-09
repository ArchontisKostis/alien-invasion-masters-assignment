import greenfoot.*;

/**
 * LevelClearWorld — brief interstitial shown when a level is cleared.
 *
 * Displays the level-clear message and bonus info, then auto-advances after
 * AUTO_ADVANCE_DELAY acts (~2 s at 60 fps).
 *
 *   completedLevel == 1  →  advance to Level2World
 *   completedLevel == 2  →  advance to Level3World (boss fight)
 *   completedLevel >= 3  →  advance to GameOverWorld (win = true)
 *
 * Source: original code; follows game1_space_invaders_FINAL.md §6.
 */
public class LevelClearWorld extends World
{
    private static final int AUTO_ADVANCE_DELAY = 300;   // acts (≈ 5 s at 60 fps)

    private int countdown;
    private final int completedLevel;

    // ── Constructor ───────────────────────────────────────────────────────────

    public LevelClearWorld(int completedLevel)
    {
        super(800, 600, 1);
        this.completedLevel = completedLevel;
        this.countdown      = AUTO_ADVANCE_DELAY;

        setBackground(buildBackground());

        drawCentred("Level " + completedLevel + "  —  Clear!", 34,
            new Color(80, 255, 120), 230);
        drawCentred("Get Ready for Level " + (completedLevel + 1) + "...", 24,
            new Color(210, 215, 230), 286);
        drawCentred("Bonus:  500 pts  +  100 per remaining life", 18,
            new Color(255, 255, 100), 334);
    }

    // ── act ───────────────────────────────────────────────────────────────────

    @Override
    public void act()
    {
        // Allow player to skip the wait by pressing SPACE or ENTER.
        if (Greenfoot.isKeyDown("space") || Greenfoot.isKeyDown("enter")) {
            countdown = 0;
        }

        countdown--;
        if (countdown <= 0) {
            if (completedLevel == 1) {
                Greenfoot.setWorld(new LoadingWorld(new Level2World()));
            } else if (completedLevel == 2) {
                Greenfoot.setWorld(new LoadingWorld(new Level3World()));
            } else {
                // All levels complete — victory!
                Greenfoot.setWorld(new LoadingWorld(new GameOverWorld(ScoreManager.getScore(), true)));
            }
        }
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    private void drawCentred(String text, int size, Color colour, int y)
    {
        GreenfootImage img = new GreenfootImage(
            text, size, colour, new Color(0, 0, 0, 0));
        getBackground().drawImage(img, 400 - img.getWidth() / 2, y);
    }

    private static GreenfootImage buildBackground()
    {
        try {
        } catch (Exception ignored) {}

        GreenfootImage bg = new GreenfootImage(800, 600);
        bg.setColor(new Color(3, 2, 12));
        bg.fill();
        java.util.Random rng = new java.util.Random(42L);
        for (int i = 0; i < 130; i++) {
            int x = rng.nextInt(800);
            int y = rng.nextInt(600);
            int b = 100 + rng.nextInt(155);
            bg.setColor(new Color(b, b, b));
            bg.fillRect(x, y, 1, 1);
        }
        return bg;
    }
}
