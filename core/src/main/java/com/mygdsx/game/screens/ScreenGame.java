package com.mygdsx.game.screens;

import static com.mygdsx.game.MyGdxGame.SCR_HEIGHT;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdsx.game.MyGdxGame;
import com.mygdsx.game.characters.Bird;
import com.mygdsx.game.characters.Tube;
import com.mygdsx.game.components.MovingBackground;
import com.mygdsx.game.components.PointCounter;
import com.mygdsx.game.config.GameConfig;

public class ScreenGame implements Screen {

    private static final int POINT_COUNTER_MARGIN_TOP = 60;
    private static final int POINT_COUNTER_MARGIN_RIGHT = 400;
    private static final String STARTER_TEXT = "Tap to Fly";

    private final MyGdxGame myGdxGame;
    private final MovingBackground background;
    private final Bird bird;
    private final PointCounter pointCounter;
    private Texture starterOverlay;
    private BitmapFont starterFont;
    private float starterTextX;
    private float starterTextY;
    private final int tubeCount = 3;
    private Tube[] tubes;
    private int gamePoints;
    private boolean isGameOver;
    private boolean isWaitingToStart;

    public ScreenGame(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;
        background = new MovingBackground(
            "backgrounds/game_bg.png",
            "backgrounds/game_bg_clouds.png",
            "backgrounds/game_bg_bushes.png"
        );
        bird = new Bird(
            20,
            SCR_HEIGHT / 2,
            GameConfig.BIRD_WIDTH,
            GameConfig.BIRD_HEIGHT
        );
        pointCounter = new PointCounter(
            MyGdxGame.SCR_WIDTH - POINT_COUNTER_MARGIN_RIGHT,
            MyGdxGame.SCR_HEIGHT - POINT_COUNTER_MARGIN_TOP
        );

        initStarter();
        initTubes();
    }

    private void initStarter() {
        Pixmap overlayPixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        overlayPixmap.setColor(Color.BLACK);
        overlayPixmap.fill();
        starterOverlay = new Texture(overlayPixmap);
        overlayPixmap.dispose();

        starterFont = new BitmapFont();
        starterFont.getData().setScale(5f);
        starterFont.setColor(Color.WHITE);
        GlyphLayout starterLayout = new GlyphLayout(starterFont, STARTER_TEXT);
        starterTextX = (MyGdxGame.SCR_WIDTH - starterLayout.width) / 2f;
        starterTextY = (MyGdxGame.SCR_HEIGHT + starterLayout.height) / 2f;
    }

    private void initTubes() {
        tubes = new Tube[tubeCount];
        for (int i = 0; i < tubeCount; i++) {
            tubes[i] = new Tube(tubeCount, i);
        }
    }

    @Override
    public void show() {
        Gdx.input.setOnscreenKeyboardVisible(false);
        gamePoints = 0;
        isGameOver = false;
        isWaitingToStart = true;
        bird.setY(SCR_HEIGHT / 2);
        initTubes();
    }

    @Override
    public void render(float delta) {
        if (isGameOver) {
            myGdxGame.screenRestart.gamePoints = gamePoints;
            myGdxGame.setScreen(myGdxGame.screenRestart);
        }

        boolean justTouched = Gdx.input.justTouched();
        if (isWaitingToStart) {
            if (justTouched) {
                isWaitingToStart = false;
                bird.onClick();
            } else {
                renderStarter();
                return;
            }
        } else if (justTouched) {
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

    private void renderStarter() {
        ScreenUtils.clear(1, 0, 0, 1);
        myGdxGame.camera.update();
        myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);
        myGdxGame.batch.begin();
        background.draw(myGdxGame.batch);
        myGdxGame.batch.setColor(1f, 1f, 1f, 0.65f);
        myGdxGame.batch.draw(
            starterOverlay,
            0,
            0,
            MyGdxGame.SCR_WIDTH,
            MyGdxGame.SCR_HEIGHT
        );
        myGdxGame.batch.setColor(Color.WHITE);
        starterFont.draw(myGdxGame.batch, STARTER_TEXT, starterTextX, starterTextY);
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
        starterOverlay.dispose();
        starterFont.dispose();
        for (Tube tube : tubes) {
            tube.dispose();
        }
    }
}
