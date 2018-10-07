package model;

import static model.UnifiedRandom._rnd;

import java.util.ArrayList;
import java.util.List;

public class Selection {

    public static enum Management {
        GENERATION_MODEL,
        STEADE_STATE_MODEL
    }

    private double lambda;
    private double mu;
    //    TODO is it require to have these structure inside the class or just use the static methods?
    private List<Individual> cur_parents;
    private List<List<Individual>> cur_pairsP;
    private List<List<Individual>> cur_pairsC;

    Selection(double num_parents, double num_childs) {
        this.mu = num_parents;
        this.lambda = num_childs;
        this.cur_parents = new ArrayList<>();
        this.cur_pairsP = new ArrayList<>();
        this.cur_pairsC = new ArrayList<>();
    }

    /**
     * Choosing parents from the whole population
     *
     * @param old  Population
     * @param mode The mode for parents selection: Random, Ranking etc.
     */
    void chooseParents(List<Individual> old, String mode) {
        List<Individual> parents = new ArrayList<Individual>();
        switch (mode) {
            case "random": {
                this.cur_parents = this._parentsRandom(old);
            }
        }
    }

    /**
     * Random parents selection
     *
     * @param p Population
     */
    private List<Individual> _parentsRandom(List<Individual> p) {
        List<Individual> parents = new ArrayList<Individual>();
        for (int i = 0; i < this.mu; i++) {
            int randomIndex = _rnd.nextInt(p.size());
            Individual randomElement = p.get(randomIndex);
            p.remove(randomIndex);
            parents.add(randomElement);
        }
        return parents;
    }

    /**
     * Making pairs from the selected parents
     *
     * @param mode The mode for parents selection: Random, Ranking etc.
     */
    public List<List<Individual>> makePairs(String mode) {
        List<List<Individual>> pairs = new ArrayList<>();
        switch (mode) {
            case "random": {
                this.cur_pairsP = this._parentsPairSequentially(this.cur_parents);
            }
        }
        return pairs;
    }

    /**
     * Sequential pairs making: [i, i+1] etc
     *
     * @param p Population
     */
    private List<List<Individual>> _parentsPairSequentially(List<Individual> p) {
        List<List<Individual>> parents = new ArrayList<>();
        List<Individual> pair = new ArrayList<>();
        for (int i = 0; i < this.mu; i = i + 2) {
            pair.add(p.get(i));
            pair.add(p.get(i + 1));
            parents.add(pair);
            pair.clear();
        }
        return parents;
    }

    /**
     * Making pairs of children
     *
     * @param mode The mode for crossover: SimpleArithmetic, SingleArithmetic etc
     */
    void makeChildren(String mode, Boolean mutate) throws Exception {
        List<List<Individual>> children = new ArrayList<>();
        switch (mode) {
            case "simpleA": {
                final Crossover crossover = new Crossover(2, 6, 0.5);
                this.cur_pairsP.forEach(item -> children.add(crossover.SimpleArithmetic(item)));
//                for (int i = 0; i < pairs.size(); i++) {
////                    TODO change to Array may be??
//                    children.add(crossover.SimpleArithmetic(pairs.get(i).get(0), pairs.get(i).get(1)));
//                }
            }
        }
        if (mutate) {

        }
        this.cur_pairsC = children;
    }


}
