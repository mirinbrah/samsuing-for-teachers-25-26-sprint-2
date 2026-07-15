package com.mygdsx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class MyGdxGame extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture birdTexture;

    int birdX = 0;
    int birdY = 0;
    int birdSpeed = 5;

    @Override
    public void create() {
        batch = new SpriteBatch();
        birdTexture = new Texture("bird0.png");
    }

    @Override
    public void render() {
        birdX += birdSpeed;
        birdY += birdSpeed;

        ScreenUtils.clear(1, 0, 0, 1);
        batch.begin();
        batch.draw(birdTexture, birdX, birdY);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        birdTexture.dispose();
    }
}
