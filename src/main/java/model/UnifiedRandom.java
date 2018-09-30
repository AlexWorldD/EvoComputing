package model;

import java.util.Random;

public class UnifiedRandom {
    public static Random _rnd = new Random();

    UnifiedRandom(long seed) {
        _rnd.setSeed(seed);
    }

    UnifiedRandom(double seed) {
        _rnd.setSeed((long) seed);
    }

}
