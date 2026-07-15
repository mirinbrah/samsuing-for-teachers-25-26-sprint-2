package com.mygdsx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdsx.game.screens.ScreenGame;
import com.mygdsx.game.screens.ScreenRestart;

public class MyGdxGame extends Game {

    public static final int SCR_WIDTH = 1280;
    public static final int SCR_HEIGHT = 720;

    public OrthographicCamera camera;
    public SpriteBatch batch;
    public ScreenGame screenGame;
    public ScreenRestart screenRestart;

    @Override
    public void create() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, SCR_WIDTH, SCR_HEIGHT);
        screenGame = new ScreenGame(this);
        screenRestart = new ScreenRestart(this);
        setScreen(screenGame);
    }

    @Override
    public void dispose() {
        if (screen != null) {
            screen.dispose();
        }
        batch.dispose();
        super.dispose();
    }
}
