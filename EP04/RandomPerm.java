/*
 * The call
 * 
 * $ java-algs4 RandomPerm N k T seed
 * 
 * generates T k-permutations of 0, ..., N - 1, using seed as the seed of
 * the random number generator in StdRandom.java. 
 * 
 * $ java-algs4 RandomPerm 10 3 5 88888
 * 1 0 2 3 4 5 6 7 8 9 
 * 1 0 2 3 5 4 6 7 8 9 
 * 1 0 2 3 4 5 6 7 8 9 
 * 0 1 2 3 4 6 5 8 7 9 
 * 3 0 2 1 4 7 6 5 9 8 
 * $ 
 */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Merge;

public class RandomPerm
{
    public static int[] kPerm(int N, int k) {
	int[] v = new int[N];
	for (int i = 0; i < N; i++)
	    v[i] = i;

	if (k == 0) return v;

	int d = (k + 1) / 2;	
	Integer[] vv = new Integer[N];
	for (int i = 0; i < N; i++)
	    vv[i] = i + StdRandom.uniformInt(-d, d + 1);

	int[] a = Merge.indexSort(vv);
	assert iskPerm(a, k);
	return a;
    }

    public static Integer[] kPermInteger(int N, int k) {
	int[] u = kPerm(N, k);
	Integer[] v = new Integer[N];
	for (int i = 0; i < N; i++) 
	    v[i] = u[i];
	return v;
    }

    private static boolean iskPerm(int[] v, int k) {
	int N = v.length;
	for (int i = 0; i < N; i++)
	    if (Math.abs(v[i] - i) > k)
		return false;
	return true;
    }

    public static void show(Integer[] v) {
	for (int i = 0; i < v.length; i++) 
	    StdOut.print(v[i] + " ");
	StdOut.println();
    }
    
    public static void main(String[] args)
    {
	int N = Integer.parseInt(args[0]);
	int k = Integer.parseInt(args[1]);
	int T = Integer.parseInt(args[2]);
	long seed = Long.parseLong(args[3]);
	StdRandom.setSeed(seed);
	
	for (int i = 0; i < T; i++) {
	    Integer[] kPerm = kPermInteger(N, k);
	    show(kPerm);	    
	}
    }
}
