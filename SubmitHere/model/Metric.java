/*
 * Developed by Alex, Lotta, Pratik and Bella during the Evolutionary Computing course at VU University, 2018.
 * Last modified 10/21/18 12:03 AM.
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

import java.util.List;

public class Metric {
    /**
     * Calculation of the Euclidean distance between two individuals
     * @param l First individual
     * @param r Second individual
     * @return Euclidean distance
     */
    public static double euclDist(Individual l, Individual r) {
        double sum = 0;
        for (int i = 0; i < 10; i++) {
            sum += Math.pow(l.getGene(i) - r.getGene(i), 2);
        }
        return Math.sqrt(sum);
    }

    /**
     * Calculation of the average distance to closest neighbour
     * @param pop Current population
     * @return Average distance
     */
    public static double avgDCN(List<Individual> pop) {
        double sum = 0;
        for (int i=0; i<pop.size(); i++) {
            double dcn = Double.MAX_VALUE;
            for (int j =0; j<pop.size();j++) {
                if (j!= i) {
                    double dist = euclDist(pop.get(i),pop.get(j));
                    if (dist < dcn) {
                        dcn = dist;

                    }
                }
            }
            sum += dcn;
        }
        return sum/pop.size();
    }

    /**
     * Calculation of the average of average distance to another individual seeing from one individual
     * @param pop Current population
     * @return Average distance
     */
    public static double avgDist(List<Individual> pop) {
        double sum = 0;
        for (int i=0; i<pop.size(); i++) {
            double distsum =0;
            for (int j =0; j<pop.size();j++) {
                if (j!= i) {
                    double dist = euclDist(pop.get(i),pop.get(j));
                    distsum += dist;
                }
            }
            sum += distsum/pop.size();
        }
        return sum/pop.size();
    }
}