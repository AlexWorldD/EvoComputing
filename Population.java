import java.util.ArrayList;
import java.util.Collections;

public class Population {
    private int size;
    private ArrayList<Individual> individuals;

    public Population(int size) {
        this.size = size;
        individuals = new ArrayList<Individual>();
        for (int i=0; i<size; i++) {
            individuals.add(new Individual());
        }
    }

    public void sortByFitness() {
        Collections.sort(this.individuals);
    }

}
