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

    public void dynSelect(double evaluationlimit) {
        double d = d_dyn - d_dyn*_evals/evaluationlimit;
        this.selection.chooseParents(this.population, selection_parents);
        this.selection.makePairs("seq");
        this.selection.makeChildren(mode_crossover);
        this.selection.mutateChilred(mode_mutation);
        this.selection.evaluateChildren();
        this.population = selection.dynSelect(d, population_size, this.population);
        if(lotta) {
            System.out.print(Collections.max(population).getFitness());
            System.out.print(" ");
            System.out.println(Metric.avgDCN(population));
        }
        this.selection.reset();

    }

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
        if(lotta) {
            System.out.print(Collections.max(population).getFitness());
            System.out.print(" ");
            System.out.println(Metric.avgDCN(population));
        }
        this.selection.reset();
    }

    public void baseline() {
        this.selection.chooseParents(this.population, selection_parents);
//        System.out.println("Parents");
        this.selection.makePairs("seq");
//        System.out.println("Pairs");
        this.selection.makeChildren(mode_crossover);
//        System.out.println("MakeChildren");
        this.selection.mutateChilred(mode_mutation);
//        System.out.println("MutateChildren");
//        _evals+=this.num_parents;
        this.selection.evaluateChildren();
//        System.out.println("Evaluate");
        this.population = selection.returnChildren();
        if(lotta) {
            System.out.print(Collections.max(population).getFitness());
            System.out.print(" ");
            System.out.println(Metric.avgDCN(population));
        }
        this.selection.reset();
    }

    public void BentCigar() {

    }

}
