import java.util.ArrayList;
import java.util.Random;
import java.util.Collections;
import java.util.List;
import org.vu.contest.ContestEvaluation;

public class Simple_EA {
    private ContestEvaluation evaluation;
    private List<Individual> population;
    private Random r;
    private int populationsize;

    public Simple_EA(ContestEvaluation e, int populationsize) {
        this.evaluation = e;
        this.populationsize = populationsize;
        this.r = new Random();
        this.population = new ArrayList<Individual>(populationsize);
        for (int i = 0; i < populationsize; i++) {
            Individual ind = new Individual(e);
            population.add(ind);
        }
    }

    public void evolve() {
    //This only works with even populationsize!
        List<Individual> parents = chooseParents(populationsize/2);
        List<Individual> children = createChildren(parents, populationsize/2);
        List<Individual> newpop = new ArrayList<Individual>();
        newpop.addAll(children);
        newpop.addAll(parents);
        this.population = newpop;
    }

    public List<Individual> chooseParents(int size) {
        List<Individual> parents = new ArrayList<Individual>(size);
        Collections.sort(this.population);
        for(int i=0;i<size;i++) {
            parents.add(this.population.get(i));
        }
        return parents;
    }

    public List<Individual> createChildren(List<Individual> parents, int size) {
        List<Individual> children = new ArrayList<Individual>(size);
        for(int i=0;i<size;i++) {
            int index1 = r.nextInt(parents.size());
            int index2 = r.nextInt(parents.size());
            Individual p1 = parents.get(index1);
            Individual p2 = parents.get(index2);
            double[] childgenes = mutate(crossover(p1,p2));
            children.add(new Individual(evaluation, childgenes));
        }
        return children;
    }

    public double[] crossover(Individual p1, Individual p2) {
        double[] childgenes = new double[10];
        for (int i=0;i<10; i++) {
            if (r.nextBoolean()) {
                childgenes[i] = p1.getGenes()[i];
            }
            else {
                childgenes[i] = p2.getGenes()[i];
            }
        }
        return childgenes;
    }

    public double[] mutate(double[] genes) {
        for (int i = 0; i< genes.length; i++) {
            if (r.nextInt(50) == 1) {
                genes[i] = -5 + r.nextDouble() * 10;
            }
        }
        return genes;
    }
}
