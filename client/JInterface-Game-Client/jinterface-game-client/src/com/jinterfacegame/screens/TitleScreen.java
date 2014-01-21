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
public class TitleScreen implements Screen {
    private final GameClient game;
    private SpriteBatch spriteBatch;
    private GameInput input;
    public final static String message = "Erlang Space Simulator";
    public final static String secondMessage = "NASA approved and sh!t";
    public final static String writeServerAddressMessage = "Server Addres:";
    private StringBuilder serverAddress = new StringBuilder();

    private final BitmapFont font;
    public static final Color FONTCOLOR = Color.WHITE;

    public TitleScreen(GameClient game) {
        this.game = game;
        this.spriteBatch = new SpriteBatch();
        this.input = game.getInput();
        this.font = Assets.getInstance().font;
        font.setColor(FONTCOLOR);
    }

    @Override
    public void render(float delta) {
        getServerAddress();
        checkForEnter();
        input.reset();
        spriteBatch.begin();
        spriteBatch.draw(Assets.getInstance().titleBg, 0, 0, GameClient.WIDTH, GameClient.HEIGHT);
        font.setScale(2);
        font.draw(spriteBatch, message, (GameClient.WIDTH - font.getBounds(message).width) / 2, GameClient.HEIGHT - 50);
        font.setScale(1);
        font.draw(spriteBatch, secondMessage, (GameClient.WIDTH - font.getBounds(secondMessage).width) / 2, GameClient.HEIGHT - 100);
        font.draw(spriteBatch, writeServerAddressMessage, (GameClient.WIDTH - font.getBounds(writeServerAddressMessage).width) / 2, GameClient.HEIGHT - 170);
        font.draw(spriteBatch, serverAddress.toString(), (GameClient.WIDTH - font.getBounds(serverAddress.toString()).width) / 2, GameClient.HEIGHT - 200);
        spriteBatch.end();
    }

    private void getServerAddress() {
        if (input.dotOnce) serverAddress.append(".");
        if (input.backspaceOnce && serverAddress.length() > 0) serverAddress.deleteCharAt(serverAddress.length() - 1);
        if (input.oneOnce) serverAddress.append("1");
        if (input.twoOnce) serverAddress.append("2");
        if (input.threeOnce) serverAddress.append("3");
        if (input.fourOnce) serverAddress.append("4");
        if (input.fiveOnce) serverAddress.append("5");
        if (input.sixOnce) serverAddress.append("6");
        if (input.sevenOnce) serverAddress.append("7");
        if (input.eightOnce) serverAddress.append("8");
        if (input.nineOnce) serverAddress.append("9");
        if (input.zeroOnce) serverAddress.append("0");
    }

    private void checkForEnter() {
        if (input.enterOnce) {
            serverAddress.insert(0, "server@");
            game.runProxy(serverAddress.toString());
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
