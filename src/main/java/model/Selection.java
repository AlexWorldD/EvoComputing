package model;

import org.vu.contest.ContestEvaluation;

import static model.UnifiedRandom._rnd;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.function.Function;

public class Selection {

    public static enum Management {
        GENERATION_MODEL,
        STEADE_STATE_MODEL
    }

    private int mu;
    //    TODO is it require to have these structure inside the class or just use the static methods?
    public List<Individual> cur_parents;
    private List<List<Individual>> cur_pairsP;
    private List<List<Individual>> cur_pairsC;

    public Selection() {
        this.cur_parents = new ArrayList<>();
        this.cur_pairsP = new ArrayList<>();
        this.cur_pairsC = new ArrayList<>();
    }

    public Selection(int num_parents) {
        this.mu = num_parents;
        this.cur_parents = new ArrayList<>();
        this.cur_pairsP = new ArrayList<>();
        this.cur_pairsC = new ArrayList<>();
    }

    public void reset() {
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
    public void chooseParents(List<Individual> old, String mode) {
        switch (mode) {
            case "all": {
                this.cur_parents = old;
                this.mu = old.size();
                break;
            }
            case "random": {
                this.cur_parents = this._parentsRandom(old);
                break;
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
        List<Integer> indexes = new ArrayList<>();
        int randomIndex = 0;
        int _l = 0;
//        indexes.add(randomIndex);
        while (_l < this.mu) {
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
        for (int i = 0; i < this.mu; i = i + 2) {
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
        for (int i = 0; i < this.mu; i += 2) {
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
        return offspring;
    }

    /**
     * multi select method
     *
     * @param p list of individuals from which to do survivor selection (for example parents + children)
     * @param d threshold (dcn below this will decrease fitness to 0
     * @return selection list of size mu
     */

    public List<Individual> dynSelect(List<Individual> p, double d) {
        List<Individual> currentMembers = new ArrayList<Individual>(p);
        Collections.sort(currentMembers);
        Individual best = currentMembers.get(0);
        currentMembers.remove(0);
        List<Individual> newPop = new ArrayList<Individual>();
        newPop.add(best);
        Individual lastAdded = best;
        while (newPop.size() < mu) {
            for (int i = 0;i<currentMembers.size();i++) {
                Individual ind = currentMembers.get(i);
                double dist = Metric.euclDist(lastAdded, ind);
                if (dist < ind.getDcn()) {
                    ind.setDcn(dist);
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
            for (int j = i; j < maybeDominated.size(); j++) {
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
