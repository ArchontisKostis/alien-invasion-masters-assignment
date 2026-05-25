import greenfoot.*;

/**
 * GameOverWorld — the end screen shown after the player wins or loses.
 *
 * ── Win State (win = true)  ────────────────────────────────────────────────────────
 *   Heading:  "EARTH  DEFENDED!" in bright green
 *   Background: bg_intro.png reused (dark space feel)
 *
 * ── Lose State (win = false) ────────────────────────────────────────────────────────
 *   Heading:  "GAME  OVER" in bright red
 *   Background: bg_gameover.png
 *
 * ── Controls ─────────────────────────────────────────────────────────────────
 *   R — reset score, return to IntroWorld, start a new game.
 *
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

    /** Build the background */
    private static GreenfootImage buildBackground(boolean win)
    {
        if (win) {
            return new GreenfootImage("bg_intro.png");
        } 
        
        // Loss case: try bg_gameover.png from images/
        return new GreenfootImage("bg_gameover.png");
    }
}
