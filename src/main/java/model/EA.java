package model;

import java.util.ArrayList;
import java.util.Random;
import static model.UnifiedRandom._rnd;
import static model.UnifiedRandom._evals;
import java.util.List;
import org.vu.contest.ContestEvaluation;

/**
 * Evolutionary algorithm class
 *
 */
public class EA {

    private ContestEvaluation evaluation;
    private List<Individual> population = new ArrayList<>();
    public int population_size;
    private double update_part;
    public int num_parents;


    public EA(ContestEvaluation e, int population_size, double update_part) {
        this.evaluation = e;
        this.population_size = population_size;
        this.update_part = update_part;
        this.num_parents = (int) Math.round(this.population_size*this.update_part);
//        Creating population
        for (int i = 0; i < this.population_size; i++) {
            this.population.add(new Individual(this.evaluation));
        }
    }

    public void crowding() {
        Selection selection = new Selection(this.num_parents);
        selection.chooseParents(this.population, "random");
        selection.makePairs("random");
        selection.makeChildren("wholeA");
        selection.mutateChilred("UncorN");
        selection.evaluateChildren();
        this.population.addAll(selection.crowding());
    }

}
