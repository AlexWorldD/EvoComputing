import java.util.Random;
import org.vu.contest.ContestEvaluation;

public class Individual implements Comparable<Individual> {
    static int n_evals = 0;
    private Double fitness = null;
    private double[] genes = new double[10];

    public Individual(ContestEvaluation e) {
        Random r = new Random();
        for(int i=0; i<10; i++) {
            // random value between -5 and 5
            this.genes[i] = -5 + r.nextDouble() * 10;
        }
        setFitness(e);
    }

    public Individual(ContestEvaluation e, double[] genes) {
        this.genes = genes;
        setFitness(e);
    }


    //Used when creating an Individual
    private void setFitness(ContestEvaluation e) {
        this.fitness = (double) e.evaluate(this.genes);
        n_evals++;
    }


    public Double getFitness() {
        return this.fitness;
    }

    public double[] getGenes() {
        return this.genes;
    }

    public double euclDist(Individual other) {
        double[] othergenes = other.getGenes();
        double sum = 0;
        for (int i=0;i<10;i++) {
           sum += Math.pow(othergenes[i]-this.genes[i],2);
        }
        return Math.sqrt(sum);
    }

    public int compareTo(Individual other) {
        if (this.fitness > other.fitness) return -1;
        if (this.fitness < other.fitness) return 1;
        else return 0;
    }
}
