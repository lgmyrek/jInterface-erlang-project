package com.jinterfacegame.entities;

import com.badlogic.gdx.utils.Pool;
import com.jinterfacegame.utils.State;

/**
 * Created by lgmyrek on 31.12.13.
 */
public class PoolAggregate {
    private final Pool<Projectile> projectilePool = new Pool<Projectile>() {
        @Override
        protected Projectile newObject() {
            return new Projectile();
        }
    };

    private final Pool<Ship> shipPool = new Pool<Ship>() {
        @Override
        protected Ship newObject() {
            return new Ship();
        }
    };

    private final Pool<State> statePool = new Pool<State>() {
        @Override
        protected State newObject() {
            return new State();
        }
    };

    private static PoolAggregate instance;

    private PoolAggregate() {
    }

    public static PoolAggregate getInstance() {
        if (instance == null) instance = new PoolAggregate();
        return instance;
    }

    public Pool<Ship> getShipPool() {
        return shipPool;
    }

    public Pool<Projectile> getProjectilePool() {
        return projectilePool;
    }

    public Pool<State> getStatePool() {
        return statePool;
    }
}
