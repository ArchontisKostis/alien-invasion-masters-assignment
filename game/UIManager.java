import greenfoot.*;
import java.util.ArrayList;
import java.util.List;

/**
 * UIManager — full-screen overlay UI for pause, settings, cheats, and help.
 */
public class UIManager extends Actor
{
    // ── State enum ────────────────────────────────────────────────────────────

    public enum State { HIDDEN, PAUSE, SETTINGS, HOW_TO_PLAY, CHEATS }

    // ── Panel geometry ────────────────────────────────────────────────────────

    private static final int W         = 800;
    private static final int H         = 600;
    private static final int PNL_W     = 300;
    private static final int PNL_H     = 385;
    private static final int PNL_X     = (W - PNL_W) / 2;        // 250
    private static final int PNL_Y     = (H - PNL_H) / 2;        // 107
    private static final int PNL_CX    = W / 2;                   // 400
    private static final int PNL_BOT   = PNL_Y + PNL_H;          // 492

    private static final int BTN_X     = PNL_CX;
    private static final int BTN_W     = 240;
    private static final int BTN_H     = 38;

    // ── Colours ───────────────────────────────────────────────────────────────

    private static final Color OVERLAY_BG   = new Color(5,  8,  25, 195);
    private static final Color PANEL_BG     = new Color(4, 10,  28, 235);
    private static final Color PANEL_BORDER = new Color(0, 230, 255, 180);
    private static final Color TITLE_COL    = new Color(0, 230, 255);
    private static final Color BODY_COL     = new Color(200, 220, 240);
    private static final Color DIM_COL      = new Color(100, 130, 160);
    private static final Color TRANSPARENT  = new Color(0, 0, 0, 0);

    // ── Runtime state ─────────────────────────────────────────────────────────

    private State          state       = State.HIDDEN;
    private boolean        escWasDown  = false;
    private List<UIButton> activeButtons = new ArrayList<>();

    // ── Constructor ───────────────────────────────────────────────────────────

    public UIManager()
    {
        GreenfootImage blank = new GreenfootImage(1, 1);
        blank.clear();
        setImage(blank);
    }

    // ── Greenfoot lifecycle ───────────────────────────────────────────────────

    @Override
    public void addedToWorld(World world)
    {
        // UIButton actors paint on top of the UIManager overlay.
        // All other classes (gameplay actors) paint below the UIManager overlay.
        world.setPaintOrder(UIButton.class, UIManager.class);
    }

    // ── act ───────────────────────────────────────────────────────────────────

    @Override
    public void act()
    {
        checkEscToggle();
    }

    // ── Public API ────────────────────────────────────────────────────────────

    /** Open the pause menu. Also sets GameSettings.paused = true. */
    public void openPause()
    {
        GameSettings.setPaused(true);
        switchTo(State.PAUSE);
    }

    /** Close the overlay and resume the game. */
    public void close()
    {
        GameSettings.setPaused(false);
        removeAllButtons();
        state = State.HIDDEN;
        GreenfootImage blank = new GreenfootImage(1, 1);
        blank.clear();
        setImage(blank);
    }

    // ── ESC key toggle ────────────────────────────────────────────────────────

    private void checkEscToggle()
    {
        boolean escDown = Greenfoot.isKeyDown("escape");
        if (escDown && !escWasDown) {
            if (state == State.HIDDEN) {
                openPause();
            } else {
                close();
            }
        }
        escWasDown = escDown;
    }

    // ── State switching ───────────────────────────────────────────────────────

    private void switchTo(State s)
    {
        removeAllButtons();
        state = s;
        drawOverlay();
        buildButtons();
    }

    // ── Overlay drawing ───────────────────────────────────────────────────────

    private void drawOverlay()
    {
        GreenfootImage img = new GreenfootImage(W, H);

        // Dark background tint
        img.setColor(OVERLAY_BG);
        img.fill();

        // Frosted-glass panel
        drawPanel(img);

        // Panel content (text drawn for non-button states)
        drawPanelContent(img);

        setImage(img);
    }

