package model;

public class Parameters {
//    DEBUG FLAGs
    public static boolean debug = false;
    public static boolean debug_sigma = false;
    //    POPULATION
    public static int population_size = 100;
    public static double update_part = 1.0;

    //    SELECTION
//    s - selection pressure, 1 < s <= 2
    public static double selection_pressure = 1.8;
    //    Method for making evolution step:
//    "crowding" - Deterministic Crowding, when children vs. parents
//    "dyn" - Multi Dynamic Selection by Lotta
//    "baseline" - no selection
    public static String method = "baseline";
    //    "SUS" - Stochastic universal sampling
//    "random" - Random selection from the whole population
    public static String selection_parents = "SUS";
    //    Special parameter for DynamicSelection by Lotta
    public static double d_dyn = 0;

    //    CROSSOVER
//    y*alpha + (1-alpha)*x
    public static double alpha = .2;
    public static int split_k = 5;
    //    "wholeA" - Whole Arithmetic recombination
//    "simpleA" - Simple Arithmetic recombination
    public static String mode_crossover = "wholeA";

    //    MUTATION
//    "uncorN" - Uncorrelated N StepMutation
//    "uncor1" - Uncorrelated One StepMutation
    public static String mode_mutation = "uncorN";

    //    INDIVIDUAL
    public static double def_sigma = 1;
    public static double def_eps = 0.001;
    public static double epsMax=4.0;


}
