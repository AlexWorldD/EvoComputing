package model;

import static model.UnifiedRandom._rnd;

import java.util.ArrayList;
import java.util.Collections;
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
    public List<Individual> old_parents;
    public List<Individual> cur_parents;
    private List<List<Individual>> cur_pairsP;
    private List<List<Individual>> cur_pairsC;
    public List<Individual> cur_children;

    public Selection() {
        this.cur_parents = new ArrayList<>();
        this.old_parents = new ArrayList<>();
        this.cur_children = new ArrayList<>();
        this.cur_pairsP = new ArrayList<>();
        this.cur_pairsC = new ArrayList<>();
    }

    public Selection(int pop_size, int num_parents) {
        this.population_size = pop_size;
        this.mating_size = num_parents;
        s_l = (2.0 - s) / (double) this.population_size;
        s_r = 2.0 * (s - 1.0) / ((double) (this.population_size * (this.population_size - 1)));
        this.cur_parents = new ArrayList<>();
        this.old_parents = new ArrayList<>();
        this.cur_children = new ArrayList<>();
        this.cur_pairsP = new ArrayList<>();
        this.cur_pairsC = new ArrayList<>();
    }

    public void reset() {
        this.cur_parents = new ArrayList<>();
        this.old_parents = new ArrayList<>();
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
        this.old_parents = this._parentsAllClone(old);
        switch (mode) {
            case "all": {
                this.cur_parents = this.old_parents;
                this.mating_size = this.cur_parents.size();
                break;
            }
            case "random": {
                this.cur_parents = this._parentsRandom();
                break;
            }
            case "SUS": {
                this.cur_parents = this._parentsSUS();
                break;
            }
        }
    }

    /**
     * Stupid cloning the whole parents population, for saving initial one without changes
     *
     * @param p Population
     */
    private List<Individual> _parentsAllClone(List<Individual> p) {
        List<Individual> parents = new ArrayList<Individual>();
        for (int i = 0; i < p.size(); i++) {
            try {
                parents.add(p.get(i).clone());
            } catch (CloneNotSupportedException ex) {
                throw new RuntimeException(ex);
            }
        }
        return parents;

    }

    /**
     * Random parents selection
     */
    private List<Individual> _parentsRandom() {
        List<Individual> parents = new ArrayList<Individual>();
        List<Integer> indexes = new ArrayList<>();
        int randomIndex = 0;
        int _l = 0;
//        indexes.add(randomIndex);
        while (_l < this.mating_size) {
            randomIndex = _rnd.nextInt(this.old_parents.size());
            if (indexes.indexOf(randomIndex) == -1) {
                indexes.add(randomIndex);
                parents.add(this.old_parents.get(randomIndex));
                _l++;
            }

        }
//        Remove selected parents from the old list for further merging
        parents.forEach(item->this.old_parents.remove(item));
        this.cur_parents = parents;
        return parents;
    }

    /**
     * Stochastic universal sampling
     *
     */
    private List<Individual> _parentsSUS() {
        List<Individual> parents = new ArrayList<>();
        Collections.sort(this.old_parents);
        Collections.reverse(this.old_parents);
        List<Double> ranks = new ArrayList<>();
        for (int i = 0; i < this.population_size; i++) {
            ranks.add(s_l + s_r * (this.population_size - i - 1));
        }
//        Getting cumulative probabilities
        double[] cum_probs = new double[this.population_size];
        cum_probs[0] = ranks.get(0);
        for (int i = 1; i < this.population_size; i++) {
            cum_probs[i] = cum_probs[i - 1] + ranks.get(i);
        }
//        Finding potential point for selection
        double[] points = new double[this.mating_size];
        double r = 1.0 / (double) this.mating_size;
        points[0] = _rnd.nextDouble() * r;
        for (int i = 1; i < this.mating_size; i++) {
            points[i] = points[i - 1] + r;
        }
//        Finding indexes with SUS
        for (int i = 0; i < this.mating_size; i++) {
            int l = 0;
            while (cum_probs[l] < points[i]) {
                l++;
            }
            parents.add(this.old_parents.get(l));

        }
        //        Remove selected parents from the old list for further merging
        parents.forEach(item->this.old_parents.remove(item));
        this.cur_parents = parents;
        return parents;

    }

    /**
     * Making pairs from the selected parents
     *
     * @param mode The mode for parents selection: Random, Ranking etc.
     */
    public List<List<Individual>> makePairs(String mode) {
        switch (mode) {
            case "seq": {
                this.cur_pairsP = this._parentsPairSequentially();
                break;
            }
            case "random": {
                this.cur_pairsP = this._parentsPairRandom();
                break;
            }
        }
        return this.cur_pairsP;
    }

    /**
     * Sequential pairs making: [i, i+1] etc
     */
    private List<List<Individual>> _parentsPairSequentially() {
        List<List<Individual>> parents = new ArrayList<>();
        List<Individual> pair;
        for (int i = 0; i < this.mating_size; i = i + 2) {
            pair = new ArrayList<>();
            try {
                pair.add(this.cur_parents.get(i).clone());
                pair.add(this.cur_parents.get(i + 1).clone());

            } catch (CloneNotSupportedException ex) {
                throw new RuntimeException(ex);
            }
            parents.add(pair);

        }
        this.cur_parents.clear();
        return parents;
    }

    /**
     * Random parents pairing
     *
     */
    private List<List<Individual>> _parentsPairRandom() {
        List<List<Individual>> parents = new ArrayList<>();
        List<Individual> pair;
        for (int i = 0; i < this.mating_size; i += 2) {
            pair = new ArrayList<>();
            try {
                int randomIndex = _rnd.nextInt(this.cur_parents.size());
                Individual randomElement = this.cur_parents.get(randomIndex).clone();
                this.cur_parents.remove(randomIndex);
                pair.add(randomElement);
                randomIndex = _rnd.nextInt(this.cur_parents.size());
                randomElement = this.cur_parents.get(randomIndex).clone();
                this.cur_parents.remove(randomIndex);
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
        this.old_parents.addAll(offspring);
        return this.old_parents;
    }

    /**
     * multi select method
     *
     * @param d threshold (dcn below this will decrease fitness to 0
     * @return selection list of size
     */

    public List<Individual> dynSelect(double d, double size, List<Individual> pop) {
        List<Individual> currentMembers = new ArrayList<>();

        //Now: select from children and parents
        this.cur_pairsC.forEach(currentMembers::addAll);
        currentMembers.addAll(pop);
        Individual best = Collections.max(currentMembers);
        System.out.println(best.getFitness());
        Collections.sort(currentMembers);
        Individual lastAdded = best;

        List<Individual> newPop = new ArrayList<Individual>();

        newPop.add(best);

        for (int i = 0; i< currentMembers.size(); i++) {
            currentMembers.get(i).setDcn(Double.MAX_VALUE);
        }
        best.setDcn(0);
        //currentMembers.remove(best);
        System.out.println(newPop.get(0).getDcn());
        while (newPop.size() < size) {
            for (int i = 0;i<currentMembers.size();i++) {
                Individual ind = currentMembers.get(i);
                double dist = Metric.euclDist(lastAdded, ind);
                //System.out.println(ind.getFitness());
                if (dist < ind.getDcn()) {
                    ind.setDcn(dist);
                    //System.out.println(dist);
                }
                if (ind.getDcn() < d) {
                    ind.setFitness(0);
                }
            }
            List<Individual> ndFront = getNDind(currentMembers);
            lastAdded = ndFront.get(_rnd.nextInt(ndFront.size()));
            newPop.add(lastAdded);
            currentMembers.remove(lastAdded);
        }
        return newPop;
    }

    /**
     * get NonDominated front (based on DCN (distance closest neighbour) and fitness)
     *
     * @param p list of individuals from which to choose
     * @return list of nondominated Individuals
     */
    public List<Individual> getNDind(List<Individual> p) {
        List<Individual> maybeDominated = new ArrayList<>(p); // should be sorted on fitness
        List<Individual> nonDominated = new ArrayList<>();
        for (int i = 0; i < maybeDominated.size(); i++) {
            Individual ind1 = maybeDominated.get(i);
            boolean nondominated = true;
            for (int j = 0; j < maybeDominated.size(); j++) {
                Individual ind2 = maybeDominated.get(j);
                if (ind1.getFitness() < ind2.getFitness() & ind1.getDcn() < ind2.getDcn()) {
                    nondominated = false;
                    break;
                }
            }
            if (nondominated) {
                nonDominated.add(ind1);
            }
        }
        return nonDominated;
    }
}
