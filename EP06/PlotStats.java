/*
 * $ java-algs4 PlotStats 1000000 1 88888888
 * $ java-algs4 PlotStats 1000000 10 88888888
 */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdRandom;

interface ReferenceFunction {
   double run(int x);
}

public class PlotStats
{
    public static void initFrame(int N, int width) {
	StdDraw.setCanvasSize(800, 600);
	StdDraw.clear(StdDraw.LIGHT_GRAY);
	double yMax = 2.5 * Math.log(N);
	double xMax = 1.05 * N;
	StdDraw.setXscale(-.1 * xMax, 1.1 * xMax);
	StdDraw.setYscale(-.1 * yMax, 1.1 * yMax);
	StdDraw.enableDoubleBuffering();
	StdDraw.line(-.05 * xMax, 0.0, 1.05 * xMax, 0.0);
	StdDraw.line(0.0, -.05 * yMax, 0.0, 1.05 * yMax);
	StdDraw.line(N, -.02 * yMax, N, .02 * yMax);
	StdDraw.text(N, -.05 * yMax, "N = " + N);
	StdDraw.line(-.01 * xMax, 2 * Math.log(N), .01 * xMax, 2 * Math.log(N));
	StdDraw.textRight(-.02 * xMax, 2 * Math.log(N), "2 ln N");
	StdDraw.line(-.01 * xMax, Math.log(N) / Math.log(2), .01 * xMax, Math.log(N) / Math.log(2));
	StdDraw.textRight(-.02 * xMax, Math.log(N) / Math.log(2), "lg N");
	StdDraw.show();
    }

    /******************************************************************************/

    // plot the evolution of the tree defined by a
    public static void plotEvolution(int[] a) {
	RedBlackBSTMod<Integer, Integer> st = new RedBlackBSTMod<>();
	int N = a.length;
	
	StdDraw.setPenRadius(.005);
	for (int i = 0; i < N; i++) {
	    st.put(a[i], 0);
	    int M = i + 1; // number of nodes in tree
	    StdDraw.setPenColor(StdDraw.BLACK);	
	    if (M % 1000 == 0 && M <= 10000 || M % 10000 == 0) {
		StdDraw.point(M, (double)st.iplP() / M);	    
		StdDraw.point(M, st.height());
		StdDraw.setPenColor(StdDraw.BLUE);
		StdDraw.point(M, st.height23());
		StdDraw.setPenColor(StdDraw.GREEN);
		StdDraw.point(M, 100.0 * st.sizeR() / M);
		StdDraw.setPenColor(StdDraw.BLUE);		
		StdDraw.point(M, st.aveRedLinks());
		StdDraw.show();		
	    }
	}
    }

    /******************************************************************************/

    // plot reference function f using adaptive plot (unnecessary; just for fun)
    // See https://introcs.cs.princeton.edu/java/24percolation/
    public static void plotFun(int N, ReferenceFunction f) {
	plotFun(100, f.run(100), N, f.run(N), f);
	StdDraw.show();
    }

    private static void plotFun(int x0, double y0, int x1, double y1, ReferenceFunction f) {
	double ym = (y0 + y1) / 2;
	int xm = x0 + (x1 - x0) / 2;
	double fm = f.run(xm);
	if (x1 - x0 < 100 || Math.abs(fm - ym) < .05) { // ad hoc values
	    StdDraw.line(x0, y0, x1, y1);
	    return;
	}
	plotFun(x0, y0, xm, fm, f);
	plotFun(xm, fm, x1, y1, f);
    }
    
    /******************************************************************************/

    public static void main(String[] args)
    {
	int N = Integer.parseInt(args[0]);
	int T = Integer.parseInt(args[1]);
	long seed = Long.parseLong(args[2]);
	StdRandom.setSeed(seed);
	
	initFrame(N, 800);

	int[] a = new int[N];
	for (int i = 0; i < N; i++)
	    a[i] = i;

	for (int i = 0; i < T; i++) {
	    StdRandom.shuffle(a);
	    plotEvolution(a);
	}
	
	StdDraw.setPenRadius(.003);
	StdDraw.setPenColor(StdDraw.RED);
	// use Java's lambda expressions (unnecessary; just for fun)
	// See https://www.geeksforgeeks.org/java-lambda-expressions-parameters/
	plotFun(N, x -> 2 * Math.log(x));
	plotFun(N, x -> Math.log(x) / Math.log(2) - 1.18);
    }
}
