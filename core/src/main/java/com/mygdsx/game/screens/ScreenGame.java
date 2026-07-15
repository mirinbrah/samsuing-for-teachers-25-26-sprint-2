package com.mygdsx.game.screens;

import static com.mygdsx.game.MyGdxGame.SCR_HEIGHT;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdsx.game.MyGdxGame;
import com.mygdsx.game.characters.Bird;
import com.mygdsx.game.characters.Tube;
import com.mygdsx.game.components.MovingBackground;
import com.mygdsx.game.components.PointCounter;
import com.mygdsx.game.components.TextButton;
import com.mygdsx.game.config.GameConfig;

public class ScreenGame implements Screen {

    private static final int POINT_COUNTER_MARGIN_TOP = 60;
    private static final int POINT_COUNTER_MARGIN_RIGHT = 400;
    private static final int PAUSE_BUTTON_X = 20;
    private static final int PAUSE_BUTTON_Y = 20;
    private static final String STARTER_TEXT = "Tap to Fly";
    private static final String PAUSE_TEXT = "Paused";

    private final MyGdxGame myGdxGame;
    private final MovingBackground background;
    private final Bird bird;
    private final PointCounter pointCounter;
    private final TextButton buttonPause;
    private Texture starterOverlay;
    private BitmapFont starterFont;
    private float starterTextX;
    private float starterTextY;
    private float pauseTextX;
    private float pauseTextY;
    private Tube[] tubes;
    private int gamePoints;
    private boolean isGameOver;
    private boolean isWaitingToStart;
    private boolean isPaused;

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
        buttonPause = new TextButton(PAUSE_BUTTON_X, PAUSE_BUTTON_Y, "Pause", 0.5f);

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
        starterFont.getRegion().getTexture().setFilter(
            Texture.TextureFilter.Linear,
            Texture.TextureFilter.Linear
        );
        starterFont.getData().setScale(5f);
        starterFont.setColor(Color.WHITE);
        GlyphLayout starterLayout = new GlyphLayout(starterFont, STARTER_TEXT);
        starterTextX = (MyGdxGame.SCR_WIDTH - starterLayout.width) / 2f;
        starterTextY = (MyGdxGame.SCR_HEIGHT + starterLayout.height) / 2f;
        GlyphLayout pauseLayout = new GlyphLayout(starterFont, PAUSE_TEXT);
        pauseTextX = (MyGdxGame.SCR_WIDTH - pauseLayout.width) / 2f;
        pauseTextY = (MyGdxGame.SCR_HEIGHT + pauseLayout.height) / 2f;
    }

    private void initTubes() {
        int tubeCount = 3;
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
        isPaused = false;
        bird.setY((float) SCR_HEIGHT / 2);
        initTubes();
    }

    @Override
    public void render(float delta) {
        if (isGameOver) {
            myGdxGame.screenRestart.setGamePoints(gamePoints);
            myGdxGame.setScreen(myGdxGame.screenRestart);
        }

        boolean pointerPressed = Gdx.input.justTouched();
        boolean pauseButtonPressed = false;
        if (pointerPressed && !isWaitingToStart) {
            Vector2 touch = myGdxGame.viewport.unproject(
                new Vector2(Gdx.input.getX(), Gdx.input.getY())
            );
            pauseButtonPressed = buttonPause.isHit((int) touch.x, (int) touch.y);
        }

        boolean pausePressed = Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)
            || pauseButtonPressed;
        if (!isWaitingToStart && pausePressed) {
            isPaused = !isPaused;
        }

        if (isPaused) {
            renderGame(true);
            return;
        }

        boolean flyPressed = (pointerPressed && !pauseButtonPressed)
            || Gdx.input.isKeyJustPressed(Input.Keys.SPACE);
        if (isWaitingToStart) {
            if (flyPressed) {
                isWaitingToStart = false;
                bird.onClick();
            } else {
                renderStarter();
                return;
            }
        } else if (flyPressed) {
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

        renderGame(false);
    }

    private void renderGame(boolean paused) {
        ScreenUtils.clear(1, 0, 0, 1);
        myGdxGame.camera.update();
        myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);
        myGdxGame.batch.begin();
        background.draw(myGdxGame.batch);
        for (Tube tube : tubes) {
            tube.draw(myGdxGame.batch);
        }
        bird.draw(myGdxGame.batch, !paused);
        pointCounter.draw(myGdxGame.batch, gamePoints);
        if (paused) {
            myGdxGame.batch.setColor(1f, 1f, 1f, 0.65f);
            myGdxGame.batch.draw(
                starterOverlay,
                0,
                0,
                MyGdxGame.SCR_WIDTH,
                MyGdxGame.SCR_HEIGHT
            );
            myGdxGame.batch.setColor(Color.WHITE);
            starterFont.draw(myGdxGame.batch, PAUSE_TEXT, pauseTextX, pauseTextY);
        }
        buttonPause.draw(myGdxGame.batch);
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
        buttonPause.dispose();
        starterOverlay.dispose();
        starterFont.dispose();
        for (Tube tube : tubes) {
            tube.dispose();
        }
    }
}
