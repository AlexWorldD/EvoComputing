/*
 * Developed by Alex, Lotta, Pratik and Bella during the Evolutionary Computing course at VU University, 2018.
 * Last modified 10/21/18 12:19 AM.
 * Copyright (c) 2018 with 💛 by Group52.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package model;

import static model.UnifiedRandom._rnd;
import static model.Parameters.debug_sigma;

import java.util.ArrayList;
import java.util.Random;
import java.util.Collections;
import java.util.List;
import java.util.Arrays;

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
            l_old_genes[i] = _mixArithmetic(l.getGene(i), r.getGene(i));
            r_old_genes[i] = _mixArithmetic(r.getGene(i), l.getGene(i));
            l_old_sigmas[i] = _mixArithmetic(l.getSigma(i), r.getSigma(i));
            r_old_sigmas[i] = _mixArithmetic(r.getSigma(i), l.getSigma(i));
        }
        List<Individual> children = new ArrayList<>();
        children.add(new Individual(l.getEvaluation(), l_old_genes, l_old_sigmas));
        children.add(new Individual(r.getEvaluation(), r_old_genes, r_old_sigmas));
        if (debug_sigma) System.out.println(Arrays.toString(l_old_sigmas));
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
        l_old_genes[k] = _mixArithmetic(l.getGene(k), r.getGene(k));
        r_old_genes[k] = _mixArithmetic(r.getGene(k), l.getGene(k));
        l_old_sigmas[k] = _mixArithmetic(l.getSigma(k), r.getSigma(k));
        r_old_sigmas[k] = _mixArithmetic(r.getSigma(k), l.getSigma(k));
        List<Individual> children = new ArrayList<>();
        children.add(new Individual(l.getEvaluation(), l_old_genes, l_old_sigmas));
        children.add(new Individual(r.getEvaluation(), r_old_genes, r_old_sigmas));
        if (debug_sigma) System.out.println(Arrays.toString(l_old_sigmas));
        return children;
    }

    /**
     * Safe SingleArithmetic recombination
     *
     * @param pair Parents for making L0ve
     * @return List of children
     */
    public List<Individual> SingleArithmetic(List<Individual> pair) {
        try {
            return this.SingleArithmetic(pair.get(0), pair.get(1));
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
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
        double[] l_old_genes = new double[Individual.num_genes];
        double[] l_old_sigmas = new double[Individual.num_genes];
        double[] r_old_genes = new double[Individual.num_genes];
        double[] r_old_sigmas = new double[Individual.num_genes];
        for (int i = 0; i < Individual.num_genes; i++) {
            l_old_genes[i] = this.alpha*l.getGene(i) + (1-this.alpha)*r.getGene(i);
            r_old_genes[i] = this.alpha*r.getGene(i) + (1-this.alpha)*l.getGene(i);
            l_old_sigmas[i] = this.alpha*l.getSigma(i) + (1-this.alpha)*r.getSigma(i);
            r_old_sigmas[i] = this.alpha*r.getSigma(i) + (1-this.alpha)*l.getSigma(i);
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
        return this.alpha * l + (1.0 - this.alpha) * r;
    }

//  /////// BLEND MUTATION \\\\\\\\
//    TODO write it here
}
