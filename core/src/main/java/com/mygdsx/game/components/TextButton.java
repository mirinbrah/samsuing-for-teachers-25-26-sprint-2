package com.mygdsx.game.components;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

public class TextButton {

    private final BitmapFont font;
    private final String text;
    private final Texture texture;
    private final int x;
    private final int y;
    private final int textX;
    private final int textY;
    private final int buttonWidth;
    private final int buttonHeight;

    public TextButton(int x, int y, String text) {
        this(x, y, text, 1f);
    }

    public TextButton(int x, int y, String text, float buttonScale) {
        this.text = text;
        this.x = x;
        this.y = y;

        font = new BitmapFont();
        font.getRegion().getTexture().setFilter(
            Texture.TextureFilter.Linear,
            Texture.TextureFilter.Linear
        );
        font.getData().setScale(5f * buttonScale);
        font.setColor(Color.WHITE);

        GlyphLayout gl = new GlyphLayout(font, text);
        int textWidth = (int) gl.width;
        int textHeight = (int) gl.height;

        texture = new Texture("ui/button_bg.png");
        buttonWidth = (int) (texture.getWidth() * buttonScale);
        buttonHeight = (int) (texture.getHeight() * buttonScale);

        textX = x + (buttonWidth - textWidth) / 2;
        textY = y + (buttonHeight + textHeight) / 2;
    }

    public void draw(Batch batch) {
        batch.draw(texture, x, y, buttonWidth, buttonHeight);
        font.draw(batch, text, textX, textY);
    }

    public boolean isHit(int tx, int ty) {
        return tx >= x
            && tx <= x + buttonWidth
            && ty >= y
            && ty <= y + buttonHeight;
    }

    public void dispose() {
        texture.dispose();
        font.dispose();
    }
}
