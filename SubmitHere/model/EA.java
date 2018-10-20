/*
 * Developed by Alex, Lotta, Pratik and Bella during the Evolutionary Computing course at VU University, 2018.
 * Last modified 10/21/18 1:05 AM.
 * Copyright (c) 2018 with  by Group52.
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

import java.util.ArrayList;
import java.util.Random;
import java.util.Collections;

import static model.UnifiedRandom._rnd;
import static model.UnifiedRandom._evals;
import static model.UnifiedRandom.makeRandoms;
import static model.Parameters.*;

import java.util.List;

import org.vu.contest.ContestEvaluation;

/**
 * Evolutionary algorithm class
 */
public class EA {

    private ContestEvaluation evaluation;
    private List<Individual> population = new ArrayList<>();
    public int population_size;
    private double update_part;
    public int num_parents;
    public Selection selection;
    private Boolean _new_size = true;
    private Boolean _new_size2 = true;
    private Boolean _one = true;


    /**
     * Default constructor for EA class
     *
     * @param e Evaluation function
     */
    public EA(ContestEvaluation e) {
        this.evaluation = e;
        makeRandoms();
        this.population_size = Parameters.population_size;
        this.update_part = Parameters.update_part;
        this.num_parents = (int) Math.round(this.population_size * this.update_part);
        if (this.num_parents % 2 == 1) {
            this.num_parents += 1;
        }
//        Creating population
        for (int i = 0; i < this.population_size; i++) {
            this.population.add(new Individual(this.evaluation));
        }
        this.selection = new Selection(this.population_size, this.num_parents);
    }

    /**
     * Adaptive the population when we are close to the optimum
     *
     * @param individual Most fitted individual right now
     * @param k Coefficient for multiplication the initial popSize
     * @param mode String mode for choosing the way of making new population
     *             1 - Uncorrelated One Step
     *             2 - Uniform
     *             3 - Uncorrelated N Step
     */
    private void EA_update(Individual individual, double k, int mode) {
        this.selection.reset();
        this.population_size = Parameters.new_size * (int) (Parameters.population_size * k);
        this.update_part = Parameters.update_part;
        this.num_parents = (int) Math.round(this.population_size * this.update_part);
        if (this.num_parents % 2 == 1) {
            this.num_parents += 1;
        }
        //        Creating population
        this.population.clear();
        for (int i = 0; i < this.population_size; i++) {
            try {
                this.population.add(individual.clone());
            } catch (CloneNotSupportedException ex) {
                throw new RuntimeException(ex);
            }
        }
        Mutation mutation = new Mutation();
        if (mode == 1) {
            this.population.forEach(item -> mutation.UncorrelatedOneMutation(item));
        }
        if (mode == 2) {
            this.population.forEach(item -> mutation.Uniform(item));
        }
        if (mode == 3) {
            this.population.forEach(item -> mutation.UncorrelatedNStepMutation(item));
        }
        this.population.forEach(item -> item.updFitness());
        this.selection = new Selection(this.population_size, this.num_parents);
    }

    /**
     * Adaptive the population when we are close to the optimum
     *
     * @param individual Most fitted individual right now
     * @param mode String mode for choosing the way of making new population
     *             1 - Uncorrelated One Step
     *             2 - Uniform
     *             3 - Uncorrelated N Step
     */
    private void EA_update_baseline(Individual individual, int mode) {
        this.selection.reset();
        this.population_size = Parameters.new_size;
        this.update_part = Parameters.update_part;
        this.num_parents = (int) Math.round(this.population_size * this.update_part);
        if (this.num_parents % 2 == 1) {
            this.num_parents += 1;
        }
        //        Creating population
        this.population.clear();
        for (int i = 0; i < this.population_size; i++) {
            try {
                this.population.add(individual.clone());
            } catch (CloneNotSupportedException ex) {
                throw new RuntimeException(ex);
            }
        }
        Mutation mutation = new Mutation();
        if (mode == 1) {
            this.population.forEach(item -> mutation.UncorrelatedOneMutation(item));
        }
        if (mode == 2) {
            this.population.forEach(item -> mutation.Uniform(item));
        }
        if (mode == 3) {
            this.population.forEach(item -> mutation.UncorrelatedNStepMutation(item));
        }
        this.population.forEach(item -> item.updFitness());
        this.selection = new Selection(this.population_size, this.num_parents);
    }

