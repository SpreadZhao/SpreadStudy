package exercise;

/**
 * We assume that the index of edges is the index of nodes.
 */
public class MatrixGraph {
    private final static int NOT_EXIST = Integer.MAX_VALUE;
    private final static int NOT_FOUND = Integer.MIN_VALUE;
    public int[][] edges;
    public GraphType type;

    public int size;
    public boolean[] visited;

    public MatrixGraph(int n, GraphType type, int[][] edges) {
        this.edges = new int[n][n];
        this.visited = new boolean[n];
        for (int i = 0; i < n; i++) {
            System.arraycopy(edges[i], 0, this.edges[i], 0, n);
            visited[i] = false;
        }
        this.size = n;
        this.type = type;
    }

    public static void DFS(MatrixGraph G, int start) {
        for (int i = 0; i < G.size; i++) G.visited[i] = false;
        DFS_Core(G, start);
//        for(int i = 0; i < G.size; i++){
//            if(!G.visited[i]){
//                DFS_Core(G, i);
//            }
//        }
    }

    private static void DFS_Core(MatrixGraph G, int index) {
        System.out.println("[" + index + "]");
        G.visited[index] = true;
        for (int i = firstNeighbor(G, index); i != NOT_FOUND; i = secondNeighbor(G, index, i)) {
            DFS_Core(G, i);
        }
    }

    // Find the first connected and UNVISITED neighbor node of index.
    private static int firstNeighbor(MatrixGraph G, int index) {
        for (int j = 0; j < G.size; j++) {
            if (G.edgeExist(index, j) && !G.visited[j]) return j;
        }
        return NOT_FOUND;
    }

    /**
     * Find the second connected and UNVISITED neighbor node of index,
     * except f_index
     *
     * @param G       Graph to search
     * @param index   the target node
     * @param f_index the first neighbor of the target node
     * @return the index of the second neighbor
     */
    private static int secondNeighbor(MatrixGraph G, int index, int f_index) {
        for (int j = f_index + 1; j < G.size; j++) {
            if (G.edgeExist(index, j) && !G.visited[j]) return j;
        }
        return NOT_FOUND;
    }

    private boolean edgeExist(int i, int j) {
        return edges[i][j] != NOT_EXIST;
    }


}
