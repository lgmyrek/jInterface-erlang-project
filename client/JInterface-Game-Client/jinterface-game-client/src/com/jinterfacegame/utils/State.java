package com.jinterfacegame.utils;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.ericsson.otp.erlang.OtpErlangList;
import com.ericsson.otp.erlang.OtpErlangObject;
import com.ericsson.otp.erlang.OtpErlangTuple;
import com.jinterfacegame.entities.PoolAggregate;
import com.jinterfacegame.entities.Projectile;
import com.jinterfacegame.entities.Ship;

import java.util.Collection;
import java.util.LinkedList;

/**
 * Created by lgmyrek on 31.12.13.
 */
public class State implements Poolable {
    private Collection<Projectile> projectiles;
    private Collection<Ship> ships;

    public State() {
        this.projectiles = new LinkedList<Projectile>();
        this.ships = new LinkedList<Ship>();
    }

    public State setState(OtpErlangTuple pureState) {
        OtpErlangList shipList = (OtpErlangList) pureState.elementAt(1);
        OtpErlangList projectilleList = (OtpErlangList) pureState.elementAt(2);
        OtpErlangTuple tempTuple;
        Projectile tempProjectile;
        Ship tempShip;
        for (OtpErlangObject object : projectilleList) {
            tempTuple = (OtpErlangTuple) object;
            tempProjectile = PoolAggregate.getInstance().getProjectilePool().obtain();
            tempProjectile.setRotation(Float.valueOf(tempTuple.elementAt(0).toString()));
            tempTuple = (OtpErlangTuple) tempTuple.elementAt(1);
            tempProjectile.setX(Float.valueOf(tempTuple.elementAt(0).toString()));
            tempProjectile.setY(Float.valueOf(tempTuple.elementAt(1).toString()));
            this.projectiles.add(tempProjectile);
        }
        for (OtpErlangObject object : shipList) {
            tempTuple = (OtpErlangTuple) object;
            tempShip = PoolAggregate.getInstance().getShipPool().obtain();
            tempShip.setRotation(Float.valueOf(tempTuple.elementAt(0).toString()));
            tempShip.setShipID((tempTuple.elementAt(2)).toString());
            tempTuple = (OtpErlangTuple) tempTuple.elementAt(1);
            tempShip.setX(Float.valueOf(tempTuple.elementAt(0).toString()));
            tempShip.setY(Float.valueOf(tempTuple.elementAt(1).toString()));
            this.ships.add(tempShip);
        }
        return this;
    }

    public Collection<Ship> getShips() {
        return ships;
    }

    public Collection<Projectile> getProjectiles() {
        return projectiles;
    }

    @Override
    public void reset() {
        for (Projectile projectile : projectiles) {
            projectile.reset();
        }
        for (Ship ship : ships) {
            ship.reset();
        }
        projectiles.clear();
        ships.clear();
    }

    public void draw(Batch batch, float parentAlpha) {
        for (Projectile projectile : projectiles) {
            projectile.draw(batch, parentAlpha);
        }
        for (Ship ship : ships) {
            ship.draw(batch, parentAlpha);
        }
    }

    public boolean isIDAlive(String ID) {
        for (Ship ship : ships) {
            if (ship.getShipID().equals(ID)) return true;
        }
        return false;
    }
}
