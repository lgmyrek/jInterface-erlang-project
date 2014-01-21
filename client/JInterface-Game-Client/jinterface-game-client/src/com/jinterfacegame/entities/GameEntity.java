package com.jinterfacegame.entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Pool.Poolable;

/**
 * Created by lgmyrek on 29.12.13.
 */
public abstract class GameEntity extends Actor implements Poolable {
    protected TextureRegion texture;
    private float x;
    private float y;
    private float rotation;


    protected GameEntity() {
        this.reset();
    }

    public GameEntity(float rotation, float y, float x) {
        this.rotation = rotation;
        this.y = y;
        this.x = x;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (texture != null) {
            batch.draw(getTexture(), getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
        }
    }

    public void setTexture(TextureRegion texture) {
        this.texture = texture;
        setOrigin(texture.getRegionWidth() / 2, texture.getRegionHeight() / 2);
        setSize(texture.getRegionWidth(), texture.getRegionHeight());
    }

    @Override
    public float getRotation() {
        return rotation + 90;
    }

    @Override
    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    @Override
    public float getY() {
        return y - (getHeight() / 2);
    }

    @Override
    public void setY(float y) {
        this.y = y;
    }

    @Override
    public float getX() {
        return x - (getWidth() / 2);
    }

    @Override
    public void setX(float x) {
        this.x = x;
    }

    @Override
    public void reset() {
        x = 0;
        y = 0;
        rotation = 0;
    }

    public TextureRegion getTexture() {
        return texture;
    }
}
