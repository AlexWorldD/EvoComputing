import java.util.ArrayList;
import java.util.Random;
import java.util.Collections;
import java.util.List;
import org.vu.contest.ContestEvaluation;

import com.sun.corba.se.impl.javax.rmi.CORBA.Util;

public class Simple_EA {
	// Uncorrelated self-adaptive mutation parameter
	public double tau = 5*Math.sqrt(1/20.0);
	public double tauPrime = 5*Math.pow(1/40.0, 0.25);
	// threshold for step sizes for self-adaptive mutation
	public double epsilon = 0.3;
	// Constants used for linear rank based parent selection
	public double s = 1.5;
	public static double c1;
	public static double c2;
	// Constant used for exponential parent selection pressure rank based
	public static double c3;
	
    private ContestEvaluation evaluation;
    private List<Individual> population;
    private Random r;
    public Random r2;
    private int populationsize;

    public Simple_EA(ContestEvaluation e, int populationsize) {
        this.evaluation = e;
        this.populationsize = populationsize;
        c1 = (2-s)/(double)(populationsize);
        c2 = 2*(s-1)/(double)(populationsize*(populationsize-1));
        c3 = populationsize - ((Math.exp(-1*populationsize)-1)/(double)(Math.exp(-1)-1));
        this.r = new Random();
        this.r2 = new Random();
        this.population = new ArrayList<Individual>(populationsize);
        for (int i = 0; i < populationsize; i++) {
            Individual ind = new Individual(e);
            population.add(ind);
        }
    }

    public void evolve() {
    //This only works with even populationsize!
        List<Individual> parents = chooseParents((int)(populationsize*2/3.0));
        List<Individual> children = createChildren(parents, populationsize);
        List<Individual> newpop = new ArrayList<Individual>();
        newpop.addAll(children);
        this.population = newpop;
    }
    
	public static double probParent(int rank) {
		return (1-Math.exp(-1*rank))/(double)c3;
//		return (c1 + c2*rank);
	}
	
	public static double sumProbParent(int index, List<Integer> popList) {
		double probSum = 0;
		for(int j = 0; j<=index; j++) {
			probSum = probSum + probParent(popList.get(j));
		}
		return probSum;
	}
    
	public static int[] SUS(int lambda, List<Integer> popList) {
		double p = 1.0/(double)lambda;
		double start = Math.random()*p;
		double[] points = new double[lambda];
		for(int z = 0; z<lambda; z++) {
			points[z] = start + z*p;
		}
		return RWS(popList, points);
	}
	
	public static int[] RWS(List<Integer> popList, double[] points) {
		int[] parentList = new int[points.length];
		for(int y = 0; y<points.length; y++) {
			int l = 0;
			while (sumProbParent(l, popList) < points[y]) {
				l++;
			}
			parentList[y] = popList.get(l); 
		}
		return parentList;
	}

    public List<Individual> chooseParents(int size) {
    	Collections.sort(this.population);
    	List<Integer> populationList = new ArrayList<>();
    	for(int g=0; g<this.populationsize; g++) {
    		populationList.add(this.populationsize-1-g);
    	}
    	Collections.shuffle(populationList);
    	int[] parentList = SUS(size,populationList);
        List<Individual> parents = new ArrayList<Individual>(size);
   
        for(int i=0;i<size;i++) {
            parents.add(this.population.get(this.populationsize-1-parentList[i]));
        }
        return parents;
    }

    public List<Individual> createChildren(List<Individual> parents, int size) {
        List<Individual> children = new ArrayList<Individual>(size);
        for(int i=0;i<size;i=i+2) {
            Individual p1 = parents.get(i);
            Individual p2 = parents.get(i+1);
            List<Individual> childList = crossover(p1, p2);
            children.add(mutate(childList.get(0)));
            children.add(mutate(childList.get(1)));
            children.add(mutate(childList.get(2)));
        }
        return children;
    }

    public List<Individual> crossover(Individual p1, Individual p2) {
        double[] child1genes = new double[10];
        double[] child1stepsize = new double[10];
        double[] child2genes = new double[10];
        double[] child2stepsize = new double[10];
        double[] child3genes = new double[10];
        double[] child3stepsize = new double[10];
        double alpha = Math.random();
        double fit_ratio = p1.getFitness()/(p1.getFitness()+p2.getFitness());        
        for (int i=0;i<10; i++) {
        	child1genes[i] = alpha*p1.getGenes()[i] + (1-alpha)*p2.getGenes()[i];
        	child1stepsize[i] = alpha*p1.getStepsize()[i] + (1-alpha)*p2.getStepsize()[i];
        	child2genes[i] = alpha*p2.getGenes()[i] + (1-alpha)*p1.getGenes()[i];
        	child2stepsize[i] = alpha*p2.getStepsize()[i] + (1-alpha)*p1.getStepsize()[i];
        	child3genes[i] = fit_ratio*p1.getGenes()[i] + (1-fit_ratio)*p2.getGenes()[i];
        	child3stepsize[i] = fit_ratio*p1.getStepsize()[i] + (1-fit_ratio)*p2.getStepsize()[i];
        }
        List<Individual>children = new ArrayList<>(3);
        children.add(new Individual(this.evaluation, child1genes, child1stepsize ));
        children.add(new Individual(this.evaluation, child2genes, child2stepsize ));
        children.add(new Individual(this.evaluation, child3genes, child3stepsize ));
        return children;
    }

    public Individual mutate(Individual c1) {
    	double[] genes = new double [10];
    	double[] stepsizes = new double[10];
    	genes = c1.getGenes();
    	stepsizes = c1.getStepsize();
    	double random1 = r.nextGaussian();
        for (int i = 0; i< genes.length; i++) {
        	stepsizes[i] = stepsizes[i]*Math.exp(this.tauPrime*random1 + this.tau*r2.nextGaussian());  
            if (stepsizes[i] < this.epsilon) {
            	stepsizes[i] = this.epsilon;
            }
        	genes[i] = genes[i] + stepsizes[i]*r2.nextGaussian();
        }
        return new Individual(this.evaluation, genes, stepsizes);
    }
}
