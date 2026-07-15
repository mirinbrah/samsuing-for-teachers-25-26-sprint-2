package com.mygdsx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public class Bird {
    private static final float MAX_HEIGHT_OF_JUMP = 200f;
    private static final float GRAVITY = 900f;

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

    public Bird(int x, int y) {
        this.x = x;
        this.y = y;
        frameCounter = 0;
        width = framesArray[0].getWidth();
        height = framesArray[0].getHeight();
    }

    public void fly() {
        fly(Gdx.graphics.getDeltaTime());
    }

    public void fly(float delta) {
        verticalSpeed -= GRAVITY * delta;
        y += verticalSpeed * delta;
    }

    public void onClick() {
        verticalSpeed = (float) Math.sqrt(2f * GRAVITY * MAX_HEIGHT_OF_JUMP);
    }

    void draw(Batch batch) {
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