    /**
     * Multi-dynamic selection wrapper
     *
     * @param evaluationlimit Max number of evaluations
     */
    public void dynSelect(double evaluationlimit) {
        double d = d_dyn - d_dyn * _evals / evaluationlimit;
        this.selection.chooseParents(this.population, selection_parents);
        this.selection.makePairs("seq");
        this.selection.makeChildren(mode_crossover);
        this.selection.mutateChilred(mode_mutation);
        this.selection.evaluateChildren();
        this.population = selection.dynSelect(d, population_size, this.population);
        if (lotta) {
            System.out.print(Collections.max(population).getFitness());
            System.out.print(" ");
            System.out.println(Metric.avgDCN(population));
        }
        this.selection.reset();

    }
    /**
     * Crowding survivor selection wrapper
     *
     */
    public void crowding() {
        this.selection.chooseParents(this.population, selection_parents);
//        System.out.println("Parents");
        this.selection.makePairs("seq");
//        System.out.println("Pairs");
        this.selection.old_parents.clear();
        this.selection.makeChildren(mode_crossover);
//        System.out.println("MakeChildren");
        this.selection.mutateChilred(mode_mutation);
//        System.out.println("MutateChildren");
//        _evals+=this.num_parents;
        this.selection.evaluateChildren();
//        System.out.println("Evaluate");
        this.population = selection.crowding();
        if (lotta) {
            System.out.print(Collections.max(population).getFitness());
            System.out.print(" ");
            System.out.println(Metric.avgDCN(population));
        }
        this.selection.reset();
    }
    /**
     * Generation model: offspring becomes the new population
     *
     */
    public void baseline() {
        this.selection.chooseParents(this.population, selection_parents);
//        System.out.println("Parents");
        this.selection.makePairs("random");
//        System.out.println("Pairs");
        this.selection.makeChildren(mode_crossover);
//        System.out.println("MakeChildren");
        this.selection.mutateChilred(mode_mutation);
//        System.out.println("MutateChildren");
//        _evals+=this.num_parents;
        this.selection.evaluateChildren();
//        System.out.println("Evaluate");
        this.population = selection.returnChildren();
        if (lotta) {
            System.out.print(Collections.max(population).getFitness());
            System.out.print(" ");
            System.out.println(Metric.avgDCN(population));
        }
        this.selection.reset();
    }

    /**
     * Special function for Contest Schaffers evaluation, adaptively changing population when it's getting closer to the optimum
     *
     */
    public void baseline_v2() {
        this.selection.chooseParents(this.population, selection_parents);
//        System.out.println("Parents");
        this.selection.makePairs("random");
//        System.out.println("Pairs");
        this.selection.makeChildren(mode_crossover);
//        System.out.println("MakeChildren");
        this.selection.mutateChilred(mode_mutation);
//        System.out.println("MutateChildren");
//        _evals+=this.num_parents;
        this.selection.evaluateChildren();
//        System.out.println("Evaluate");
        this.population = selection.returnChildren();
        if (Collections.max(population).getFitness() > 9.999 && this._new_size) {
            try {
                Individual tmp = Collections.max(population).clone();
            for (int i=0; i<Individual.num_genes; i++) {
                tmp.updSigma(i, tmp.getSigma(i)*10);
            }
                this.EA_update_baseline(tmp, 1);
            } catch (CloneNotSupportedException ex) {
                throw new RuntimeException(ex);
            }
            this._new_size = false;
//            mode_crossover = "no";
            alpha = 0.25;
            selection_pressure = 1.95;
        }
        if (lotta) {
            System.out.print(Collections.max(population).getFitness());
            System.out.print(" ");
            System.out.println(Metric.avgDCN(population));
        }
        this.selection.reset();
        if (debug) System.out.println(Collections.max(this.population).getFitness());
    }
    /**
     * Special function for Contest BentCigar evaluation, adaptively changing population when it's getting closer to the optimum
     *
     */
    public void BentCigar() {
        this.selection.chooseParents(this.population, selection_parents);
//        System.out.println("Parents");
        this.selection.makePairs("random");
//        System.out.println("Pairs");
        this.selection.old_parents.clear();
        this.selection.makeChildren(mode_crossover);
//        System.out.println("MakeChildren");
        this.selection.mutateChilred(mode_mutation);
//        System.out.println("MutateChildren");
//        _evals+=this.num_parents;
        this.selection.evaluateChildren();
        this.population = selection.returnChildren();
        if (Collections.max(population).getFitness() > 9.999 && this._new_size) {
            try {
                Individual tmp = Collections.max(population).clone();
//                                tmp.updSigma(tmp.getSigma()/2)
                def_sigma = 0.0001;
                for (int i=0; i<Individual.num_genes; i++) {
                    tmp.updSigma(i, tmp.getSigma(i)*4);
                }
                this.EA_update_baseline(tmp, 2);
            } catch (CloneNotSupportedException ex) {
                throw new RuntimeException(ex);
            }
            this._new_size = false;
            mode_crossover = "wholeA";
            alpha = 0.25;
            epsMax = 2.0;
            selection_pressure = 1.3;
        }
//        if (Collections.max(population).getFitness() > 9.99 && this._new_size) {
//            try {
//                Individual tmp = Collections.max(population).clone();
////                                tmp.updSigma(tmp.getSigma()/2);
//                this.EA_update(tmp, 3, 1);
//            } catch (CloneNotSupportedException ex) {
//                throw new RuntimeException(ex);
//            }
//            this._new_size = false;
//        }
//        if (Collections.max(population).getFitness() > 9.999 && this._new_size2) {
//            try {
//                Individual tmp = Collections.max(population).clone();
//                tmp.updSigma(tmp.getSigma() / 2);
//                this.EA_update(tmp, 1, 1);
//            } catch (CloneNotSupportedException ex) {
//                throw new RuntimeException(ex);
//            }
//            this._new_size2 = false;
//        }
    }

}
