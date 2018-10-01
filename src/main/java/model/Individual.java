package model;

import java.util.Random;

import static model.UnifiedRandom._rnd;

import org.vu.contest.ContestEvaluation;


public class Individual implements Comparable<Individual> {

    final static public int num_genes = 10;
    final static private int min_gene = -5;
    final static private int max_gene = 5;
    public double tau = Math.sqrt(1 / (2 * num_genes));
    public double[] taus = {Math.sqrt(1 / (2 * Math.sqrt(num_genes))),
            Math.sqrt(1 / (2 * num_genes))};
    public double epsilon = 0.05;
    private double fitness = 0.0;
    private double[] genes = new double[num_genes];
    private double[] sigmas = new double[num_genes];
    public Random ind_rand = new Random();
    private ContestEvaluation evaluation;

    /**
     * Default constructor
     *
     * @param eval Contest evaluation method
     */
    public Individual(ContestEvaluation eval) {
        for (int i = 0; i < num_genes; i++) {
            this.genes[i] = min_gene + _rnd.nextDouble() * (max_gene - min_gene);
            this.sigmas[i] = 1;
        }
        this.fitness = (double) eval.evaluate(this.genes);
        this.evaluation = eval;
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
     */
    public double getFitness() {
        return this.fitness;
    }

    /**
     * Getting current fitness of Individual
     */
    public double updFitness() {
        this.fitness = (double) this.evaluation.evaluate(this.genes);
        return this.fitness;
    }

    /**
     * Update specific gene
     *
     * @param position position of gene for update
     * @param value    new value
     */
    public void updGene(int position, double value) throws Exception {
        if (position >= 0 && position < num_genes) {
            if (value <= min_gene) {
                genes[position] = min_gene;
            } else if (value >= max_gene) {
                genes[position] = max_gene;
            } else genes[position] = value;
        } else {
            throw new Exception();
        }
    }

    /**
     * Update specific gene
     *
     * @param new_genes array of new values
     */
    public void updGenes(double[] new_genes) throws Exception {
        if (new_genes.length == num_genes) {
            for (int i = 0; i < num_genes; i++) {
                if (new_genes[i] <= min_gene) {
                    genes[i] = min_gene;
                } else if (new_genes[i] >= max_gene) {
                    genes[i] = max_gene;
                } else genes[i] = new_genes[i];
            }
        } else {
            throw new Exception();
        }
    }

    /**
     * Get specific gene
     *
     * @param position position of required gene
     * @return value of required gene
     */
    public double getGene(int position) throws Exception {
        if (position >= 0 && position < num_genes) {
            return genes[position];
        } else {
            throw new Exception();
        }
    }

    /**
     * Get sigma
     *
     * @return value of sigma
     */
    public double getSigma() {
        return sigmas[0];
    }

    /**
     * Get sigma
     *
     * @return array of of sigmas
     */
    public double[] getSigmas() {
        return sigmas;
    }

    /**
     * Get specific sigma
     *
     * @param position position of required sigma
     * @return value of required sigma
     */
    public double getSigma(int position) throws Exception {
        if (position >= 0 && position < num_genes) {
            return sigmas[position];
        } else {
            throw new Exception();
        }
    }

    /**
     * Update sigma
     *
     * @param value new value
     */
    public void updSigma(double value) {
        sigmas[0] = Math.max(value, epsilon);
    }

    /**
     * Update specific sigma
     *
     * @param position position of gene for update
     * @param value    new value
     */
    public void updSigma(int position, double value) throws Exception {
        if (position >= 0 && position < num_genes) {
            sigmas[position] = Math.max(value, epsilon);
        } else {
            throw new Exception();
        }
    }

    /**
     * Update specific sigma
     *
     * @param new_sigmas new values for sigma
     */
    public void updSigmas(double[] new_sigmas) throws Exception {
        if (new_sigmas.length == num_genes) {
            for (int i = 0; i < Individual.num_genes; i++) {
                sigmas[i] = Math.max(new_sigmas[i], epsilon);
            }

        } else {
            throw new Exception();
        }
    }

    /**
     * Getting current Genes array
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
