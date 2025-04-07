/**
 * $ java-algs4 Grid 63 | java-algs4 SPGeoGraph 0.03 .1 .1 .9 .9
 * Number of vertices in shortest-paths tree: 3866
 * Length of path: 1.1490485194
 * $ java-algs4 Grid 63 | java-algs4 SPGeoGraphA 0.03 .1 .1 .9 .9
 * Number of vertices in shortest-paths tree: 53
 * Length of path: 1.1490485194
 * $ java-algs4 Grid 63 | java-algs4 SPGeoGraph 0.03 .3 .1 .9 .9
 * Number of vertices in shortest-paths tree: 3896
 * Length of path: 1.0649113896
 * $ java-algs4 Grid 63 | java-algs4 SPGeoGraphA 0.03 .3 .1 .9 .9
 * Number of vertices in shortest-paths tree: 707
 * Length of path: 1.0649113896
 * $ java-algs4 Grid 63 | java-algs4 SPGeoGraphA 0.03 .45 .1 .55 .9
 * Number of vertices in shortest-paths tree: 422
 * Length of path: 0.8513325215
 * $ java-algs4 Grid 63 | java-algs4 SPGeoGraphA 0.03 .35 .1 .65 .9
 * Number of vertices in shortest-paths tree: 801
 * Length of path: 0.9419417382
 * $ 
 */

import edu.princeton.cs.algs4.StdOut;

public class Grid
{
    public static void main(String[] args)
    {
	int N = Integer.parseInt(args[0]);

	double l = 1.0 / (N + 1);
	double x, y = 0.0;

	for (int i = 0; i < N; i++) {
	    y += l;
	    x = 0.0;
	    for (int j = 0; j < N; j++) {
		x += l;
		StdOut.println(x + " " + y);
	    }
	}
    }
}
