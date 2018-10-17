package model;

import java.util.List;

public class Metric {
    public static double euclDist(Individual l, Individual r) {
        double sum = 0;
        for (int i = 0; i < 10; i++) {
            sum += Math.pow(l.getGene(i) - r.getGene(i), 2);
        }
        return Math.sqrt(sum);
    }

    //returns average distance to closest neighbour
    public static double avgDCN(List<Individual> pop) {
        double sum = 0;
        for (int i=0; i<pop.size(); i++) {
            double dcn = Double.MAX_VALUE;
            for (int j =0; j<pop.size();j++) {
                if (j!= i) {
                    double dist = euclDist(pop.get(i),pop.get(j));
                    if (dist < dcn) {
                        dcn = dist;

                    }
                }
            }
            sum += dcn;
        }
        return sum/pop.size();
    }

    //returns average of average distance to another individual seeing from one individual
    public static double avgDist(List<Individual> pop) {
        double sum = 0;
        for (int i=0; i<pop.size(); i++) {
            double distsum =0;
            for (int j =0; j<pop.size();j++) {
                if (j!= i) {
                    double dist = euclDist(pop.get(i),pop.get(j));
                    distsum += dist;
                }
            }
            sum += distsum/pop.size();
        }
        return sum/pop.size();
    }
}


