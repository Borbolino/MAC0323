/******************************************************************************
 *
 * MAC0323 ALGORITMOS E ESTRUTURAS DE DADOS II
 * Aluno: João Pedro Barioni Agostini
 * Numero USP: 14582163
 * Tarefa: E07
 * Data: 18/05/2024
 * 
 * O código abaixo incrementa à classe original LinearProbingHashST de
 * Sedgewick e Wayne alguns métodos que podem realizar análises estatísticas
 * das tabelas de Hash implementadas no programa original. Por meio de pequenas
 * alterações no código original, podemos calcular exatamente os ANPH e a ANPM
 * da tabela corrente, tal como outras estatísticas. O programa foi criado com
 * base nos códigos vistos em sala, nos programas e Sedgewick e Wayne e em
 * estudos sobre Linear Probing e Hashing em sala de aula e individuais.
 *
 * DECLARO QUE SOU O ÚNICO AUTOR E RESPONSÁVEL POR ESTE PROGRAMA.  TODAS AS 
 * PARTES DO PROGRAMA, EXCETO AS QUE SÃO BASEADAS EM MATERIAL FORNECIDO  
 * PELO PROFESSOR OU COPIADAS DO LIVRO OU DAS BIBLIOTECAS DE SEDGEWICK & WAYNE, 
 * FORAM DESENVOLVIDAS POR MIM.  DECLARO TAMBÉM QUE SOU RESPONSÁVEL POR TODAS 
 * AS CÓPIAS DESTE PROGRAMA E QUE NÃO DISTRIBUÍ NEM FACILITEI A DISTRIBUIÇÃO
 * DE CÓPIAS DESTA PROGRAMA.
 *
 ******************************************************************************/

/******************************************************************************
 *  Compilation:  javac LinearProbingHashST.java
 *  Execution:    java LinearProbingHashST < input.txt
 *  Dependencies: StdIn.java StdOut.java
 *  Data files:   https://algs4.cs.princeton.edu/34hash/tinyST.txt
 *  
 *  Symbol-table implementation with linear-probing hash table.
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Queue;

/**
 *  The {@code LinearProbingHashST} class represents a symbol table of generic
 *  key-value pairs.
 *  It supports the usual <em>put</em>, <em>get</em>, <em>contains</em>,
 *  <em>delete</em>, <em>size</em>, and <em>is-empty</em> methods.
 *  It also provides a <em>keys</em> method for iterating over all of the keys.
 *  A symbol table implements the <em>associative array</em> abstraction:
 *  when associating a value with a key that is already in the symbol table,
 *  the convention is to replace the old value with the new value.
 *  Unlike {@link java.util.Map}, this class uses the convention that
 *  values cannot be {@code null}—setting the
 *  value associated with a key to {@code null} is equivalent to deleting the key
 *  from the symbol table.
 *  <p>
 *  This implementation uses a linear probing hash table. It requires that
 *  the key type overrides the {@code equals()} and {@code hashCode()} methods.
 *  The expected time per <em>put</em>, <em>contains</em>, or <em>remove</em>
 *  operation is constant, subject to the uniform hashing assumption.
 *  The <em>size</em>, and <em>is-empty</em> operations take constant time.
 *  Construction takes constant time.
 *  <p>
 *  For additional documentation, see <a href="https://algs4.cs.princeton.edu/34hash">Section 3.4</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *  For other implementations, see {@link ST}, {@link BinarySearchST},
 *  {@link SequentialSearchST}, {@link BST}, {@link RedBlackBST}, and
 *  {@link SeparateChainingHashST},
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */
public class LinearProbingHashSTX<Key, Value> {

    // must be a power of 2
    private static final int INIT_CAPACITY = 4;

    private int n;           // number of key-value pairs in the symbol table
    private int m;           // size of linear probing table
    private Key[] keys;      // the keys
    private Value[] vals;    // the values

    /******************************************************/
    //                                                    //
    //                    FUNÇÕES EP 7                    //
    //                                                    //
    /******************************************************/

    // VARIÁVEL DE INSTÂNCIA DO EP 7
    // CONTA O TOTAL DE DESLOCAMENTO DAS CHAVES INSERIDAS NA TABELA
    // ATUALIZADO NO MÉTODO put()
    private int displacement = 0;

