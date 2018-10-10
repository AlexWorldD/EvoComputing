package model;

public class Metric {
    public static double euclDist(Individual l, Individual r) {
        double sum = 0;
        for (int i = 0; i < 10; i++) {
            sum += Math.pow(l.getGene(i) - r.getGene(i), 2);
        }
        return Math.sqrt(sum);
    }
}
