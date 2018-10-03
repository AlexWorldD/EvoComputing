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

    Selection(double num_parents, double num_childs) {
        this.mu = num_parents;
        this.lambda = num_childs;
    }

    public List<Individual> chooseParents(List<Individual> old, String mode) {
        List<Individual> parents = new ArrayList<Individual>();
        switch (mode) {
            case "random": {
                parents = this._parentsRandom(old);
            }
        }
        return parents;
    }

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

    public List<List<Individual>> makePairs(List<Individual> parents, String mode) {
        List<List<Individual>> pairs = new ArrayList<>();
        switch (mode) {
            case "random": {
                pairs = this._parentsPairSequentially(parents);
            }
        }
        return pairs;
    }

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

    public List<List<Individual>> makeChildren(List<List<Individual>> pairs, String mode) {
        List<List<Individual>> children = new ArrayList<>();
        switch (mode) {
            case "simpleA": {
                Crossover crossover = new Crossover(2, 6, 0.5);
                for (int i = 0; i < pairs.size(); i++) {
//                    TODO change to Array may be??
                    children.add(crossover.SimpleArithmetic(pairs.get(i).get(0), pairs.get(i).get(1)))
                }
            }
        }
        return pairs;
    }


}
