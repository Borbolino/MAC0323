/*
 * $ java-algs4 InstanceGenerator 5 7 88888888
 * 5
 * 7
 * 0 4
 * 0 2
 * 0 3
 * 1 4
 * 2 3
 * 2 4
 * 3 4
 * 
 * 5 vertices, 7 edges 
 * 0: 4 2 3 
 * 1: 4 
 * 2: 3 4 0 
 * 3: 4 2 0 
 * 4: 3 0 2 1 
 * 
 * $ 
 */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.StdRandom;

public class InstanceGenerator
{
    public static boolean adjacent(Graph G, int v, int w) {
	for (int u : G.adj(v))
	    if (u == w) 
		return true;
	return false;
    }

    public static Graph instance(int N, int M) {
	if (M < N - 1) {
	    System.err.println("No. edges should be at least no. vertices - 1");
	    System.exit(-1);
	}
	
	Graph G = GraphGenerator.tree(N); // random tree on N vertices

	// add random edges to have M egdes in total
	while (G.E() < M) {
	    int w, v = StdRandom.uniformInt(N);
	    do {
		w = StdRandom.uniformInt(N);
	    } while (w == v);
	    if (adjacent(G, v, w))
		continue;
	    G.addEdge(v, w);
	}
	
	return G;
    }
    
    public static void main(String[] args)
    {
	int N = Integer.parseInt(args[0]);
	int M = Integer.parseInt(args[1]);
	long seed = Long.parseLong(args[2]);
	StdRandom.setSeed(seed);
	
	Graph G = instance(N, M);
	
	StdOut.println(N);
	StdOut.println(G.E());
	for (int i = 0; i < G.V(); i++)
	    for (int j: G.adj(i))
		if (i < j)
		    StdOut.println(i + " " + j);

	StdOut.println("\n" + G);
    }
}
