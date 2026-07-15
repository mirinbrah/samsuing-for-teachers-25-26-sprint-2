package com.mygdsx.game.screens;

import static com.mygdsx.game.MyGdxGame.SCR_HEIGHT;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdsx.game.MyGdxGame;
import com.mygdsx.game.characters.Bird;
import com.mygdsx.game.characters.Tube;
import com.mygdsx.game.components.MovingBackground;
import com.mygdsx.game.components.PointCounter;

public class ScreenGame implements Screen {

    private static final int POINT_COUNTER_MARGIN_TOP = 60;
    private static final int POINT_COUNTER_MARGIN_RIGHT = 400;

    private final MyGdxGame myGdxGame;
    private final MovingBackground background;
    private final Bird bird;
    private final PointCounter pointCounter;
    private final int tubeCount = 3;
    private Tube[] tubes;
    private int gamePoints;
    private boolean isGameOver;

    public ScreenGame(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;
        background = new MovingBackground(
            "backgrounds/game_bg.png",
            "backgrounds/game_bg_clouds.png",
            "backgrounds/game_bg_bushes.png"
        );
        bird = new Bird(20, SCR_HEIGHT / 2, 250, 200);
        pointCounter = new PointCounter(
            MyGdxGame.SCR_WIDTH - POINT_COUNTER_MARGIN_RIGHT,
            MyGdxGame.SCR_HEIGHT - POINT_COUNTER_MARGIN_TOP
        );
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
        gamePoints = 0;
        isGameOver = false;
        bird.setY(SCR_HEIGHT / 2);
        initTubes();
    }

    @Override
    public void render(float delta) {
        if (isGameOver) {
            myGdxGame.screenRestart.gamePoints = gamePoints;
            myGdxGame.setScreen(myGdxGame.screenRestart);
        }

        if (Gdx.input.justTouched()) {
            bird.onClick();
        }
        background.move();
        bird.fly(delta);
        if (!bird.isInField()) {
            System.out.println("not in field");
            isGameOver = true;
        }
        for (Tube tube : tubes) {
            tube.move();
            if (tube.isHit(bird)) {
                System.out.println("hit");
                isGameOver = true;
            } else if (tube.needAddPoint(bird)) {
                gamePoints += 1;
                tube.setPointReceived();
                System.out.println(gamePoints);
            }
        }

        ScreenUtils.clear(1, 0, 0, 1);
        myGdxGame.camera.update();
        myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);
        myGdxGame.batch.begin();
        background.draw(myGdxGame.batch);
        for (Tube tube : tubes) {
            tube.draw(myGdxGame.batch);
        }
        bird.draw(myGdxGame.batch);
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
        bird.dispose();
        pointCounter.dispose();
        for (Tube tube : tubes) {
            tube.dispose();
        }
    }
}
