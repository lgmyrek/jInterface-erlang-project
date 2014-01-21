package com.jinterfacegame.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jinterfacegame.GameClient;
import com.jinterfacegame.utils.Assets;
import com.jinterfacegame.utils.GameInput;

/**
 * @author Lukasz Gmyrek <lukasz.gmyrek@gmail.com>.
 */
public class DeathScreen implements Screen {
    private final GameClient game;
    private SpriteBatch spriteBatch;
    private GameInput input;

    private final BitmapFont font;
    public static final Color FONTCOLOR = Color.WHITE;

    public static final String deathMessage = "Unfortunately you died";
    public static final String clickToRespawnMessage = "click enter to respawn";

    public DeathScreen(GameClient game) {
        this.game = game;
        this.spriteBatch = new SpriteBatch();
        this.input = game.getInput();
        this.font = Assets.getInstance().font;
        this.font.setColor(FONTCOLOR);
        this.font.setScale(3);
    }

    @Override
    public void render(float delta) {
        tryToRespawn();
        input.reset();
        spriteBatch.begin();
        spriteBatch.draw(Assets.getInstance().titleBg, 0, 0, GameClient.WIDTH, GameClient.HEIGHT);
        font.setScale(2);
        font.draw(spriteBatch, deathMessage, (GameClient.WIDTH - font.getBounds(deathMessage).width) / 2, GameClient.HEIGHT - 50);
        font.setScale(1);
        font.draw(spriteBatch, clickToRespawnMessage, (GameClient.WIDTH - font.getBounds(clickToRespawnMessage).width) / 2, GameClient.HEIGHT - 100);
        spriteBatch.end();
    }

    private void tryToRespawn() {
        if (input.enterOnce) {
            game.setScreen(new GameScreen(game));
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void show() {

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

    }
}
