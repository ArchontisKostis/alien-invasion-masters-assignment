import greenfoot.*;

/**
 * Level2World — Lunar Surface, the second gameplay level.
 *
 * ── What changes vs Level 1 ───────────────────────────────────────────────────
 *   Background : lunar surface (bg_level2.png + terrain_moon.png strip at Y=510)
 *   Alien skins: same aliens as Level 1
 *   AlienGrid  : tuned to be less punishing than before (slower pressure ramp)
 *   BunkerTile : 3 hit-points per tile to keep shields viable
 *   (MysteryShip removed)
 *
 * ── Implementation status ────────────────────────────────────────────────────
 *   Player mechanics: ✓ (inherited from GameWorld)
 *   Alien grid / MysteryShip / Bunkers: [Phase 6 — to be added]
 *
 * Source: original code; follows game1_space_invaders_FINAL.md §4 + §6.
 */
public class Level2World extends GameWorld
{
    private static final int TWINKLE_STAR_COUNT = 100;
    // MysteryShip removed: spawn timer and related logic deleted

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

        // 8. Register alien count for win condition
        setAlienCount(55);

        // 9. MysteryShip removed (no spawn)

        // 10. Background music (starts on Run via GameWorld.started())
        bgMusic = loadSound("music_level2.wav");

        // 11. UI overlay (pause menu / settings). MUST be last so it paints on top.
        buildUI();
    }

    // ── Mystery Ship spawn ────────────────────────────────────────────────────

    @Override
    public void act()
    {
        super.act();
        // No MysteryShip in Level 2; nothing extra to tick here
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
        try {
            return new GreenfootImage("bg_level2.png");
        } catch (Exception ignored) {}
        try {
            return new GreenfootImage("raw/bg_level2.png");
        } catch (Exception ignored) {}

        // Procedural lunar surface fallback
        GreenfootImage bg = new GreenfootImage(800, 600);

        // Space portion (upper 510 px)
        bg.setColor(new Color(3, 2, 12));
        bg.fill();

        java.util.Random rng = new java.util.Random(99L);

        // Stars
        for (int i = 0; i < 150; i++) {
            int x = rng.nextInt(800);
            int y = rng.nextInt(510);
            int b = 120 + rng.nextInt(136);
            bg.setColor(new Color(b, b, b));
            bg.fillRect(x, y, (rng.nextInt(5) == 0) ? 2 : 1, 1);
        }

        // Earth in top-right corner (placeholder blue circle)
        bg.setColor(new Color(30, 80, 180, 180));
        drawCircle(bg, 710, 60, 55);
        bg.setColor(new Color(200, 220, 255, 120));
        drawCircle(bg, 700, 50, 20);   // cloud highlight

        // Lunar ground strip (lower 90 px)
        bg.setColor(new Color(110, 110, 120));
        bg.fillRect(0, 510, 800, 90);

        // Terrain surface highlight
        bg.setColor(new Color(150, 150, 160));
        for (int x = 0; x < 800; x += 8) {
            int h = 3 + rng.nextInt(6);
            bg.fillRect(x, 510, 8, h);
        }

        // Craters
        bg.setColor(new Color(80, 80, 90));
        bg.fillOval(120, 520, 30, 12);
        bg.fillOval(380, 530, 22, 8);
        bg.fillOval(630, 518, 40, 14);

        return bg;
    }

    /** Draw a filled circle on img centred at (cx, cy) with radius r. */
    private static void drawCircle(GreenfootImage img, int cx, int cy, int r)
    {
        img.fillOval(cx - r, cy - r, r * 2, r * 2);
    }
}
