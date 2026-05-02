import greenfoot.*;

/**
 * Level3World — final boss fight.
 *
 * Uses bg_intro.png as requested and spawns a single BossAlien target.
 * Defeat the boss to win the game.
 */
public class Level3World extends GameWorld
{
    private BossAlien boss;

    public Level3World()
    {
        super(800, 600, 1, 3);
        buildLevel();
    }

    @Override
    protected void buildLevel()
    {
        setBackground(buildBackground());

        // Atmosphere layer
        addTwinklingStars(80);

        // Boss
        boss = new BossAlien();
        addObject(boss, 400, 120);

        // Player + HUD
        spawnPlayer();
        buildLifeIcons();
        updateHUD();

        // Boss fight music.
        bgMusic = loadSound("music_level_boss.wav");

        // UI overlay (pause menu / settings). Keep this last so it paints on top.
        buildUI();
    }

    @Override
    protected void updateHUD()
    {
        super.updateHUD();

        if (boss == null || boss.getWorld() == null) return;

        int hp = boss.getHp();
        int max = Math.max(1, boss.getMaxHp());
        int barW = 220;
        int fillW = (int)Math.round((hp / (double)max) * barW);

        GreenfootImage bg = getBackground();

        bg.setColor(new Color(30, 30, 30));
        bg.fillRect(280, 10, barW + 2, 12);
        bg.setColor(new Color(240, 70, 70));
        bg.fillRect(281, 11, Math.max(0, fillW), 10);

        GreenfootImage label = new GreenfootImage(
            "BOSS: " + hp + " / " + max,
            16,
            Color.WHITE,
            new Color(0, 0, 0, 0));
        bg.drawImage(label, 286, 6);
    }

    public void onBossDefeated()
    {
        triggerGameOver(true);
    }

    private void addTwinklingStars(int count)
    {
        for (int i = 0; i < count; i++) {
            addObject(new StarActor(2, 4),
                Greenfoot.getRandomNumber(getWidth()),
                Greenfoot.getRandomNumber(getHeight()));
        }
    }

    private static GreenfootImage buildBackground()
    {
        return new GreenfootImage("bg_level3.png");
    }
}
