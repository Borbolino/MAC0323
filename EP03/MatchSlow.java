/*
 * Slow match (quadratic).  Example runs of GenerateAndMatchSlow with this match:
 * 
 * $ java-algs4 GenerateAndMatchSlow  50000  50000 1212021
 * Time to find the matching: 3.98
 * Success!
 * $ java-algs4 GenerateAndMatchSlow 100000 100000 1212021
 * Time to find the matching: 15.802
 * Success!
 * $ java-algs4 GenerateAndMatchSlow 200000 200000 1212021
 * Time to find the matching: 63.329
 * Success!
 * $ 
 */

import pieces.Nut;
import pieces.Bolt;

public class MatchSlow {

    public static int[] match(NutsAndBolts nab) {
	int N = nab.N();
	int[] p = new int[N];
	boolean[] hit = new boolean[N];
	for (int i = 0; i < N; i++) 
	    for (int j = 0; j < N; j++) 
		if (nab.nuts(i).compareTo(nab.bolts(j)) == 0 && !hit[j]) {
		    p[i] = j;
		    hit[j] = true;
		    break;
		}
	return p;
    }
} 
