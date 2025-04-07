/*
$ java-algs4 GetStatsP 10000 1 88888888
1000:
  8.494
  14
  7
  25.5
  2.4835164835164836
2000:
  9.3435
  15
[...]

10000:
  11.71
  17
  10
  25.6
  2.708629137086291
$ java-algs4 GetStatsP 100000 10 88888888 > 10_runs.txt
 */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class GetStatsP
{
    // plot the evolution of the tree defined by a
    public static void getEvolution(int[] a) {
	RedBlackBSTMod<Integer, Integer> st = new RedBlackBSTMod<>();
	int N = a.length;
	
	for (int i = 0; i < N; i++) {
	    st.put(a[i], 0);
	    int M = i + 1; // number of nodes in tree
	    if (M % 1000 == 0 && M <= 10000 || M % 10000 == 0) {
		StdOut.println(M + ":");
		StdOut.println("  " + (double)st.iplP() / M);
		StdOut.println("  " + st.heightP());		
		StdOut.println("  " + st.height23P());
		StdOut.println("  " + 100.0 * st.sizeRP() / M);
		StdOut.println("  " + st.aveRedLinksP());
	    }
	}
    }

    /******************************************************************************/

    public static void main(String[] args)
    {
	int N = Integer.parseInt(args[0]);
	int T = Integer.parseInt(args[1]);
	long seed = Long.parseLong(args[2]);
	StdRandom.setSeed(seed);
	
	int[] a = new int[N];
	for (int i = 0; i < N; i++)
	    a[i] = i;

	for (int i = 0; i < T; i++) {
	    StdRandom.shuffle(a);
	    getEvolution(a);
	}
    }
}
