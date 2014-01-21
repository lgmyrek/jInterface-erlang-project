package com.jinterfacegame;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
        System.setProperty("user.name","ASCII");
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = GameClient.TITLE;
		cfg.useGL20 = true;
		cfg.width = GameClient.WIDTH;
		cfg.height = GameClient.HEIGHT;
		new LwjglApplication(new GameClient(), cfg);
	}
}
