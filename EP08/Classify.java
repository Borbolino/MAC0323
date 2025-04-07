/*
 * $ java-algs4 InstanceGenerator 8 8 88888888 > tmp.txt
 * $ cat tmp.txt 
 * 8
 * 8
 * 0 3
 * 0 7
 * 0 6
 * 0 2
 * 1 4
 * 2 5
 * 3 5
 * 4 6
 * 
 * 8 vertices, 8 edges 
 * 0: 3 7 6 2 
 * 1: 4 
 * 2: 0 5 
 * 3: 0 5 
 * 4: 6 1 
 * 5: 2 3 
 * 6: 0 4 
 * 7: 0 
 * 
 * $ java-algs4 Classify tmp.txt
 * Number of vertices in cycles: 4
 * Number of vertices not in cycles: 4
 * Ratio (in cycles/total): 0.5
 * $ java-algs4 Classify tmp.txt +
 * Vertices in cycles: 0 2 3 5 
 * Vertices not in cycles: 1 4 6 7 
 * Number of vertices in cycles: 4
 * Number of vertices not in cycles: 4
 * Ratio (in cycles/total): 0.5
 * $ 
 */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Graph;

public class Classify
{
    public static void main(String[] args)
    {
	In in = new In(args[0]);
	Graph G = new Graph(in);

	boolean verbose = args.length > 1;

	boolean[] inCycle = new boolean[G.V()];

	for (int i = 0; i < G.V(); i++) {
	    if (inCycle[i]) continue;
	    Cycle finder = new Cycle(G, i);
	    if (finder.hasCycle()) 
		for (int x: finder.cycle())
		    inCycle[x] = true;
	}

	int noInCycle = 0;
	for (int i = 0; i < G.V(); i++)
	    if (inCycle[i])
		noInCycle++;
	
	if (verbose) {
	    StdOut.print("Vertices in cycles: ");
	    for (int i = 0; i < G.V(); i++)
		if (inCycle[i])
		    StdOut.print(i + " ");
	    StdOut.print("\nVertices not in cycles: ");
	    for (int i = 0; i < G.V(); i++)
		if (!inCycle[i])
		    StdOut.print(i + " ");
	    StdOut.println();
	}

	StdOut.println("Number of vertices in cycles: " + noInCycle); 
	StdOut.println("Number of vertices not in cycles: " + (G.V() - noInCycle)); 
	StdOut.println("Ratio (in cycles/total): " + 1.0 * noInCycle / G.V()); 
    }
}
