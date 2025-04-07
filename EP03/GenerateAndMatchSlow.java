/*
 * GenerateAndMatchSlow uses MatchSlow.match(), which is quadratic:
 * 
 * $ java-algs4 GenerateAndMatchSlow  50000  50000 1212021
 * Time to find the matching: 1.038
 * Success!
 * $ java-algs4 GenerateAndMatchSlow 100000 100000 1212021
 * Time to find the matching: 4.132
 * Success!
 * $ java-algs4 GenerateAndMatchSlow 200000 200000 1212021
 * Time to find the matching: 16.597
 * Success!
 * $ 
 */

import pieces.Nut;
import pieces.Bolt;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;

public class GenerateAndMatchSlow {

    public static void main(String[] args) {
	int N = Integer.parseInt(args[0]);
	int t = Integer.parseInt(args[1]);
	long seed = Long.parseLong(args[2]);
	boolean sorted = args.length > 3;

	// N nuts and N bolts
	NutsAndBolts nutsAndBolts = new NutsAndBolts(N, t, seed, sorted);
	// for (int i = 0; i < N; i++) 
	//     StdOut.printf("%3d", i);
	// StdOut.println();
	// nutsAndBolts.printNuts();
	// nutsAndBolts.printBolts();
	
	Stopwatch sw = new Stopwatch();
	int[] p = MatchSlow.match(nutsAndBolts);
	StdOut.println("Time to find the matching: " + sw.elapsedTime());
	// for (int i = 0; i < N; i++) 
	//     StdOut.printf("%3d", p[i]);
	// StdOut.println();

	int i = nutsAndBolts.check(p);
	if (i < 0)
	    StdOut.println("Success!");
	else
	    StdOut.println("Unmatched pair: " + i);
    }
    
} 
