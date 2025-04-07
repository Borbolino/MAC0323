/*
 * $ java-algs4 Generator 1 60 actg 88888888
 * ttgcaacacattatctcccgcaaagtccatcactgcacgcggatggacgagtagcaaaca
 * $ java-algs4 Generator 10 3 abc 88888888
 * cbc
 * bbc
 * caa
 * aca
 * bcc
 * bcb
 * aac
 * bca
 * ccb
 * cba
 * $ java-algs4 Generator 15 8 012345678 88888888
 * 87817850
 * 30531821
 * 57038426
 * [...]
 * 67068523
 * $ 
 */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Generator {

    public static String randomString(int L, String alpha) {
	char[] a = new char[L];
	for (int i = 0; i < L; i++)  { 
	    int t = StdRandom.uniformInt(alpha.length());
	    a[i] = alpha.charAt(t);
	}
	return new String(a);
    }
    
    public static void main(String[] args) {
	int N = Integer.parseInt(args[0]);
	int L = Integer.parseInt(args[1]);
	String alpha = args[2];
	long seed = Long.parseLong(args[3]);
	StdRandom.setSeed(seed);
	
	for (int i = 0; i < N; i++)
	    StdOut.println(randomString(L, alpha));
    }

}
