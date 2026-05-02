# Game 1: Space Invaders — Complete Asset Manifest
### Every file you need, exactly what each frame shows, and the prompt to generate it

---

## How to Use This Document

1. **Sprites** → Generate with an AI image tool (prompts below). Best tools: **Midjourney**, **DALL-E 3**, **Adobe Firefly**, or **Stable Diffusion** with pixel-art LoRA.
2. **Sounds** → Generate free at [sfxr.me](https://sfxr.me) (no account, instant download). Settings given for each.
3. **Music** → Download free from [incompetech.com](https://incompetech.com) or generate with [suno.ai](https://suno.ai).
4. **File placement** → All sprites go in `images/` folder inside your Greenfoot project. All sounds go in `sounds/`.

> **Tip for AI images:** Always add `"transparent background, no shadow"` to every sprite prompt. Export as PNG. If the tool gives you a white background, use **remove.bg** (free) to strip it.

> **Canvas size trick:** In Midjourney use `--ar 1:1` for square sprites, `--ar 2:1` for wide sprites. In DALL-E set the custom size to exactly the dimensions listed.

---

## SECTION 1 — PLAYER ASSETS

---

### 1.1 — Player Cannon (Main Ship)

**Greenfoot filename:** `player_ship_0.png` and `player_ship_1.png`  
**Dimensions:** 48 × 28 px each  
**Animation:** 2-frame cycle (engine glow off → engine glow on), swaps every 3 acts (~20fps)  
**Used in:** `PlayerCannon` actor, both levels

**Frame 0 — Engine off:**
> The cannon rests between firing cycles. Engine exhaust nozzle is dark/unlit.

**Frame 1 — Engine on:**
> Same shape but the rear exhaust nozzles glow bright cyan/blue. The glow is the ONLY difference from frame 0.

---

**PROMPT (Frame 0 — Engine Off):**
```
Pixel art space invaders style player cannon ship, 48x28 pixels, viewed from above/top-down, 
white and light grey hull with blue accent stripe, two side wings, flat bottom, 
single cannon barrel pointing upward, dark exhaust nozzle at the bottom center, 
no glow, no engine fire, retro 8-bit style, transparent background, 
sharp pixel edges, no anti-aliasing, no shadow
```

**PROMPT (Frame 1 — Engine Glow On):**
```
Pixel art space invaders style player cannon ship, 48x28 pixels, viewed from above/top-down, 
white and light grey hull with blue accent stripe, two side wings, flat bottom, 
single cannon barrel pointing upward, bright cyan/blue glowing exhaust nozzle at bottom center, 
small engine flame glow effect below nozzle, retro 8-bit style, transparent background, 
sharp pixel edges, no anti-aliasing, no shadow
```

**Alternative:** [kenney.nl/assets/space-shooter-redux](https://kenney.nl/assets/space-shooter-redux) → file `playerShip1_blue.png` (resize to 48×28)

---

### 1.2 — Player Life Icon (HUD)

**Greenfoot filename:** `player_life_icon.png`  
**Dimensions:** 24 × 14 px  
**Animation:** None — static  
**Used in:** HUD top-right, 3 icons shown. One removed per life lost.

**PROMPT:**
```
Tiny pixel art spaceship icon, 24x14 pixels, top-down view, white hull, blue accent, 
looks like a miniature version of a space invaders player cannon, 
retro 8-bit style, transparent background, no anti-aliasing, no shadow
```

---

### 1.3 — Player Laser Bullet

**Greenfoot filename:** `player_bullet.png`  
**Dimensions:** 4 × 16 px  
**Animation:** None — static  
**Used in:** `PlayerBullet` actor, travels upward at 8 px/act

**PROMPT:**
```
Pixel art laser bolt, 4x16 pixels, vertical orientation pointing upward, 
bright white core with cyan outer glow, sharp and thin, 
retro 8-bit style, transparent background, no anti-aliasing
```

**Alternative:** Draw in Greenfoot code using `GreenfootImage`:
```java
GreenfootImage img = new GreenfootImage(4, 16);
img.setColor(new Color(200, 255, 255));
img.fillRect(1, 0, 2, 16);
img.setColor(Color.WHITE);
img.fillRect(1, 4, 2, 8);
setImage(img);
```

---

---

## SECTION 2 — ALIEN ASSETS

> **Naming convention:** Level 1 aliens use normal names. Level 2 aliens (same shape, different color palette) use the same names but are stored in a `level2/` subfolder or renamed with `_l2` suffix. Suggested approach: use the same class but pass a different image path based on current level.

---

### 2.1 — EasyAlien (Bottom Row — 10 pts)

**Greenfoot filenames:** `easy_alien_0.png`, `easy_alien_1.png`  
**Dimensions:** 32 × 32 px each  
**Animation:** 2-frame cycle — tentacles/legs alternate positions, swaps every 4 acts  
**Shape inspiration:** Classic Space Invaders squid — round head, drooping tentacles below  
**Level 1 color:** Lime green  
**Level 2 color:** Dark purple / magenta

**Frame 0 — Tentacles down:**
> Two short tentacles droop straight below the body. Body is round/oval.

**Frame 1 — Tentacles spread:**
> The two tentacles splay outward at 45° angles. Body unchanged.

---

**PROMPT (Level 1, Frame 0 — Tentacles Down):**
```
Pixel art space invaders squid alien, 32x32 pixels, lime green color, 
round oval head with two large black eyes, two short tentacle legs drooping straight down, 
retro 8-bit style, classic arcade game aesthetic, transparent background, 
no anti-aliasing, no shadow, no background, viewed from front
```

**PROMPT (Level 1, Frame 1 — Tentacles Spread):**
```
Pixel art space invaders squid alien, 32x32 pixels, lime green color, 
round oval head with two large black eyes, two tentacle legs splaying outward at 45 degrees, 
retro 8-bit style, classic arcade game aesthetic, transparent background, 
no anti-aliasing, no shadow, no background, viewed from front
```

**PROMPT (Level 2, Frame 0 — Color variant):**
```
Pixel art space invaders squid alien, 32x32 pixels, dark purple and magenta color scheme, 
glowing red eyes, round oval head, two short tentacle legs drooping straight down, 
more menacing than level 1 version, retro 8-bit style, transparent background, 
no anti-aliasing, no shadow, viewed from front
```

**PROMPT (Level 2, Frame 1):**
```
Pixel art space invaders squid alien, 32x32 pixels, dark purple and magenta color scheme, 
glowing red eyes, round oval head, two tentacle legs splaying outward at 45 degrees, 
retro 8-bit style, transparent background, no anti-aliasing, no shadow, viewed from front
```

---

### 2.2 — MidAlien (Middle Rows — 20 pts)

**Greenfoot filenames:** `mid_alien_0.png`, `mid_alien_1.png`  
**Dimensions:** 32 × 32 px each  
**Animation:** 2-frame cycle — claws open/close, swaps every 4 acts  
**Shape inspiration:** Classic Space Invaders crab — wide body, two claws  
**Level 1 color:** Cyan / light blue  
**Level 2 color:** Orange / burnt orange

**Frame 0 — Claws closed/up:**
> Claw arms bent upward/inward. Wide flat body with 4 legs below.

**Frame 1 — Claws open/spread:**
> Claw arms extended outward horizontally. Body unchanged.

---

**PROMPT (Level 1, Frame 0 — Claws Closed):**
```
Pixel art space invaders crab alien, 32x32 pixels, cyan light blue color, 
wide flat rectangular body, two claw arms bent upward, four small legs below body, 
two white dot eyes on top, retro 8-bit style, classic arcade aesthetic, 
transparent background, no anti-aliasing, no shadow, viewed from front
```

**PROMPT (Level 1, Frame 1 — Claws Open):**
```
Pixel art space invaders crab alien, 32x32 pixels, cyan light blue color, 
wide flat rectangular body, two claw arms extended straight outward horizontally, 
four small legs below body, two white dot eyes on top, retro 8-bit style, 
transparent background, no anti-aliasing, no shadow, viewed from front
```

**PROMPT (Level 2, Frame 0):**
```
Pixel art space invaders crab alien, 32x32 pixels, orange burnt orange color scheme, 
wide flat rectangular body, two claw arms bent upward, four small legs below body, 
glowing red eyes, more aggressive look, retro 8-bit style, 
transparent background, no anti-aliasing, no shadow, viewed from front
```

**PROMPT (Level 2, Frame 1):**
```
Pixel art space invaders crab alien, 32x32 pixels, orange burnt orange color scheme, 
wide flat rectangular body, two claw arms extended straight outward horizontally, 
glowing red eyes, retro 8-bit style, transparent background, no anti-aliasing, no shadow
```

---

### 2.3 — HardAlien (Top Rows — 30 pts)

**Greenfoot filenames:** `hard_alien_0.png`, `hard_alien_1.png`  
**Dimensions:** 32 × 32 px each  
**Animation:** 2-frame cycle — antennae alternate positions, swaps every 4 acts  
**Shape inspiration:** Classic Space Invaders octopus/bug — dome helmet, spindly legs  
**Level 1 color:** Yellow / gold  
**Level 2 color:** Red / dark crimson

**Frame 0 — Antennae straight up:**
> Two thin antennae point straight up from top of helmet. Body has 6 small legs.

**Frame 1 — Antennae angled out:**
> Antennae angle diagonally outward at ~45°. Body unchanged.

---

**PROMPT (Level 1, Frame 0 — Antennae Up):**
```
Pixel art space invaders bug alien, 32x32 pixels, yellow gold color, 
rounded dome helmet head, two thin antennae pointing straight upward, 
six small spindly legs below the body, two round eyes in helmet, 
retro 8-bit style, classic arcade aesthetic, transparent background, 
no anti-aliasing, no shadow, viewed from front
```

**PROMPT (Level 1, Frame 1 — Antennae Out):**
```
Pixel art space invaders bug alien, 32x32 pixels, yellow gold color, 
rounded dome helmet head, two thin antennae angled diagonally outward at 45 degrees, 
six small spindly legs below the body, two round eyes in helmet, 
retro 8-bit style, transparent background, no anti-aliasing, no shadow, viewed from front
```

**PROMPT (Level 2, Frame 0):**
```
Pixel art space invaders bug alien, 32x32 pixels, deep red crimson color scheme, 
rounded dome helmet with glowing orange eyes, two thin antennae pointing straight up, 
six small spindly legs, more menacing and angular than level 1 version, 
retro 8-bit style, transparent background, no anti-aliasing, no shadow, viewed from front
```

**PROMPT (Level 2, Frame 1):**
```
Pixel art space invaders bug alien, 32x32 pixels, deep red crimson color scheme, 
rounded dome helmet with glowing orange eyes, two antennae angled outward at 45 degrees, 
six small spindly legs, retro 8-bit style, transparent background, no anti-aliasing, no shadow
```

---

### 2.4 — Mystery Ship / UFO (Level 2 Boss Bonus)

**Greenfoot filenames:** `mystery_ship_0.png`, `mystery_ship_1.png`  
**Dimensions:** 56 × 22 px each  
**Animation:** 2-frame cycle — lights blink, swaps every 3 acts  
**Behavior:** Flies horizontally across top of screen in level 2. Worth 50/100/150 pts.

**Frame 0 — Lights dim:**
> Classic saucer shape. Lights along the underside are off/dark.

**Frame 1 — Lights bright:**
> Same saucer shape. Lights along underside glow brightly (white/yellow dots lit up).

---

**PROMPT (Frame 0 — Lights Dim):**
```
Pixel art flying saucer UFO, 56x22 pixels, side view, classic saucer disc shape 
with dome on top, metallic silver and grey hull, a row of small circular porthole 
windows along the equator that are dark/unlit, retro 8-bit style, space invaders aesthetic, 
transparent background, no anti-aliasing, no shadow
```

**PROMPT (Frame 1 — Lights Bright):**
```
Pixel art flying saucer UFO, 56x22 pixels, side view, classic saucer disc shape 
with dome on top, metallic silver and grey hull, a row of small circular porthole 
windows along the equator glowing bright white-yellow, 
retro 8-bit style, space invaders aesthetic, transparent background, no anti-aliasing, no shadow
```

---

---

## SECTION 3 — PROJECTILE ASSETS

---

### 3.1 — Alien Bomb (Standard zigzag bolt)

**Greenfoot filenames:** `alien_bomb_0.png`, `alien_bomb_1.png`  
**Dimensions:** 6 × 18 px each  
**Animation:** 2-frame zigzag — bolt leans left then right, swaps every 2 acts (fast flicker)  
**Used in:** `AlienBullet` actor, travels downward at 3.5 px/act

**Frame 0 — Leans left:**
> A jagged lightning-bolt shape leaning to the left. Green/yellow color.

**Frame 1 — Leans right:**
> Same bolt mirrored — leaning to the right.

---

**PROMPT (Frame 0 — Leans Left):**
```
Pixel art alien laser bomb projectile, 6x18 pixels, vertical lightning bolt shape 
leaning left, bright neon green color with yellow inner glow, zigzag edges, 
retro 8-bit style space invaders aesthetic, transparent background, no anti-aliasing
```

**PROMPT (Frame 1 — Leans Right):**
```
Pixel art alien laser bomb projectile, 6x18 pixels, vertical lightning bolt shape 
leaning right, bright neon green color with yellow inner glow, zigzag edges, 
mirror image of a leftward bolt, retro 8-bit style space invaders aesthetic, 
transparent background, no anti-aliasing
```

---

---

## SECTION 4 — EXPLOSION ASSETS

> Explosions are the most important animations for game feel. Spend extra effort here.

---

### 4.1 — Standard Alien Explosion (when an alien is killed)

**Greenfoot filenames:** `explosion_alien_0.png` through `explosion_alien_3.png`  
**Dimensions:** 40 × 40 px each (4 frames)  
**Animation:** Plays frames 0→3 once at threshold=3, then actor is removed (12 acts total)  
**Used in:** All alien death events

**What each frame shows:**

| Frame | Description |
|---|---|
| 0 | Small bright white core burst — 8px radius starburst |
| 1 | Expanding ring of yellow/white — 16px radius, fragments visible |
| 2 | Orange scattered debris — 24px radius, ring breaking apart |
| 3 | Fading red/dark embers — 32px radius, nearly dissipated |

---

**PROMPT (Frame 0 — Initial Flash):**
```
Pixel art explosion frame 1 of 4, 40x40 pixels, very small initial burst, 
bright white starburst at center with 8 short spiky rays, no outer ring yet, 
retro 8-bit style arcade explosion, transparent background, no anti-aliasing, centered
```

**PROMPT (Frame 1 — Expanding Ring):**
```
Pixel art explosion frame 2 of 4, 40x40 pixels, expanding yellow-white burst ring, 
bright center with radiating yellow fragments and sparks spreading outward to 16px radius, 
small bright shards flying off, retro 8-bit style, transparent background, no anti-aliasing
```

**PROMPT (Frame 2 — Orange Debris):**
```
Pixel art explosion frame 3 of 4, 40x40 pixels, orange and yellow debris cloud, 
scattered fragments at 24px radius, broken ring structure visible, 
some sparks still bright, edges starting to fade, 
retro 8-bit style arcade explosion, transparent background, no anti-aliasing
```

**PROMPT (Frame 3 — Fading Embers):**
```
Pixel art explosion frame 4 of 4, 40x40 pixels, fading red-orange embers, 
scattered dust and dying sparks at 32px radius, mostly transparent center, 
few remaining bright pixels, nearly dissipated, 
retro 8-bit style, transparent background, no anti-aliasing
```

**SINGLE SHEET PROMPT (alternative — generate all 4 at once):**
```
Pixel art explosion sprite sheet, 160x40 pixels (4 frames of 40x40 each), 
retro 8-bit style, horizontal layout left to right: 
frame 1 = tiny white starburst, frame 2 = expanding yellow ring with fragments, 
frame 3 = orange debris cloud, frame 4 = fading red embers, 
arcade game style, each frame centered in its 40x40 cell, 
transparent background, no anti-aliasing, no border between frames
```

---

### 4.2 — Player Death Explosion (when the player ship is hit)

**Greenfoot filenames:** `explosion_player_0.png` through `explosion_player_4.png`  
**Dimensions:** 64 × 48 px each (5 frames — bigger and more dramatic than alien explosion)  
**Animation:** Plays frames 0→4 once at threshold=4, then a respawn sequence triggers

**What each frame shows:**

| Frame | Description |
|---|---|
| 0 | Large white flash — full image nearly white |
| 1 | Bright yellow-white explosion ball, ship silhouette breaking apart |
| 2 | Orange fireball with large debris chunks flying outward |
| 3 | Red-orange cloud, debris continues outward, smoke streaks |
| 4 | Dark smoke cloud, fading embers, ship completely gone |

---

**PROMPT (All 5 frames as a sheet):**
```
Pixel art player ship explosion sprite sheet, 320x48 pixels (5 frames of 64x48 each), 
retro 8-bit style, horizontal layout: 
frame 1 = white flash full screen, frame 2 = yellow fireball with ship breaking apart, 
frame 3 = orange fireball large debris chunks outward, 
frame 4 = red-orange smoke cloud scattered fragments, 
frame 5 = dark grey smoke fading embers, 
dramatic destruction sequence, space invaders player death, 
transparent background, no anti-aliasing
```

**Individual frame prompts:**

**PROMPT (Frame 0 — White Flash):**
```
Pixel art explosion frame 1 of 5, 64x48 pixels, 
blinding white flash filling most of the frame, 
very bright, minimal detail, single intense burst, 
retro 8-bit, transparent background
```

**PROMPT (Frame 1 — Fireball with Ship):**
```
Pixel art explosion frame 2 of 5, 64x48 pixels, 
bright yellow-white fireball, broken spaceship pieces visible inside the explosion, 
ship hull fragments separating, hot bright core, 
retro 8-bit style, transparent background
```

**PROMPT (Frame 2 — Orange Fireball):**
```
Pixel art explosion frame 3 of 5, 64x48 pixels, 
orange-red fireball, large ship debris chunks flying outward in all directions, 
sparks trailing the fragments, some pieces distinctly recognizable as ship parts, 
retro 8-bit style, transparent background
```

**PROMPT (Frame 3 — Smoke and Debris):**
```
Pixel art explosion frame 4 of 5, 64x48 pixels, 
red-orange smoke cloud with scattered debris fragments, 
some burning pieces visible, edges of cloud wispy and dark, 
retro 8-bit style, transparent background
```

**PROMPT (Frame 4 — Fading Smoke):**
```
Pixel art explosion frame 5 of 5, 64x48 pixels, 
dark grey smoke cloud fading out, a few faint embers, 
mostly transparent/empty, the ship is completely gone, 
retro 8-bit style, transparent background
```

---

### 4.3 — Mystery Ship Explosion (when MysteryShip is hit)

**Greenfoot filenames:** `explosion_mystery_0.png` through `explosion_mystery_3.png`  
**Dimensions:** 56 × 28 px each (4 frames — same width/height as the ship itself)  
**Animation:** Plays frames 0→3 once at threshold=3  
**Special:** After frame 3, display a score label (50/100/150) that fades out

**PROMPT (All 4 frames as a sheet):**
```
Pixel art UFO explosion sprite sheet, 224x28 pixels (4 frames of 56x28 each), 
retro 8-bit style, horizontal layout: 
frame 1 = white flash over saucer shape, frame 2 = orange fireball saucer breaking apart, 
frame 3 = scattered saucer debris pieces and sparks, frame 4 = fading smoke dust, 
space invaders mystery ship destruction, transparent background, no anti-aliasing
```

---

### 4.4 — Screen Flash (player hit — brief white flash)

**Greenfoot filename:** Not a sprite — implemented in code  
**Implementation:** On player hit, set world background to white image for 5 acts, then restore normal BG:

```java
// In GameWorld:
private void triggerScreenFlash() {
    flashTimer = 5;
}
public void act() {
    if (flashTimer > 0) {
        flashTimer--;
        if (flashTimer == 5) {
            GreenfootImage flash = new GreenfootImage(getWidth(), getHeight());
            flash.setColor(new Color(255, 255, 255, 180));
            flash.fill();
            getBackground().drawImage(flash, 0, 0);
        }
        if (flashTimer == 0) {
            restoreBackground();
        }
    }
}
```

---

---

## SECTION 5 — BUNKER ASSETS

---

### 5.1 — Bunker Tile (Individual damage block)

**Greenfoot filenames:** `bunker_tile_0.png`, `bunker_tile_1.png`, `bunker_tile_2.png`  
**Dimensions:** 8 × 8 px each  
**Animation:** None — tiles are swapped to next damage stage on hit, removed after 3 hits  
**Pattern:** Each bunker is assembled from ~14 tile actors (see layout in design doc)

**Stage 0 — Solid (intact):**  
**Stage 1 — Cracked (1 hit):**  
**Stage 2 — Heavily damaged (2 hits):**

**DRAW IN CODE — no sprite needed:**
```java
// BunkerTile image generation
public static GreenfootImage makeTileImage(int damageStage) {
    GreenfootImage img = new GreenfootImage(8, 8);
    switch (damageStage) {
        case 0: // Solid green
            img.setColor(new Color(0, 200, 80));
            img.fill();
            break;
        case 1: // Cracked — draw cracks
            img.setColor(new Color(0, 160, 60));
            img.fill();
            img.setColor(new Color(0, 80, 30));
            img.drawLine(2, 0, 4, 4);
            img.drawLine(4, 4, 6, 8);
            img.drawLine(0, 3, 3, 5);
            break;
        case 2: // Heavy damage — mostly holes
            img.setColor(new Color(0, 100, 40));
            img.fill();
            img.setColor(new Color(0, 0, 0, 0)); // transparent holes
            img.fillRect(1, 1, 2, 2);
            img.fillRect(5, 3, 2, 2);
            img.fillRect(2, 5, 3, 2);
            img.drawLine(0, 0, 3, 3);
            img.drawLine(5, 5, 7, 7);
            break;
    }
    return img;
}
```

> **Recommendation:** Draw in code — it's faster and easier to adjust than sourcing/generating tiny 8×8 sprites.

---

---

## SECTION 6 — BACKGROUND ASSETS

---

### 6.1 — Level 1 Background (Earth Orbit / Space)

**Greenfoot filename:** `bg_level1.png`  
**Dimensions:** 800 × 600 px  
**Used in:** `Level1World` background  
**Content:** Deep black space with scattered stars. No foreground elements. Clean and dark so aliens are clearly visible.

**PROMPT:**
```
Pixel art deep space background, 800x600 pixels, pure black sky, 
scattered white and pale blue pixel stars of varying sizes (some 1px, some 2px clusters), 
subtle blue-purple nebula cloud in the upper right corner, 
a faint distant galaxy smear in the lower left, 
retro 8-bit arcade style, no foreground objects, no planets visible, 
designed as a game background — must not distract from foreground sprites
```

**Alternative — draw in code (recommended for speed):**
```java
// In Level1World constructor:
GreenfootImage bg = new GreenfootImage(800, 600);
bg.setColor(Color.BLACK);
bg.fill();
// Scatter stars
java.util.Random r = new java.util.Random(42);
for (int i = 0; i < 150; i++) {
    int x = r.nextInt(800), y = r.nextInt(600);
    int bright = 150 + r.nextInt(106); // 150-255
    bg.setColor(new Color(bright, bright, bright));
    bg.fillRect(x, y, r.nextInt(2)+1, r.nextInt(2)+1);
}
setBackground(bg);
```

---

### 6.2 — Level 2 Background (Lunar Surface)

**Greenfoot filename:** `bg_level2.png`  
**Dimensions:** 800 × 600 px  
**Used in:** `Level2World` background  
**Content:** Grey moon surface terrain at the bottom ~80px. Stars above. Earth visible top-right. Different feel from level 1.

**PROMPT:**
```
Pixel art moon surface background, 800x600 pixels, 
upper 500 pixels: black space with scattered white stars, 
large Earth visible in the top-right corner (blue and white pixel art globe, roughly 100px diameter), 
lower 100 pixels: grey rocky lunar terrain with 3-4 visible craters, 
some small rocks and dust along the terrain line, 
retro 8-bit arcade style, designed as a game background, no foreground sprites
```

---

### 6.3 — Intro/Menu Background

**Greenfoot filename:** `bg_intro.png`  
**Dimensions:** 800 × 600 px  
**Used in:** `IntroWorld` (or generate starfield in code — recommended)

**PROMPT:**
```
Pixel art space background for a game menu screen, 800x600 pixels, 
dark blue-black deep space, many scattered stars of different brightnesses, 
large dramatic purple-blue nebula cloud on the left side, 
a glowing distant star cluster on the right, 
cinematic and dramatic feel, retro pixel art style, no text, no UI, no foreground objects
```

**Alternative:** Reuse `bg_level1.png` with a semi-transparent dark overlay drawn over it.

---

### 6.4 — Game Over / Win Screen Background

**Greenfoot filename:** `bg_gameover.png`  
**Dimensions:** 800 × 600 px  
**Used in:** `GameOverWorld`

**PROMPT:**
```
Pixel art dark dramatic space background for a game over screen, 800x600 pixels, 
very dark red-black color scheme, scattered faint red-tinted stars, 
faint red glow at bottom as if viewed from burning atmosphere below, 
somber and dramatic mood, retro pixel art style, no text
```

> For the **WIN** screen, use the Level 2 background or the intro background tinted green in code.

---

### 6.5 — Moon Terrain Overlay (Level 2 only, bottom strip)

**Greenfoot filename:** `terrain_moon.png`  
**Dimensions:** 800 × 80 px  
**Placed at:** Bottom of Level2World (Y = 520)  
**Content:** The grey lunar surface that sits above the player's movement zone

**PROMPT:**
```
Pixel art moon surface terrain strip, 800x80 pixels, 
grey rocky lunar ground texture, horizontal terrain tile, 
uneven surface with small rocks and dust, 2 visible impact craters of varying sizes, 
bottom half is solid grey fill, top edge is irregular/bumpy terrain silhouette, 
retro 8-bit style, no transparency, designed to sit at the bottom of a game screen
```

---

---

## SECTION 7 — UI / HUD ASSETS

---

### 7.1 — Score / Text Font

**Approach:** Use Greenfoot's built-in `GreenfootImage` text drawing — no sprite needed:
```java
GreenfootImage scoreImg = new GreenfootImage("SCORE: " + score, 18, Color.WHITE, new Color(0,0,0,0));
```
Font size 18, white text, transparent background. Greenfoot renders this with its built-in font.

---

### 7.2 — "GAME OVER" Text Graphic (optional — for dramatic effect)

**Greenfoot filename:** `text_gameover.png`  
**Dimensions:** 400 × 60 px  
**Used in:** `GameOverWorld` as a prominent header

**PROMPT:**
```
Pixel art game over text graphic, 400x60 pixels, bold retro 8-bit arcade font, 
text reading "GAME OVER" in large bright red pixel letters, 
slight outer glow/outline in dark red, 
retro arcade style, transparent background, no extra decorations
```

---

### 7.3 — "LEVEL CLEAR" Text Graphic (optional)

**Greenfoot filename:** `text_levelclear.png`  
**Dimensions:** 400 × 60 px

**PROMPT:**
```
Pixel art level clear text graphic, 400x60 pixels, bold retro 8-bit arcade font, 
text reading "LEVEL CLEAR!" in large bright yellow-green pixel letters, 
slight outer glow in dark green, retro arcade style, transparent background
```

---

---

## SECTION 8 — SOUND EFFECTS

> All sounds generated at **[sfxr.me](https://sfxr.me)** — free browser tool. Click the preset category, tweak, download .wav.  
> File size should be tiny (under 50KB each). All go in `sounds/` folder.

---

### 8.1 — Player Laser Fire

**Filename:** `laser.wav`  
**Triggered:** Every time player fires  
**Description:** Sharp, quick "pew" zap — classic arcade laser

**sfxr.me settings:**
- Click preset: **"Laser/Shoot"** → Tweaks:
  - Wave Shape: Square
  - Start Frequency: 0.5
  - Frequency Cutoff: 0.1
  - Slide: −0.3
  - Duration: 0.2 seconds
- Click Generate a few times until it sounds like a crisp "pew"
- Download as .wav

---

### 8.2 — Alien Explosion

**Filename:** `alien_explode.wav`  
**Triggered:** When any alien is killed  
**Description:** Retro 8-bit crunch/pop — satisfying destruction sound

**sfxr.me settings:**
- Click preset: **"Explosion"** → Tweaks:
  - Wave Shape: Noise
  - Start Frequency: 0.3
  - Slide: −0.2
  - Duration: 0.4 seconds
  - Punch: 0.5
- Should sound like a dry crunch, not a boom

---

### 8.3 — Player Hit / Ship Destroyed

**Filename:** `player_hit.wav`  
**Triggered:** When alien bomb hits the player  
**Description:** Low boom with electronic distortion — heavier than alien explosion

**sfxr.me settings:**
- Click preset: **"Explosion"** → Tweaks:
  - Wave Shape: Noise
  - Start Frequency: 0.2
  - Slide: −0.4
  - Duration: 0.6 seconds
  - Punch: 0.8
  - Low Pass Filter: enabled, cutoff 0.4
- Lower and heavier than alien_explode.wav

---

### 8.4 — Alien March Step (iconic descending thunk)

**Filename:** `alien_step.wav`  
**Triggered:** Each time the alien formation descends one row (hits wall and drops)  
**Description:** The iconic low "thunk" of classic Space Invaders. Short, punchy.

**sfxr.me settings:**
- Click preset: **"Hit/Hurt"** → Tweaks:
  - Wave Shape: Square
  - Start Frequency: 0.15
  - Slide: 0.0
  - Duration: 0.15 seconds
  - No reverb
- Generate 4 slightly different versions: `alien_step_1.wav` through `alien_step_4.wav`
- Cycle through these 4 sounds in order (the classic Space Invaders tempo effect!)

**Implementation:**
```java
private String[] stepSounds = {"alien_step_1.wav","alien_step_2.wav","alien_step_3.wav","alien_step_4.wav"};
private int stepIndex = 0;
// When formation drops:
Greenfoot.playSound(stepSounds[stepIndex]);
stepIndex = (stepIndex + 1) % 4;
```

---

### 8.5 — Alien Bomb Fire

**Filename:** `alien_shoot.wav`  
**Triggered:** When an alien fires a bomb  
**Description:** Low, menacing "blorp" or "bloop" — alien weapon sound

**sfxr.me settings:**
- Click preset: **"Laser/Shoot"** → Tweaks:
  - Wave Shape: Sine
  - Start Frequency: 0.15 (low pitch)
  - Slide: −0.15
  - Duration: 0.2 seconds
- Should sound LOW and heavy compared to player's laser

---

### 8.6 — Bunker Hit

**Filename:** `bunker_hit.wav`  
**Triggered:** When a bullet or bomb hits and removes a bunker tile  
**Description:** Short dull "thud" — like something absorbing impact

**sfxr.me settings:**
- Click preset: **"Hit/Hurt"** → Tweaks:
  - Wave Shape: Noise
  - Start Frequency: 0.1 (very low)
  - Duration: 0.08 seconds
  - Very short and dull

---

### 8.7 — Mystery Ship Appears

**Filename:** `mystery_ship_loop.wav`  
**Triggered:** When MysteryShip spawns — played looping until it leaves/dies  
**Description:** High-pitched warbling beep that pulses — players instantly know to look up

**sfxr.me settings:**
- Click preset: **"Powerup"** → Tweaks:
  - Wave Shape: Square
  - Start Frequency: 0.7 (high pitch)
  - Vibrato Speed: 0.4
  - Vibrato Depth: 0.3
  - Duration: 0.5 seconds (short loop)
- Greenfoot plays this with `.playLoop()` on the sound object

```java
// In Level2World, when MysteryShip spawns:
private GreenfootSound mysterySound = new GreenfootSound("mystery_ship_loop.wav");
mysterySound.playLoop();
// When MysteryShip leaves/is destroyed:
mysterySound.stop();
```

---

### 8.8 — Mystery Ship Hit / Destroyed

**Filename:** `mystery_ship_hit.wav`  
**Triggered:** When player bullet destroys the MysteryShip  
**Description:** Satisfying cash-register ping + explosion combo — reward sound

**sfxr.me settings:**
- Click preset: **"Powerup"** → Tweaks:
  - Wave Shape: Triangle
  - Start Frequency: 0.8
  - Slide: 0.2 (rising pitch = rewarding feeling)
  - Duration: 0.4 seconds

---

### 8.9 — Life Lost (not ship destroyed — the "next life" moment)

**Filename:** `life_lost.wav`  
**Triggered:** After player explosion animation finishes and a life is deducted  
**Description:** Descending sad melody — 3 notes going down

**sfxr.me settings:**
- Click preset: **"Hit/Hurt"** → Tweaks:
  - Wave Shape: Square
  - Start Frequency: 0.4
  - Slide: −0.35
  - Duration: 0.8 seconds
  - More musical than explosive

**Alternative:** Record 3 descending notes in Audacity (free) at pitches B4 → G4 → E4.

---

### 8.10 — Level Clear Fanfare

**Filename:** `level_clear.wav`  
**Triggered:** When all aliens are destroyed and the level completes  
**Description:** Ascending happy melody — 4-5 notes going up

**sfxr.me settings:**
- Click preset: **"Powerup"** several times until you get an ascending arpeggio
- Duration: ~1.5 seconds
- Should feel triumphant, not just a blip

**Alternative:** Use Audacity or BeepBox.co (free browser chiptune composer) to make a 4-note ascending melody.

---

### 8.11 — Game Over Sound

**Filename:** `game_over.wav`  
**Triggered:** When lives reach 0 or aliens reach the bottom  
**Description:** Dramatic descending tone — the death knell

**sfxr.me settings:**
- Click preset: **"Explosion"** → Tweaks:
  - Wave Shape: Sine or Square
  - Start Frequency: 0.35
  - Slide: −0.45 (strong pitch drop)
  - Duration: 1.5 seconds
  - Low pass filter enabled

---

---

## SECTION 9 — MUSIC TRACKS

> Music files go in `sounds/` folder alongside SFX. Use `.wav` or `.mp3` format.

---

### 9.1 — Intro / Menu Music

**Filename:** `music_intro.wav`  
**Loop:** Yes — plays continuously on `IntroWorld`  
**Style:** Calm, mysterious, spacey. Not aggressive. Sets up the atmosphere.  
**Duration:** At least 30 seconds (loops seamlessly)

**Free sources:**
- [incompetech.com](https://incompetech.com) → Search: **"Galactic Rap"** or **"Space Fighter"** (8-bit pack)
- [opengameart.org](https://opengameart.org) → Search: `chiptune space menu loop`

**Suno.ai prompt (AI music generation):**
```
Chiptune retro 8-bit space video game menu theme, mysterious and atmospheric, 
square wave melody, simple arpeggiated bass, 120 BPM, loopable, 
no drums, spacey feel, NES/Game Boy style, 30 seconds
```

---

### 9.2 — Level 1 Gameplay Music

**Filename:** `music_level1.wav`  
**Loop:** Yes — plays continuously during Level 1  
**Style:** Upbeat chiptune, driving rhythm, building tension. Classic arcade feel.  
**Duration:** 60+ seconds (seamless loop)

**Free sources:**
- [incompetech.com](https://incompetech.com) → Search: **"Pac-Man Style"** or **"Retro Frantic"**
- [opengameart.org](https://opengameart.org) → Search: `chiptune arcade loop upbeat`
- [freemusicarchive.org](https://freemusicarchive.org) → Filter: Chiptune

**Suno.ai prompt:**
```
Retro 8-bit chiptune arcade game background music, upbeat and tense, 
driving square wave melody, fast arpeggiated bass, energetic drum machine, 
140 BPM, loopable seamlessly, NES space shooter game style, 60 seconds
```

---

### 9.3 — Level 2 Gameplay Music

**Filename:** `music_level2.wav`  
**Loop:** Yes — plays continuously during Level 2  
**Style:** Same energy as Level 1 but FASTER and more menacing. Higher stakes feel.  
**Duration:** 60+ seconds

**Suno.ai prompt:**
```
Retro 8-bit chiptune arcade game background music, more intense and faster than level 1, 
aggressive square wave lead melody, rapid arpeggiated bass, driving drum machine, 
160 BPM, darker and more urgent mood, loopable, NES space shooter style, 60 seconds
```

---

### 9.4 — Game Over Music

**Filename:** `music_gameover.wav`  
**Loop:** No — plays once on `GameOverWorld`  
**Style:** Short melancholy 4-bar phrase. Sad but not too long.  
**Duration:** ~6-8 seconds

**Free sources:**
- [freesound.org](https://freesound.org) → Search: `chiptune game over`
- [opengameart.org](https://opengameart.org) → Search: `8-bit game over`

**Suno.ai prompt:**
```
Short retro 8-bit chiptune game over jingle, melancholy descending melody, 
4 bars, square wave, slow and sad, arcade game over screen music, 8 seconds, no loop
```

---

### 9.5 — Win / Mission Complete Music (optional)

**Filename:** `music_win.wav`  
**Loop:** No — plays once  
**Duration:** ~5 seconds

**Suno.ai prompt:**
```
Short retro 8-bit chiptune victory jingle, triumphant ascending melody, 
4 bars, square wave arpeggio going up, happy and celebratory, 
arcade game victory screen, 5 seconds, no loop
```

---

---

## SECTION 10 — COMPLETE FILE CHECKLIST

Use this to tick off each asset as you generate/download it.

### Sprites (`images/` folder)

```
PLAYER
[ ] player_ship_0.png          (48×28 — engine off)
[ ] player_ship_1.png          (48×28 — engine glow)
[ ] player_life_icon.png       (24×14 — HUD icon)
[ ] player_bullet.png          (4×16 — laser bolt)

EASY ALIEN (Level 1)
[ ] easy_alien_0.png           (32×32 — tentacles down)
[ ] easy_alien_1.png           (32×32 — tentacles spread)

EASY ALIEN (Level 2)
[ ] easy_alien_l2_0.png        (32×32 — purple variant frame 0)
[ ] easy_alien_l2_1.png        (32×32 — purple variant frame 1)

MID ALIEN (Level 1)
[ ] mid_alien_0.png            (32×32 — claws closed)
[ ] mid_alien_1.png            (32×32 — claws open)

MID ALIEN (Level 2)
[ ] mid_alien_l2_0.png         (32×32 — orange variant frame 0)
[ ] mid_alien_l2_1.png         (32×32 — orange variant frame 1)

HARD ALIEN (Level 1)
[ ] hard_alien_0.png           (32×32 — antennae up)
[ ] hard_alien_1.png           (32×32 — antennae out)

HARD ALIEN (Level 2)
[ ] hard_alien_l2_0.png        (32×32 — red variant frame 0)
[ ] hard_alien_l2_1.png        (32×32 — red variant frame 1)

MYSTERY SHIP
[ ] mystery_ship_0.png         (56×22 — lights dim)
[ ] mystery_ship_1.png         (56×22 — lights bright)

ALIEN BOMB
[ ] alien_bomb_0.png           (6×18 — leans left)
[ ] alien_bomb_1.png           (6×18 — leans right)

EXPLOSIONS
[ ] explosion_alien_0.png      (40×40 — flash)
[ ] explosion_alien_1.png      (40×40 — expanding ring)
[ ] explosion_alien_2.png      (40×40 — orange debris)
[ ] explosion_alien_3.png      (40×40 — fading embers)

[ ] explosion_player_0.png     (64×48 — white flash)
[ ] explosion_player_1.png     (64×48 — fireball + ship)
[ ] explosion_player_2.png     (64×48 — orange fireball)
[ ] explosion_player_3.png     (64×48 — smoke + debris)
[ ] explosion_player_4.png     (64×48 — fading smoke)

[ ] explosion_mystery_0.png    (56×28 — flash)
[ ] explosion_mystery_1.png    (56×28 — fireball)
[ ] explosion_mystery_2.png    (56×28 — debris)
[ ] explosion_mystery_3.png    (56×28 — fading)

BACKGROUNDS
[ ] bg_intro.png               (800×600 — menu starfield)
[ ] bg_level1.png              (800×600 — dark space) [OR draw in code]
[ ] bg_level2.png              (800×600 — moon surface)
[ ] bg_gameover.png            (800×600 — dark red)

LEVEL 2 TERRAIN
[ ] terrain_moon.png           (800×80 — lunar ground strip)

UI TEXT (optional — can draw with code instead)
[ ] text_gameover.png          (400×60)
[ ] text_levelclear.png        (400×60)
```

### Sounds (`sounds/` folder)

```
SOUND EFFECTS
[ ] laser.wav                  (player fires)
[ ] alien_explode.wav          (alien killed)
[ ] player_hit.wav             (player destroyed)
[ ] alien_step_1.wav           (march step 1)
[ ] alien_step_2.wav           (march step 2)
[ ] alien_step_3.wav           (march step 3)
[ ] alien_step_4.wav           (march step 4)
[ ] alien_shoot.wav            (alien fires bomb)
[ ] bunker_hit.wav             (bunker tile removed)
[ ] mystery_ship_loop.wav      (UFO appears - looping)
[ ] mystery_ship_hit.wav       (UFO destroyed)
[ ] life_lost.wav              (after player death)
[ ] level_clear.wav            (all aliens killed)
[ ] game_over.wav              (lives = 0)

MUSIC
[ ] music_intro.wav            (menu screen - loops)
[ ] music_level1.wav           (level 1 gameplay - loops)
[ ] music_level2.wav           (level 2 gameplay - loops)
[ ] music_gameover.wav         (game over screen - once)
[ ] music_win.wav              (victory screen - once)
```

---

## SECTION 11 — QUICK GENERATION WORKFLOW

Follow this order to generate everything efficiently in one session:

```
SESSION 1 — Core sprites (30-45 min)
  1. Generate player_ship_0 and _1 (most important sprite)
  2. Generate all 3 alien types × 2 frames = 6 sprites
  3. Generate mystery_ship_0 and _1
  4. Generate alien_bomb_0 and _1
  ── Upload all 12 to remove.bg for background removal ──

SESSION 2 — Explosions (20-30 min)
  5. Generate explosion_alien sheet (all 4 frames in one prompt)
  6. Generate explosion_player sheet (all 5 frames in one prompt)
  7. Generate explosion_mystery sheet (4 frames)
  ── Slice sprite sheets into individual files with any image editor ──

SESSION 3 — Backgrounds (15-20 min)
  8. Generate bg_level1 (or code it — faster)
  9. Generate bg_level2 (most important — unique lunar look)
  10. Generate bg_gameover

SESSION 4 — Level 2 alien color variants (20 min)
  11. Re-run each alien prompt with new color instruction
  OR: Open the Level 1 sprites in any editor, use Hue/Saturation shift

SESSION 5 — Sounds (20 min)
  12. Open sfxr.me → generate all 10 sound effects
  13. Download music from incompetech.com (search & download = 10 min)

TOTAL ESTIMATED TIME: ~90-120 minutes
```

---

## SECTION 12 — FREE ALTERNATIVE PACKS (If AI Generation Fails)

If you can't generate sprites with AI, these free packs cover almost everything:

| Pack | URL | What it contains |
|---|---|---|
| Space Shooter Redux (Kenney) | kenney.nl/assets/space-shooter-redux | Ships, aliens, bullets, explosions |
| Space Shooter Extension (Kenney) | kenney.nl/assets/space-shooter-extension | More ships and enemies |
| Pixel Explosion Pack | opengameart.org/content/pixel-explosion-12-frames | Explosions 12-frame |
| Space Invaders Sprites | opengameart.org/content/space-invaders-0 | Classic Space Invaders exact replicas |
| 512 Sound Effects (Kenney) | kenney.nl/assets/512-sound-effects | All SFX needed in one pack |
| Chiptune Pack | opengameart.org/content/chiptune-pack | Music loops |

**License:** All Kenney assets are **CC0** (completely free, no attribution required). OpenGameArt is mixed — check each asset's license. Most are CC-BY (require attribution in report — which you need to do anyway).
