/*
 * This is a client for LinearProbingHashSTX.java, which is a little extension
 * of LinearProbingHashST.java: some extra functions are implemented in 
 * LinearProbingHashSTX.java so that we can check some statistics about the
 * current status of the hash table.
 * 
 * Examples executions of this program appear at the end of this file.
 */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.ST;
import edu.princeton.cs.algs4.Stack;

public class LPXClient
{
   /**
    * From StdStats, with a simple changes
    */
    public static void plotBars(boolean[] a) {
        int n = a.length;
        StdDraw.setXscale(-3, n + 2);
        StdDraw.setYscale(-1, 1.5);
	StdDraw.rectangle(.5 * (n - 1), .25, .5 * n + .05, .75);
        for (int i = 0; i < n; i++)
            StdDraw.filledRectangle(i, a[i] ? .25 : .0, 0.45, a[i] ? .5 : .0);
    }
    
    public static void main(String[] args)
    {
	boolean noPicture = args.length > 0;
	boolean clusterSizes = args.length <= 1;	
	LinearProbingHashSTX<String, Integer> st = new LinearProbingHashSTX<>();

	while (!StdIn.isEmpty()) {
	    String s = StdIn.readString();
	    st.put(s, 0);
	}

	int[] clusters = st.clusters();

	// Frequencies of cluster sizes
	if (clusterSizes) {
	    ST<Integer, Integer> cFreqs = new ST<>();
	    for (int s : clusters)
		if (!cFreqs.contains(s))
		    cFreqs.put(s, 1);
		else 
		    cFreqs.put(s, cFreqs.get(s) + 1);
	    // For reversing the order of the sizes
	    Stack<Integer> sizes = new Stack<>();
	    for (int s : cFreqs.keys())
		sizes.push(s);
	    StdOut.println("Size [number of clusters of that size]:");
	    for (int s : sizes) 
		StdOut.println(s + " [" + cFreqs.get(s) + "]");
	}

	StdOut.printf("Table size: %d\n", st.tableSize());
	StdOut.printf("Largest cluster: %d\n", st.maxClusterSize());

	StdOut.printf("Ave. no. probes in a search hit: %.4f\n", st.hitProbes());
	StdOut.printf("Ave. no. probes in a search miss: %.4f\n", st.missProbes());

	double alpha = st.loadFactor();
	StdOut.printf("Load factor: %.4f\n", alpha);	

	StdOut.printf("Theoretical / ave. probes search hit / under uniform hashing hypothesis: %.4f\n",
		       .5 * (1 + 1/(1 - alpha)));
	StdOut.printf("Theoretical / ave. probes search miss / under uniform hashing hypothesis: %.4f\n",
		       .5 * (1 + 1/(1 - alpha)/(1-alpha)));

	if (noPicture)
	    System.exit(0);

	StdDraw.enableDoubleBuffering();
	StdDraw.setCanvasSize(800, 30);
	plotBars(st.table());
	StdDraw.show();
    }
}

/*
 * $ echo "S E A R C H E X A M P L E" | java-algs4 LPXClient
 * Size [number of clusters of that size]:
 * 2 [2]
 * 1 [6]
 * Table size: 32
 * Largest cluster: 2
 * Ave. no. probes in a search hit: 1.0000
 * Ave. no. probes in a search miss: 1.3750
 * Load factor: 0.3125
 * Theoretical / ave. probes search hit / under uniform hashing hypothesis: 1.2273
 * Theoretical / ave. probes search miss / under uniform hashing hypothesis: 1.5579
 * $ head -n 2 DATA/LEIPZIG/leipzig1m.txt | java-algs4 LPXClient
 * Size [number of clusters of that size]:
 * 3 [3]
 * 2 [9]
 * 1 [15]
 * Table size: 128
 * Largest cluster: 3
 * Ave. no. probes in a search hit: 1.0952
 * Ave. no. probes in a search miss: 1.4688
 * Load factor: 0.3281
 * Theoretical / ave. probes search hit / under uniform hashing hypothesis: 1.2442
 * Theoretical / ave. probes search miss / under uniform hashing hypothesis: 1.6076
 * $ head -n 500000 DATA/LEIPZIG/leipzig1m.txt | java-algs4 LPXClient -
 * Size [number of clusters of that size]:
 * 1018 [1]
 * 345 [1]
 * 327 [1]
 * 295 [1]
 * 155 [1]
 * 121 [1]
 * 118 [2]
 * 117 [1]
 * 114 [1]
 * 113 [1]
 * 103 [1]
 * 85 [1]
 * [...]
 * 5 [4320]
 * 4 [8385]
 * 3 [16415]
 * 2 [36696]
 * 1 [117231]
 * Table size: 1048576
 * Largest cluster: 1018
 * Ave. no. probes in a search hit: 1.6204
 * Ave. no. probes in a search miss: 2.5396
 * Load factor: 0.3461
 * Theoretical / ave. probes search hit / under uniform hashing hypothesis: 1.2646
 * Theoretical / ave. probes search miss / under uniform hashing hypothesis: 1.6692
 * $ java-algs4 LPXClient - - < DATA/LEIPZIG/leipzig1m.txt
 * Table size: 2097152
 * Largest cluster: 1129
 * Ave. no. probes in a search hit: 1.4906
 * Ave. no. probes in a search miss: 1.8321
 * Load factor: 0.2549
 * Theoretical / ave. probes search hit / under uniform hashing hypothesis: 1.1711
 * Theoretical / ave. probes search miss / under uniform hashing hypothesis: 1.4006
 * $ 
 */
