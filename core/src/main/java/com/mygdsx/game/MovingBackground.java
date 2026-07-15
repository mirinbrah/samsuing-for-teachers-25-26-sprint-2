package com.mygdsx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public class MovingBackground {

    private final Texture baseTexture;
    private final Texture cloudsTexture;
    private final Texture bushesTexture;

    private int clouds1X;
    private int clouds2X;
    private int bushes1X;
    private int bushes2X;

    private final int cloudsSpeed = 1;
    private final int bushesSpeed = 3;

    MovingBackground() {
        clouds1X = 0;
        clouds2X = MyGdxGame.SCR_WIDTH;
        bushes1X = 0;
        bushes2X = MyGdxGame.SCR_WIDTH;

        baseTexture = new Texture("game_bg.png");
        cloudsTexture = new Texture("game_bg_clouds.png");
        bushesTexture = new Texture("game_bg_bushes.png");
    }

    void move() {
        clouds1X -= cloudsSpeed;
        clouds2X -= cloudsSpeed;
        bushes1X -= bushesSpeed;
        bushes2X -= bushesSpeed;

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

    void draw(Batch batch) {
        batch.draw(baseTexture, 0, 0, MyGdxGame.SCR_WIDTH, MyGdxGame.SCR_HEIGHT);

        batch.draw(cloudsTexture, clouds1X, 0,
            MyGdxGame.SCR_WIDTH + 2, MyGdxGame.SCR_HEIGHT);
        batch.draw(cloudsTexture, clouds2X, 0,
            MyGdxGame.SCR_WIDTH + 2, MyGdxGame.SCR_HEIGHT);

        batch.draw(bushesTexture, bushes1X, 0,
            MyGdxGame.SCR_WIDTH + 2, MyGdxGame.SCR_HEIGHT);
        batch.draw(bushesTexture, bushes2X, 0,
            MyGdxGame.SCR_WIDTH + 2, MyGdxGame.SCR_HEIGHT);
    }

    void dispose() {
        baseTexture.dispose();
        cloudsTexture.dispose();
        bushesTexture.dispose();
    }
}
