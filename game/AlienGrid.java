import greenfoot.*;
import java.util.List;

/**
 * AlienGrid — invisible manager actor that drives the alien formation.
 */
public class AlienGrid extends Actor
{
    // ── Formation movement ───────────────────────────────────────────────────

    /** Cumulative horizontal offset from the formation's starting position (px). */
    private double gridOffsetX = 0;

    /** Current march speed in px/act. Increases as aliens are killed. */
    private double speed;

    /** Direction: +1 = moving right, -1 = moving left. */
    private int direction = 1;

    /** Pixels dropped each time the formation bounces off a wall. */
    private final int dropAmount;

    // ── Bomb timer ────────────────────────────────────────────────────────────

    private int bombTimer   = 0;
    private int bombInterval;   // acts between shots; decreases with difficulty

    // ── Kill counter ─────────────────────────────────────────────────────────

    private int aliensRemaining = 55;

    // ── Level config ─────────────────────────────────────────────────────────

    private final double baseSpeed;
    private final double speedStep;
    private final int    baseInterval;
    private final int    minInterval;
    private final double aggressionMultiplier;

    // ── Constructor ───────────────────────────────────────────────────────────

    /**
     *   @param level  1 = Earth Orbit, 2 = Lunar Surface (slower start, still tougher overall).
     */
    public AlienGrid(int level)
    {
        baseSpeed    = (level == 1) ? 0.45 : 0.72;
        speedStep    = (level == 1) ? 0.12 : 0.05;
        baseInterval = (level == 1) ? 90   : 85;
        minInterval  = (level == 1) ? 35   : 48;
        aggressionMultiplier = (level == 1) ? 0.75 : 0.90;
        dropAmount   = (level == 1) ? 20   : 16;
        speed        = baseSpeed;
        bombInterval = adjustedBombInterval(baseInterval);

        // Invisible: give it a 1×1 transparent image
        setImage(new GreenfootImage(1, 1));
    }

    // ── act ───────────────────────────────────────────────────────────────────

    @Override
    public void act()
    {
        if (GameSettings.isPaused()) return;
        moveFormation();
        checkWallAndDrop();
        tickBombTimer();
        scaleSpeedAndBombs();
    }

    // ── Formation movement ────────────────────────────────────────────────────

    private void moveFormation()
    {
        gridOffsetX += speed * direction;

        List<Alien> aliens = getAliens();
        for (Alien a : aliens) {
            a.updateGridPosition(gridOffsetX);
        }
    }

    private void checkWallAndDrop()
    {
        List<Alien> aliens = getAliens();
        if (aliens.isEmpty()) return;

        for (Alien a : aliens) {
            if (direction == 1 && a.getX() >= 776) {
                direction = -1;
                dropAllAliens(aliens);
                return;
            }
            if (direction == -1 && a.getX() <= 24) {
                direction = 1;
                dropAllAliens(aliens);
                return;
            }
        }
    }

    private void dropAllAliens(List<Alien> aliens)
    {
        for (Alien a : aliens) {
            a.dropOneRow(dropAmount);
        }

        // march sound removed

        // Check invasion line — if any alien reaches Y=480, game over
        for (Alien a : aliens) {
            if (a.getY() >= 480) {
                ((GameWorld) getWorld()).triggerGameOver(false);
                return;
            }
        }
    }

    // ── Bomb timer ────────────────────────────────────────────────────────────

    private void tickBombTimer()
    {
        bombTimer++;
        if (bombTimer >= bombInterval) {
            bombTimer = 0;
            fireAlienBomb();
        }
    }

    private void fireAlienBomb()
    {
        List<Alien> aliens = getAliens();
        if (aliens.isEmpty()) return;

        Alien shooter = pickShooterWeighted(aliens);
        getWorld().addObject(new AlienBullet(), shooter.getX(), shooter.getY() + 16);
        GameWorld.playSound("alien_shoot.mp3");
    }

    private Alien pickShooterWeighted(List<Alien> aliens)
    {
        double totalWeight = 0.0;
        for (Alien a : aliens) {
            totalWeight += a.getAggressiveness(aggressionMultiplier);
        }

        if (totalWeight <= 0.0) {
            return aliens.get(Greenfoot.getRandomNumber(aliens.size()));
        }

        double r = Math.random() * totalWeight;
        double running = 0.0;
        for (Alien a : aliens) {
            running += a.getAggressiveness(aggressionMultiplier);
            if (r <= running) return a;
        }

        return aliens.get(aliens.size() - 1);
    }

    // ── Progressive difficulty ────────────────────────────────────────────────

    private void scaleSpeedAndBombs()
    {
        int killed = 55 - aliensRemaining;
        speed = baseSpeed + (killed / 10) * speedStep;
        int rawInterval = Math.max(minInterval, baseInterval - (killed / 5) * 5);
        bombInterval = adjustedBombInterval(rawInterval);
    }

    private int adjustedBombInterval(int interval)
    {
        return Math.max(minInterval, (int)Math.round(interval / Math.max(0.1, aggressionMultiplier)));
    }

    // ── Kill counter ─────────────────────────────────────────────────────────

    /**
     * Called by Alien.die() each time an alien is destroyed.
     * Triggers the level-clear transition when the last alien is killed.
     */
    public void alienKilled()
    {
        aliensRemaining--;
        if (aliensRemaining <= 0) {
            ((GameWorld) getWorld()).triggerLevelClear();
        }
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    @SuppressWarnings("unchecked")
    private List<Alien> getAliens()
    {
        return (List<Alien>) getWorld().getObjects(Alien.class);
    }
}
