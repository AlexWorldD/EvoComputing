package model;

import java.util.Random;

public class UnifiedRandom {
    public static Random _rnd = new Random();
//    Counter for elapsed evaluations
    public static int _evals = 0;

    UnifiedRandom(long seed) {
        _rnd.setSeed(seed);
    }

    UnifiedRandom(double seed) {
        _rnd.setSeed((long) seed);
    }

}
