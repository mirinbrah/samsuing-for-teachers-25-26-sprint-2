package com.mygdsx.game.characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.mygdsx.game.MyGdxGame;
import com.mygdsx.game.config.GameConfig;

import java.util.Random;

public class Tube {

    private final Texture textureUpperTube;
    private final Texture textureDownTube;

    private static final int WIDTH = 200;
    private static final int HEIGHT = 700;
    private static final int PADDING = 50;
    private int gapY;
    private final int distanceBetweenTubes;
    private final Random random;
    private float x;
    private boolean isPointReceived;

    public Tube(int tubeCount, int tubeIdx) {
        random = new Random();
        gapY = GameConfig.TUBE_GAP_HEIGHT / 2 + PADDING
            + random.nextInt(
                MyGdxGame.SCR_HEIGHT
                    - 2 * (PADDING + GameConfig.TUBE_GAP_HEIGHT / 2)
            );
        distanceBetweenTubes = (MyGdxGame.SCR_WIDTH + WIDTH) / (tubeCount - 1);
        x = distanceBetweenTubes * tubeIdx + MyGdxGame.SCR_WIDTH;
        isPointReceived = false;

        textureUpperTube = new Texture("tubes/tube_flipped.png");
        textureDownTube = new Texture("tubes/tube.png");
    }

    public void draw(Batch batch) {
        float downTubeY = gapY - GameConfig.TUBE_GAP_HEIGHT / 2f - HEIGHT;
        float upperTubeY = gapY + GameConfig.TUBE_GAP_HEIGHT / 2f;

        batch.draw(textureDownTube, x, downTubeY, WIDTH, HEIGHT);
        batch.draw(textureUpperTube, x, upperTubeY, WIDTH, HEIGHT);
    }

    public void move() {
        x -= GameConfig.TUBE_SPEED;
        if (x < -WIDTH) {
            x = MyGdxGame.SCR_WIDTH + distanceBetweenTubes;
            isPointReceived = false;
            gapY = GameConfig.TUBE_GAP_HEIGHT / 2 + PADDING
                + random.nextInt(
                    MyGdxGame.SCR_HEIGHT
                        - 2 * (PADDING + GameConfig.TUBE_GAP_HEIGHT / 2)
                );
        }
    }

    private boolean isPassed(Bird bird) {
        return bird.left() > x + WIDTH && !isPointReceived;
    }

    public boolean needAddPoint(Bird bird) {
        return isPassed(bird);
    }

    public void setPointReceived() {
        isPointReceived = true;
    }

    public boolean isHit(Bird bird) {
        boolean intersectsByX = bird.hitboxRight() >= x
            && bird.hitboxLeft() <= x + WIDTH;

        boolean hitsDownTube = bird.hitboxBottom()
            <= gapY - GameConfig.TUBE_GAP_HEIGHT / 2f;
        boolean hitsUpperTube = bird.hitboxTop()
            >= gapY + GameConfig.TUBE_GAP_HEIGHT / 2f;

        return intersectsByX && (hitsDownTube || hitsUpperTube);
    }

    public void dispose() {
        textureUpperTube.dispose();
        textureDownTube.dispose();
    }
}
