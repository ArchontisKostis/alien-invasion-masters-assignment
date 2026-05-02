import greenfoot.*;

/**
 * BunkerTile — a single 8×8 pixel tile in a bunker (shield).
 *
 * ── Bunker layout (from Level1World.buildBunker) ─────────────────────────────
 *   8 columns × 3 rows of BunkerTile actors form a classic Space Invaders shield.
 *   Shape mask:
 *     { 0,1,1,1,1,1,1,0 }
 *     { 1,1,1,1,1,1,1,1 }
 *     { 1,1,0,0,0,0,1,1 }  ← bottom notch for player cannon
 *
 * ── Damage states ────────────────────────────────────────────────────────────
 *   Each tile starts at `maxHp` hit points (3 for Level 1, 2 for Level 2).
 *   The colour darkens with each hit; at 0 hp the tile removes itself.
 *
 *   Level 1 (maxHp=3):   green → yellow-green → orange → gone
 *   Level 2 (maxHp=2):   green → orange → gone           (more fragile)
 *
 * ── Sounds ───────────────────────────────────────────────────────────────────
 *   bunker_hit.wav plays when any bullet hits a tile (via damage()).
 *
 * Source: original code; follows game1_space_invaders_FINAL.md §7.16.
 */
public class BunkerTile extends Actor
{
    // ── Constants ─────────────────────────────────────────────────────────────

    private static final int TILE_SIZE = 8;

    // ── State ─────────────────────────────────────────────────────────────────

    private int hp;
    private final int maxHp;

    // ── Constructor ───────────────────────────────────────────────────────────

    /**
     * @param maxHp  Max hit points — 3 for Level 1, 2 for Level 2.
     */
    public BunkerTile(int maxHp)
    {
        this.maxHp = maxHp;
        this.hp    = maxHp;
        updateImage();
    }

    // ── Public API ────────────────────────────────────────────────────────────

    /**
     * Reduce hp by 1, update the tile's colour, play bunker_hit.wav,
     * and remove the tile if hp reaches 0.
     *
     * Called by AlienBullet and PlayerBullet on intersection.
     */
    public void damage()
    {
        hp--;
        GameWorld.playSound("bunker_hit.wav");

        if (hp <= 0) {
            getWorld().removeObject(this);
        } else {
            updateImage();
        }
    }

    // ── Image ─────────────────────────────────────────────────────────────────

    /**
     * Redraw the 8×8 tile in a colour that represents remaining durability.
     *
     * Full health:   bright green  #40FF60
     * 2/3 health:    yellow-green  #A0E020
     * 1/3 health:    orange        #FF8020
     * (0 hp: tile is removed, never drawn)
     */
    private void updateImage()
    {
        GreenfootImage img = new GreenfootImage(TILE_SIZE, TILE_SIZE);

        Color fill, edge;

        if (maxHp == 2) {
            // Level 2 — only two states
            if (hp == 2) {
                fill = new Color( 64, 255,  96);
                edge = new Color( 20, 180,  50);
            } else {
                fill = new Color(255, 128,  32);
                edge = new Color(200,  80,  10);
            }
        } else {
            // Level 1 — three states
            if (hp >= 3) {
                fill = new Color( 64, 255,  96);
                edge = new Color( 20, 180,  50);
            } else if (hp == 2) {
                fill = new Color(160, 224,  32);
                edge = new Color(100, 160,  10);
            } else {
                fill = new Color(255, 128,  32);
                edge = new Color(200,  80,  10);
            }
        }

        img.setColor(fill);
        img.fillRect(0, 0, TILE_SIZE, TILE_SIZE);

        // Slightly darker border for definition
        img.setColor(edge);
        img.drawRect(0, 0, TILE_SIZE - 1, TILE_SIZE - 1);

        setImage(img);
    }
}
