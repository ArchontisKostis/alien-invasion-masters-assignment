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

        bgMusic = new GreenfootSound("music_intro.mp3");
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
            "ALIEN INVASION", 40,
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
            "Score:   Easy Alien 10 pts  |  Mid Alien 20 pts  |  Hard Alien 30 pts"
        };

        final int top = 218;

        // Measure the block so the backing panel hugs the text. Without a panel
        // the light text washes out against the bright nebula in bg_intro.png.
        int blockHeight = 0;
        for (String line : lines) {
            blockHeight += line.isEmpty() ? 14 : 28;
        }

        // Semi-transparent dark panel for guaranteed contrast.
        int panelX = 90, panelW = 620;
        int panelY = top - 16, panelH = blockHeight + 28;
        GreenfootImage panel = new GreenfootImage(panelW, panelH);
        panel.setColor(new Color(10, 12, 28, 165));
        panel.fillRect(0, 0, panelW, panelH);
        panel.setColor(new Color(120, 130, 200, 110));
        panel.drawRect(0, 0, panelW - 1, panelH - 1);
        getBackground().drawImage(panel, panelX, panelY);

        int y = top;
        for (String line : lines) {
            if (line.isEmpty()) {
                y += 14;
                continue;
            }

            GreenfootImage txt = new GreenfootImage(
                line, 17,
                new Color(235, 240, 250),
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
