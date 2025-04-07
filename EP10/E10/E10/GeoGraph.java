/*
 * $ java-algs4 RandomPoints 20 77 | java-algs4 GeoGraph .3
 * 20 vertices / 92 arcs
 * (0.7240700207191966, 0.2954549236689207):
 *   (0.6316157732110923, 0.4442658341477774)  [0.17519267952696002]
 *   (0.5513471308911821, 0.41790829408150953)  [0.21172629642050395]
 *   (0.5254283055048928, 0.2696688781041433)  [0.2003083901616438]
 *   (0.6273641485476927, 0.3233058467238594)  [0.10063647265014489]
 *   (0.6725921641542352, 0.5618509274808084)  [0.2713241614074686]
 *   (0.5006660792218953, 0.233141101274831)  [0.23193174327351518]
 * (0.5503188863736588, 0.7859999811690331):
 *   (0.48034265260021336, 0.515586199543202)  [0.27932111732967596]
 *   (0.6725921641542352, 0.5618509274808084)  [0.2553302816520842]
 *   (0.6859219907523308, 0.9627056763021298)  [0.22273999328724944]
 * (0.10376327232002569, 0.14642492755797953):
 * [...]
 *   (0.6273641485476927, 0.3233058467238594)  [0.12103468457377344]
 *   (0.5006660792218953, 0.233141101274831)  [0.2484380711294815]
 * $ java-algs4 RandomPoints 500 77 | java-algs4 GeoGraph .1 > /dev/null
 * $ java-algs4 RandomPoints 1000 77 | java-algs4 GeoGraph .07 > /dev/null
 * $ java-algs4 RandomPoints 10000 77 | java-algs4 GeoGraph .015 > /dev/null
 * $ 
 */

import java.util.Arrays;
import java.awt.Color;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.ST;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.EdgeWeightedDigraph;
import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.StdDraw;

public class GeoGraph
{
    private ST<Point2D, Integer> st;   // string -> index
    private Point2D[] points;          // index  -> point2d
    private EdgeWeightedDigraph ewDigraph; // the underlying digraph

    // Read a set of 2D points from in.  Consider d as the threshold
    // for conneting two such points by an arc.  If points P and Q
    // are at Euclidean distance <= d, then there is an arc from P to
    // Q and an arc from Q to P.  The length (weight) of each arc is the 
    // Euclidean distance between P and Q.
    public GeoGraph(In in, double d) {

	// Read all points.  We assume there are no repeated points.	
	Queue<Point2D> inPoints = new Queue<>();
        while (!in.isEmpty()) {
	    double x = in.readDouble();
	    double y = in.readDouble();
	    Point2D p = new Point2D(x, y);
	    inPoints.enqueue(p);
	}
	int l = 0;
	points = new Point2D[inPoints.size()];
	for (Point2D p : inPoints) 
	    points[l++] = p;

	// Sort points, so that we know which point is
	// which vertex (not necessary, but good for debugging).
	Arrays.sort(points);

	// Produce inverted index of array points.
	st = new ST<>();
	for (int i = 0; i < points.length; i++) {
	    Point2D p = points[i];
	    if (st.contains(p)) {
		System.err.println("Panic: repeated point in input!");
		System.exit(-1);
	    }
	    st.put(p, i);
	}

	// We use gridding: see Web Exercise 1.3.45 in
	// https://algs4.cs.princeton.edu/13stacks/
        int rows = (int) (1.0 / d);    // # rows in grid
        int cols = (int) (1.0 / d);    // # columns in grid

        Queue<Point2D>[][] grid = (Queue<Point2D>[][]) new Queue[rows+2][cols+2];
        for (int i = 0; i <= rows+1; i++)
            for (int j = 0; j <= cols+1; j++)
                grid[i][j] = new Queue<Point2D>();

        ewDigraph = new EdgeWeightedDigraph(points.length);	
	for (int k = 0; k < points.length; k++) {
	    Point2D p = points[k];
	    double x = p.x();
	    double y = p.y();
            int row = 1 + (int) (x * rows);
            int col = 1 + (int) (y * cols);
            for (int i = row - 1; i <= row + 1; i++)
                for (int j = col - 1; j <= col + 1; j++) {
                    for (Point2D q : grid[i][j])
                        if (p.distanceTo(q) <= d) {
			    double D = p.distanceTo(q);
			    DirectedEdge e = new DirectedEdge(st.get(p), st.get(q), D);
			    DirectedEdge f = new DirectedEdge(st.get(q), st.get(p), D);
			    ewDigraph.addEdge(e);
			    ewDigraph.addEdge(f);
			}
                }
            grid[row][col].enqueue(p);
        }
    }

