import greenfoot.*;

/**
 * Level1World, the first gameplay level.
 */
public class Level1World extends GameWorld
{
    private static final int TWINKLE_STAR_COUNT = 100;

    // ── Constructor ───────────────────────────────────────────────────────────

    public Level1World()
    {
        super(800, 600, 1, 1);    // width, height, cellSize, level = 1
        buildLevel();             // called HERE, not from GameWorld constructor
    }

    // ── GameWorld contract ────────────────────────────────────────────────────

    @Override
    protected void buildLevel()
    {
        // 1. Background
        setBackground(new GreenfootImage("bg_intro.png"));

        // 1b. Intro-style twinkling stars layered over the background
        addTwinklingStars(TWINKLE_STAR_COUNT);

        // 2. AlienGrid manager (invisible, positioned off-screen)
        addObject(new AlienGrid(1), -1, -1);

        // 3. Spawn 55 aliens — 5 rows × 11 columns
        spawnAlienGrid();

        // 4. No bunkers in Level 1 (difficulty tuning)

        // 5. Player ship at default spawn (400, 540)
        spawnPlayer();

        // 6. HUD life icons (top-right)
        buildLifeIcons();

        // 7. Initial HUD draw
        updateHUD();

        // 8. Background music (starts on Run via GameWorld.started())
        bgMusic = loadSound("music_level1.wav");

        // 9. UI overlay (pause menu / settings). MUST be last so it paints on top.
        buildUI();
    }

    private void addTwinklingStars(int count)
    {
        for (int i = 0; i < count; i++) {
            addObject(new StarActor(2, 4),
                Greenfoot.getRandomNumber(getWidth()),
                Greenfoot.getRandomNumber(getHeight()));
        }
    }

    // ── act ───────────────────────────────────────────────────────────────────

    @Override
    public void act()
    {
        super.act();   // HUD refresh + respawn timer
    }

    // ── Alien grid construction ────────────────────────────────────────────────

    /**
     * Spawn 55 aliens in a 5-row × 11-column formation.
     *
     * Row allocation:
     *   Rows 0-1  → HardAlien  (30 pts)  gold
     *   Rows 2-3  → MidAlien   (20 pts)  cyan
     *   Row  4    → EasyAlien  (10 pts)  green
     *
     * NOTE: mid-alien asset files are named "med_alien_*" (not "mid_alien_*").
     */
    private void spawnAlienGrid()
    {
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 11; col++) {
                Alien a = createAlien(row);
                int startX = 55 + col * 60;
                int startY = 80 + row * 36;
                a.setBasePosition(startX, startY);
                addObject(a, startX, startY);
            }
        }
    }

    private Alien createAlien(int row)
    {
        if (row <= 1) return new HardAlien();
        if (row <= 3) return new MidAlien();
        return new EasyAlien();
    }
}
