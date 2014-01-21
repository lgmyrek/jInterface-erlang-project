package com.jinterfacegame;

import com.badlogic.gdx.Game;
import com.jinterfacegame.entities.PoolAggregate;
import com.jinterfacegame.screens.TitleScreen;
import com.jinterfacegame.utils.Assets;
import com.jinterfacegame.utils.ErlangProxy;
import com.jinterfacegame.utils.GameInput;
import com.jinterfacegame.utils.State;

public class GameClient extends Game {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    public static final String TITLE = "JInterface_Game_Client";

    private GameInput input;

    private ErlangProxy proxy;
    private String ID;


    public GameClient() {
    }

    @Override
    public void create() {
        this.input = new GameInput();
        setScreen(new TitleScreen(this));
    }

    public void runProxy(String server) {
        proxy = new ErlangProxy(server);
        ID = proxy.getPid().toString();
        Thread proxyThread = new Thread(proxy);
        proxyThread.start();
    }

    @Override
    public void dispose() {
        Assets.getInstance().dispose();
        input.dispose();
        super.dispose();
    }

    public GameInput getInput() {
        return input;
    }

    public String getID() {
        return ID;
    }

    public ErlangProxy getProxy() {
        return proxy;
    }

    public State getState() {
        if (proxy.getPureState() != null)
            return PoolAggregate.getInstance().getStatePool().obtain().setState(proxy.getPureState());
        return null;
    }
}
