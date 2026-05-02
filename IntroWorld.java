import greenfoot.*;

/**
 * IntroWorld — the game's welcome / title screen.
 */
public class IntroWorld extends World
{
    private GreenfootSound bgMusic;
    private boolean musicLoopStarted = false;

    public IntroWorld()
    {
        super(800, 600, 1);
        setBackground("bg_intro.png");

        for (int i = 0; i < 50; i++) {
            addObject(new StarActor(),
                Greenfoot.getRandomNumber(800),
                Greenfoot.getRandomNumber(600));
        }

        drawTitle();
        drawInstructions();

        PulsePrompt prompt = new PulsePrompt(
            "►  Press SPACE to Start  ◄", 25,
            new Color(255, 255, 100),
            90, 80, 255);
        prompt.setActivationKey("space");
        prompt.setOnActivate(new PulsePrompt.OnActivate() {
            public void onActivate() {
                stopMusic();
                Greenfoot.setWorld(new LoadingWorld(new Level1World()));
            }
        });
        addObject(prompt, 400, 525);

        bgMusic = new GreenfootSound("music_intro.wav");
    }

    public void act()
    {
        startMusicLoopIfNeeded();
    }

    public void started()
    {
        startMusicLoopIfNeeded();
    }

    public void stopped()
    {
        stopMusic();
    }

    private void drawTitle()
    {
        GreenfootImage title = new GreenfootImage(
            "SPACE  INVADERS", 40,
            Color.WHITE,
            new Color(0, 0, 0, 0));
        getBackground().drawImage(title, 400 - title.getWidth() / 2, 148);
    }

    private void drawInstructions()
    {
        String[] lines = {
            "Defend Earth from the alien fleet!",
            "",
            "Move:  LEFT / RIGHT arrow keys",
            "Fire:  SPACE bar",
            "",
            "You have 3 lives.  Lose them all and it's Game Over.",
            "Destroy all aliens to advance to Level 2.",
            "",
            "Score:   Easy Alien 10 pts  |  Mid Alien 20 pts  |  Hard Alien 30 pts",
            "Bonus:   Mystery UFO in Level 2 — worth 50, 100 or 150 pts!"
        };

        int y = 228;
        for (String line : lines) {
            if (line.isEmpty()) {
                y += 14;
                continue;
            }

            GreenfootImage txt = new GreenfootImage(
                line, 17,
                new Color(210, 215, 230),
                new Color(0, 0, 0, 0));
            getBackground().drawImage(txt, 400 - txt.getWidth() / 2, y);
            y += 28;
        }
    }

    private void startMusicLoopIfNeeded()
    {
        if (musicLoopStarted || bgMusic == null) return;

        try {
            BackgroundMusic.playLoop(bgMusic, GameSettings.getMusicVolume());
            musicLoopStarted = true;
        } catch (Exception ignored) {}
    }

    private void stopMusic()
    {
        if (bgMusic != null) {
            BackgroundMusic.stop(bgMusic);
        }
        musicLoopStarted = false;
    }
}
