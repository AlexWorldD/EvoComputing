/*
 * Developed by Alex, Lotta, Pratik and Bella during the Evolutionary Computing course at VU University, 2018.
 * Last modified 10/14/18 1:37 PM.
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

import java.util.Date;

//import com.sun.xml.internal.xsom.impl.scd.Iterators;
import org.vu.contest.ContestEvaluation;


public class TestRun {
    static String[] evals = {"BentCigarFunction",
            "KatsuuraEvaluation", "SchaffersEvaluation", "SphereEvaluation"};

    public static void main(String args[]) {
        player52_old ver1 = new player52_old();
        int seed = 0;
        String eval_name = evals[2];
        if (eval_name == null) {
            throw new Error("Evaluation ID was not specified! Cannot run...\n Use -evaluation=<classnamehere> to specify the name of the evaluation class.");
        } else {
            Class eval_class = null;
            try {
                eval_class = Class.forName(eval_name);
            } catch (Throwable er) {
                System.err.println("Could not load evaluation class for evaluation '" + eval_name + "'");
                er.printStackTrace();
                System.exit(1);
            }
            ContestEvaluation eval = null;

            try {
                eval = (ContestEvaluation) eval_class.newInstance();
            } catch (Throwable var21) {
                System.err.println("ExecutionError: Could not instantiate evaluation object for evaluation '" + eval_name + "'");
                var21.printStackTrace();
                System.exit(1);
            }
            ver1.setSeed(seed);
            ver1.setEvaluation(eval);
//            Timing
            Date time = new Date();
            long st = time.getTime();
            try {
                ver1.run();
            } catch (Throwable er2) {
                er2.printStackTrace();
                System.out.println("Your code has thrown an Exception/Error!");
            }
            time = new Date();
            long var15 = time.getTime() - st;
            System.out.println("Evaluation function: " + eval_name);
            System.out.println("Score: " + Double.toString(eval.getFinalResult()));
            System.out.println("Runtime: " + var15 + "ms");
            System.exit(0);
        }
    }
}
