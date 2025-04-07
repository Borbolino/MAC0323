/******************************************************************************
 *
 * MAC0323 ALGORITMOS E ESTRUTURAS DE DADOS II
 * Aluno: João Pedro Barioni Agostini
 * Numero USP: 14582163
 * Tarefa: E09
 * Data: 10/06/2024
 * 
 * O código a seguir resolve o problema 2SAT, ou seja, decide se um grafo de
 * implicações possui solução verdadeira e, se sim, a encontra. O código
 * utiliza as implementações do algoritmo de Kosaraju e Depth First Order de
 * Sedgewick e Wayne. Encontrando as componentes fortemente conexas do grafo,
 * o algoritmo as ordena em ordem topológica e decide se há uma contradição
 * nas implicações, encontrando ou não uma solução válida para o sistema.
 * O código é baseado nas implementações de grafos e algoritmos relacionados
 * de Sedgewick e Wayne, assim como demais algoritmos vistos em sala e em
 * estudos de Java, estruturas de dados e grafos.
 *
 * DECLARO QUE SOU O ÚNICO AUTOR E RESPONSÁVEL POR ESTE PROGRAMA.  TODAS AS 
 * PARTES DO PROGRAMA, EXCETO AS QUE SÃO BASEADAS EM MATERIAL FORNECIDO  
 * PELO PROFESSOR OU COPIADAS DO LIVRO OU DAS BIBLIOTECAS DE SEDGEWICK & WAYNE, 
 * FORAM DESENVOLVIDAS POR MIM.  DECLARO TAMBÉM QUE SOU RESPONSÁVEL POR TODAS 
 * AS CÓPIAS DESTE PROGRAMA E QUE NÃO DISTRIBUÍ NEM FACILITEI A DISTRIBUIÇÃO
 * DE CÓPIAS DESTA PROGRAMA.
 *
 ******************************************************************************/

import java.util.Stack;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;

public class TwoSAT {
    
    //////////////////////////////////////////
    //          DEPTH FIRST ORDER           //
    //////////////////////////////////////////

    private boolean[] markedDFO;
    private Stack<Integer> reversePostorder;

    private void depthFirstOrder(Digraph G) {
        reversePostorder = new Stack<Integer>();
        markedDFO = new boolean[G.V()];
        for (int v = 0; v < G.V(); v++)
            if (!markedDFO[v]) dfsDFO(G, v);
    }

    private void dfsDFO(Digraph G, int v) {
        markedDFO[v] = true;
        for (int w : G.adj(v))
            if (!markedDFO[w]) dfsDFO(G, w);
        reversePostorder.push(v);
    }

    //////////////////////////////////////////////
    //          KOSARAJU'S ALGORITHM            //
    //////////////////////////////////////////////

    private boolean[] markedK;
    private int[] id;
    private int count;

    private void kosarajuSharirSCC(Digraph G) {
        markedK = new boolean[G.V()];
        id = new int[G.V()];
        depthFirstOrder(G.reverse());
        while(!reversePostorder.isEmpty()) {
            int v = reversePostorder.pop();
            if (!markedK[v]) {
                dfsK(G, v);
                count++;
            }
        }
    }

    private void dfsK(Digraph G, int v) {
        markedK[v] = true;
        id[v] = count;
        for (int w : G.adj(v))
            if (!markedK[w]) dfsK(G, w);
    }

    private boolean stronglyConnected(int v, int w) { return id[v] == id[w]; }

    //////////////////////////////////////////
    //              2SAT SOLVER             //
    //////////////////////////////////////////

    boolean isSolvable = true;
    boolean[] assignment;
    String unSATProof = "";

    int indexUnSAT1, indexUnSAT2;
    boolean[] marked2SAT, marked;
    int[] edgeTo;
    boolean found;
    
    public TwoSAT(ImplicationGraph impgr) {
        kosarajuSharirSCC(impgr.g());

        for(int i = 1; i <= impgr.N() && isSolvable; i++) {
            if(stronglyConnected(2*i-2, 2*i-1)) {
                isSolvable = false;
                indexUnSAT1 = 2*i-2;
                indexUnSAT2 = 2*i-1;
            }
        }

        if(hasSolution()) {
            assignment = new boolean[impgr.N()+1];
            for(int i = 1; i <= impgr.N(); i++) {
                if(id[2*i-2] > id[2*i-1]) assignment[i] = false;        // NOTE QUE O ALGORITMO DE KOSARAJU RETORNA AS 
                else if(id[2*i-2] < id[2*i-1]) assignment[i] = true;    // COMPONENTES EM ORDEM TOPOLÓGICA INVERSA
            }
        }
        else {
            Stack<Integer> path1 = findPath(impgr, indexUnSAT1, indexUnSAT2);
            while(!path1.isEmpty()) {
                int pop = path1.pop();
                if(pop % 2 == 0) pop = (pop + 2) / 2;
                else pop = -(pop + 2) / 2;
                unSATProof = " => " + Integer.toString(pop) + unSATProof;
            }
            unSATProof = unSATProof.substring(4);

            unSATProof = unSATProof + "\n";
            String temp = "";

            Stack<Integer> path2 = findPath(impgr, indexUnSAT2, indexUnSAT1);
            while(!path2.isEmpty()) {
                int pop = path2.pop();
                if(pop % 2 == 0) pop = (pop + 2) / 2;
                else pop = -(pop + 2) / 2;
                temp = " => " + Integer.toString(pop) + temp;
            }
            temp = temp.substring(4);

            unSATProof = unSATProof + temp + "\n";
        }
    }

    public boolean hasSolution() {
        return isSolvable;
    }

    public boolean[] assignment() {
        return assignment;
    }

    public String unSATProof() {
        return unSATProof;
    }

    private Stack<Integer> findPath(ImplicationGraph impgr, int v, int w) {
        found = false;
        Stack<Integer> path = new Stack<Integer>();
        marked = new boolean[impgr.N()*2];
        return dfs(impgr, v, w, path);
    }

    private Stack<Integer> dfs(ImplicationGraph impgr, int v, int target, Stack<Integer> path) {
        marked[v] = true;
        path.push(v);
        if(v == target || found) { found = true; return path; }
        for (int w : impgr.g().adj(v)) {
            if (!marked[w]) {
                dfs(impgr, w, target, path);
                if(found) return path;
                path.pop();
            }
        }
        return path;
    }
}