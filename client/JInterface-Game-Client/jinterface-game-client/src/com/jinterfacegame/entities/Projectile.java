package com.jinterfacegame.entities;

import com.jinterfacegame.utils.Assets;

/**
 * Created by lgmyrek on 29.12.13.
 */
public class Projectile extends GameEntity {

    public Projectile() {
        super();
        if (texture == null) setTexture(texture = Assets.getInstance().projectile);
    }

    @Override
    public void reset() {
        super.reset();
    }
}
