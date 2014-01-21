package com.jinterfacegame.utils;

import com.badlogic.gdx.utils.TimeUtils;

/**
 * Created by lgmyrek on 06.01.14.
 */
public class Cooldown {
    private double delay;
    private double last;
    private double now;

    public void update() {
        now = TimeUtils.nanoTime();
        delay -= now - last;
        last = now;
    }

    public void set(double delay) {
        this.delay = delay;
        last = TimeUtils.nanoTime();
    }

    public boolean onCooldown() {
        return delay >= 0;
    }
}
