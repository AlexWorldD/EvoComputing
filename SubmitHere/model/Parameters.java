/*
 * Developed by Alex, Lotta, Pratik and Bella during the Evolutionary Computing course at VU University, 2018.
 * Last modified 10/20/18 4:09 PM.
 * Copyright (c) 2018 with 💛 by Group52.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package model;

public class Parameters {
    //    DEBUG FLAGs
    public static boolean debug = false;
    public static boolean lotta = false;
    public static boolean debug_sigma = false;
    //    POPULATION
    public static int population_size = 4;
    public static int new_size = 100;
    public static double update_part = 1;

    //    SELECTION
//    s - selection pressure, 1 < s <= 2
    public static double selection_pressure = 3;
    //    Method for making evolution step:
//    "crowding" - Deterministic Crowding, when children vs. parents
//    "dyn" - Multi Dynamic Selection by Lotta
//    "baseline" - no selection
    public static String method = "cigar";
    //    "SUS" - Stochastic universal sampling
//    "random" - Random selection from the whole population
    public static String selection_parents = "SUS";
    //    Special parameter for DynamicSelection by Lotta
    public static double d_dyn = 0;

    //    CROSSOVER
//    y*alpha + (1-alpha)*x
    public static double alpha = .2;
    public static int split_k = 0;
    //    "wholeA" - Whole Arithmetic recombination
//    "simpleA" - Simple Arithmetic recombination
    public static String mode_crossover = "no";

    //    MUTATION
//    "uncorN" - Uncorrelated N StepMutation
//    "uncor1" - Uncorrelated One StepMutation
    public static String mode_mutation = "uncorN";

    //    INDIVIDUAL
    public static double def_sigma = 0.1;
    public static double def_eps = 0.0001;
    public static double epsMax = 3.0;


    public static int eval_limit;
}
