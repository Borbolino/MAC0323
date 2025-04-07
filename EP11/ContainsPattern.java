/******************************************************************************
 *
 * MAC0323 ALGORITMOS E ESTRUTURAS DE DADOS II
 * Aluno: João Pedro Barioni Agostini
 * Numero USP: 14582163
 * Tarefa: E11 Métodos adicionais para TSTs
 * Data: 05/07/2024
 * 
 * O código a seguir utiliza a Ternary Search Tree implementada em TSTPlus.java
 * para encontrar as palavras de uma lista com o padrão passado, em qualquer
 * posição. Adicionando todos os sufixos de uma palavra, como a ideia dada por
 * Sedgewick e Wayne, com respectivo valor sendo uma bag (no caso, uma Queue)
 * que contém todas as palavras às quais a substring em questão pertence. 
 * Após isso, para cada sufixo adicionado à TST que inicia com o padrão dado,
 * adicionamos à TST que contém as respostas (palavras que contém o padrão dado)
 * todas as palavras da Queue correspondente ao sufixo. Dessa maneira, procuramos
 * o prefixo (padrão) para cada posição das palavras da lista, ou seja, procuramos
 * o padrão independente da posição da palavra.
 * O código é uma modificação de ContainsPatternLazy.java, de Sedgewick e Wayne,
 * utiliza a implementação de TSTPlus.java e é baseado em estudos de Tries, TSTs
 * e Estruturas de Dados vistas em aula e pelo material de Sedgewick e Wayne.
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
import edu.princeton.cs.algs4.Queue;

public class ContainsPattern {
    
    public static void main(String[] args) {
	In in = new In(args[0]);
	int maxLength = 0;
	// use TST to hold a set of strings; i.e., ignore values
        TSTMinus<Queue<String>> st = new TSTMinus<>();
	while (!in.isEmpty()) {
	    String key = in.readString();
	    if (key.length() > maxLength) maxLength = key.length();
		for(int i = 0; i < key.length(); i++){
			Queue<String> preQueue = new Queue<>();
			Queue<String> queue = st.get(key.substring(i, key.length()));
			if(queue == null) {
				queue = preQueue;
			}
			queue.enqueue(key);
			st.put(key.substring(i, key.length()), queue);
		}
	} 
	while (!StdIn.isEmpty()) {
	    String p = StdIn.readString();
	    TST<Integer> words = new TST<>();
		for (String s : st.keysThatStartWith(p)){
			Queue<String> q = st.get(s);
			while(!q.isEmpty()) words.put(q.dequeue(), 0);
		}
	    StdOut.println("Words that contain " + p + " (" + words.size() + ")");
	    for (String w : words.keys()) StdOut.println(w);
	    StdOut.println("- * - * -");	    
	}
}
}
