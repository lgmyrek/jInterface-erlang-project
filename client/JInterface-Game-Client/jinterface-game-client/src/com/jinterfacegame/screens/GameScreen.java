package com.jinterfacegame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jinterfacegame.GameClient;
import com.jinterfacegame.utils.Assets;
import com.jinterfacegame.utils.Cooldown;
import com.jinterfacegame.utils.GameInput;
import com.jinterfacegame.utils.State;

/**
 * Created by lgmyrek on 05.01.14.
 */
public class GameScreen implements Screen {
    private final GameClient game;
    private SpriteBatch spriteBatch;
    private GameInput input;
    private String ID;
    private int deadCounter = 0;
    private FPSLogger fpsLogger;
    private State state;

    private final Cooldown fireCooldown;
    public static final double fireCooldownDuration = 200000000;

    public GameScreen(GameClient game) {
        this.game = game;
        this.input = game.getInput();
        this.spriteBatch = new SpriteBatch();
        this.ID = game.getID();
        fpsLogger = new FPSLogger();
        deadCounter = 0;
        fireCooldown = new Cooldown();
        fireCooldown.set(fireCooldownDuration);
        game.getProxy().sendSpawn();
    }

    @Override
    public void render(float delta) {
        final GL20 gl = Gdx.gl20;
        if (gl != null) {
            render(null, delta);
        }
    }

    public void render(GL20 gl, float delta) {
        fireCooldown.update();
        fpsLogger.log();
        handleMovement();
        handleDeath();

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        spriteBatch.begin();
        //Drawing
        spriteBatch.enableBlending();
        spriteBatch.draw(Assets.getInstance().spaceBg, 0, 0, game.WIDTH, game.HEIGHT);
        setState(game.getState());
        if (getState() != null) getState().draw(spriteBatch, 1);
        spriteBatch.end();
    }


    private void handleDeath() {
        if (getState() == null || !getState().isIDAlive(ID))
            if (deadCounter++ > 5) handleRealDeath();
    }

    private void handleRealDeath() {
        deadCounter = 0;
        game.setScreen(new DeathScreen(game));
    }


    private void handleMovement() {
        if (input.attack && !fireCooldown.onCooldown()) {
            game.getProxy().sendAttack();
            fireCooldown.set(fireCooldownDuration);
        }
        if (input.left) game.getProxy().sendLeft();
        if (input.right) game.getProxy().sendRight();
        if (input.forward) game.getProxy().sendForward();
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
        spriteBatch = new SpriteBatch();
    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        if (spriteBatch != null) spriteBatch.dispose();
        spriteBatch = null;
    }
}
