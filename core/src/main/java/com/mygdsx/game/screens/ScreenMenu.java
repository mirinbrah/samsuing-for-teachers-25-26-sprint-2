package com.mygdsx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdsx.game.MyGdxGame;
import com.mygdsx.game.components.MovingBackground;
import com.mygdsx.game.components.TextButton;

public class ScreenMenu implements Screen {

    private final MyGdxGame myGdxGame;
    private final MovingBackground background;
    private final TextButton buttonStart;
    private final TextButton buttonExit;

    public ScreenMenu(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;
        background = new MovingBackground("backgrounds/restart_bg.png");
        buttonStart = new TextButton(100, 400, "Start");
        buttonExit = new TextButton(100, 200, "Exit");
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        if (Gdx.input.justTouched()) {
            Vector2 touch = myGdxGame.viewport.unproject(
                new Vector2(Gdx.input.getX(), Gdx.input.getY())
            );
            int touchX = (int) touch.x;
            int touchY = (int) touch.y;

            if (buttonStart.isHit(touchX, touchY)) {
                myGdxGame.setScreen(myGdxGame.screenGame);
            } else if (buttonExit.isHit(touchX, touchY)) {
                Gdx.app.exit();
            }
        }

        ScreenUtils.clear(1, 0, 0, 1);
        myGdxGame.camera.update();
        myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);
        myGdxGame.batch.begin();
        background.draw(myGdxGame.batch);
        buttonStart.draw(myGdxGame.batch);
        buttonExit.draw(myGdxGame.batch);
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
        background.dispose();
        buttonStart.dispose();
        buttonExit.dispose();
    }
}
