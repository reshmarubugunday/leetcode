package serverconnection;
/*
LinkedIn operates M applications distributed across N servers in a cluster. Each i-th server hosts one or
 more application components, represented by apps[i], a list of application IDs running on that server.

The servers communicate via a network of cables, represented as a list of bidirectional connections.
Each connection between two servers has a corresponding durability score, given in durability[i].

During an earthquake of intensity K, any connection with a durability score less than K is damaged, breaking
communication between the connected servers.

An application is considered disconnected if at least one of its components can no longer reach another
component of the same application through the remaining network.

Task
Write a function to return a list of application IDs that become disconnected due to the earthquake.

Example
Input:
M = 4, N = 4

apps = [ [1, 2], [2, 3, 4], [3], [1] ]

connections = [ [1, 2], [2, 3], [3, 4] ]

durability = [10, 8, 12]

K = 9

Output:

[1, 3]

Explanation:
The connection between servers 2 and 3 has a durability of 8, which is less than K (9), so it is damaged.
Application 1 is hosted on servers 1 and 4, but without the connection 1 → 2 → 3 → 4, its components can no longer communicate.
Application 3 is hosted on servers 2 and 3, and since the connection 2 → 3 is broken, it becomes disconnected.

*/
import java.util.*;

public class Solution {

    /**
     * Returns a list of application IDs that become disconnected after the earthquake.
     *
     * @param M            Total number of applications (application IDs range from 1 to M)
     * @param N            Number of servers (servers numbered 1..N)
     * @param apps         2D array where apps[i] is an array of application IDs running on server i (0-indexed).
     * @param connections  2D array of connections, where each connection is represented as [u, v] (1-indexed servers).
     * @param durability   Array of durability scores corresponding to each connection.
     * @param K            Earthquake intensity: any connection with durability less than K is broken.
     * @return             List of application IDs that become disconnected.
     */
    public static List<Integer> getDisconnectedApps(
            int M, int N, int[][] apps,
            int[][] connections, int[] durability, int K) {

        // Build graph: each server is a node (0-indexed)
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            graph.add(new ArrayList<>());
        }

        // Include an edge only if the connection's durability is at least K.
        for (int i = 0; i < connections.length; i++) {
            if (durability[i] >= K) {
                // Convert the 1-indexed server IDs to 0-indexed.
                int u = connections[i][0] - 1;
                int v = connections[i][1] - 1;
                graph.get(u).add(v);
                graph.get(v).add(u);
            }
        }

        // Identify connected components using DFS.
        int[] component = new int[N];
        Arrays.fill(component, -1);
        int compId = 0;
        for (int i = 0; i < N; i++) {
            if (component[i] == -1) {
                dfs(i, compId, graph, component);
                compId++;
            }
        }

        // For each application, record the connected components in which it appears.
        // If an app spans more than one connected component, then it is disconnected.
        Map<Integer, Set<Integer>> appComponents = new HashMap<>();
        for (int server = 0; server < N; server++) {
            for (int app : apps[server]) {
                appComponents.computeIfAbsent(app, k -> new HashSet<>()).add(component[server]);
            }
        }

        List<Integer> disconnectedApps = new ArrayList<>();
        for (Map.Entry<Integer, Set<Integer>> entry : appComponents.entrySet()) {
            if (entry.getValue().size() > 1) {
                disconnectedApps.add(entry.getKey());
            }
        }
        Collections.sort(disconnectedApps);
        return disconnectedApps;
    }

    // Helper method: DFS to label connected components.
    private static void dfs(int node, int compId, List<List<Integer>> graph, int[] component) {
        component[node] = compId;
        for (int neighbor : graph.get(node)) {
            if (component[neighbor] == -1) {
                dfs(neighbor, compId, graph, component);
            }
        }
    }

    // Example testing with the provided sample input.
    public static void main(String[] args) {
        int M = 4, N = 4;

        // apps[i] lists the applications hosted on server i.
        int[][] apps = new int[4][];
        apps[0] = new int[]{1, 2};    // Server 1 hosts applications 1 and 2.
        apps[1] = new int[]{2, 3, 4}; // Server 2 hosts applications 2, 3, and 4.
        apps[2] = new int[]{3};       // Server 3 hosts application 3.
        apps[3] = new int[]{1};       // Server 4 hosts application 1.

        // Connections: each connection is between two servers (1-indexed).
        int[][] connections = {
                {1, 2},
                {2, 3},
                {3, 4}
        };

        // Durability scores for the corresponding connections.
        int[] durability = {10, 8, 12};

        // Earthquake intensity K: connections with durability less than 9 break.
        int K = 9;

        List<Integer> result = getDisconnectedApps(M, N, apps, connections, durability, K);
        System.out.println(result); // Expected output: [1, 3]
    }
}
