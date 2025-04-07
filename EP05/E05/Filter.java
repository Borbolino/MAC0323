/*
 * $ head -n5 DATA/accessips
 * 46.229.168.138 - - [21/Apr/2019:06:25:48 -0300] 
 * 157.55.39.129 - - [21/Apr/2019:06:25:49 -0300] 
 * 88.198.36.62 - - [21/Apr/2019:06:26:03 -0300] 
 * 88.198.36.62 - - [21/Apr/2019:06:26:04 -0300] 
 * 45.171.89.166 - - [21/Apr/2019:06:26:10 -0300] 
 * $ head -n5 DATA/accessips | java-algs4 Filter
 * 46.229.168.138
 * 157.55.39.129
 * 88.198.36.62
 * 88.198.36.62
 * 45.171.89.166
 * $ 
 */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Filter {

    public static void main(String[] args) {
	while (!StdIn.isEmpty()) {
	    StdOut.println(StdIn.readString());
	    StdIn.readLine();
	}
    }
    
}
