package com.jinterfacegame.entities;

import com.jinterfacegame.utils.Assets;

/**
 * Created by lgmyrek on 29.12.13.
 */
public class Ship extends GameEntity {
    private String shipID;

    public Ship() {
        super();
        if (texture == null) setTexture(Assets.getInstance().ship);
        this.reset();
    }

    public Ship(String shipID) {
        this.shipID = shipID;
        if (texture == null) setTexture(Assets.getInstance().ship);
    }

    public String getShipID() {
        return shipID;
    }

    public void setShipID(String shipID) {
        this.shipID = shipID;
    }

    @Override
    public void reset() {
        super.reset();
        shipID = "";
    }
}
