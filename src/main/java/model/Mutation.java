package model;

import static model.UnifiedRandom._rnd;
import static model.UnifiedRandom._randoms;
import static model.Parameters.debug_sigma;
import java.util.Arrays;
import java.util.Random;

public class Mutation {
    public String mode;
    public double ratio = 1.0;
    public int low;
    public int up;


    /**
     * Default constructor
     */
    public Mutation() {
        this.low = -5;
        this.up = 5;
    }

    /**
     * Default constructor
     *
     * @param borders Upper and lower border for Genes values
     */
    public Mutation(int[] borders) {
        this.low = borders[0];
        this.up = borders[1];
    }

    /**
     * Default constructor
     *
     * @param l Lower border for Genes values
     * @param u Upper border for Genes values
     */
    public Mutation(int l, int u) {
        this.low = l;
        this.up = u;
    }

    /**
     * Default constructor with Mutation ratio
     *
     * @param mut_ratio Mutation ration for the whole mutation process
     */
    Mutation(double mut_ratio) {
        this.ratio = mut_ratio;
    }

    //    Different kinds of mutation   \\

//    /////// UNIFORM \\\\\\\\

    /**
     * Simplest one mutation - uniform
     *
     * @param individual Current individual
     * @return mutated individual
     */
    public Individual UniformMutation(Individual individual) {
        for (int i = 0; i < Individual.num_genes; i++) {
            if (_rnd.nextDouble() < this.ratio) {
                individual.updGene(i, _uniform());
            }
        }
        return individual;
    }

    /**
     * Simplest one mutation - uniform with specified probability
     *
     * @param individual Current individual
     * @param mut_ratio  Mutation ratio for current Individual
     * @return mutated individual
     */
    public Individual UniformMutation(Individual individual, double mut_ratio) {
        for (int i = 0; i < Individual.num_genes; i++) {
            if (_rnd.nextDouble() < mut_ratio) {
                individual.updGene(i, _uniform());
            }
        }
        return individual;
    }

    private double _uniform() {
        return _rnd.nextDouble() * (up - low) + low;
    }

//  /////// NONE-UNIFORM \\\\\\\\

    /**
     * Nonuniform mutation
     *
     * @param individual Current individual
     * @param sigma      sigma value for Gaussian
     * @return mutated individual
     */
    public Individual NonUniformMutation(Individual individual, double sigma) {
        for (int i = 0; i < Individual.num_genes; i++) {
            individual.updGene(i, _nonuniform(individual.getGene(i), sigma));
        }
        return individual;
    }

    /**
     * NonUniform gene mutation
     *
     * @param cur_gene Current value of gene
     * @param sigma    sigma value for Gaussian
     * @return mutated gene
     */
    private double _nonuniform(double cur_gene, double sigma) {
        return _rnd.nextGaussian() * sigma + cur_gene;
    }

//  /////// UNCORRELATED ONE STEP \\\\\\\\

    /**
     * Simplest one mutation - uniform
     * In these formulas N(0,1) denotes a draw from the standard normal distribution,
     * while Ni(0,1) denotes a separate draw from the standard normal
     * distribution for each variable i.
     *
     * @param individual Current individual
     * @return mutated individual
     */
    public Individual UncorrelatedOneMutation(Individual individual) {
        double new_sigma = individual.getSigma() * Math.exp(individual.tau * _rnd.nextGaussian());
        individual.updSigma(new_sigma);
        new_sigma = individual.getSigma();
        double[] old_genes = individual.getGenes().clone();
        for (int i = 0; i < Individual.num_genes; i++) {
            old_genes[i] += new_sigma * _randoms.get(i).nextGaussian();
        }
        individual.updGenes(old_genes);
        if (debug_sigma) System.out.println(Arrays.toString(old_genes));
        return individual;
    }

    /**
     * Simplest one mutation - uniform
     *
     * @param individual Current individual
     * @return mutated individual
     */
    public Individual UncorrelatedNStepMutation(Individual individual) {
        double[] old_genes = individual.getGenes().clone();
        double[] old_sigmas = individual.getSigmas().clone();
        double r2 = _rnd.nextGaussian();
//        TODO check the correctness of different random generators
        for (int i = 0; i < Individual.num_genes; i++) {
            Random r1 = _randoms.get(i);
//            Random r1 = new Random();
            old_sigmas[i] *= Math.exp(individual.taus[0] * r1.nextGaussian() + individual.taus[1] * r2);
            old_genes[i] += Math.min(Math.max(old_sigmas[i], individual.epsilon), individual.stepSizeMax) * r1.nextGaussian();
        }
        individual.updSigmas(old_sigmas);
        individual.updGenes(old_genes);
        if (debug_sigma) System.out.println(Arrays.toString(old_sigmas));
        return individual;
    }

//  /////// CORRELATED \\\\\\\\
//    TODO write it here
}
