/*
 * $ head -n5 DATA/ssh.log
 * May 27 00:01:32 machine.ime.usp.br sshd[33205]: Invalid user adam from 175.126.111.86
 * May 27 00:09:00 machine.ime.usp.br sshd[33213]: Invalid user carol from 175.126.111.86
 * May 27 00:12:57 machine.ime.usp.br sshd[33218]: Invalid user chloe from 175.126.111.86
 * May 27 00:17:04 machine.ime.usp.br sshd[33223]: Invalid user chloe from 175.126.111.86
 * May 27 00:24:55 machine.ime.usp.br sshd[33233]: Invalid user dylan from 175.126.111.86
 * $ head -n5 DATA/ssh.log | java-algs4 Filter2
 * 175.126.111.86
 * 175.126.111.86
 * 175.126.111.86
 * 175.126.111.86
 * 175.126.111.86
 * $ 
 */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Filter2 {

    public static void main(String[] args) {
	while (!StdIn.isEmpty()) {
	    String line = StdIn.readLine();
	    int start = line.indexOf(" from ") + 6;
	    StdOut.println(line.substring(start));
	}
    }
    
}
