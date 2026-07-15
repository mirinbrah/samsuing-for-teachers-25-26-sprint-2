package com.mygdsx.game.characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.mygdsx.game.MyGdxGame;

public class Bird {
    private static final float MAX_HEIGHT_OF_JUMP = 200f;
    private static final float GRAVITY = 900f;

    float x;
    float y;

    final float width;
    final float height;
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
        verticalSpeed -= GRAVITY * delta;
        y += verticalSpeed * delta;
    }

    public void onClick() {
        verticalSpeed = (float) Math.sqrt(2f * GRAVITY * MAX_HEIGHT_OF_JUMP);
    }

    public void setY(float y) {
        this.y = y;
    }

    public boolean isInField() {
        if (y + height < 0) {
            return false;
        }
        return !(y > MyGdxGame.SCR_HEIGHT);
    }

    public void draw(Batch batch) {
        int frameMultiplier = 10;
        batch.draw(framesArray[frameCounter / frameMultiplier], x, y, width, height);
        if (frameCounter++ == framesArray.length * frameMultiplier - 1) frameCounter = 0;
    }

    public void dispose() {
        for (Texture frame : framesArray) {
            frame.dispose();
        }
    }
}
