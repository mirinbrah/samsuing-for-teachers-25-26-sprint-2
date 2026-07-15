package com.mygdsx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdsx.game.MyGdxGame;
import com.mygdsx.game.components.MovingBackground;
import com.mygdsx.game.components.PointCounter;
import com.mygdsx.game.components.TextButton;

public class ScreenRestart implements Screen {

    private final PointCounter pointCounter;
    private int gamePoints;

    private final MyGdxGame myGdxGame;
    private final MovingBackground background;
    private final TextButton buttonRestart;
    private final TextButton buttonMenu;

    public ScreenRestart(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;
        pointCounter = new PointCounter(750, 530);
        buttonRestart = new TextButton(100, 400, "Restart");
        buttonMenu = new TextButton(100, 200, "Menu");
        background = new MovingBackground("backgrounds/restart_bg.png");
    }

    void setGamePoints(int gamePoints) {
        this.gamePoints = gamePoints;
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

            if (buttonRestart.isHit(touchX, touchY)) {
                myGdxGame.setScreen(myGdxGame.screenGame);
            } else if (buttonMenu.isHit(touchX, touchY)) {
                myGdxGame.setScreen(myGdxGame.screenMenu);
            }
        }

        ScreenUtils.clear(1, 0, 0, 1);
        myGdxGame.camera.update();
        myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);
        myGdxGame.batch.begin();
        background.draw(myGdxGame.batch);
        buttonRestart.draw(myGdxGame.batch);
        buttonMenu.draw(myGdxGame.batch);
        pointCounter.draw(myGdxGame.batch, gamePoints);
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
        buttonRestart.dispose();
        buttonMenu.dispose();
        pointCounter.dispose();
    }
}
