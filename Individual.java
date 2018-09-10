import java.util.Random;

public class Individual implements Comparable<Individual> {
    private double fitness = -1; //dummy value (fitness is never negative)
    private double[] genes = new double[10];

    public Individual() {
        Random r = new Random();
        for(int i=0; i<10; i++) {
            // random value between -5 and 5
            this.genes[i] = -5 + r.nextDouble()*10;
        }
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    public double getFitness() {
        return fitness;
    }

    public double[] getGenes() {
        return this.genes;
    }

    public int compareTo(Individual other) {
        if (this.fitness < other.fitness) return -1;
        if (this.fitness > other.fitness) return 1;
        else return 0;
    }
}
