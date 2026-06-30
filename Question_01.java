import java.util.*;

public class Question_01 {

    private static boolean bfs(int[][] residualGraph, int source, int sink, int[] parent) {
        int n = residualGraph.length;
        boolean[] visited = new boolean[n];
        Queue<Integer> queue = new LinkedList<>();

        queue.add(source);
        visited[source] = true;
        parent[source] = -1;

        while (!queue.isEmpty()) {
            int u = queue.poll();

            for (int v = 0; v < n; v++) {
                if (!visited[v] && residualGraph[u][v] > 0) {
                    queue.add(v);
                    visited[v] = true;
                    parent[v] = u;
                    if (v == sink) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static void computeMaxFlowMinCut(int[][] graph, int source, int sink, String[] nodeLabels) {
        int n = graph.length;
        int[][] residualGraph = new int[n][n];

        for (int i = 0; i < n; i++) {
            System.arraycopy(graph[i], 0, residualGraph[i], 0, n);
        }

        int[] parent = new int[n];
        int maxFlow = 0;


        while (bfs(residualGraph, source, sink, parent)) {
            int pathFlow = Integer.MAX_VALUE;


            for (int v = sink; v != source; v = parent[v]) {
                int u = parent[v];
                pathFlow = Math.min(pathFlow, residualGraph[u][v]);
            }


            for (int v = sink; v != source; v = parent[v]) {
                int u = parent[v];
                residualGraph[u][v] -= pathFlow;
                residualGraph[v][u] += pathFlow;
            }

            maxFlow += pathFlow;
        }

        System.out.println("--- EDMONDS-KARP MAXIMAL FLOW OUTPUT ---");
        System.out.println("Maximal Flow Value: " + maxFlow);


        boolean[] isReachable = new boolean[n];
        Queue<Integer> queue = new LinkedList<>();
        queue.add(source);
        isReachable[source] = true;

        while (!queue.isEmpty()) {
            int u = queue.poll();
            for (int v = 0; v < n; v++) {
                if (!isReachable[v] && residualGraph[u][v] > 0) {
                    isReachable[v] = true;
                    queue.add(v);
                }
            }
        }


        System.out.println("\nEdges forming the Minimum Cut:");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (isReachable[i] && !isReachable[j] && graph[i][j] > 0) {
                    System.out.println(nodeLabels[i] + " -> " + nodeLabels[j] + " (Capacity: " + graph[i][j] + ")");
                }
            }
        }
    }

    public static void main(String[] args) {

        String[] nodeLabels = {"A", "B", "C", "D", "E", "F", "G"};
        int n = 7;
        int[][] graph = new int[n][n];


        graph[0][1] = 3; // A -> B
        graph[0][3] = 3; // A -> D
        graph[1][2] = 4; // B -> C
        graph[2][0] = 3; // C -> A
        graph[2][3] = 1; // C -> D
        graph[2][4] = 2; // C -> E
        graph[3][4] = 2; // D -> E
        graph[3][5] = 6; // D -> F
        graph[4][1] = 1; // E -> B
        graph[4][6] = 1; // E -> G
        graph[5][6] = 9; // F -> G

        computeMaxFlowMinCut(graph, 0, 6, nodeLabels);
    }
}