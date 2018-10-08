package model;

import static model.UnifiedRandom._rnd;

import java.util.ArrayList;
import java.util.Random;
import java.util.Collections;
import java.util.List;

public class Crossover {
    public double alpha;
    public int k;
    public int num_parents;


    /**
     * Default constructor
     *
     * @param split_k Crossover point, index in Genes array
     */
    public Crossover(int split_k) {
        this.num_parents = 2;
        this.alpha = 0.5;
        this.k = Math.min(split_k, Individual.num_genes - 1);
    }

    /**
     * Default constructor with additional parameters
     *
     * @param p       Number of parents
     * @param split_k Crossover point, index in Genes array
     * @param a       Alpha for mixing alleles
     */
    public Crossover(int p, int split_k, double a) {
        this.num_parents = p;
        this.alpha = a;
        this.k = Math.min(split_k, Individual.num_genes - 1);
    }

    //    Different kinds of recombination operators   \\

//  /////// SIMPLE ARITHMETIC \\\\\\\\

    /**
     * Simple Arithmetic recombination
     *
     * @param l Left parent
     * @param r Right parent
     * @return List of children
     */
    public List<Individual> SimpleArithmetic(Individual l, Individual r) {
        double[] l_old_genes = l.getGenes().clone();
        double[] l_old_sigmas = l.getSigmas().clone();
        double[] r_old_genes = r.getGenes().clone();
        double[] r_old_sigmas = r.getSigmas().clone();
        for (int i = this.k + 1; i < Individual.num_genes; i++) {
//            TODO p65, check the formula
            l_old_genes[i] = _mixArithmetic(l_old_genes[i], r_old_genes[i]);
            r_old_genes[i] = _mixArithmetic(r_old_genes[i], l_old_genes[i]);
            l_old_sigmas[i] = _mixArithmetic(l_old_sigmas[i], r_old_sigmas[i]);
            r_old_sigmas[i] = _mixArithmetic(r_old_sigmas[i], l_old_sigmas[i]);
        }
        List<Individual> children = new ArrayList<>();
        children.add(new Individual(l.getEvaluation(), l_old_genes, l_old_sigmas));
        children.add(new Individual(r.getEvaluation(), r_old_genes, r_old_sigmas));
        return children;
    }

    /**
     * Safe Simple Arithmetic recombination
     *
     * @param pair Parents for making L0ve
     * @return List of children
     */
    public List<Individual> SimpleArithmetic(List<Individual> pair) {
        try {
            return this.SimpleArithmetic(pair.get(0), pair.get(1));
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
//  /////// SINGLE ARITHMETIC \\\\\\\\
    /**
     * Single Arithmetic recombination
     *
     * @param l Left parent
     * @param r Right parent
     * @return List of children
     */
    public List<Individual> SingleArithmetic(Individual l, Individual r) {
        double[] l_old_genes = l.getGenes().clone();
        double[] l_old_sigmas = l.getSigmas().clone();
        double[] r_old_genes = r.getGenes().clone();
        double[] r_old_sigmas = r.getSigmas().clone();
        l_old_genes[k] = _mixArithmetic(l_old_genes[k], r_old_genes[k]);
        r_old_genes[k] = _mixArithmetic(r_old_genes[k], l_old_genes[k]);
        l_old_sigmas[k] = _mixArithmetic(l_old_sigmas[k], r_old_sigmas[k]);
        r_old_sigmas[k] = _mixArithmetic(r_old_sigmas[k], l_old_sigmas[k]);
        List<Individual> children = new ArrayList<>();
        children.add(new Individual(l.getEvaluation(), l_old_genes, l_old_sigmas));
        children.add(new Individual(r.getEvaluation(), r_old_genes, r_old_sigmas));
        return children;
    }


//  /////// WHOLE ARITHMETIC \\\\\\\\
    /**
     * Whole Arithmetic recombination
     *
     * @param l Left parent
     * @param r Right parent
     * @return List of children
     */
    public List<Individual> WholeArithmetic(Individual l, Individual r) {
        double[] l_old_genes = l.getGenes().clone();
        double[] l_old_sigmas = l.getSigmas().clone();
        double[] r_old_genes = r.getGenes().clone();
        double[] r_old_sigmas = r.getSigmas().clone();
        for (int i = 0; i < Individual.num_genes; i++) {
            l_old_genes[i] = _mixArithmetic(l_old_genes[i], r_old_genes[i]);
            r_old_genes[i] = _mixArithmetic(r_old_genes[i], l_old_genes[i]);
            l_old_sigmas[i] = _mixArithmetic(l_old_sigmas[i], r_old_sigmas[i]);
            r_old_sigmas[i] = _mixArithmetic(r_old_sigmas[i], l_old_sigmas[i]);
        }
        List<Individual> children = new ArrayList<>();
        children.add(new Individual(l.getEvaluation(), l_old_genes, l_old_sigmas));
        children.add(new Individual(r.getEvaluation(), r_old_genes, r_old_sigmas));
        return children;
    }

    /**
     * Safe Whole Arithmetic recombination
     *
     * @param pair Parents for making L0ve
     * @return List of children
     */
    public List<Individual> WholeArithmetic(List<Individual> pair) {
        try {
            return this.WholeArithmetic(pair.get(0), pair.get(1));
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private double _mixArithmetic(double l, double r) {
        //            TODO p65, check the formula
        return this.alpha * l + (1 - this.alpha) * r;
    }

//  /////// BLEND MUTATION \\\\\\\\
//    TODO write it here
}
