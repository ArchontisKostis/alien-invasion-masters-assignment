import greenfoot.*;

/**
 * UIButton — a clickable, hover-aware button actor for the in-game UI overlay.
 *
 * ── Visual states ─────────────────────────────────────────────────────────────
 *   Normal : dark navy background (#040A1C), white label, subtle cyan border.
 *   Hover  : solid cyan fill, white label, bright top-edge glow line.
 *   Active : (held) slightly darker cyan — flips back to hover on release.
 *
 * ── Usage ─────────────────────────────────────────────────────────────────────
 *   Create via UIManager.addBtn() — all buttons are built through the full
 *   UIButton(label, width, height, fontSize) constructor internally.
 *
 * ── Callback ──────────────────────────────────────────────────────────────────
 *   Set a lambda or anonymous class via setOnClick(OnClick cb).
 *   The callback fires once per mouse-press-and-release cycle.
 *
 * Source: original code; follows game1_space_invaders_FINAL.md §UI.
 */
public class UIButton extends Actor
{
    // ── Callback interface ────────────────────────────────────────────────────

    /** Functional interface — implement with a lambda. */
    public interface OnClick {
        void clicked();
    }

    // ── Colours ───────────────────────────────────────────────────────────────

    private static final Color BG_NORMAL   = new Color(4,  10,  28, 220);
    private static final Color BG_HOVER    = new Color(0, 200, 255, 230);
    private static final Color BG_ACTIVE   = new Color(0, 160, 210, 240);
    private static final Color BORDER_DIM  = new Color(0, 180, 220,  90);
    private static final Color BORDER_LIT  = new Color(0, 230, 255, 220);
    private static final Color TEXT_NORMAL = Color.WHITE;
    private static final Color TEXT_HOVER  = Color.WHITE;
    private static final Color GLOW_LINE   = new Color(180, 240, 255, 180);
    private static final Color TEXT_SHADOW = new Color(0,   0,   0,   0);  // transparent bg

    // ── Dimensions ────────────────────────────────────────────────────────────

    private final int width;
    private final int height;
    private final String label;
    private final int fontSize;

    // ── State ─────────────────────────────────────────────────────────────────

    private OnClick listener;
    private boolean hovered      = false;
    private boolean pressedDown  = false;   // mouse button is held over this btn
    private boolean prevClicked  = false;   // last-act click state (edge detect)

    // ── Constructors ──────────────────────────────────────────────────────────

    /** Full constructor — used by all UIManager.addBtn() calls. */
    public UIButton(String label, int width, int height, int fontSize)
    {
        this.label    = label;
        this.width    = width;
        this.height   = height;
        this.fontSize = fontSize;
        repaint(false, false);
    }

    // ── Public API ────────────────────────────────────────────────────────────

    public void setOnClick(OnClick cb) { this.listener = cb; }

    // ── act ───────────────────────────────────────────────────────────────────

    @Override
    public void act()
    {
        MouseInfo mi = Greenfoot.getMouseInfo();

        boolean nowHovered  = false;
        boolean nowPressed  = false;
        boolean justClicked = false;

        if (mi != null) {
            int mx = mi.getX();
            int my = mi.getY();
            int hw = width  / 2;
            int hh = height / 2;
            nowHovered = (mx >= getX() - hw && mx <= getX() + hw &&
                          my >= getY() - hh && my <= getY() + hh);

            if (nowHovered) {
                nowPressed  = (mi.getButton() == 1);
                justClicked = (mi.getClickCount() > 0 && mi.getButton() == 1);
            }
        }

        // Redraw only when visual state changes
        if (nowHovered != hovered || nowPressed != pressedDown) {
            hovered     = nowHovered;
            pressedDown = nowPressed;
            repaint(hovered, pressedDown);
        }

        // Fire callback on click (edge: was not clicked, now is)
        if (justClicked && !prevClicked && listener != null) {
            listener.clicked();
        }
        prevClicked = justClicked;
    }

    // ── Rendering ────────────────────────────────────────────────────────────

    private void repaint(boolean hover, boolean pressed)
    {
        GreenfootImage img = new GreenfootImage(width, height);

        // ── Background fill ───────────────────────────────────────────────────
        Color bg     = pressed ? BG_ACTIVE  : (hover ? BG_HOVER   : BG_NORMAL);
        Color border = hover   ? BORDER_LIT :                        BORDER_DIM;

        img.setColor(bg);
        img.fillRect(0, 0, width, height);

        // ── Border ────────────────────────────────────────────────────────────
        img.setColor(border);
        img.drawRect(0, 0, width - 1, height - 1);

        // ── Glow line on top edge (hover only) ────────────────────────────────
        if (hover) {
            img.setColor(GLOW_LINE);
            img.fillRect(4, 1, width - 8, 1);
        }

        // ── Label text ────────────────────────────────────────────────────────
        Color textCol = hover ? TEXT_HOVER : TEXT_NORMAL;
        GreenfootImage textImg = new GreenfootImage(label, fontSize, textCol, TEXT_SHADOW);
        int tx = (width  - textImg.getWidth())  / 2;
        int ty = (height - textImg.getHeight()) / 2;
        img.drawImage(textImg, tx, ty);

        setImage(img);
    }
}
