/******************************************************************************
 * $ java-algs4 SATPlot  1000 1000 88888888
 * $ java-algs4 SATPlot 20000  300 88888888
 * $ java-algs4 SATPlot 20000  500 88888888
 ******************************************************************************/

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class SATPlot {

    // recursive curve plotting
    public static void curve(int N, int M, int T, double x0, double y0, double x1, double y1) {
        double gap = .01;
        double err = .0025;
        double xm = (x0 + x1) / 2;
        double ym = (y0 + y1) / 2;
        double fxm = Estimate.eval(N, (int)(M*xm), T);
        if (x1 - x0 < gap || Math.abs(ym - fxm) < err) {
            StdDraw.line(x0, y0, x1, y1);
            return;
        }
        curve(N, M, T, x0, y0, xm, fxm);
        StdDraw.filledCircle(xm, fxm, .005);
        curve(N, M, T, xm, fxm, x1, y1);
    }

    // test client
    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        long seed = Long.parseLong(args[2]);
	StdRandom.setSeed(seed);
        StdDraw.setCanvasSize(700, 700);
        StdDraw.setScale(-0.05, 1.05);
	StdDraw.clear(StdDraw.GRAY);
        SATPlot.curve(N, 2 * N, T, 0.0, 1.0, 1.0, 0.0);
	StdDraw.setPenColor(StdDraw.RED);
	StdDraw.line(.5, 0.0, .5, 1.0);
	StdOut.println("Done!");
    }
}
