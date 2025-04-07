/******************************************************************************
 *
 * MAC0323 ALGORITMOS E ESTRUTURAS DE DADOS II
 * Aluno: João Pedro Barioni Agostini
 * Numero USP: 14582163
 * Tarefa: E01 (Organização de Bags)
 * Data: 10/03/2024
 * 
 * O programa a seguir toma como base o programa Bag.java, dos autores
 * Sedgewick e Wayne, implementando um novo método: o sort(), que organiza
 * uma bag instanciada utilizando a técnica do merge sort com custo de memória
 * O(1) (ignorando o custo da recursão). 
 *
 * DECLARO QUE SOU O ÚNICO AUTOR E RESPONSÁVEL POR ESTE PROGRAMA.  TODAS AS 
 * PARTES DO PROGRAMA, EXCETO AS QUE SÃO BASEADAS EM MATERIAL FORNECIDO  
 * PELO PROFESSOR OU COPIADAS DO LIVRO OU DAS BIBLIOTECAS DE SEDGEWICK & WAYNE, 
 * FORAM DESENVOLVIDAS POR MIM.  DECLARO TAMBÉM QUE SOU RESPONSÁVEL POR TODAS 
 * AS CÓPIAS DESTE PROGRAMA E QUE NÃO DISTRIBUÍ NEM FACILITEI A DISTRIBUIÇÃO
 * DE CÓPIAS DESTA PROGRAMA.
 *
 ******************************************************************************/

import java.util.Iterator;
import java.util.NoSuchElementException;

public class BagVariant<Item extends Comparable<Item>> implements Iterable<Item> {
    private Node<Item> first;
    private int n;

    private static class Node<Item extends Comparable<Item>> {
        private Item item;
        private Node<Item> next;
    }

    public BagVariant() {
        first = null;
        n = 0;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public int size() {
        return n;
    }

    public void add(Item item) {
        Node<Item> oldfirst = first;
        first = new Node<Item>();
        first.item = item;
        first.next = oldfirst;
        n++;
    }

    // FUNÇÃO QUE COMPARA O VALOR DE DOIS NÓS E RETORNA true SE v < w
    private boolean less(Node<Item> v, Node<Item> w) { return v.item.compareTo(w.item) < 0; }

    // FUNÇÃO CÁPSULA (CHAMA sort(a, b))
    public void sort() {
        if(n >= 2) this.first = sort(this.first, n);
        // IMEDIATAMENTE DEFINE O ELEMENTO first DA BAG
    }

    // FUNÇÃO QUE REALIZA O merge sort
    public Node<Item> sort(Node<Item> start, int size) {
        // NÃO É NECESSÁRIO ORGANIZAR OS ELEMENTOS 1 A 1
        if(size > 1) {
            Node<Item> left = start;    // INÍCIO A BAG À ESQUERDA
            Node<Item> right = start;

            // ENCONTRA O INÍCIO DA BAG À DIREITA
            for(int i = 0; i < size/2 - 1; i++) right = right.next;
            Node<Item> tmp = right;
            right = right.next;
            tmp.next = null;    // SEPARA A BAG EM DUAS (ESQUERDA E DIREITA)

            left = sort(left, size/2);  // ORGANIZA A BAG ESQUERDA
            // ORGANIZA A BAG DIREITA (TAMANHOS DIFERENTES PARA n PAR/ÍMPAR)
            if(size%2 == 0) right = sort(right, size/2);
            else right = sort(right, size/2 + 1);

            Node<Item> iter;    // NÓ QUE "ITERA" AS BAGS EM ORDEM ALTERNADAMENTE

            // DEFINE O MENOR ELEMENTO
            if(less(left, right)) { iter = left; left = left.next; }
            else { iter = right; right = right.next; }

            Node<Item> saveFirst = iter;    // GUARDA O MENOR ELEMENTO

            // ORGANIZAÇÃO DA BAG (iter ITERA A BAG EM ORDEM NÃO-DECRESCENTE, CONECTANDO OS ELEMENTOS)
            while(left != null && right != null) {
                if(less(left, right)) { iter.next = left; left = left.next; iter = iter.next; }
                else { iter.next = right; right = right.next; iter = iter.next; }
            }

            // CONECTA OS ELEMENTOS QUE RESTARAM
            if(left != null) iter.next = left;
            if(right != null) iter.next = right;

            return saveFirst;   // RETORNA O NÓ DE MENOR VALOR
        } else return start;    // NÃO É NECESSÁRIO ORGANIZAR OS ELEMENTOS 1 A 1
    }

    public Iterator<Item> iterator()  {
        return new LinkedIterator(first);
    }

    private class LinkedIterator implements Iterator<Item> {
        private Node<Item> current;

        public LinkedIterator(Node<Item> first) {
            current = first;
        }

        public boolean hasNext()  {
            return current != null;
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    public static void main(String[] args) {
        BagVariant<String> bag = new BagVariant<String>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            bag.add(item);
        }

        StdOut.println("size of bag = " + bag.size());
        for (String s : bag) {
            StdOut.println(s);
        }

        bag.sort();
        
        System.out.println("Sorted bag:");
        for(String s : bag) {
            StdOut.println(s);
        }
    }

}