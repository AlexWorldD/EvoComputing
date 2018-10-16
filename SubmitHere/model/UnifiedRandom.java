package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class UnifiedRandom {
    public static Random _rnd = new Random();
    public static Random _rnd2 = new Random();
    //    public static Random[] _randoms = new Random[Individual.num_genes];
    public static List<Random> _randoms = new ArrayList<>();
    //    Counter for elapsed evaluations
    public static int _evals = 0;

    public static void makeRandoms() {
        for (int i = 0; i < Individual.num_genes; i++) {
            _randoms.add(new Random());
        }
    }

    UnifiedRandom(long seed) {
        _rnd.setSeed(seed);
    }

    UnifiedRandom(double seed) {
        _rnd.setSeed((long) seed);
    }

}