    public double hitProbes() {
        return 1.0 * this.displacement / n + 1;
    }

    public double missProbes() {
        int[] clusters = clusters();
        int ret = 0;

        for(int i : clusters) {
            ret += (i + 2) * (i + 1) / 2 - 1;
        }
        return 1.0 * (ret + tableSize() - n) / tableSize();
    }

    public int[] clusters() {
        int clusterNumber = 0;
        int clusterCounter = 0;
        int count = 0;
        int[] clusters;

        for(int i = 1; i < tableSize(); i++) {
            if(keys[i] == null && keys[i-1] != null) clusterNumber++;
        }

        if(keys[tableSize()-1] != null) clusterNumber++;

        if(clusterNumber == 0) { clusters = new int[1]; clusters[0] = 0; return clusters; }

        clusters = new int[clusterNumber];

        for(int i = 0; i < tableSize(); i++) {
            if(keys[i] != null) clusterCounter++;
            if(i > 0 && keys[i] == null && keys[i-1] != null) {
                clusters[count++] = clusterCounter;
                clusterCounter = 0;
            }
            if(keys[tableSize()-1] != null) clusters[count++] = ++clusterCounter;
        }

        return clusters;
    }

    public int maxClusterSize()  {
        int clusterCounter = 0;
        int maxCluster = 0;
        for(int i = 0; i < tableSize(); i++) {
            if(keys[i] == null) clusterCounter = 0;
            else clusterCounter++;
            if(clusterCounter > maxCluster) maxCluster = clusterCounter;
        }

        return maxCluster;
    }

    public boolean[] table() {
        boolean[] boolTable = new boolean[tableSize()];

        for(int i = 0; i < tableSize(); i++) {
            if(keys[i] == null) boolTable[i] = false;
            else boolTable[i] = true;
        }

        return boolTable;
    }

    public int tableSize() {
        return m;
    }

    public double loadFactor() {
        return 1.0 * n / m;
    }


    /******************************************************/

    /**
     * Initializes an empty symbol table.
     */
    public LinearProbingHashSTX() {
        this(INIT_CAPACITY);
    }

    /**
     * Initializes an empty symbol table with the specified initial capacity.
     *
     * @param capacity the initial capacity
     */
    public LinearProbingHashSTX(int capacity) {
        m = capacity;
        n = 0;
        keys = (Key[])   new Object[m];
        vals = (Value[]) new Object[m];
    }

    /**
     * Returns the number of key-value pairs in this symbol table.
     *
     * @return the number of key-value pairs in this symbol table
     */
    public int size() {
        return n;
    }

