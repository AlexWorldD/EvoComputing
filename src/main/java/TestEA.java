import model.Individual;
import org.vu.contest.ContestEvaluation;

import java.util.Date;
import model.*;

//import com.sun.xml.internal.xsom.impl.scd.Iterators;


public class TestEA {
    static String[] evals = {"BentCigarFunction",
            "KatsuuraEvaluation", "SchaffersEvaluation", "SphereEvaluation"};

    public static void main(String args[]) {
        String name = evals[0];
        Class eval_class = null;
        ContestEvaluation eval = null;
        try {
            eval_class = Class.forName(name);
        } catch (Throwable er) {
            System.err.println("Could not load evaluation class for evaluation '" + name + "'");
            er.printStackTrace();
            System.exit(1);
        }
        try {
            eval = (ContestEvaluation) eval_class.newInstance();
        } catch (Throwable var21) {
            System.err.println("ExecutionError: Could not instantiate evaluation object for evaluation '" + name + "'");
            var21.printStackTrace();
            System.exit(1);
        }
        Individual a = new Individual(eval);
        Individual b = new Individual(eval);
        Crossover crossover = new Crossover(2, 6, 0.5);
        try {
            crossover.SimpleArithmetic(a,b);
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        System.out.println("Hello There");

    }
}
