package model;

import java.util.Random;

import org.vu.contest.ContestEvaluation;


public class Individual implements Comparable<Individual> {

    final static private int num_genes = 10;
    final static private int min_gene = -5;
    final static private int max_gene = -5;
    private double fitness = 0.0;
    private double[] genes = new double[num_genes];

    /**
     * Default constructor
     *
     * @param eval Contest evaluation method
     */
    public Individual(ContestEvaluation eval) {
        Random r = new Random();
        for (int i = 0; i < num_genes; i++) {
            this.genes[i] = min_gene + r.nextDouble() * (max_gene - min_gene);
        }
        this.fitness = (double) eval.evaluate(this.genes);
    }

    /**
     * Setting specific fitness value
     *
     * @param fitness Specific fitness, could be useful for debug
     */
    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    /**
     * Setting fitness according to specific evaluation method
     *
     * @param eval Contest evaluation method
     */
    public void setFitness(ContestEvaluation eval) {
        this.fitness = (double) eval.evaluate(this.genes);
    }

    /**
     * Getting current fitness of Individual
     *
     */
    public double getFitness() {
        return this.fitness;
    }

    /**
     * Getting current Genes array
     *
     */
    public double[] getGenes() {
        return this.genes;
    }

    /**
     * Comparator for class Individual
     *
     * @param other another one instance of class Individual
     * @return boolean result of comparison
     */
    public int compareTo(Individual other) {
        return Double.compare(this.fitness, other.fitness);
    }
}
