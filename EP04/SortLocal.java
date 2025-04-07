/******************************************************************************
 *
 * MAC0323 ALGORITMOS E ESTRUTURAS DE DADOS II
 * Aluno: João Pedro Barioni Agostini
 * Numero USP: 14582163
 * Tarefa: E04
 * Data: 10/04/2024
 * 
 * O código a seguir utiliza a implementação da estrutura de dados HEAP para
 * agilizar a organização de um array k-permutado. Com um HEAP de tamanho
 * k + 1, itera sobre a lista e utiliza os métodos remove() e swim() para
 * obter o próximo menor valor e colocá-lo em seu lugar. Como os métodos são
 * de complexidade log k, o algoritmo tem complexidade de ordem N log k,
 * consideravelmente pequena e comportada quando compara-se com outros
 * algoritmos de ordenação comum. Baseado nas aulas dadas e em estudos sobre
 * HEAPs, listas de prioridade e Binary Trees. 
 *
 * DECLARO QUE SOU O ÚNICO AUTOR E RESPONSÁVEL POR ESTE PROGRAMA.  TODAS AS 
 * PARTES DO PROGRAMA, EXCETO AS QUE SÃO BASEADAS EM MATERIAL FORNECIDO  
 * PELO PROFESSOR OU COPIADAS DO LIVRO OU DAS BIBLIOTECAS DE SEDGEWICK & WAYNE, 
 * FORAM DESENVOLVIDAS POR MIM.  DECLARO TAMBÉM QUE SOU RESPONSÁVEL POR TODAS 
 * AS CÓPIAS DESTE PROGRAMA E QUE NÃO DISTRIBUÍ NEM FACILITEI A DISTRIBUIÇÃO
 * DE CÓPIAS DESTA PROGRAMA.
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.StdOut;

public class SortLocal
{
	public static int getLeftChild(Comparable[] v, int root, int k, int i) {
        int child = (i - root + 1) * 2 - 1 + root; 
        if(child <= k + root) return child;
        return -1;
    }

    public static int getRightChild(Comparable[] v, int root, int k, int i) {
        int child = (i - root + 1) * 2 + root;
        if(child <= k + root) return child;
        return -1;
    }

	public static int getParent(Comparable[] v, int root, int i) {
		if(i <= 0) return -1;
		return (i - root - 1) / 2 + root;
	}

    public static void exch(Comparable[] v, int i, int j) {
		Comparable temp = v[i];
		v[i] = v[j];
		v[j] = temp;
	}

    public static Comparable[] buildMinHeap(Comparable[] v, int root, int k) {
		Comparable[] heap = new Comparable[k+1];
		for(int i = 0; i <= k; i++) heap[i] = v[root + i];
        for(int i = (k + 1) / 2 - 1; i >= 0; i--) minHeapify(heap, 0, k, i);
        return heap;
    }

    public static void minHeapify(Comparable[] v, int root, int k, int i) {
        int l = getLeftChild(v, root, k, i);
        int r = getRightChild(v, root, k, i);
        int smallest = i;

        if(l >= 0 && v[l].compareTo(v[i]) < 0) smallest = l;
        if(r >= 0 && v[r].compareTo(v[smallest]) < 0) smallest = r;

        if(smallest != i) {
            exch(v, i, smallest);
            minHeapify(v, root, k, smallest);
        }
    }

	public static void swim(Comparable[] v, int root, int k, int i) {
        while(i > 0) {
            int p = getParent(v, root, i);
            if(v[p].compareTo(v[i]) > 0) { exch(v, i, p); i = p; }
            else break;
        }
    }

	public static void sink(Comparable[] v, int root, int k, int i) {
        int last = root + k;

        while(i < last && getLeftChild(v, root, k, i) >= 0) {
            int l = getLeftChild(v, root, k, i);
            int r = getRightChild(v, root, k, i);
            int smallest = -1;

            if(r >= 0 && v[r].compareTo(v[l]) < 0) smallest = r;
            else if(l >= 0) smallest = l;

            if(smallest >= 0 && v[i].compareTo(v[smallest]) > 0) {
                exch(v, i, smallest);
                i = smallest;
            } else break;
        }
    }

    public static Comparable remove(Comparable[] v, int root, int k) {
        exch(v, root, root + k);
        sink(v, root, k-1, root);
		Comparable ret = v[root + k];
		v[root + k] = 0;
		return ret;
    }

    public static void sortLocal(Comparable[] v, int k) {
		Comparable[] heap = buildMinHeap(v, 0, k);
        Comparable temp = remove(heap, 0, k);
        int i = 0;
        for(i = 1; i < v.length - k; i++) {
            heap[k] = v[i + k];
            v[i-1] = temp;
            swim(heap, 0, k, k);
            temp = remove(heap, 0, k);
		}
        v[i-1] = temp;
        k--;

        while(k >= 0) {
            temp = remove(heap, 0, k--);
            v[i++] = temp;
        }
    }

    public static void show(Comparable[] v) {
	for (int i = 0; i < v.length; i++) 
	    StdOut.print(v[i] + " ");
	StdOut.println();
    }

    public static boolean isSorted(Comparable[] v) {
	for (int i = 1; i < v.length; i++) 
	    if (v[i - 1].compareTo(v[i]) > 0)
		return false;
	return true;
    }

    public static void main(String[] args)
    {
	int N = Integer.parseInt(args[0]);
	int k = Integer.parseInt(args[1]);
	long seed = Long.parseLong(args[2]);
	Integer[] v = RandomPerm.kPermInteger(N, k);
	show(v);
	sortLocal(v, k);
	StdOut.println("Sorted: ");
	show(v);
	assert isSorted(v);
    }
}
