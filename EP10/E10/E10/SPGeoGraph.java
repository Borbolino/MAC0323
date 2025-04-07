/*
 * $ java-algs4 RandomPoints 200 77 | java-algs4 SPGeoGraph .1 .1 .1 .9 .9
 * Number of vertices in shortest-paths tree: 188
 * Length of path: 1.5187240292
 * $ java-algs4 RandomPoints 5000 77 | java-algs4 SPGeoGraph .0181282 .1 .1 .9 .9
 * Number of vertices in shortest-paths tree: 4441
 * Length of path: 1.7847262702
 * $ java-algs4 RandomPoints 5000 77 | java-algs4 SPGeoGraph .0181281 .1 .1 .9 .9
 * Number of vertices in shortest-paths tree: 4256
 * No path!
 * $ java-algs4 RandomPoints 10000 77 | java-algs4 SPGeoGraph .015 .1 .1 .9 .9
 * Number of vertices in shortest-paths tree: 9627
 * Length of path: 1.2996984824
 * $ java-algs4 RandomPoints 10000 77 | java-algs4 SPGeoGraph .018 .1 .1 .9 .9
 * Number of vertices in shortest-paths tree: 9736
 * Length of path: 1.1833153451
 * $ 
 */

import java.awt.Color;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.EdgeWeightedDigraph;

public class SPGeoGraph
{
    // Draw the arcs of gg in list in order.
    public static void drawDynamically(Iterable<DirectedEdge> list, GeoGraph gg) {
	Color c = StdDraw.getPenColor();
	double r = StdDraw.getPenRadius();
	StdDraw.setPenColor(StdDraw.RED);
        StdDraw.textLeft(0.0, -0.02, "Press any key [ 's' (step) | 'c' (continue) | 'f' (finish) ]");
        StdDraw.show();
        StdDraw.pause(100);
        while (!StdDraw.hasNextKeyTyped())
            StdDraw.pause(100);
        char mode = StdDraw.nextKeyTyped();
	StdDraw.setPenColor(StdDraw.BLUE);
	StdDraw.setPenRadius(.008);	
	for (DirectedEdge e : list) {
	    gg.drawArc(e);
	    switch (mode) {
	    case 'c':
                StdDraw.show();
                StdDraw.pause(20);
                if (StdDraw.hasNextKeyTyped()) 
                    mode = StdDraw.nextKeyTyped();
		break;
	    case 'f':
		break;
	    default: // step mode
                StdDraw.show();
                StdDraw.pause(100);
                while (!StdDraw.hasNextKeyTyped())
                    StdDraw.pause(100);
                mode = StdDraw.nextKeyTyped();
		break;
	    }
	}
	StdDraw.show();
	StdDraw.setPenColor(c);
	StdDraw.setPenRadius(r);
    }
    
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

	GeoGraph gg = new GeoGraph(in, d);
	gg.initCanvas();
	gg.draw();
	int s = gg.nearestVertex(x0, y0);
	int t = gg.nearestVertex(x1, y1);
	gg.highlight(s);
	gg.highlight(t);
        StdDraw.show();
	
	EdgeWeightedDigraph ewdg = gg.ewDigraph();
        DijkstraSPX sp = new DijkstraSPX(ewdg, s, t);

	// Draw arcs in sp.sptree() one by one
	StdOut.println("Number of vertices in shortest-paths tree: "
			+ (sp.sptree().size() + 1));
	drawDynamically(sp.sptree(), gg);

	// Draw the path from s to t
	if (!sp.hasPathTo(t)) 
	    StdOut.println("No path!");
	else {
	    StdOut.printf("Length of path: %.10f\n", sp.distTo(t));
	    StdDraw.setPenColor(StdDraw.RED);
	    StdDraw.setPenRadius(.008);
	    gg.drawArcs(sp.pathTo(t));
	}
	StdDraw.pause(1000); // small pause
	gg.highlight(s);
	gg.highlight(t);
	gg.show();
    }
}
