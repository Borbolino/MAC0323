/******************************************************************************
 *
 * MAC0323 ALGORITMOS E ESTRUTURAS DE DADOS II
 * Aluno: João Pedro Barioni Agostini
 * Numero USP: 14582163
 * Tarefa: E02
 * Data: 11/03/2024
 * 
 * O programa a seguir implementa a função countABC(), que em tempo linear e
 * sem memória adicional, encontra a quantidade de ocorrências de "abc" em uma
 * string dada.
 *
 * DECLARO QUE SOU O ÚNICO AUTOR E RESPONSÁVEL POR ESTE PROGRAMA.  TODAS AS 
 * PARTES DO PROGRAMA, EXCETO AS QUE SÃO BASEADAS EM MATERIAL FORNECIDO  
 * PELO PROFESSOR OU COPIADAS DO LIVRO OU DAS BIBLIOTECAS DE SEDGEWICK & WAYNE, 
 * FORAM DESENVOLVIDAS POR MIM.  DECLARO TAMBÉM QUE SOU RESPONSÁVEL POR TODAS 
 * AS CÓPIAS DESTE PROGRAMA E QUE NÃO DISTRIBUÍ NEM FACILITEI A DISTRIBUIÇÃO
 * DE CÓPIAS DESTA PROGRAMA.
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;

public class CountABC
{
    public static long countABC(String s) {
	long a = 0;	// QUANTIDADE DE OCORRÊNCIAS DE a EM s
        long ab = 0;	// QUANTIDADE DE OCORRÊNCIAS DE ab EM s
        long abc = 0;	// QUANTIDADE DE OCORRÊNCIAS DE abc EM s (VALOR PROCURADO)

        for(int i = 0; i < s.length(); i++) {
            if(s.charAt(i) == 'a') a++;		// INCREMENTA a QUANDO ENCONTRA UM a
            if(s.charAt(i) == 'b') ab += a;	// INCREMENTA A QUANTIDADE a EM ab QUANDO ENCONTRA UM b
            if(s.charAt(i) == 'c') abc += ab;	// INCREMENTA A QUANTIDADE ab EM abc QUANDO ENCONTRA UM c
        }

        return abc;	// RETORNA A QUANTIDADE DE OCORRÊNCIAS DE abc NA STRING
    }

    public static long countABCPlain(String s) {
	int N = s.length();
	long t = 0;
	for (int i = 0; i < N; i++) 
	    for (int j = i + 1; j < N; j++) 
		for (int k = j + 1; k < N; k++)  
		    if (s.charAt(i) == 'a' && s.charAt(j) == 'b' && s.charAt(k) == 'c')
			t++;
	return t;
    }    
    
    public static void main(String[] args)
    {
	String s = StdIn.readString();
	Stopwatch sw = new Stopwatch();
	StdOut.println(countABC(s));
	StdOut.println("time: " + sw.elapsedTime());
	sw = new Stopwatch();
	StdOut.println(countABCPlain(s));
	StdOut.println("time: " + sw.elapsedTime());
    }
}
