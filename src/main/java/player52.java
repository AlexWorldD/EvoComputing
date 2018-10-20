import org.vu.contest.ContestEvaluation;
import org.vu.contest.ContestSubmission;
import model.*;

import static model.UnifiedRandom._rnd;
import static model.UnifiedRandom._evals;
import static model.Parameters.*;

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
        eval_limit = evaluations_limit_;
        // Property keys depend on specific evaluation
        // E.g. double param = Double.parseDouble(props.getProperty("property_name"));
        boolean isMultimodal = Boolean.parseBoolean(props.getProperty("Multimodal"));
        boolean hasStructure = Boolean.parseBoolean(props.getProperty("Regular"));
        boolean isSeparable = Boolean.parseBoolean(props.getProperty("Separable"));

//        BentCigar - all false, Schaffers - Multimodal and Regular are true, Katsuura - Multimodal and NotRegular

//         Do sth with property values, e.g. specify relevant settings of your algorithm
        if (isMultimodal && hasStructure) {
//            Schaffers function here
            method = "baseline";
            mode_crossover = "wholeA";
            mode_mutation = "uncorN";
            def_sigma = 0.9;
            selection_pressure = 1.88;
            def_eps = 0.0;
            population_size = 120;
            epsMax = 5.0;
        } else {
            if (isMultimodal) {
//                Katsuura function here
                method = "dyn";
                def_sigma = 0.01;
                selection_pressure = 1.8;
                def_eps = 0.0001;
                population_size = 130;
                epsMax = 4.0;
            } else {
//                BencCigar function here
                method = "cigar";
                def_sigma = 0.1;
                selection_pressure = 3;
                def_eps = 0.0001;
                population_size = 4;
                mode_crossover = "no";
                mode_mutation = "uncorN";
                new_size = 100;
                epsMax = 4.0;
            }
        }
    }

    public static void main(String args[]) {
        System.out.println("Hello There");
    }

    private void setParameters() {
        String tmp = System.getProperty("method");
        if (tmp != null) {
            method = tmp;
        }
        tmp = System.getProperty("popSize");
        if (tmp != null) {
            population_size = (int) Double.parseDouble(tmp);
        }
        tmp = System.getProperty("updSize");
        if (tmp != null) {
            update_part = (double) Double.parseDouble(tmp);
        }
        tmp = System.getProperty("selectionPressure");
        if (tmp != null) {
            selection_pressure = Double.parseDouble(tmp);
        }
        tmp = System.getProperty("eps");
        if (tmp != null) {
            def_eps = (double) Double.parseDouble(tmp);
        }
        tmp = System.getProperty("sigma");
        if (tmp != null) {
            def_sigma = (double) Double.parseDouble(tmp);
        }
        tmp = System.getProperty("alpha");
        if (tmp != null) {
            alpha = (double) Double.parseDouble(tmp);
        }
    }

    public void run() {
        this.setParameters();
        EA ea = new EA(evaluation_);

        if (method.equals("crowding")) {
            while (_evals < evaluations_limit_ - population_size) {
//            System.out.println(_evals);
                ea.crowding();
            }
        }
        if (method.equals("dyn")) {
            while (_evals < evaluations_limit_ - population_size) {
//            System.out.println(_evals);
                ea.dynSelect(evaluations_limit_);
            }
        }
        if (method.equals("baseline")) {
            while (_evals < evaluations_limit_ - population_size) {
                ea.baseline();
            }
        }
        if (method.equals("cigar")) {
            while (_evals < eval_limit - population_size) {
                ea.BentCigar();
            }
        }
    }

}