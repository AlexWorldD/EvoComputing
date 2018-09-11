import java.util.ArrayList;
import java.util.Random;
import java.util.Collections;
import java.util.List;
import org.vu.contest.ContestEvaluation;

public class Simple_EA {
    ContestEvaluation evaluation;

    public Simple_EA(ContestEvaluation e) {
        this.evaluation = e;
    }

    public List<Individual> evolve(List<Individual> pop) {
        int popsize = pop.size();
        List<Individual> parents = chooseParents(pop, popsize/2);
        List<Individual> children = createChildren(parents, popsize/2);
        List<Individual> newpop = new ArrayList<Individual>();
        newpop.addAll(children);
        newpop.addAll(parents);
        return newpop;
    }

    public List<Individual> chooseParents(List<Individual> pop, int size) {
        List<Individual> parents = new ArrayList<Individual>(size);
        Collections.sort(pop);
        for(int i=0;i<size;i++) {
            parents.add(pop.get(i));
        }
        return parents;
    }

    public List<Individual> createChildren(List<Individual> parents, int size) {
        Random r = new Random();
        List<Individual> children = new ArrayList<Individual>(size);
        for(int i=0;i<size;i++) {
            int index1 = r.nextInt(parents.size());
            int index2 = r.nextInt(parents.size());
            Individual p1 = parents.get(index1);
            Individual p2 = parents.get(index2);
            Individual child = crossover(p1,p2);
            children.add(mutate(child));
        }
        return children;
    }

    public Individual crossover(Individual p1, Individual p2) {
        Random r = new Random();
        double[] childgenes = new double[10];
        for (int i=0;i<10; i++) {
            if (r.nextBoolean()) {
                childgenes[i] = p1.getGenes()[i];
            }
            else {
                childgenes[i] = p2.getGenes()[i];
            }
        }
        return new Individual(evaluation, childgenes);
    }

    public Individual mutate(Individual ind) {
        Random r = new Random();
        double[] genes = ind.getGenes().clone();
        boolean mutated = false;
        for (int i = 0; i< genes.length; i++) {
            if (r.nextInt(100) == 1) {
                genes[i] = -5 + r.nextDouble() * 10;
                mutated = true;
            }
        }
        if (mutated) return new Individual(evaluation, genes);
        else return ind;
    }
}
