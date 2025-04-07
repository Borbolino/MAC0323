/******************************************************************************
 *
 * MAC0323 ALGORITMOS E ESTRUTURAS DE DADOS II
 * Aluno: João Pedro Barioni Agostini
 * Numero USP: 14582163
 * Tarefa: E11 Métodos adicionais para TSTs
 * Data: 04/07/2024
 * 
 *
 * DECLARO QUE SOU O ÚNICO AUTOR E RESPONSÁVEL POR ESTE PROGRAMA.  TODAS AS 
 * PARTES DO PROGRAMA, EXCETO AS QUE SÃO BASEADAS EM MATERIAL FORNECIDO  
 * PELO PROFESSOR OU COPIADAS DO LIVRO OU DAS BIBLIOTECAS DE SEDGEWICK & WAYNE, 
 * FORAM DESENVOLVIDAS POR MIM.  DECLARO TAMBÉM QUE SOU RESPONSÁVEL POR TODAS 
 * AS CÓPIAS DESTE PROGRAMA E QUE NÃO DISTRIBUÍ NEM FACILITEI A DISTRIBUIÇÃO
 * DE CÓPIAS DESTA PROGRAMA.
 *
 ******************************************************************************/

/*
 * java-algs4 ContainsPatternLazy DATA/Pwords < 2patterns.in
 * Words that contain .a.b.c.d. (4)
 * embasbacada
 * embasbacadas
 * embasbacado
 * embasbacados
 * - * - * -
 * Words that contain .a.e.i.o. (24)
 * agradecidos
 * alfabetizou
 * aparecidos
 * baleeiros
 * carecidos
 * [...]
 */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.TST;
import edu.princeton.cs.algs4.In;

public class ContainsPatternLazy {
    
    public static void main(String[] args) {
	In in = new In(args[0]);
	int maxLength = 0;
	// use TST to hold a set of strings; i.e., ignore values
        TSTMinus<Integer> st = new TSTMinus<>();
	while (!in.isEmpty()) {
	    String key = in.readString();
	    if (key.length() > maxLength) maxLength = key.length();
	    st.put(key, 0);
	} 
	while (!StdIn.isEmpty()) {
	    String p = StdIn.readString();
	    String pp = p;
	    TST<Integer> words = new TST<>();
	    while (pp.length() <= maxLength) {
		for (String s : st.keysThatStartWith(pp)) 
		    words.put(s, 0);
		pp = "." + pp;
	    }
	    StdOut.println("Words that contain " + p + " (" + words.size() + ")");
	    for (String w : words.keys()) StdOut.println(w);
	    StdOut.println("- * - * -");	    
	}
    }
}
