package com.mygdsx.game.characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.mygdsx.game.MyGdxGame;
import com.mygdsx.game.config.GameConfig;

public class Bird {
    private float x;
    private float y;

    private final float width;
    private final float height;
    private float verticalSpeed;
    private final Texture[] framesArray = new Texture[]{
        new Texture("birdTiles/bird0.png"),
        new Texture("birdTiles/bird1.png"),
        new Texture("birdTiles/bird2.png"),
        new Texture("birdTiles/bird1.png")
    };
    private int frameCounter;

    public Bird(int x, int y, float width, float height) {
        this.x = x;
        this.y = y;
        frameCounter = 0;
        this.width = width;
        this.height = height;
    }

    public void fly(float delta) {
        verticalSpeed -= GameConfig.BIRD_GRAVITY * delta;
        y += verticalSpeed * delta;
    }

    public void onClick() {
        verticalSpeed = (float) Math.sqrt(
            2f * GameConfig.BIRD_GRAVITY * GameConfig.BIRD_MAX_JUMP_HEIGHT
        );
    }

    public void setY(float y) {
        this.y = y;
    }

    float left() {
        return x;
    }

    float hitboxLeft() {
        return x + width * (1f - GameConfig.BIRD_HITBOX_SCALE) / 2f;
    }

    float hitboxRight() {
        return hitboxLeft() + width * GameConfig.BIRD_HITBOX_SCALE;
    }

    float hitboxBottom() {
        return y + height * (1f - GameConfig.BIRD_HITBOX_SCALE) / 2f;
    }

    float hitboxTop() {
        return hitboxBottom() + height * GameConfig.BIRD_HITBOX_SCALE;
    }

    public boolean isInField() {
        if (y + height < 0) {
            return false;
        }
        return !(y > MyGdxGame.SCR_HEIGHT);
    }

    public void draw(Batch batch) {
        draw(batch, true);
    }

    public void draw(Batch batch, boolean animate) {
        int frameMultiplier = 10;
        batch.draw(framesArray[frameCounter / frameMultiplier], x, y, width, height);
        if (animate && frameCounter++ == framesArray.length * frameMultiplier - 1) {
            frameCounter = 0;
        }
    }

    public void dispose() {
        for (Texture frame : framesArray) {
            frame.dispose();
        }
    }
}
