import java.io.*;
import java.util.ArrayList;

public class PebblingGame {
    public int[][] G_adj; //adjacency matrix of the graph
    public double[] profit; //profits of each node
    public int n; //number of vertices
    
    /**
    * Read in a file for an adjacency matrix to find the vertices
    * and profits of each vertex.
    */
    public PebblingGame(String filename) throws IOException {
        File f = new File(filename);
        FileReader fr = new FileReader(f);
        BufferedReader bfr = new BufferedReader(fr);
        String line = bfr.readLine();
        n = Integer.parseInt(line);
        G_adj = new int[n][n];
        profit = new double[n];
        String[] readLine;
        for (int i = 0; i < n; i++) {
            readLine = bfr.readLine().split(" ");
            for (int j = 0; j < n; j++) {
                G_adj[i][j] = Integer.parseInt(readLine[j]);
            }
        }
        readLine = bfr.readLine().split(" ");
        for (int i = 0; i < readLine.length; i++) {
            profit[i] = Double.parseDouble(readLine[i]);
        }
        bfr.close();
    }
    
    public static void main(String[] args) throws IOException {
        System.out.println("Main function: ");
        Graph g = new Graph("inputfile.txt");
        g.rootTree(0);
    }
    
    public ArrayList<Integer> pebbling(int V){
        ArrayList<Integer> pebbled = new ArrayList<Integer>();
        rootTree(V);
        double[] isPebbled = new double[n];
        double[] notPebbled = new double[n];
        for (int i = 0; i < isPebbled.length; i++) {
            isPebbled[i] = profit[i];
        }
        return pebbled;
    }
    
    private void recursePebble(int V, double[] pebbled, double[] notPebbled) {
        ArrayList<Integer> children = findChildren(V);
        if (children.size() == 0) {
            notPebbled[V] = 0;
        } else {
            for (int i = 0; i < children.size(); i++) {
                recursePebble(children.get(i), pebbled, notPebbled);
                pebbled[V] += notPebbled[children.get(i)];
                notPebbled[V] += Double.max(pebbled[children.get(i)], notPebbled[children.get(i)]);
            }
        }
    }
    
    private void rootTree(int V) {
        for (int i = 0; i < G_adj.length; i++) {
            if (G_adj[V][i] == 1) {
                G_adj[i][V] = 0;
                rootTree(i);
            }
        }
    }

    private ArrayList<Integer> findChildren(int V) {
        ArrayList<Integer> children = new ArrayList<>();
        for (int i = 0; i < G_adj.length; i++) {
            if (G_adj[V][i] == 1) {
                children.add(i);
            }
        }
        return children;
    }
}
