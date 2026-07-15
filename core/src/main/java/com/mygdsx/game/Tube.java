package com.mygdsx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

import java.util.Random;

public class Tube {

    private final Texture textureUpperTube;
    private final Texture textureDownTube;

    private final int width = 200;
    private final int height = 700;
    private final int gapHeight = 400;
    private final int padding = 50;
    private final int speed = 5;
    private int gapY;
    private final int distanceBetweenTubes;
    private final Random random;
    private float x;

    public Tube(int tubeCount, int tubeIdx) {
        random = new Random();
        gapY = gapHeight / 2 + padding
            + random.nextInt(MyGdxGame.SCR_HEIGHT - 2 * (padding + gapHeight / 2));
        distanceBetweenTubes = (MyGdxGame.SCR_WIDTH + width) / (tubeCount - 1);
        x = distanceBetweenTubes * tubeIdx + MyGdxGame.SCR_WIDTH;

        textureUpperTube = new Texture("tubes/tube_flipped.png");
        textureDownTube = new Texture("tubes/tube.png");
    }

    public void draw(Batch batch) {
        float downTubeY = gapY - gapHeight / 2f - height;
        float upperTubeY = gapY + gapHeight / 2f;

        batch.draw(textureDownTube, x, downTubeY, width, height);
        batch.draw(textureUpperTube, x, upperTubeY, width, height);
    }

    public void move() {
        x -= speed;
        if (x < -width) {
            x = MyGdxGame.SCR_WIDTH + distanceBetweenTubes;
            gapY = gapHeight / 2 + padding
                + random.nextInt(MyGdxGame.SCR_HEIGHT - 2 * (padding + gapHeight / 2));
        }
    }

    public void dispose() {
        textureUpperTube.dispose();
        textureDownTube.dispose();
    }
}
