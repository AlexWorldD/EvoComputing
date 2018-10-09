package model;

import static model.UnifiedRandom._rnd;

import java.util.ArrayList;
import java.util.List;

public class Selection {

    public static enum Management {
        GENERATION_MODEL,
        STEADE_STATE_MODEL
    }

    private int population_size;
    private int mating_size;
//    Constants for SUS parents selection
//    s - selection pressure, 1 < s <= 2
    private double s = 1.8;
//    Constants for linear rank based selection
    public static double s_l;
    public static double s_r;

    //    TODO is it require to have these structure inside the class or just use the static methods?
    public List<Individual> cur_parents;
    private List<List<Individual>> cur_pairsP;
    private List<List<Individual>> cur_pairsC;
    public List<Individual> cur_children;

    public Selection() {
        this.cur_parents = new ArrayList<>();
        this.cur_children = new ArrayList<>();
        this.cur_pairsP = new ArrayList<>();
        this.cur_pairsC = new ArrayList<>();
    }

    public Selection(int pop_size, int num_parents) {
        this.population_size = pop_size;
        this.mating_size = num_parents;
        s_l = (2.0-s)/(double)this.population_size;
        s_r = 2.0*(s-1.0)/((double)(this.population_size*(this.population_size-1)));
        this.cur_parents = new ArrayList<>();
        this.cur_children = new ArrayList<>();
        this.cur_pairsP = new ArrayList<>();
        this.cur_pairsC = new ArrayList<>();
    }

    public void reset() {
        this.cur_parents = new ArrayList<>();
        this.cur_children = new ArrayList<>();
        this.cur_pairsP = new ArrayList<>();
        this.cur_pairsC = new ArrayList<>();
    }
    /**
     * Choosing parents from the whole population
     *
     * @param old  Population
     * @param mode The mode for parents selection: Random, Ranking etc.
     */
    public void chooseParents(List<Individual> old, String mode) {
        switch (mode) {
            case "all": {
                this.cur_parents = this._parentsAllClone(old);
                this.mating_size = this.cur_parents.size();
                break;
            }
            case "random": {
                this.cur_parents = this._parentsRandom(old);
                break;
            }
        }
    }
    /**
     * Stupid cloning the whole parents population, for saving initial one without changes
     *
     * @param p Population
     */
    private List<Individual> _parentsAllClone(List<Individual> p)  {
        List<Individual> parents = new ArrayList<Individual>();
        for (int i=0; i<p.size(); i++) {
            try {
                parents.add(p.get(i).clone());
            }
            catch (CloneNotSupportedException ex) {
                throw new RuntimeException(ex);
            }
        }
        return parents;

    }
    /**
     * Random parents selection
     *
     * @param p Population
     */
    private List<Individual> _parentsRandom(List<Individual> p) {
        List<Individual> parents = new ArrayList<Individual>();
        List<Integer> indexes = new ArrayList<>();
        int randomIndex = 0;
        int _l = 0;
//        indexes.add(randomIndex);
        while (_l < this.mating_size) {
            randomIndex = _rnd.nextInt(p.size());
            if (indexes.indexOf(randomIndex) == -1) {
                indexes.add(randomIndex);
                try {
                    parents.add(p.get(randomIndex).clone());
//                    p.remove(randomIndex);
                } catch (CloneNotSupportedException ex) {
                    throw new RuntimeException(ex);
                }
                _l++;
            }

        }
        this.cur_parents = parents;
        return this.cur_parents;
    }

    /**
     * Making pairs from the selected parents
     *
     * @param mode The mode for parents selection: Random, Ranking etc.
     */
    public List<List<Individual>> makePairs(String mode) {
        switch (mode) {
            case "seq": {
                this.cur_pairsP = this._parentsPairSequentially(this.cur_parents);
                break;
            }
            case "random": {
                this.cur_pairsP = this._parentsPairRandom(this.cur_parents);
                break;
            }
        }
        return this.cur_pairsP;
    }

    /**
     * Sequential pairs making: [i, i+1] etc
     *
     * @param p Population
     */
    private List<List<Individual>> _parentsPairSequentially(List<Individual> p) {
        List<List<Individual>> parents = new ArrayList<>();
        List<Individual> pair;
        for (int i = 0; i < this.mating_size; i = i + 2) {
            pair = new ArrayList<Individual>();
            pair.add(p.get(i));
            pair.add(p.get(i + 1));
            parents.add(pair);

        }
        return parents;
    }