    /**
     * Returns true if this symbol table is empty.
     *
     * @return {@code true} if this symbol table is empty;
     *         {@code false} otherwise
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Returns true if this symbol table contains the specified key.
     *
     * @param  key the key
     * @return {@code true} if this symbol table contains {@code key};
     *         {@code false} otherwise
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public boolean contains(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to contains() is null");
        return get(key) != null;
    }

    // hash function for keys - returns value between 0 and m-1
    private int hashTextbook(Key key) {
        return (key.hashCode() & 0x7fffffff) % m;
    }

    // textbook hash function
    private int hash(Key key) {
	return hashTextbook(key);
    }

    // hash function for keys - returns value between 0 and m-1 (assumes m is a power of 2)
    // (from Java 7 implementation, protects against poor quality hashCode() implementations)
    // private int hash(Key key) {
    //     int h = key.hashCode();
    //     h ^= (h >>> 20) ^ (h >>> 12) ^ (h >>> 7) ^ (h >>> 4);
    //     return h & (m-1);
    // }

    // resizes the hash table to the given capacity by re-hashing all of the keys
    private void resize(int capacity) {
        LinearProbingHashSTX<Key, Value> temp = new LinearProbingHashSTX<Key, Value>(capacity);
        this.displacement = 0;
        for (int i = 0; i < m; i++) {
            if (keys[i] != null) {
                temp.put(keys[i], vals[i]);
            }
        }
        keys = temp.keys;
        vals = temp.vals;
        m    = temp.m;
        this.displacement = temp.displacement;
    }

    /**
     * Inserts the specified key-value pair into the symbol table, overwriting the old 
     * value with the new value if the symbol table already contains the specified key.
     * Deletes the specified key (and its associated value) from this symbol table
     * if the specified value is {@code null}.
     *
     * @param  key the key
     * @param  val the value
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public void put(Key key, Value val) {
        if (key == null) throw new IllegalArgumentException("first argument to put() is null");

        if (val == null) {
            delete(key);
            return;
        }

        // double table size if 50% full
        if (n >= m/2) resize(2*m);

        int i;
        int tempD = 0;
        for (i = hash(key); keys[i] != null; i = (i + 1) % m) {
            tempD++;
            if (keys[i].equals(key)) {
                vals[i] = val;
                return;
            }
        }
        this.displacement += tempD;
        keys[i] = key;
        vals[i] = val;
        n++;
    }

    /**
     * Returns the value associated with the specified key.
     * @param key the key
     * @return the value associated with {@code key};
     *         {@code null} if no such value
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public Value get(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to get() is null");
        for (int i = hash(key); keys[i] != null; i = (i + 1) % m)
            if (keys[i].equals(key))
                return vals[i];
        return null;
    }

    /**
     * Removes the specified key and its associated value from this symbol table     
     * (if the key is in this symbol table).    
     *
     * @param  key the key
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public void delete(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to delete() is null");
        if (!contains(key)) return;

        // find position i of key
        int i = hash(key);
        while (!key.equals(keys[i])) {
            i = (i + 1) % m;
        }

        // delete key and associated value
        keys[i] = null;
        vals[i] = null;

        // rehash all keys in same cluster
        i = (i + 1) % m;
        while (keys[i] != null) {
            // delete keys[i] and vals[i] and reinsert
            Key   keyToRehash = keys[i];
            Value valToRehash = vals[i];
            keys[i] = null;
            vals[i] = null;
            n--;
            put(keyToRehash, valToRehash);
            i = (i + 1) % m;
        }

        n--;

        // halves size of array if it's 12.5% full or less
        if (n > 0 && n <= m/8) resize(m/2);

        assert check();
    }

    /**
     * Returns all keys in this symbol table as an {@code Iterable}.
     * To iterate over all of the keys in the symbol table named {@code st},
     * use the foreach notation: {@code for (Key key : st.keys())}.
     *
     * @return all keys in this symbol table
     */
    public Iterable<Key> keys() {
        Queue<Key> queue = new Queue<Key>();
        for (int i = 0; i < m; i++)
            if (keys[i] != null) queue.enqueue(keys[i]);
        return queue;
    }

    // integrity check - don't check after each put() because
    // integrity not maintained during a call to delete()
    private boolean check() {

        // check that hash table is at most 50% full
        if (m < 2*n) {
            System.err.println("Hash table size m = " + m + "; array size n = " + n);
            return false;
        }

        // check that each key in table can be found by get()
        for (int i = 0; i < m; i++) {
            if (keys[i] == null) continue;
            else if (get(keys[i]) != vals[i]) {
                System.err.println("get[" + keys[i] + "] = " + get(keys[i]) + "; vals[i] = " + vals[i]);
                return false;
            }
        }
        return true;
    }


    /**
     * Unit tests the {@code LinearProbingHashST} data type.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) { 
        LinearProbingHashSTX<String, Integer> st = new LinearProbingHashSTX<String, Integer>();
        for (int i = 0; !StdIn.isEmpty(); i++) {
            String key = StdIn.readString();
            st.put(key, i);
        }

        // print keys
        for (String s : st.keys()) 
            StdOut.println(s + " " + st.get(s)); 
    }
}

/******************************************************************************
 *  Copyright 2002-2022, Robert Sedgewick and Kevin Wayne.
 *
 *  This file is part of algs4.jar, which accompanies the textbook
 *
 *      Algorithms, 4th edition by Robert Sedgewick and Kevin Wayne,
 *      Addison-Wesley Professional, 2011, ISBN 0-321-57351-X.
 *      http://algs4.cs.princeton.edu
 *
 *
 *  algs4.jar is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  algs4.jar is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with algs4.jar.  If not, see http://www.gnu.org/licenses.
 ******************************************************************************/
