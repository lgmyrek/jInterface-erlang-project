package com.jinterfacegame.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

/**
 * Created by lgmyrek on 29.12.13.
 */
public class GameInput {
    public boolean left;
    public boolean right;
    public boolean forward;
    public boolean attack;
    public boolean escape;
    public boolean escapeOnce;
    public boolean enter;
    public boolean enterOnce;
    public boolean dotOnce;
    public boolean oneOnce;
    public boolean twoOnce;
    public boolean threeOnce;
    public boolean fourOnce;
    public boolean fiveOnce;
    public boolean sixOnce;
    public boolean sevenOnce;
    public boolean eightOnce;
    public boolean nineOnce;
    public boolean zeroOnce;
    public boolean backspaceOnce;

    public GameInput() {
        Gdx.input.setInputProcessor(new CustomInputListener());
    }

    public void dispose() {
        Gdx.input.setInputProcessor(null);
    }

    public void reset() {
        escapeOnce = false;
        enterOnce = false;
        dotOnce = false;
        oneOnce = false;
        twoOnce = false;
        threeOnce = false;
        fourOnce = false;
        fiveOnce = false;
        sixOnce = false;
        sevenOnce = false;
        eightOnce = false;
        nineOnce = false;
        zeroOnce = false;
        backspaceOnce = false;
    }

    public class CustomInputListener implements InputProcessor {

        public CustomInputListener() {
        }

        @Override
        public boolean keyDown(int keycode) {
            switch (keycode) {
                case com.badlogic.gdx.Input.Keys.LEFT:
                case com.badlogic.gdx.Input.Keys.A:
                    left = true;
                    break;
                case com.badlogic.gdx.Input.Keys.RIGHT:
                case com.badlogic.gdx.Input.Keys.D:
                    right = true;
                    break;
                case com.badlogic.gdx.Input.Keys.UP:
                case com.badlogic.gdx.Input.Keys.W:
                    forward = true;
                    break;
                case com.badlogic.gdx.Input.Keys.SPACE:
                    attack = true;
                    break;
                case com.badlogic.gdx.Input.Keys.ESCAPE:
                    escapeOnce = true;
                    escape = true;
                    break;
                case Input.Keys.ENTER:
                    enter = true;
                    enterOnce = true;
                    break;
                case Input.Keys.PERIOD:
                    dotOnce = true;
                    break;
                case Input.Keys.BACKSPACE:
                    backspaceOnce = true;
                    break;
                case Input.Keys.NUM_0:
                case Input.Keys.NUMPAD_0:
                    zeroOnce = true;
                    break;
                case Input.Keys.NUM_1:
                case Input.Keys.NUMPAD_1:
                    oneOnce = true;
                    break;
                case Input.Keys.NUM_2:
                case Input.Keys.NUMPAD_2:
                    twoOnce = true;
                    break;
                case Input.Keys.NUM_3:
                case Input.Keys.NUMPAD_3:
                    threeOnce = true;
                    break;
                case Input.Keys.NUM_4:
                case Input.Keys.NUMPAD_4:
                    fourOnce = true;
                    break;
                case Input.Keys.NUM_5:
                case Input.Keys.NUMPAD_5:
                    fiveOnce = true;
                    break;
                case Input.Keys.NUM_6:
                case Input.Keys.NUMPAD_6:
                    sixOnce = true;
                    break;
                case Input.Keys.NUM_7:
                case Input.Keys.NUMPAD_7:
                    sevenOnce = true;
                    break;
                case Input.Keys.NUM_8:
                case Input.Keys.NUMPAD_8:
                    eightOnce = true;
                    break;
                case Input.Keys.NUM_9:
                case Input.Keys.NUMPAD_9:
                    nineOnce = true;
                    break;
                default:
                    return false;
            }
            return true;
        }

        @Override
        public boolean keyUp(int keycode) {
            switch (keycode) {
                case com.badlogic.gdx.Input.Keys.LEFT:
                case com.badlogic.gdx.Input.Keys.A:
                    left = false;
                    break;
                case com.badlogic.gdx.Input.Keys.RIGHT:
                case com.badlogic.gdx.Input.Keys.D:
                    right = false;
                    break;
                case com.badlogic.gdx.Input.Keys.UP:
                case com.badlogic.gdx.Input.Keys.W:
                    forward = false;
                    break;
                case com.badlogic.gdx.Input.Keys.SPACE:
                    attack = false;
                    break;
                case com.badlogic.gdx.Input.Keys.ESCAPE:
                    escapeOnce = false;
                    escape = false;
                    break;
                case Input.Keys.ENTER:
                    enter = false;
                    enterOnce = false;
                    break;
                case Input.Keys.PERIOD:
                    dotOnce = false;
                    break;
                case Input.Keys.BACKSPACE:
                    backspaceOnce = false;
                    break;
                case Input.Keys.NUM_0:
                case Input.Keys.NUMPAD_0:
                    zeroOnce = false;
                    break;
                case Input.Keys.NUM_1:
                case Input.Keys.NUMPAD_1:
                    oneOnce = false;
                    break;
                case Input.Keys.NUM_2:
                case Input.Keys.NUMPAD_2:
                    twoOnce = false;
                    break;
                case Input.Keys.NUM_3:
                case Input.Keys.NUMPAD_3:
                    threeOnce = false;
                    break;
                case Input.Keys.NUM_4:
                case Input.Keys.NUMPAD_4:
                    fourOnce = false;
                    break;
                case Input.Keys.NUM_5:
                case Input.Keys.NUMPAD_5:
                    fiveOnce = false;
                    break;
                case Input.Keys.NUM_6:
                case Input.Keys.NUMPAD_6:
                    sixOnce = false;
                    break;
                case Input.Keys.NUM_7:
                case Input.Keys.NUMPAD_7:
                    sevenOnce = false;
                    break;
                case Input.Keys.NUM_8:
                case Input.Keys.NUMPAD_8:
                    eightOnce = false;
                    break;
                case Input.Keys.NUM_9:
                case Input.Keys.NUMPAD_9:
                    nineOnce = false;
                    break;
                default:
                    return false;
            }
            return true;
        }

        @Override
        public boolean keyTyped(char character) {
            return false;
        }

        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            return true;
        }

        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) {
            return true;
        }

        @Override
        public boolean touchDragged(int screenX, int screenY, int pointer) {
            return false;
        }

        @Override
        public boolean mouseMoved(int screenX, int screenY) {
            return false;
        }

        @Override
        public boolean scrolled(int amount) {
            return false;
        }
    }
}