    /**
     * Random parents pairing
     *
     * @param p Population
     */
    private List<List<Individual>> _parentsPairRandom(List<Individual> p) {
        List<List<Individual>> parents = new ArrayList<>();
        List<Individual> pair;
        for (int i = 0; i < this.mating_size; i += 2) {
            pair = new ArrayList<>();
            try {
                int randomIndex = _rnd.nextInt(p.size());
                Individual randomElement = p.get(randomIndex).clone();
                p.remove(randomIndex);
                pair.add(randomElement);
                randomIndex = _rnd.nextInt(p.size());
                randomElement = p.get(randomIndex).clone();
                p.remove(randomIndex);
                pair.add(randomElement);
            } catch (CloneNotSupportedException ex) {
                throw new RuntimeException(ex);
            }
            parents.add(pair);

        }
        return parents;
    }

    /**
     * Making pairs of children
     *
     * @param mode The mode for crossover: SimpleArithmetic, SingleArithmetic etc
     */
//    TODO add meaningful arguments?
    public void makeChildren(String mode) {
        final Crossover crossover = new Crossover(2, 5, 0.5);
        switch (mode) {
            case "simpleA": {
                this.cur_pairsP.forEach(item -> cur_pairsC.add(crossover.SimpleArithmetic(item)));
                break;
            }
            case "wholeA": {
                this.cur_pairsP.forEach(item -> cur_pairsC.add(crossover.WholeArithmetic(item)));
                break;
            }
        }
    }

    /**
     * Mutation for the set of children
     *
     * @param mode The mode for crossover: UncorrelatedNStep etc
     */
    public void mutateChilred(String mode) {
        final Mutation mutation = new Mutation();
        switch (mode) {
            case "UncorN": {
                this.cur_pairsC.forEach(item -> item.forEach(item2 -> mutation.UncorrelatedNStepMutation(item2)));
                break;
            }
        }


    }

    public void evaluateChildren() {
        this.cur_pairsC.forEach(item -> item.forEach(item2 -> item2.updFitness()));
    }


    public List<Individual> crowding() {
        List<Individual> offspring = new ArrayList<Individual>();
//        this.chooseParents(p, "random");
//        this.makePairs("random");
//        this.makeChildren("wholeA");
//        this.mutateChilred("UncorN");
//        this.evaluateChildren();
        for (int i = 0; i < this.cur_pairsC.size(); i++) {
            if (Metric.euclDist(this.cur_pairsP.get(i).get(0), this.cur_pairsC.get(i).get(0)) +
                    Metric.euclDist(this.cur_pairsP.get(i).get(1), this.cur_pairsC.get(i).get(1)) <
                    Metric.euclDist(this.cur_pairsP.get(i).get(0), this.cur_pairsC.get(i).get(1)) +
                            Metric.euclDist(this.cur_pairsP.get(i).get(1), this.cur_pairsC.get(i).get(0))) {
                if (this.cur_pairsC.get(i).get(0).getFitness() < this.cur_pairsP.get(i).get(0).getFitness()) {
                    try {
                        offspring.add(this.cur_pairsP.get(i).get(0).clone());
                    } catch (CloneNotSupportedException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    try {
                        offspring.add(this.cur_pairsC.get(i).get(0).clone());
                    } catch (CloneNotSupportedException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                if (this.cur_pairsC.get(i).get(1).getFitness() < this.cur_pairsP.get(i).get(1).getFitness()) {
                    try {
                        offspring.add(this.cur_pairsP.get(i).get(1).clone());
                    } catch (CloneNotSupportedException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    try {
                        offspring.add(this.cur_pairsC.get(i).get(1).clone());
                    } catch (CloneNotSupportedException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            } else {
                if (this.cur_pairsC.get(i).get(0).getFitness() < this.cur_pairsP.get(i).get(1).getFitness()) {
                    try {
                        offspring.add(this.cur_pairsP.get(i).get(1).clone());
                    } catch (CloneNotSupportedException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    try {
                        offspring.add(this.cur_pairsC.get(i).get(0).clone());
                    } catch (CloneNotSupportedException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                if (this.cur_pairsC.get(i).get(1).getFitness() < this.cur_pairsP.get(i).get(0).getFitness()) {
                    try {
                        offspring.add(this.cur_pairsP.get(i).get(0).clone());
                    } catch (CloneNotSupportedException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    try {
                        offspring.add(this.cur_pairsC.get(i).get(1).clone());
                    } catch (CloneNotSupportedException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }

        }
        this.cur_parents.addAll(offspring);
        return this.cur_parents;
    }

}
