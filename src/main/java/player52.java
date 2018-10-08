import org.vu.contest.ContestEvaluation;
import org.vu.contest.ContestSubmission;
import model.*;

import static model.UnifiedRandom._rnd;
import static model.UnifiedRandom._evals;

import java.util.Properties;


public class player52 implements ContestSubmission {
    ContestEvaluation evaluation_;
    private int evaluations_limit_;

    public player52() {
// What we should do here?
    }

    public void setSeed(long seed) {
        _rnd.setSeed(seed);
    }

    public void setEvaluation(ContestEvaluation evaluation) {
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
        double update = 0.2;
        //Creates a random population of size 'populationsize'
        EA ea = new EA(evaluation_, populationsize, update);

        //this condition could entail that some evaluations are not used...
        while (_evals < evaluations_limit_ - populationsize) {
            ea.crowding();
        }
    }

}