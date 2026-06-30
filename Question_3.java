import java.util.*;

public class Question_3 {

        static final int INF = Integer.MAX_VALUE;
        static int V = 9; // nodes 0 to 8


        static List<int[]>[] buildGraph() {
            List<int[]>[] graph = new ArrayList[V];
            for (int i = 0; i < V; i++) graph[i] = new ArrayList<>();


            int[][] edges = {
                    {0, 1, 4},
                    {0, 7, 8},
                    {1, 2, 8},
                    {1, 7, 11},
                    {2, 3, 7},
                    {2, 8, 2},
                    {2, 5, 4},
                    {3, 4, 9},
                    {3, 5, 14},
                    {4, 5, 10},
                    {5, 6, 2},
                    {6, 7, 1},
                    {6, 8, 6},
                    {7, 8, 7}
            };

            for (int[] e : edges) {
                graph[e[0]].add(new int[]{e[1], e[2]});
                graph[e[1]].add(new int[]{e[0], e[2]});
            }
            return graph;
        }

        static void dijkstra(int src) {
            List<int[]>[] graph = buildGraph();

            int[] dist   = new int[V];   // shortest distance from src
            int[] parent = new int[V];   // to reconstruct path
            boolean[] visited = new boolean[V];

            Arrays.fill(dist,   INF);
            Arrays.fill(parent, -1);

            dist[src] = 0;

            // priority queue: {distance, node}
            PriorityQueue<int[]> pq = new PriorityQueue<>(
                    Comparator.comparingInt(a -> a[0])
            );
            pq.offer(new int[]{0, src});

            System.out.println("=== Dijkstra's Algorithm (Source: Node " + src + ") ===\n");
            System.out.printf("%-10s %-15s %-15s%n", "Step", "Node Visited", "Distances");
            System.out.println("-".repeat(55));

            int step = 1;

            while (!pq.isEmpty()) {
                int[] curr = pq.poll();
                int currDist = curr[0];
                int u        = curr[1];

                if (visited[u]) continue;
                visited[u] = true;


                System.out.printf("%-10d %-15s ", step++, "Node " + u);
                for (int i = 0; i < V; i++) {
                    if (dist[i] == INF)
                        System.out.printf("%-6s", "INF");
                    else
                        System.out.printf("%-6d", dist[i]);
                }
                System.out.println();

                for (int[] neighbor : graph[u]) {
                    int v      = neighbor[0];
                    int weight = neighbor[1];

                    if (!visited[v] && dist[u] != INF
                            && dist[u] + weight < dist[v]) {
                        dist[v]   = dist[u] + weight;
                        parent[v] = u;
                        pq.offer(new int[]{dist[v], v});
                    }
                }
            }


            System.out.println("\n=== Shortest Path Results ===\n");
            System.out.printf("%-10s %-15s %-20s%n",
                    "Node", "Distance", "Path from " + src);
            System.out.println("-".repeat(50));

            for (int i = 0; i < V; i++) {
                String path = getPath(parent, src, i);
                System.out.printf("%-10d %-15d %-20s%n", i, dist[i], path);
            }


            System.out.println("\n=== Shortest Path Tree Edges ===\n");
            for (int i = 0; i < V; i++) {
                if (parent[i] != -1) {

                    int w = 0;
                    for (int[] nb : graph[parent[i]]) {
                        if (nb[0] == i) { w = nb[1]; break; }
                    }
                    System.out.println("  " + parent[i] + " --(" + w + ")--> " + i);
                }
            }
        }


        static String getPath(int[] parent, int src, int node) {
            if (node == src) return "" + src;
            if (parent[node] == -1) return "No path";
            return getPath(parent, src, parent[node]) + " -> " + node;
        }

        public static void main(String[] args) {
            dijkstra(0);
        }
    }

