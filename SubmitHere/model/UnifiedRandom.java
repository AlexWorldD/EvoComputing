/*
 * Developed by Alex, Lotta, Pratik and Bella during the Evolutionary Computing course at VU University, 2018.
 * Last modified 10/16/18 6:48 PM.
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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class UnifiedRandom {
    public static Random _rnd = new Random();
    public static Random _rnd2 = new Random();
    //    public static Random[] _randoms = new Random[Individual.num_genes];
    public static List<Random> _randoms = new ArrayList<>();
    //    Counter for elapsed evaluations
    public static int _evals = 0;

    public static void makeRandoms() {
        for (int i = 0; i < Individual.num_genes; i++) {
            _randoms.add(new Random());
        }
    }

    UnifiedRandom(long seed) {
        _rnd.setSeed(seed);
    }

    UnifiedRandom(double seed) {
        _rnd.setSeed((long) seed);
    }

}
