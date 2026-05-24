import greenfoot.*;

/**
 * A reusable pulsing prompt Actor.
 *
 * Can be constructed from text or an existing GreenfootImage and configured
 * with pulse parameters, an activation key, and an activation listener.
 */
public class PulsePrompt extends Actor
{
    public interface OnActivate { void onActivate(); }

    private GreenfootImage originalImage;
    private int period = 90;
    private int minAlpha = 80;
    private int maxAlpha = 255;
    private int pulseTimer = 0;

    private String activationKey = null;
    private boolean prevKeyDown = false;
    private OnActivate listener = null;
    public PulsePrompt()
    {
        this("►  Press SPACE to Start  ◄", 25, new Color(255,255,100), 90, 80, 255);
    }

    public PulsePrompt(String text, int fontSize, Color color,
                       int period, int minAlpha, int maxAlpha)
    {
        this.period = period;
        this.minAlpha = minAlpha;
        this.maxAlpha = maxAlpha;
        originalImage = new GreenfootImage(text, fontSize, color, new Color(0,0,0,0));
        setImage(new GreenfootImage(originalImage));
    }

    public void setActivationKey(String key)
    {
        this.activationKey = key;
    }

    public void setOnActivate(OnActivate l)
    {
        this.listener = l;
    }

    public void act()
    {
        pulseTimer = (pulseTimer + 1) % period;
        double alphaD = minAlpha + (maxAlpha - minAlpha) * Math.abs(Math.sin(Math.PI * pulseTimer / (double)period));
        int alpha = Math.max(0, Math.min(255, (int)alphaD));
        updateImage(alpha);

        // Activation via mouse click
        if (Greenfoot.mouseClicked(this)) {
            triggerActivate();
            return;
        }

        // Activation via key (edge-detect so it fires once per press)
        if (activationKey != null) {
            boolean keyDown = Greenfoot.isKeyDown(activationKey);
            if (!prevKeyDown && keyDown) {
                triggerActivate();
            }
            prevKeyDown = keyDown;
        }
    }

    private void triggerActivate()
    {
        if (listener != null) {
            listener.onActivate();
        }
    }

    private void updateImage(int alpha)
    {
        GreenfootImage img = new GreenfootImage(originalImage);
        img.setTransparency(alpha);
        setImage(img);
    }
}
