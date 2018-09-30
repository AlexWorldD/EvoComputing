import org.vu.contest.ContestSubmission;
import org.vu.contest.ContestEvaluation;

import java.util.Random;
import java.util.Properties;


public class player52 implements ContestSubmission {
    static public Random rnd_;
    ContestEvaluation evaluation_;
    private int evaluations_limit_;

    public player52() {
        rnd_ = new Random();
    }

    public void setSeed(long seed) {
        // Set seed of algortihms random process
        rnd_.setSeed(seed);
    }

    public void setEvaluation(ContestEvaluation evaluation) {
        // Set evaluation problem used in the run
        evaluation_ = evaluation;

        // Get evaluation properties
        Properties props = evaluation.getProperties();
        // Get evaluation limit
        evaluations_limit_ = Integer.parseInt(props.getProperty("Evaluations"));
        // Property keys depend on specific evaluation
        // E.g. double param = Double.parseDouble(props.getProperty("property_name"));
        boolean isMultimodal = Boolean.parseBoolean(props.getProperty("Multimodal"));
        boolean hasStructure = Boolean.parseBoolean(props.getProperty("Regular"));
        boolean isSeparable = Boolean.parseBoolean(props.getProperty("Separable"));

        // Do sth with property values, e.g. specify relevant settings of your algorithm
        if (isMultimodal) {
            // Do sth
        } else {
            // Do sth else
        }
    }

    public static void main(String args[]) {
        System.out.println("Hello There");
    }

    public void run() {
        int populationsize = 150;
        //Creates a random population of size 'populationsize'
        Simple_EA ea = new Simple_EA(evaluation_, populationsize);

        //this condition could entail that some evaluations are not used...
        while (Individual.n_evals < evaluations_limit_ - populationsize) {
            ea.evolve();
        }
    }

}