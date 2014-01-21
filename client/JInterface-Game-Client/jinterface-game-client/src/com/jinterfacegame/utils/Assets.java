package com.jinterfacegame.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lgmyrek on 29.12.13.
 */
public class Assets implements Disposable {
    private final List<Disposable> disposables = new ArrayList<Disposable>();

    private final Texture shipTexture;
    public final TextureRegion ship;
    private static final String SHIP_FILENAME = "data/charsets/spaceshipc.png";

    private final Texture projectileTexture;
    public final TextureRegion projectile;
    private static final String PROJECTILE_FILENAME = "data/charsets/shot.png";

    private final Texture spaceBgTexture;
    public final TextureRegion spaceBg;
    private static final String SPACE_BG_FILENAME = "data/background/bg.png";

    private final Texture titleBgTexture;
    public final TextureRegion titleBg;
    private static final String TITLE_BG_FILENAME = "data/background/bg.png";

    private final Texture deathBgTexture;
    public final TextureRegion deathBg;
    private static final String DEATH_BG_FILENAME = "data/background/bg.png";

    private static final String FONT = "data/fonts/intro.fnt";
    public final BitmapFont font;

    private static Assets instance;

    private Assets() {
        //Font
        font = new BitmapFont(Gdx.files.internal(FONT));
        font.setColor(0, 0, 0, 1);
        //Ship
        shipTexture = new Texture(Gdx.files.internal(SHIP_FILENAME));
        ship = new TextureRegion(shipTexture, 100, 81);
        //Projectille
        projectileTexture = new Texture(Gdx.files.internal(PROJECTILE_FILENAME));
        projectile = new TextureRegion(projectileTexture, 6, 17);
        //Space Background
        spaceBgTexture = new Texture(Gdx.files.internal(SPACE_BG_FILENAME));
        spaceBg = new TextureRegion(spaceBgTexture, 0, 0, 300, 180);
        //Title Background
        titleBgTexture = new Texture(Gdx.files.internal(TITLE_BG_FILENAME));
        titleBg = new TextureRegion(titleBgTexture, 0, 0, 300, 180);
        //Death Background
        deathBgTexture = new Texture(Gdx.files.internal(DEATH_BG_FILENAME));
        deathBg = new TextureRegion(deathBgTexture, 0, 0, 300, 180);

        manage(spaceBgTexture);
        manage(shipTexture);
        manage(projectileTexture);
    }

    public static Assets getInstance() {
        if (instance == null) instance = new Assets();
        return instance;
    }

    public void manage(Disposable disposable) {
        if (disposables.contains(disposable))
            throw new RuntimeException("Attempting to add disposable to same container twice.");
        disposables.add(disposable);
    }

    @Override
    public void dispose() {
        for (Disposable disposable : disposables) {
            disposable.dispose();
        }
        disposables.clear();
        instance = null;
    }
}
