package com.mygdsx.game.components;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.mygdsx.game.MyGdxGame;

public class MovingBackground {

    private static final int CLOUDS_SPEED = 1;
    private static final int BUSHES_SPEED = 3;

    private final Texture baseTexture;
    private final Texture cloudsTexture;
    private final Texture bushesTexture;

    private int clouds1X;
    private int clouds2X;
    private int bushes1X;
    private int bushes2X;

    public MovingBackground(String pathToTexture) {
        this(pathToTexture, null, null);
    }

    public MovingBackground(String pathToTexture, String cloudsPath, String bushesPath) {
        clouds1X = 0;
        clouds2X = MyGdxGame.SCR_WIDTH;
        bushes1X = 0;
        bushes2X = MyGdxGame.SCR_WIDTH;

        baseTexture = new Texture(pathToTexture);
        cloudsTexture = cloudsPath == null ? null : new Texture(cloudsPath);
        bushesTexture = bushesPath == null ? null : new Texture(bushesPath);
    }

    public void move() {
        if (cloudsTexture == null || bushesTexture == null) {
            return;
        }

        clouds1X -= CLOUDS_SPEED;
        clouds2X -= CLOUDS_SPEED;
        bushes1X -= BUSHES_SPEED;
        bushes2X -= BUSHES_SPEED;

        if (clouds1X <= -MyGdxGame.SCR_WIDTH) {
            clouds1X = MyGdxGame.SCR_WIDTH;
        }
        if (clouds2X <= -MyGdxGame.SCR_WIDTH) {
            clouds2X = MyGdxGame.SCR_WIDTH;
        }
        if (bushes1X <= -MyGdxGame.SCR_WIDTH) {
            bushes1X = MyGdxGame.SCR_WIDTH;
        }
        if (bushes2X <= -MyGdxGame.SCR_WIDTH) {
            bushes2X = MyGdxGame.SCR_WIDTH;
        }
    }

    public void draw(Batch batch) {
        batch.draw(baseTexture, 0, 0, MyGdxGame.SCR_WIDTH, MyGdxGame.SCR_HEIGHT);

        if (cloudsTexture != null && bushesTexture != null) {
            batch.draw(cloudsTexture, clouds1X, 0,
                MyGdxGame.SCR_WIDTH + 2, MyGdxGame.SCR_HEIGHT);
            batch.draw(cloudsTexture, clouds2X, 0,
                MyGdxGame.SCR_WIDTH + 2, MyGdxGame.SCR_HEIGHT);

            batch.draw(bushesTexture, bushes1X, 0,
                MyGdxGame.SCR_WIDTH + 2, MyGdxGame.SCR_HEIGHT);
            batch.draw(bushesTexture, bushes2X, 0,
                MyGdxGame.SCR_WIDTH + 2, MyGdxGame.SCR_HEIGHT);
        }
    }

    public void dispose() {
        baseTexture.dispose();
        if (cloudsTexture != null) {
            cloudsTexture.dispose();
        }
        if (bushesTexture != null) {
            bushesTexture.dispose();
        }
    }
}
