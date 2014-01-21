package com.jinterfacegame.utils;

import com.ericsson.otp.erlang.*;

import java.io.IOException;

/*
 * Created by lgmyrek on 31.12.13.
 */
public class ErlangProxy implements Runnable {
    private final static double spamDelay = 16670000;
    private final OtpErlangAtom okAtom = new OtpErlangAtom("ok");
    private final OtpErlangAtom stateAtom = new OtpErlangAtom("state");
    private final OtpErlangAtom getStateAtom = new OtpErlangAtom("getState");
    private final OtpErlangAtom leftAtom = new OtpErlangAtom("left");
    private final OtpErlangAtom rightAtom = new OtpErlangAtom("right");
    private final OtpErlangAtom forwardAtom = new OtpErlangAtom("forward");
    private final OtpErlangAtom fireAtom = new OtpErlangAtom("fire");
    private final OtpErlangAtom spawnAtom = new OtpErlangAtom("newShip");
    private final OtpErlangAtom disconnectedAtom = new OtpErlangAtom("disconnected");

    //Czysty nie przetworzony stan gry
    private OtpErlangTuple pureState;

    private String server;
    private OtpSelf selfNode;
    private OtpPeer serverPeer;
    private OtpConnection cookedConnection;

    private Cooldown spamCooldown;

    public ErlangProxy(String server) {
        this.server = server;
        try {
            selfNode = new OtpSelf(new SecureRandomGenerator().next(), "jInterface_Game");
            serverPeer = new OtpPeer(server);
            cookedConnection = selfNode.connect(serverPeer);
            spamCooldown = new Cooldown();
            spamCooldown.set(spamDelay);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OtpAuthException e) {
            e.printStackTrace();
        }
    }

    synchronized public OtpErlangTuple getPureState() {
        return pureState;
    }

    synchronized public void setPureState(OtpErlangTuple pureState) {
        this.pureState = pureState;
    }

    public OtpErlangTuple tupleCreator(OtpErlangObject... args) {
        OtpErlangObject[] objects = new OtpErlangObject[args.length];
        int i = 0;
        for (OtpErlangObject arg : args) {
            objects[i++] = arg;
        }
        return new OtpErlangTuple(objects);
    }

    @Override
    public void run() {
        OtpErlangObject msgObject;
        OtpErlangTuple msg;
        while (true) {
            if (spamCooldown.onCooldown()) {
                spamCooldown.update();
            } else {
                spamCooldown.set(spamDelay);
                sendUpdateRequestToServer();
                try {
                    msgObject = cookedConnection.receive();
                    if (msgObject != null) {
                        msg = (OtpErlangTuple) msgObject;

                        if ((msg.elementAt(0).equals(stateAtom))) {
                            setPureState((OtpErlangTuple) msg.elementAt(1));
                        }
                    }
                } catch (OtpErlangExit otpErlangExit) {
                    break;
                } catch (OtpAuthException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void sendUpdateRequestToServer() {
        try {
            cookedConnection.send("proxy", tupleCreator(getStateAtom, getPid()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public OtpErlangPid getPid() {
        return cookedConnection.self().pid();
    }

    public void sendAttack() {
        try {
            cookedConnection.send("proxy", tupleCreator(fireAtom, getPid()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendLeft() {
        try {
            cookedConnection.send("proxy", tupleCreator(leftAtom, getPid()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendRight() {
        try {
            cookedConnection.send("proxy", tupleCreator(rightAtom, getPid()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendForward() {
        try {
            cookedConnection.send("proxy", tupleCreator(forwardAtom, getPid()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendSpawn() {
        try {
            cookedConnection.send("proxy", tupleCreator(spawnAtom, getPid()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendDisconnected() {
        try {
            cookedConnection.send("proxy", tupleCreator(disconnectedAtom, getPid()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

