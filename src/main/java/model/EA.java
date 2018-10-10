package model;

import java.util.ArrayList;
import java.util.Random;

import static model.UnifiedRandom._rnd;
import static model.UnifiedRandom._evals;

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


    public EA(ContestEvaluation e, int population_size, double update_part) {
        this.evaluation = e;
        this.population_size = population_size;
        this.update_part = update_part;
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

    public void crowding() {
//        Selection selection = new Selection(this.num_parents);
        if (this.update_part == 1.0) {
            this.selection.chooseParents(this.population, "SUS");
        } else {
            this.selection.chooseParents(this.population, "SUS");
        }
//        System.out.println("Parents");
        this.selection.makePairs("seq");
//        System.out.println("Pairs");
        this.selection.makeChildren("wholeA");
//        System.out.println("MakeChildren");
        this.selection.mutateChilred("UncorN");
//        System.out.println("MutateChildren");
//        _evals+=this.num_parents;
        this.selection.evaluateChildren();
//        System.out.println("Evaluate");
        this.population = selection.crowding();
        this.selection.reset();
    }

}
