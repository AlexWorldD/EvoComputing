import org.vu.contest.ContestSubmission;
import org.vu.contest.ContestEvaluation;

import java.util.Random;
import java.util.Properties;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

public class player52 implements ContestSubmission
{
	Random rnd_;
	ContestEvaluation evaluation_;
    private int evaluations_limit_;
	public player52()
	{
		rnd_ = new Random();
	}
	
	public void setSeed(long seed)
	{
		// Set seed of algortihms random process
		rnd_.setSeed(seed);
	}

	public void setEvaluation(ContestEvaluation evaluation)
	{
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
        if(isMultimodal){
            // Do sth
        }else{
            // Do sth else
        }
    }

	public void run() {
		// Run your algorithm here
        int populationsize = 10;
        List<Individual> population = new ArrayList<Individual>(populationsize);
        // init population
        for (int i = 0; i < populationsize; i++) {
            Individual ind = new Individual(evaluation_);
            population.add(ind);
        }
        //evolution
        Simple_EA ea = new Simple_EA(evaluation_);
        while (Individual.n_evals < evaluations_limit_) {
            double fitness_sum = 0;
            population = ea.evolve(population);
            System.out.println(Individual.n_evals);
            for(int j=0;j<population.size();j++) {
                Individual ind = population.get(j);
                fitness_sum += ind.getFitness();
            }
            System.out.println(fitness_sum);
        }

	}

}




