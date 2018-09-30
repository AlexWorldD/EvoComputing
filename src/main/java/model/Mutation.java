package model;

import static model.UnifiedRandom._rnd;

public class Mutation {
    public String mode;
    public double ratio;
    public int low = -5;
    public int up = 5;

    /**
     * Default constructor
     *
     * @param borders Upper and lower border for Genes values
     */
    Mutation(int[] borders) {
        this.low = borders[0];
        this.up = borders[1];
    }

    /**
     * Default constructor
     *
     * @param l Lower border for Genes values
     * @param u Upper border for Genes values
     */
    Mutation(int l, int u) {
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

    /**
     * Simplest one mutation - uniform
     *
     * @param individual Current individual
     * @param mut_ratio  Mutation ratio for current Individual
     * @return mutated individual
     */
    public Individual UniformMutation(Individual individual, double mut_ratio) throws Exception {
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

    /**
     * Simplest one mutation - uniform
     *
     * @param individual Current individual
     * @param sigma      sigma value for Gaussian
     * @return mutated individual
     */
    public Individual NonUniformMutation(Individual individual, double sigma) throws Exception {
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
}