    // ── Glass panel ───────────────────────────────────────────────────────────

    private void drawPanel(GreenfootImage img)
    {
        // Panel body
        img.setColor(PANEL_BG);
        img.fillRect(PNL_X, PNL_Y, PNL_W, PNL_H);

        // Cyan border (double line for glow effect)
        img.setColor(PANEL_BORDER);
        img.drawRect(PNL_X,     PNL_Y,     PNL_W,     PNL_H);
        img.setColor(new Color(0, 200, 240, 90));
        img.drawRect(PNL_X + 2, PNL_Y + 2, PNL_W - 4, PNL_H - 4);

        // Corner gems
        drawGem(img, PNL_X + 8,           PNL_Y + 8);
        drawGem(img, PNL_X + PNL_W - 8,   PNL_Y + 8);
        drawGem(img, PNL_X + 8,           PNL_Y + PNL_H - 8);
        drawGem(img, PNL_X + PNL_W - 8,   PNL_Y + PNL_H - 8);

        // Title text
        String title = titleFor(state);
        if (!title.isEmpty()) {
            GreenfootImage titleImg = new GreenfootImage(title, 22, TITLE_COL, TRANSPARENT);
            img.drawImage(titleImg, PNL_CX - titleImg.getWidth() / 2, PNL_Y + 16);
        }

        // Separator line
        img.setColor(new Color(0, 180, 220, 130));
        img.fillRect(PNL_X + 20, PNL_Y + 50, PNL_W - 40, 1);
    }

    private void drawGem(GreenfootImage img, int cx, int cy)
    {
        img.setColor(PANEL_BORDER);
        img.fillOval(cx - 3, cy - 3, 7, 7);
        img.setColor(new Color(180, 240, 255));
        img.fillOval(cx - 1, cy - 1, 3, 3);
    }

    // ── Panel content (text for text-heavy panels) ────────────────────────────

    private void drawPanelContent(GreenfootImage img)
    {
        switch (state) {
            case SETTINGS:    drawSettingsContent(img);  break;
            case HOW_TO_PLAY: drawHowToPlayContent(img); break;
            case CHEATS:      drawCheatsContent(img);    break;
            default: break;
        }
    }

    // ── Settings panel content ────────────────────────────────────────────────

    private void drawSettingsContent(GreenfootImage img)
    {
        // Music Volume row
        drawLabel(img, "MUSIC VOLUME", PNL_CX, PNL_Y + 80);
        drawVolumeBar(img, GameSettings.getMusicVolume(), PNL_Y + 105);

        // SFX Volume row
        drawLabel(img, "SFX VOLUME", PNL_CX, PNL_Y + 160);
        drawVolumeBar(img, GameSettings.getSfxVolume(), PNL_Y + 185);
    }

    /** Draw a 10-segment volume bar centred at (PNL_CX, y). */
    private void drawVolumeBar(GreenfootImage img, int volume, int y)
    {
        int filled   = volume / 10;           // 0-10
        int segW     = 10;
        int segH     = 12;
        int gap      = 3;
        int totalW   = 10 * segW + 9 * gap;
        int startX   = PNL_CX - totalW / 2;

        for (int i = 0; i < 10; i++) {
            int sx = startX + i * (segW + gap);
            if (i < filled) {
                img.setColor(new Color(0, 220, 255));   // lit segment
            } else {
                img.setColor(new Color(20, 40, 80));    // dim segment
            }
            img.fillRect(sx, y, segW, segH);
        }

        // Percentage label to the right
        String pct = volume + "%";
        GreenfootImage pctImg = new GreenfootImage(pct, 12, DIM_COL, TRANSPARENT);
        img.drawImage(pctImg, PNL_CX + totalW / 2 + 6, y);
    }

    // ── How To Play content ───────────────────────────────────────────────────

