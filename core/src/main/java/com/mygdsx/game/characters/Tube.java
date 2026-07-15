package com.mygdsx.game.characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.mygdsx.game.MyGdxGame;
import com.mygdsx.game.config.GameConfig;

import java.util.Random;

public class Tube {

    private final Texture textureUpperTube;
    private final Texture textureDownTube;

    private final int width = 200;
    private final int height = 700;
    private final int gapHeight = 400;
    private final int padding = 50;
    private int gapY;
    private final int distanceBetweenTubes;
    private final Random random;
    private float x;
    private boolean isPointReceived;

    public Tube(int tubeCount, int tubeIdx) {
        random = new Random();
        gapY = gapHeight / 2 + padding
            + random.nextInt(MyGdxGame.SCR_HEIGHT - 2 * (padding + gapHeight / 2));
        distanceBetweenTubes = (MyGdxGame.SCR_WIDTH + width) / (tubeCount - 1);
        x = distanceBetweenTubes * tubeIdx + MyGdxGame.SCR_WIDTH;
        isPointReceived = false;

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
        x -= GameConfig.TUBE_SPEED;
        if (x < -width) {
            x = MyGdxGame.SCR_WIDTH + distanceBetweenTubes;
            isPointReceived = false;
            gapY = gapHeight / 2 + padding
                + random.nextInt(MyGdxGame.SCR_HEIGHT - 2 * (padding + gapHeight / 2));
        }
    }

    public boolean isPassed(Bird bird) {
        return bird.x > x + width && !isPointReceived;
    }

    public boolean needAddPoint(Bird bird) {
        return isPassed(bird);
    }

    public void setPointReceived() {
        isPointReceived = true;
    }

    public boolean isHit(Bird bird) {
        boolean intersectsByX = bird.hitboxRight() >= x
            && bird.hitboxLeft() <= x + width;

        boolean hitsDownTube = bird.hitboxBottom() <= gapY - gapHeight / 2f;
        boolean hitsUpperTube = bird.hitboxTop()
            >= gapY + gapHeight / 2f;

        return intersectsByX && (hitsDownTube || hitsUpperTube);
    }

    public void dispose() {
        textureUpperTube.dispose();
        textureDownTube.dispose();
    }
}
