import greenfoot.*;

/**
 * BackgroundMusic — global guard to ensure only one BGM loop plays at a time.
 */
public final class BackgroundMusic
{
    private static GreenfootSound currentLoop;

    private BackgroundMusic() {}

    /**
     * Start looping the provided track, stopping any previous looping BGM first.
     */
    public static synchronized void playLoop(GreenfootSound sound, int volume)
    {
        if (sound == null) return;

        if (currentLoop != null && currentLoop != sound) {
            try { currentLoop.stop(); } catch (Exception ignored) {}
        }

        try { sound.stop(); } catch (Exception ignored) {}
        try { sound.setVolume(Math.max(0, Math.min(100, volume))); } catch (Exception ignored) {}
        try { sound.playLoop(); } catch (Exception ignored) {}

        currentLoop = sound;
    }

    /**
     * Stop this specific track. Clears global pointer if it was the active loop.
     */
    public static synchronized void stop(GreenfootSound sound)
    {
        if (sound == null) return;

        try { sound.stop(); } catch (Exception ignored) {}

        if (currentLoop == sound) {
            currentLoop = null;
        }
    }

    /**
     * Stop whichever track is currently registered as active BGM.
     */
    public static synchronized void stopCurrent()
    {
        if (currentLoop != null) {
            try { currentLoop.stop(); } catch (Exception ignored) {}
            currentLoop = null;
        }
    }
}