    public EdgeWeightedDigraph ewDigraph() {
	return ewDigraph;
    }
    
    public Point2D points(int i) {
	return points[i];
    }
    
    public int points(Point2D p) {
	return st.get(p);
    }

    public int V() {
	return ewDigraph.V();
    }
    
    public int E() {
	return ewDigraph.E();
    }

    public int nearestVertex(double x, double y) {
	Point2D p0 = new Point2D(x, y);
	int nearest = 0;
	double min = p0.distanceTo(points[nearest]);
	for (int i = 1; i < points.length; i++) {
	    if (p0.distanceTo(points[i]) < min) {
		min = p0.distanceTo(points[i]);
		nearest = i;
	    }
	}
	return nearest;
    }

    /*******************************************************************************
     Drawing routines
     *******************************************************************************/

    public void initCanvas() {
	// initCanvas(500, 500, 0.0, 1.0, 0.0, 1.0);
	initCanvas(800, 800, 0.0, 1.0, 0.0, 1.0);
    }

    public void initCanvas(int width, int height,
		      double xmin, double xmax, double ymin, double ymax) {
        StdDraw.setCanvasSize(width, height);
        StdDraw.enableDoubleBuffering();	
        StdDraw.setXscale(xmin - .05, xmax + .05); // hardcoded frame width :-(
        StdDraw.setYscale(ymin - .05, ymax + .05);
	StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
	StdDraw.filledRectangle((xmin + xmax)/2, (ymin + ymax)/2,
				(xmax - xmin)/2, (ymax - ymin)/2);
	StdDraw.setPenColor(StdDraw.BLACK);
    }

    public void drawVertices() {
	for (int i = 0; i < points.length; i++)
	    points[i].draw();
    }

    public void drawArc(DirectedEdge e) {
	points[e.from()].drawTo(points[e.to()]);
    }

    public void drawArcs() {
	for (int i = 0; i < ewDigraph.V(); i++) {
	    for (DirectedEdge e : ewDigraph.adj(i))
		drawArc(e);
	}
    }

    public void drawArcs(Iterable<DirectedEdge> f) {
	for (DirectedEdge e : f) 
	    drawArc(e);
    }

    public void draw() {
	drawVertices();
	drawArcs();
    }

    public void show() {
	StdDraw.show();
    }

    public void highlight(int v) {
	double r = StdDraw.getPenRadius();
	Color c = StdDraw.getPenColor();
	StdDraw.setPenRadius(.004); // hard coded
	StdDraw.setPenColor(StdDraw.RED);
	StdDraw.circle(points[v].x(), points[v].y(), .01); // hard coded
	StdDraw.setPenRadius(r);
	StdDraw.setPenColor(c);
    }
        
    /******************************************************************************/
    public static void main(String[] args)
    {
	double d = Double.parseDouble(args[0]);
	In in;
	if (args.length > 1)
	    in = new In(args[1]);
	else
	    in = new In(); // stdin

	GeoGraph gg = new GeoGraph(in, d);
	EdgeWeightedDigraph ewdg = gg.ewDigraph();
	
	StdOut.println(gg.V() + " vertices / " + gg.E() + " arcs");
	for (int i = 0; i < ewdg.V(); i++) {
	    StdOut.println(gg.points(i) + ":");
	    for (DirectedEdge e : ewdg.adj(i)) 
		StdOut.println("  " + gg.points(e.to()) + "  [" + e.weight() + "]");
	}
	gg.initCanvas();
	gg.draw();
	gg.show();
    }
}
