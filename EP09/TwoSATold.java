import java.util.Stack;
import java.util.List;
import java.lang.Math;
import edu.princeton.cs.algs4.In;
import java.util.ArrayList;

public class TwoSATold {

    private boolean solvable = true;
    private boolean[] solutionArray;
    private int[] unsatIndex = new int[3];
    private String unSAT = "";
    private boolean[] stackVisited;
    private boolean[] visited;
    
    public TwoSATold(ImplicationGraph impgr) {

        solutionArray = new boolean[impgr.N()];

        List<List<Integer>> scc = kosaraju(impgr);

        for(int i = 0; i < scc.size() && solvable; i++) {
            boolean[] lp = new boolean[scc.get(i).size()];
            for(int j = 0; j < scc.get(i).size(); j++) {
                if(!lp[Math.abs(impgr.literal(scc.get(i).get(j)))])
                    lp[Math.abs(impgr.literal(scc.get(i).get(j)))] = true;
                else {
                    solvable = false;
                    unsatIndex[0] = i;
                    unsatIndex[1] = impgr.literal(scc.get(i).get(j));
                    unsatIndex[2] = -impgr.literal(scc.get(i).get(j));
                    break;
                }
            }
        }

        if(solvable) {
            System.out.println("Solvable...");;
        } else {
            for(int i = 0; i < scc.get(unsatIndex[0]).size(); i++) {
                if(scc.get(unsatIndex[0]).get(i) == impgr.vertex(unsatIndex[1])) {
                    int j = i;
                    while(scc.get(unsatIndex[0]).get(j) != impgr.vertex(unsatIndex[2])) {
                        unSAT = unSAT + Integer.toString(impgr.literal(scc.get(unsatIndex[0]).get(j))) + " => ";
                        j--;
                        if(j < 0) j = scc.get(unsatIndex[0]).size()-1;
                    }
                    unSAT = unSAT + Integer.toString(unsatIndex[2]) + "\n";
                    while(scc.get(unsatIndex[0]).get(j) != impgr.vertex(unsatIndex[1])) {
                        unSAT = unSAT + Integer.toString(impgr.literal(scc.get(unsatIndex[0]).get(j))) + " => ";
                        j--;
                        if(j < 0) j = scc.get(unsatIndex[0]).size()-1;
                    }
                    unSAT = unSAT + Integer.toString(unsatIndex[1]) + "\n";
                }
            }
        }

    }

    public boolean hasSolution() {
        return solvable;
    }

    public boolean[] assignment() {
        return solutionArray;
    }

    public String unSATProof() {
        return unSAT;
    }

    //////////////////////////////////////////////////
    //              FUNÇÕES AUXILIARES              //
    //////////////////////////////////////////////////

    private List<List<Integer>> kosaraju(ImplicationGraph impgr) {
        Stack<Integer> s = new Stack<>();
        s = stackDFS(impgr);    // CRIA UMA STACK NA ORDEM DE FINALIZAÇÃO DE BUSCA DOS VÉRTICES

        ImplicationGraph transposed = impgr.g().reverse();  // CRIA O GRAFO TRANSPOSTO

        return DFS(transposed, s);    // LISTA DE COMPONENTES FORTEMENTE CONEXAS
    
    }

    private Stack<Integer> stackDFS(ImplicationGraph impgr) {
        List<Integer> s = new ArrayList<Integer>();
        stackVisited = new boolean[2*impgr.N()];
        Stack<Integer> st = new Stack<Integer>();
        for(int i = 0; i < 2*impgr.N(); i++) {
            if(!stackVisited[i]) s = stackDFS(impgr, i, s);
            for(int j = 0; j < s.size(); j++) st.push(s.get(j));
        }
        return st;
    }

    private List<Integer> stackDFS(ImplicationGraph impgr, int v, List<Integer> s) {
        stackVisited[v] = true;
        for(int i: impgr.g().adj(v)) {
            if(!stackVisited[i]) s = stackDFS(impgr, i, s);
        }
        s.add(v);
        return s;
    }

    private List<List<Integer>> DFS(ImplicationGraph impgr, Stack<Integer> s) {
        visited = new boolean[2*impgr.N()];
        List<List<Integer>> l = new ArrayList<List<Integer>>();
        while(!s.empty()) {
            int v = s.pop();
            if(!visited[v]) l.add(DFS(impgr, v));
        }
    }

    private List<Integer> DFS(ImplicationGraph impgr, int v) {
        visited[v] = true;
        for(int j: impgr.g().adj(v)) {
            if(!visited[j]) DFS(impgr, j);
        }
    }

    public static void main(String[] args) {
        In in;
        if (args.length > 0)
            in = new In(args[0]); // input in args[0]
        else
            in = new In();        // input in stdin
        ImplicationGraph impgr = new ImplicationGraph(in);
        TwoSATold twoSAT = new TwoSATold(impgr);

        
    }

}
