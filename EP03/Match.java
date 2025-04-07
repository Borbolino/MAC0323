/******************************************************************************
 *
 * MAC0323 ALGORITMOS E ESTRUTURAS DE DADOS II
 * Aluno: João Pedro Barioni Agostini
 * Numero USP: 14582163
 * Tarefa: E03
 * Data: 22/03/2024
 * 
 * O código a seguir encontra o pareamento perfeito de um objeto da classe
 * NutsAndBolts em tempo Nlog(N) (algoritmo probabilístico). Utilizando a
 * mesma lógica do algoritmo quicksort, organiza as porcas e parafusos
 * de acordo com um pivot selecionado e, ao final, devolve o vetor que
 * pareia os parafusos com as porcas.
 * Baseado nos códigos vistos em sala de aula e em estudos sobre recursão,
 * orientação a objetos e algoritmos de ordenação.
 *
 * DECLARO QUE SOU O ÚNICO AUTOR E RESPONSÁVEL POR ESTE PROGRAMA.  TODAS AS 
 * PARTES DO PROGRAMA, EXCETO AS QUE SÃO BASEADAS EM MATERIAL FORNECIDO  
 * PELO PROFESSOR OU COPIADAS DO LIVRO OU DAS BIBLIOTECAS DE SEDGEWICK & WAYNE, 
 * FORAM DESENVOLVIDAS POR MIM.  DECLARO TAMBÉM QUE SOU RESPONSÁVEL POR TODAS 
 * AS CÓPIAS DESTE PROGRAMA E QUE NÃO DISTRIBUÍ NEM FACILITEI A DISTRIBUIÇÃO
 * DE CÓPIAS DESTA PROGRAMA.
 *
 ******************************************************************************/

public class Match {

    public static void exch(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static int[] match(NutsAndBolts nab) {
        int N = nab.N();
        int[] bolts = new int[N];
        int[] nuts = new int[N];
        for(int i = 0; i < N; i++) { bolts[i] = i; nuts[i] = i; }

        int[] p = new int[N];

        return match(nab, 0, N-1, nuts, bolts, p);
    }

    public static int[] match(NutsAndBolts nab, int left, int right, int[] nuts, int[] bolts, int[] p) {
        if(left > right) return p;

        int i = left;

        while(nab.bolts(bolts[i]).compareTo(nab.nuts(nuts[left])) != 0) i++;
        exch(bolts, i, left);

        int l = left + 1; int r = right;

        while(l <= r) {
            if(nab.bolts(bolts[l]).compareTo(nab.nuts(nuts[left])) <= 0) l++;
            else if(nab.bolts(bolts[r]).compareTo(nab.nuts(nuts[left])) > 0) r--;
            else {
                exch(bolts, l++, r--);
            }
        }

        exch(bolts, left, r);
        int boltPivot = r;
        p[nuts[left]] = bolts[r];

        l = left + 1; r = right;

        while(l <= r) {
            if(nab.nuts(nuts[l]).compareTo(nab.bolts(bolts[boltPivot])) <= 0) l++;
            else if(nab.nuts(nuts[r]).compareTo(nab.bolts(bolts[boltPivot])) > 0) r--;
            else {
                exch(nuts, l++, r--);
            }
        }

        exch(nuts, left, r);

        p = match(nab, left, boltPivot - 1, nuts, bolts, p);
        p = match(nab, boltPivot + 1, right, nuts, bolts, p);

        return p;
    }
    
}