    private void drawHowToPlayContent(GreenfootImage img)
    {
        int x  = PNL_X + 20;
        int y  = PNL_Y + 68;
        int dy = 28;

        drawSmallLabel(img, "CONTROLS", PNL_CX, y); y += 24;
        drawBody(img, "← / →  Move left / right",   x, y); y += dy;
        drawBody(img, "SPACE      Fire laser",                 x, y); y += dy;
        drawBody(img, "ESC        Pause / Menu",               x, y); y += dy + 10;

        drawSmallLabel(img, "SCORING", PNL_CX, y); y += 24;
        drawBody(img, "Easy Alien   10 pts",    x, y); y += dy;
        drawBody(img, "Mid Alien    20 pts",     x, y); y += dy;
        drawBody(img, "Hard Alien   30 pts",     x, y); y += dy;
    }

    // ── Cheats content ────────────────────────────────────────────────────────

    private void drawCheatsContent(GreenfootImage img)
    {
        drawLabel(img, "⚠ CHEATS ENABLED ⚠", PNL_CX, PNL_Y + 68);
    }

    // ── Text helpers ──────────────────────────────────────────────────────────

    private void drawLabel(GreenfootImage img, String text, int cx, int y)
    {
        GreenfootImage t = new GreenfootImage(text, 14, DIM_COL, TRANSPARENT);
        img.drawImage(t, cx - t.getWidth() / 2, y);
    }

    private void drawSmallLabel(GreenfootImage img, String text, int cx, int y)
    {
        GreenfootImage t = new GreenfootImage(text, 13, TITLE_COL, TRANSPARENT);
        img.drawImage(t, cx - t.getWidth() / 2, y);
    }

    private void drawBody(GreenfootImage img, String text, int x, int y)
    {
        GreenfootImage t = new GreenfootImage(text, 13, BODY_COL, TRANSPARENT);
        img.drawImage(t, x, y);
    }

    // ── Button builders ───────────────────────────────────────────────────────

    private void buildButtons()
    {
        switch (state) {
            case PAUSE:       buildPauseButtons();    break;
            case SETTINGS:    buildSettingsButtons(); break;
            case HOW_TO_PLAY: buildBackButton(PNL_BOT - 48); break;
            case CHEATS:      buildCheatsButtons();   break;
            default: break;
        }
    }

    // ── PAUSE buttons ─────────────────────────────────────────────────────────

    private void buildPauseButtons()
    {
        int y = PNL_Y + 72;
        int dy = 50;
        addBtn("RESUME",      BTN_X, y,       BTN_W, BTN_H, () -> close());           y += dy;
        addBtn("SETTINGS",    BTN_X, y,       BTN_W, BTN_H, () -> switchTo(State.SETTINGS));   y += dy;
        addBtn("HOW TO PLAY", BTN_X, y,       BTN_W, BTN_H, () -> switchTo(State.HOW_TO_PLAY));y += dy;
        addBtn("CHEATS",      BTN_X, y,       BTN_W, BTN_H, () -> switchTo(State.CHEATS));     y += dy;
        addBtn("QUIT",        BTN_X, y,       BTN_W, BTN_H, () -> quitToIntro());
    }

    // ── SETTINGS buttons ──────────────────────────────────────────────────────

    private void buildSettingsButtons()
    {
        int smallW = 34;
        int smallH = 26;
        int barTotalW = 10 * 10 + 9 * 3;   // matches drawVolumeBar totalW = 157

        // Music volume: [-] centred left, [+] centred right of bar
        int musicY   = PNL_Y + 105 - smallH / 2 + 6;
        int barLeft  = PNL_CX - barTotalW / 2;
        int barRight = PNL_CX + barTotalW / 2;
        addBtn("-", barLeft  - smallW / 2 - 6, musicY, smallW, smallH, 14,
               () -> { GameSettings.setMusicVolume(GameSettings.getMusicVolume() - 10);
                        applyMusicVolume(); switchTo(State.SETTINGS); });
        addBtn("+", barRight + smallW / 2 + 6, musicY, smallW, smallH, 14,
               () -> { GameSettings.setMusicVolume(GameSettings.getMusicVolume() + 10);
                        applyMusicVolume(); switchTo(State.SETTINGS); });

        // SFX volume
        int sfxY = PNL_Y + 185 - smallH / 2 + 6;
        addBtn("-", barLeft  - smallW / 2 - 6, sfxY, smallW, smallH, 14,
               () -> { GameSettings.setSfxVolume(GameSettings.getSfxVolume() - 10);
                        switchTo(State.SETTINGS); });
        addBtn("+", barRight + smallW / 2 + 6, sfxY, smallW, smallH, 14,
               () -> { GameSettings.setSfxVolume(GameSettings.getSfxVolume() + 10);
                        switchTo(State.SETTINGS); });

        // Back
        buildBackButton(PNL_BOT - 48);
    }

