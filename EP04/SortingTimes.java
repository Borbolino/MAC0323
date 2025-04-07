/*
 * $ java-algs4 SortingTimes InsertionX 500000 1000 100 88
 * InsertionX: 20.35s
 * Local: 4.99s
 * Local / InsertionX ratio: 0.24
 * $ java-algs4 SortingTimes Heap 1000000 1000 100 88
 * Heap: 13.75s
 * Local: 10.06s
 * Local / Heap ratio: 0.73
 * $ java-algs4 SortingTimes MergeX 1000000 1000 100 88
 * MergeX: 18.94s
 * Local: 10.04s
 * Local / MergeX ratio: 0.53
 * $ java-algs4 SortingTimes QuickX 1000000 1000 100 88
 * QuickX: 20.39s
 * Local: 10.08s
 * Local / QuickX ratio: 0.49
 * $ 
 */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stopwatch;
import edu.princeton.cs.algs4.InsertionX;
import edu.princeton.cs.algs4.MergeX;
import edu.princeton.cs.algs4.QuickX;
import edu.princeton.cs.algs4.Heap;

public class SortingTimes
{
    public static void main(String[] args)
    {
	String alg = args[0];
	int N = Integer.parseInt(args[1]);
	int k = Integer.parseInt(args[2]);
	int T = Integer.parseInt(args[3]);
	long seed = Long.parseLong(args[4]);
	StdRandom.setSeed(seed);

	double tA = 0.0;
	double tL = 0.0;
	Stopwatch sw;
	for (int i = 0; i < T; i++) {
	    Integer[] v = RandomPerm.kPermInteger(N, k);

	    Integer[] c = v.clone();
	    sw = new Stopwatch();
	    if (alg.equals("InsertionX")) InsertionX.sort(c);
	    else if (alg.equals("MergeX")) MergeX.sort(c);
	    else if (alg.equals("QuickX")) QuickX.sort(c);
	    else if (alg.equals("Heap")) Heap.sort(c);
	    else {
		StdOut.println("No such algorithm");
		System.exit(-1);
	    }
	    tA += sw.elapsedTime();

	    c = v.clone();
	    sw = new Stopwatch();
	    SortLocal.sortLocal(v, k);
	    tL += sw.elapsedTime();	    
	}
	StdOut.printf("%s: %.2fs\n", alg, tA);
	StdOut.printf("Local: %.2fs\n", tL);
	StdOut.printf("Local / %s ratio: %.2f\n", alg, tL / tA);
    }
}
