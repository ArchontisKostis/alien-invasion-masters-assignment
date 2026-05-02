import greenfoot.*;

/**
 * A twinkling star for the IntroWorld background.
 * 50 of these are placed randomly across the 800×600 screen.
 *
 * Each star has a random twinkle speed and phase so they never all blink together.
 * Size: randomly 1×1 or 2×2 px.
 * Alpha oscillates between 60 and 255 using a sine wave.
 *
 * Source: original code, pattern from course lecture examples.
 */
public class StarActor extends Actor
{
    private double phase;      // current position in twinkle cycle (0–2π)
    private double speed;      // how fast this star twinkles
    private int size;          // 1 or 2 px
    private int r, g, b;       // star colour components

    public StarActor()
    {
        this(1, 2);
    }

    public StarActor(int minSize, int maxSize)
    {
        phase = Math.random() * Math.PI * 2;          // random start phase
        speed = 0.03 + Math.random() * 0.06;          // random twinkle speed

        int min = Math.max(1, Math.min(minSize, maxSize));
        int max = Math.max(min, Math.max(minSize, maxSize));
        size = min + Greenfoot.getRandomNumber(max - min + 1);

        // Pale white / blue-white / yellow-white variety
        r = 200 + Greenfoot.getRandomNumber(56);
        g = 200 + Greenfoot.getRandomNumber(56);
        b = 200 + Greenfoot.getRandomNumber(56);

        updateImage(255);
    }

    public void act()
    {
        phase += speed;
        // Alpha oscillates between 60 and 255
        int alpha = 60 + (int)(195 * (0.5 + 0.5 * Math.sin(phase)));
        updateImage(alpha);
    }

    private void updateImage(int alpha)
    {
        GreenfootImage img = new GreenfootImage(size, size);
        img.setColor(new Color(r, g, b, alpha));
        img.fill();
        setImage(img);
    }
}
