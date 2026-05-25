import greenfoot.*;

/**
 * LifeIcon — a small ship silhouette shown in the top-right HUD corner.
 *
 * Three icons are added to the world when a level starts.
 * GameWorld.playerHit() removes one icon (rightmost first) every time the
 * player's ship is destroyed.
 */
public class LifeIcon extends Actor
{
    public LifeIcon()
    {
        GreenfootImage ship = new GreenfootImage("ship/player_ship_0.png");
        ship.scale(24, 14);
        setImage(ship);
    }

    // No act() — static HUD element.
}
