import greenfoot.*;

/**
 * LoadingWorld — a simple loading screen displayed between levels or before the game starts.
 * 
 * Shows a loading animation for a brief moment before transitioning to the next world.
 * Displays a "Loading..." message with animated dots.
 * 
 * Usage:
 *   Greenfoot.setWorld(new LoadingWorld(new Level1World()));
 *   Greenfoot.setWorld(new LoadingWorld(new Level2World()));
 */
public class LoadingWorld extends World
{
    private static final int DISPLAY_TIME = 180;  // acts (~3 seconds at 60 fps)
    private static final int BAR_HEIGHT = 12;
    private static final String[] LOADING_BACKGROUNDS = {
        "loading/loading_banner_1.png",
        "loading/loading_banner_2.png",
        "loading/loading_banner_3.png",
        "loading/loading_banner_4.png",
        "loading/loading_banner_5.png",
        "loading/loading_banner_6.png"
    };
    
    private int countdown;
    private final World nextWorld;
    private final GreenfootImage baseBackground;
    
    /**
     * Creates a loading screen that will transition to the given world.
     * 
     * @param nextWorld The world to display after the loading screen.
     */
    public LoadingWorld(World nextWorld)
    {
        super(800, 600, 1);
        this.nextWorld = nextWorld;
        this.countdown = DISPLAY_TIME;
        this.baseBackground = buildBackground();

        // Ensure no world BGM keeps running during transition.
        BackgroundMusic.stopCurrent();
        
        setBackground(baseBackground);
        drawLoadingScreen();
    }
    
    @Override
    public void act()
    {
        drawLoadingScreen();
        
        countdown--;
        if (countdown <= 0) {
            Greenfoot.setWorld(nextWorld);
        }
    }
    
    private void drawLoadingScreen()
    {
        GreenfootImage frame = new GreenfootImage(baseBackground);
        
        // Draw a full-width loading bar at the bottom.
        int barY = getHeight() - BAR_HEIGHT;
        int fillWidth = (int)(getWidth() * (1.0 - (countdown / (double)DISPLAY_TIME)));
        
        // Track
        frame.setColor(new Color(8, 12, 30, 210));
        frame.fillRect(0, barY, getWidth(), BAR_HEIGHT);
        
        // Fill
        frame.setColor(new Color(110, 200, 255));
        frame.fillRect(0, barY, Math.max(0, fillWidth), BAR_HEIGHT);

        setBackground(frame);
    }
    
    private static GreenfootImage buildBackground()
    {
        String chosen = LOADING_BACKGROUNDS[Greenfoot.getRandomNumber(LOADING_BACKGROUNDS.length)];
        GreenfootImage bg = new GreenfootImage(chosen);

        if (bg.getWidth() != 800 || bg.getHeight() != 600) {
            bg.scale(800, 600);
        }

        return bg;
    }
}
