import java.util.Random;
import org.vu.contest.ContestEvaluation;

public class Individual implements Comparable<Individual> {
    static int n_evals = 0;
    private double fitness = 0;
    private double[] genes = new double[10];
    private double[] stepsize = new double[10];

    public Individual(ContestEvaluation e) {
        Random r = new Random();
        for(int i=0; i<10; i++) {
            // random value between -5 and 5
            this.genes[i] = -5 + r.nextDouble() * 10;
            this.stepsize[i] = 0.8;
        }
        setFitness(e);
    }

    public Individual(ContestEvaluation e, double[] genes, double[] stepsize) {
        this.genes = genes;
        this.stepsize = stepsize;
        setFitness(e);
    }


    //Used when creating an Individual
    public void setFitness(ContestEvaluation e) {
        this.fitness = (double) e.evaluate(this.genes);
        n_evals++;
    }


    public double getFitness() {
        return this.fitness;
    }

    public double[] getGenes() {
        return this.genes;
    }
    
    public double[] getStepsize() {
    	return this.stepsize;
    }

    public int compareTo(Individual other) {
        if (this.fitness > other.fitness) return -1;
        if (this.fitness < other.fitness) return 1;
        else return 0;
    }
}
