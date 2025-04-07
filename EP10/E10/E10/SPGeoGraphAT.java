/*
 * $ java-algs4 RandomPoints 1000000 77 | java-algs4 SPGeoGraphAT .0015 .1 .1 .9 .9
 * Time to read data and produce geometric graph: 5.39
 * Time to build DijkstraSPX object: 0.71
 * Number of vertices in shortest-paths tree: 350746
 * Length of path: 1.2628249354
 * $ java-algs4 RandomPoints 1000000 77 > tmp; java-algs4 SPGeoGraphAT .0015 .1 .1 .9 .9 tmp; rm tmp
 * Time to read data and produce geometric graph: 5.22
 * Time to build DijkstraSPX object: 0.547
 * Number of vertices in shortest-paths tree: 350746
 * Length of path: 1.2628249354
 * $ java-algs4 RandomPoints 2000000 77 > tmp; java-algs4 SPGeoGraphAT .001 .1 .1 .9 .9 tmp; rm tmp
 * Time to read data and produce geometric graph: 10.987
 * Time to build DijkstraSPX object: 1.076
 * Number of vertices in shortest-paths tree: 812555
 * Length of path: 1.3083677533
 * $ 
 */

import java.awt.Color;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.EdgeWeightedDigraph;
import edu.princeton.cs.algs4.Stopwatch;

public class SPGeoGraphAT
{
    public static void main(String[] args)
    {
	double d = Double.parseDouble(args[0]);
	double x0 = Double.parseDouble(args[1]);
	double y0 = Double.parseDouble(args[2]);
	double x1 = Double.parseDouble(args[3]);
	double y1 = Double.parseDouble(args[4]);
	In in = new In();
	if (args.length > 5)
	    in = new In(args[5]);
	else
	    in = new In(); // stdin

	Stopwatch sw = new Stopwatch();
	GeoGraph gg = new GeoGraph(in, d);
	StdOut.println("Time to read data and produce geometric graph: " + sw.elapsedTime());
	int s = gg.nearestVertex(x0, y0);
	int t = gg.nearestVertex(x1, y1);

	sw = new Stopwatch();
        DijkstraSPX sp = new DijkstraSPX(gg, s, t);
	StdOut.println("Time to build DijkstraSPX object: " + sw.elapsedTime());	
	
	StdOut.println("Number of vertices in shortest-paths tree: "
			+ (sp.sptree().size() + 1));

	if (!sp.hasPathTo(t)) 
	    StdOut.println("No path!");
	else 
	    StdOut.printf("Length of path: %.10f\n", sp.distTo(t));
    }
}