    // ── CHEATS buttons ────────────────────────────────────────────────────────

    private void buildCheatsButtons()
    {
        int y = PNL_Y + 102;
        int dy = 54;

        String invLabel = "INVINCIBLE: " + (GameSettings.isCheatInvincible() ? "ON " : "OFF");
        addBtn(invLabel, BTN_X, y, BTN_W, BTN_H,
               () -> { GameSettings.toggleCheatInvincible(); switchTo(State.CHEATS); });
        y += dy;

        addBtn("KILL ALL ALIENS", BTN_X, y, BTN_W, BTN_H, () -> {
            killAllAliens();
            switchTo(State.CHEATS);
        });
        y += dy;

        addBtn("SKIP LEVEL", BTN_X, y, BTN_W, BTN_H, () -> {
            close();
            ((GameWorld) getWorld()).triggerLevelClear();
        });
        y += dy;

        buildBackButton(PNL_BOT - 38);
    }

    // ── Back button ───────────────────────────────────────────────────────────

    private void buildBackButton(int y)
    {
        addBtn("BACK", BTN_X, y, BTN_W, BTN_H, () -> switchTo(State.PAUSE));
    }

    // ── Button factory helpers ────────────────────────────────────────────────

    private void addBtn(String label, int x, int y, int w, int h,
                        UIButton.OnClick cb)
    {
        addBtn(label, x, y, w, h, 16, cb);
    }

    private void addBtn(String label, int x, int y, int w, int h,
                        int fontSize, UIButton.OnClick cb)
    {
        UIButton btn = new UIButton(label, w, h, fontSize);
        btn.setOnClick(cb);
        getWorld().addObject(btn, x, y);
        activeButtons.add(btn);
    }

    // ── Remove all tracked buttons ────────────────────────────────────────────

    private void removeAllButtons()
    {
        World w = getWorld();
        if (w == null) { activeButtons.clear(); return; }
        for (UIButton btn : activeButtons) {
            if (btn.getWorld() != null) w.removeObject(btn);
        }
        activeButtons.clear();
    }

    // ── Cheat: kill all aliens ────────────────────────────────────────────────

    @SuppressWarnings("unchecked")
    private void killAllAliens()
    {
        World w = getWorld();
        if (w == null) return;
        java.util.List<Alien> aliens =
            (java.util.List<Alien>) w.getObjects(Alien.class);
        // Work on a copy to avoid ConcurrentModificationException
        for (Alien a : new java.util.ArrayList<>(aliens)) {
            if (a.getWorld() != null) a.die();
        }
    }

    // ── Music volume application ──────────────────────────────────────────────

    private void applyMusicVolume()
    {
        World w = getWorld();
        if (w instanceof GameWorld) {
            ((GameWorld) w).applyMusicVolume(GameSettings.getMusicVolume());
        }
    }

    // ── Quit to intro ─────────────────────────────────────────────────────────

    private void quitToIntro()
    {
        GameSettings.setPaused(false);
        try {
            Greenfoot.setWorld(new IntroWorld());
        } catch (Exception e) {
            Greenfoot.stop();
        }
    }

    // ── Utility ───────────────────────────────────────────────────────────────

    private static String titleFor(State s)
    {
        switch (s) {
            case PAUSE:       return "PAUSED";
            case SETTINGS:    return "SETTINGS";
            case HOW_TO_PLAY: return "HOW TO PLAY";
            case CHEATS:      return "CHEATS";
            default:          return "";
        }
    }
}
