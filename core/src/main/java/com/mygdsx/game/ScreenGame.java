package com.mygdsx.game;

import static com.mygdsx.game.MyGdxGame.SCR_HEIGHT;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.ScreenUtils;

/** Экран основного игрового процесса. */
public class ScreenGame implements Screen {

    private final MyGdxGame myGdxGame;
    private final Bird bird;
    private final int tubeCount = 3;
    private Tube[] tubes;

    public ScreenGame(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;
        bird = new Bird(20, SCR_HEIGHT / 2, 250, 200);
        initTubes();
    }

    private void initTubes() {
        tubes = new Tube[tubeCount];
        for (int i = 0; i < tubeCount; i++) {
            tubes[i] = new Tube(tubeCount, i);
        }
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        if (Gdx.input.justTouched()) {
            bird.onClick();
        }
        bird.fly(delta);
        for (Tube tube : tubes) {
            tube.move();
        }

        ScreenUtils.clear(1, 0, 0, 1);
        myGdxGame.camera.update();
        myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);
        myGdxGame.batch.begin();
        for (Tube tube : tubes) {
            tube.draw(myGdxGame.batch);
        }
        bird.draw(myGdxGame.batch);
        myGdxGame.batch.end();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        bird.dispose();
        for (Tube tube : tubes) {
            tube.dispose();
        }
    }
}
