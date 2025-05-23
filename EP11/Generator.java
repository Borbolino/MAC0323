/*
 * $ java-algs4 Generator 5 .5 ab 3232021 .
 * abb
 * a
 * ababb
 * bb
 * baa
 * $ java-algs4 Generator 5 .5 ab 3232021
 * abb
 * a
 * ababb
 * a
 * bb
 */

import java.util.Arrays;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.TST;

public class Generator {

    public static String randomString(String alpha, double p) {
	Queue<Character> q = new Queue<>();
	while (!StdRandom.bernoulli(p)) { // geometric length
	    int t = StdRandom.uniformInt(alpha.length());
	    q.enqueue(alpha.charAt(t));
	}
	char[] w = new char[q.size()];
	int i = 0; 
	for (Character c : q) w[i++] = c;
	return new String(w);
    }
    
    public static void main(String[] args) {
	int N = Integer.parseInt(args[0]);
	double p = Double.parseDouble(args[1]);
	String alpha = args[2];
	long seed = Long.parseLong(args[3]);
	StdRandom.setSeed(seed);
	boolean unique = args.length > 4; // repetitions prohibited?

	String[] a = new String[N];
	// use TST to hold a set
	TST<Integer> st = new TST<>();
	int i = 0;
	while (i < N) {
	    String r = randomString(alpha, p);
	    if (r.length() == 0) continue;
	    if (unique && st.contains(r)) continue;
	    a[i++] = r;
	    if (unique) st.put(r, 0);
	} 
	for (String s : a) StdOut.println(s);
    }
}
