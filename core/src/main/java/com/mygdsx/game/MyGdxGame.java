package com.mygdsx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdsx.game.screens.ScreenGame;
import com.mygdsx.game.screens.ScreenMenu;
import com.mygdsx.game.screens.ScreenRestart;

public class MyGdxGame extends Game {

    public static final int SCR_WIDTH = 1280;
    public static final int SCR_HEIGHT = 720;

    public OrthographicCamera camera;
    public StretchViewport viewport;
    public SpriteBatch batch;
    public ScreenMenu screenMenu;
    public ScreenGame screenGame;
    public ScreenRestart screenRestart;

    @Override
    public void create() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        viewport = new StretchViewport(SCR_WIDTH, SCR_HEIGHT, camera);
        viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
        screenMenu = new ScreenMenu(this);
        screenGame = new ScreenGame(this);
        screenRestart = new ScreenRestart(this);
        setScreen(screenMenu);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        super.resize(width, height);
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
