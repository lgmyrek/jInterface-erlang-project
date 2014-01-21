package com.jinterfacegame.utils;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * Created by lgmyrek on 06.01.14.
 */
public class SecureRandomGenerator {
    private SecureRandom random = new SecureRandom();

    public String next() {
        return new BigInteger(130, random).toString(32);
    }
}
