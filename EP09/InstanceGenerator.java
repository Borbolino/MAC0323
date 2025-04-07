/**
 * $ java-algs4 InstanceGenerator 3 9 88888888
 * 3
 * -2  1
 * -3  1
 *  1  3
 * -3  3
 * -1  1
 * -1  2
 * -2  3
 * -2  2
 * -3 -1
 * $ 
*/

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.LinearProbingHashST;

public class InstanceGenerator
{
    public static int[] instance(int N, int M) {
	int[] f = new int[1 + 2*M];
	f[0] = N;
	LinearProbingHashST<RandomTwoClause, Integer> tsat = new LinearProbingHashST<>();
	int m = 0;
	while (m < M) {
	    RandomTwoClause c = new RandomTwoClause(N);
	    if (tsat.contains(c)) continue;
	    tsat.put(c, 0);
	    m++;
	}
	int i = 1;
	for (RandomTwoClause c : tsat.keys()) {
	    f[i++] = c.a();
	    f[i++] = c.b();
	}
	return f;
    }
    
    public static void main(String[] args)
    {
	int N = Integer.parseInt(args[0]);
	int M = Integer.parseInt(args[1]);
	long seed = Long.parseLong(args[2]);
	StdRandom.setSeed(seed);
	int[] f = instance(N, M);
	
	StdOut.println(f[0]);
	for (int i = 1; i <= M; i++) 
	    StdOut.printf("%2d %2d\n", f[2*i - 1], f[2*i]);
    }
}
