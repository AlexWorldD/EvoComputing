/*
 * Developed by Alex, Lotta, Pratik and Bella during the Evolutionary Computing course at VU University, 2018.
 * Last modified 10/21/18 12:04 AM.
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

import org.vu.contest.ContestEvaluation;
import static model.UnifiedRandom._evals;
import static model.Parameters.*;
import java.util.Date;
import java.util.Properties;

import model.*;



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
            Properties props = eval.getProperties();
            // Get evaluation limit
            eval_limit = Integer.parseInt(props.getProperty("Evaluations"));
        } catch (Throwable var21) {
            System.err.println("ExecutionError: Could not instantiate evaluation object for evaluation '" + name + "'");
            var21.printStackTrace();
            System.exit(1);
        }

        Date time = new Date();
        long st = time.getTime();
        EA ea = new EA(eval);
        if (method.equals("crowding")) {
            while (_evals < eval_limit - population_size) {
//            System.out.println(_evals);
                ea.crowding();
            }
        }
        if (method.equals("dyn")) {
            while (_evals < eval_limit - population_size) {
                ea.dynSelect(eval_limit);
            }
        }
        if (method.equals("baseline")) {
            while (_evals < eval_limit - population_size) {
                ea.baseline();
            }
        }
        if (method.equals("cigar")) {
            while (_evals < eval_limit - population_size) {
                ea.BentCigar();
            }
        }
        time = new Date();
        long var15 = time.getTime() - st;
        System.out.println("Evaluation function: " + name);
        System.out.println("Score: " + Double.toString(eval.getFinalResult()));
        System.out.println("Runtime: " + var15 + "ms");
        System.exit(0);

    }
}
