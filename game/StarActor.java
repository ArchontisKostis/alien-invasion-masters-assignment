import greenfoot.*;

/**
 * A twinkling star for the IntroWorld background.
 * 50 of these are placed randomly across the 800×600 screen.
 *
 * Each star has a random twinkle speed and phase so they never all blink together.
 * Size: randomly 1×1 or 2×2 px.
 * Alpha oscillates between 60 and 255 using a sine wave.
 *
 * Performance note: instead of building a new GreenfootImage every act() (which
 * generated ~50 allocations per frame and stuttered the browser/Gallery runtime
 * via garbage collection), each star pre-builds a small set of alpha-stepped
 * images ONCE in the constructor. act() then just swaps to the nearest
 * pre-built frame — zero per-frame allocation. The twinkle is quantised into
 * {@link #ALPHA_STEPS} brightness levels rather than perfectly smooth, which is
 * imperceptible at this size.
 */
public class StarActor extends Actor
{
    /** Number of pre-built brightness levels per star (alpha 60 → 255). */
    private static final int ALPHA_STEPS = 16;

    private double phase;      // current position in twinkle cycle (0–2π)
    private double speed;      // how fast this star twinkles

    /** Pre-built images, one per brightness level (index 0 = dimmest). */
    private GreenfootImage[] frames;

    /** Index of the frame currently shown, so we only setImage() on change. */
    private int currentFrame = -1;

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
        int size = min + Greenfoot.getRandomNumber(max - min + 1);

        // Pale white / blue-white / yellow-white variety
        int r = 200 + Greenfoot.getRandomNumber(56);
        int g = 200 + Greenfoot.getRandomNumber(56);
        int b = 200 + Greenfoot.getRandomNumber(56);

        buildFrames(size, r, g, b);
        setImage(frames[ALPHA_STEPS - 1]);            // start at full brightness
        currentFrame = ALPHA_STEPS - 1;
    }

    public void act()
    {
        phase += speed;
        // Sine maps to 0..1, then to a frame index. Alpha oscillates 60..255
        // across the pre-built frames.
        double brightness = 0.5 + 0.5 * Math.sin(phase);
        int frame = (int)Math.round(brightness * (ALPHA_STEPS - 1));

        if (frame != currentFrame) {
            setImage(frames[frame]);
            currentFrame = frame;
        }
    }

    /**
     * Build the ALPHA_STEPS images once. Frame i uses an alpha linearly
     * interpolated between 60 (dimmest) and 255 (brightest).
     */
    private void buildFrames(int size, int r, int g, int b)
    {
        frames = new GreenfootImage[ALPHA_STEPS];
        for (int i = 0; i < ALPHA_STEPS; i++) {
            int alpha = 60 + (int)((195.0 * i) / (ALPHA_STEPS - 1));
            GreenfootImage img = new GreenfootImage(size, size);
            img.setColor(new Color(r, g, b, alpha));
            img.fill();
            frames[i] = img;
        }
    }
}
