import greenfoot.*;

/**
 * Level2World — Lunar Surface, the second gameplay level.
 *
 * ── What changes vs Level 1 ───────────────────────────────────────────────────
 *   Background : lunar surface (bg_level2.png + terrain_moon.png strip at Y=510)
 *   Alien skins: same aliens as Level 1
 *   AlienGrid  : tuned to be less punishing than before (slower pressure ramp)
 *   BunkerTile : 3 hit-points per tile to keep shields viable
 *
 * Source: original code; follows game1_space_invaders_FINAL.md §4 + §6.
 */
public class Level2World extends GameWorld
{
    private static final int TWINKLE_STAR_COUNT = 100;

    // ── Constructor ───────────────────────────────────────────────────────────

    public Level2World()
    {
        super(800, 600, 1, 2);   // level = 2
        buildLevel();
    }

    // ── GameWorld contract ────────────────────────────────────────────────────

    @Override
    protected void buildLevel()
    {
        setBackground(buildBackground());

        // 1. Twinkling stars layered over background (same as Level 1)
        addTwinklingStars(TWINKLE_STAR_COUNT);

        // 2. AlienGrid manager (invisible, positioned off-screen)
        addObject(new AlienGrid(2), -1, -1);

        // 3. Spawn 55 aliens — same aliens as Level 1
        spawnAlienGrid();

        // 4. Bunkers — 4 shield clusters (3 HP per tile for Level 2)
        int[] bunkerX = { 130, 280, 430, 580 };
        for (int bx : bunkerX) {
            buildBunker(bx, 460, 3);   // raised to 3 hp so shields survive longer under Level 2 fire
        }

        // 5. Player ship at default spawn (400, 540)
        spawnPlayer();

        // 6. HUD life icons (top-right)
        buildLifeIcons();

        // 7. Initial HUD draw
        updateHUD();

        // 8. Background music (starts on Run via GameWorld.started())
        bgMusic = loadSound("music_level2.wav");

        // 9. UI overlay (pause menu / settings). MUST be last so it paints on top.
        buildUI();
    }

    @Override
    public void act()
    {
        super.act();
    }

    // ── Alien grid construction ────────────────────────────────────────────────

    /**
     * Spawn 55 aliens in a 5-row × 11-column formation.
     * Row allocation:
     *   Rows 0-1  → HardAlien  (30 pts)
     *   Rows 2-3  → MidAlien   (20 pts)
     *   Row  4    → EasyAlien  (10 pts)
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

    // ── Bunker construction ───────────────────────────────────────────────────

    /**
     * Build a classic Space Invaders bunker from BunkerTile actors.
     *
     * Shape (0=gap, 1=tile):
     *   { 0,1,1,1,1,1,1,0 }
     *   { 1,1,1,1,1,1,1,1 }
     *   { 1,1,0,0,0,0,1,1 }  ← notch for the player cannon
     *
     * Each tile is 8×8 px.
     */
    private void buildBunker(int originX, int originY, int hp)
    {
        int[][] shape = {
            { 0,1,1,1,1,1,1,0 },
            { 1,1,1,1,1,1,1,1 },
            { 1,1,0,0,0,0,1,1 }
        };

        for (int r = 0; r < shape.length; r++) {
            for (int c = 0; c < shape[r].length; c++) {
                if (shape[r][c] == 1) {
                    addObject(new BunkerTile(hp),
                              originX + c * 8,
                              originY + r * 8);
                }
            }
        }
    }

    // ── Twinkling stars ───────────────────────────────────────────────────────

    private void addTwinklingStars(int count)
    {
        for (int i = 0; i < count; i++) {
            addObject(new StarActor(2, 4),
                Greenfoot.getRandomNumber(getWidth()),
                Greenfoot.getRandomNumber(getHeight()));
        }
    }

    // ── Background ────────────────────────────────────────────────────────────

    private static GreenfootImage buildBackground()
    {
        return new GreenfootImage("bg_level2.png");
    }
}
