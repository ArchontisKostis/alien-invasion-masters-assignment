import greenfoot.*;

/**
 * GameOverWorld — the end screen shown after the player wins or loses.
 *
 * ── Win  (win = true)  ────────────────────────────────────────────────────────
 *   Heading:  "EARTH  DEFENDED!" in bright green
 *   Music:    none (transition cue is handled by GameWorld using the real files in sounds/)
 *   Background: bg_intro.png reused (dark space feel)
 *
 * ── Lose (win = false) ────────────────────────────────────────────────────────
 *   Heading:  "GAME  OVER" in bright red
 *   Music:    none (transition cue is handled by GameWorld using the real files in sounds/)
 *   Background: bg_gameover.png (dark red space) or procedural red starfield
 *
 * ── Controls ─────────────────────────────────────────────────────────────────
 *   R — reset score, return to IntroWorld, start a new game.
 *
 * Source: original code; follows game1_space_invaders_FINAL.md §6.
 */
public class GameOverWorld extends World
{
    // ── Constructor ───────────────────────────────────────────────────────────

    public GameOverWorld(int finalScore, boolean win)
    {
        super(800, 600, 1);

        setBackground(buildBackground(win));

        // Heading
        Color headingColour = win ? new Color(80, 255, 120) : new Color(255, 55, 55);
        drawCentred(win ? "EARTH  DEFENDED!" : "GAME  OVER", 40, headingColour, 185);

        // Score lines
        drawCentred("Final Score :   " + String.format("%05d", finalScore),
            22, Color.WHITE, 275);
        drawCentred("High  Score :   " + String.format("%05d", ScoreManager.getHighScore()),
            22, Color.WHITE, 313);

        // Restart prompt
        drawCentred("Press  R  to Restart", 20, new Color(200, 200, 200), 390);
    }

    // ── act ───────────────────────────────────────────────────────────────────

    @Override
    public void act()
    {
        if (Greenfoot.isKeyDown("r")) {
            ScoreManager.reset();
            Greenfoot.setWorld(new LoadingWorld(new IntroWorld()));
        }
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    /** Draw text horizontally centred at Y=y on the background. */
    private void drawCentred(String text, int size, Color colour, int y)
    {
        GreenfootImage img = new GreenfootImage(
            text, size, colour, new Color(0, 0, 0, 0));
        getBackground().drawImage(img, 400 - img.getWidth() / 2, y);
    }

    /** Build the background — PNG if present, procedural fallback otherwise. */
    private static GreenfootImage buildBackground(boolean win)
    {
        if (win) {
            try { return new GreenfootImage("bg_intro.png"); } catch (Exception ignored) {}
        } else {
            // Loss case: try bg_gameover.png from images/
            try { return new GreenfootImage("bg_gameover.png"); } catch (Exception ignored) {}
            try { return new GreenfootImage("raw/bg_gameover.png"); } catch (Exception ignored) {}
        }
        GreenfootImage bg = new GreenfootImage(800, 600);
        bg.setColor(win ? new Color(4, 14, 4) : new Color(18, 3, 3));
        bg.fill();

        java.util.Random rng = new java.util.Random(win ? 77L : 78L);
        for (int i = 0; i < 140; i++) {
            int x = rng.nextInt(800);
            int y = rng.nextInt(600);
            int b = 70 + rng.nextInt(100);
            Color star = win
                ? new Color(b / 2, b, b / 2)
                : new Color(b, b / 2, b / 2);
            bg.setColor(star);
            bg.fillRect(x, y, 1, 1);
        }
        return bg;
    }
}
