/*
 * Exemplo de execução, com CountABC.java adequadamente implementado:
 * 
 * $ java-algs4 E02 1024 88
 * Fast counting:
 * 8: 0 [time: 0.013]
 * 16: 0 [time: 0.0]
 * 32: 22 [time: 0.0]
 * 64: 257 [time: 0.001]
 * 128: 1778 [time: 0.0]
 * 256: 22580 [time: 0.001]
 * 512: 254449 [time: 0.001]
 * 1024: 1822247 [time: 0.001]
 * Plain counting:
 * 8: 0 [time: 0.0]
 * 16: 0 [time: 0.0]
 * 32: 22 [time: 0.0]
 * 64: 257 [time: 0.002]
 * 128: 1778 [time: 0.005]
 * 256: 22580 [time: 0.012]
 * 512: 254449 [time: 0.036]
 * 1024: 1822247 [time: 0.287]
 * $ 
 */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stopwatch;

public class E02
{
    public static void main(String[] args)
    {
	int N = Integer.parseInt(args[0]);
	long seed = Long.parseLong(args[1]);
	
	StdRandom.setSeed(seed);
	StdOut.println("Fast counting:");
	for (int n = 8; n <= N; n *= 2) {
	    String s = RandomString.ran_abcde(n);
	    Stopwatch sw = new Stopwatch();
	    StdOut.print(n + ": " + CountABC.countABC(s));
	    StdOut.println(" [time: " + sw.elapsedTime() + "]");
	}

	StdRandom.setSeed(seed);	
	StdOut.println("Plain counting:");
	for (int n = 8; n <= N; n *= 2) {
	    String s = RandomString.ran_abcde(n);
	    Stopwatch sw = new Stopwatch();
	    StdOut.print(n + ": " + CountABC.countABCPlain(s));
	    StdOut.println(" [time: " + sw.elapsedTime() + "]");
	}
	
    }

}
