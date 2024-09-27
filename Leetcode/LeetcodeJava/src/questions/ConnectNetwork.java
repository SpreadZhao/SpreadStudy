package questions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Link: <a href="https://leetcode.com/problems/number-of-operations-to-make-network-connected">Number of Operations to Make Network Connected</a>
 * <p>
 * Solution: <a href="https://leetcode.com/problems/number-of-operations-to-make-network-connected/editorial/">Using DFS</a>
 */
public class ConnectNetwork {
    private static void DFS_Core(int index, Map<Integer, List<Integer>> edges, List<Boolean> visited) {
        visited.set(index, true);
        if (!edges.containsKey(index)) return;
        for (int neighbor : edges.get(index)) {
            if (!visited.get(neighbor)) {
                DFS_Core(neighbor, edges, visited);
            }
        }
    }

    public int makeConnected(int n, int[][] connections) {
        if (connections.length < n - 1) return -1;
        Map<Integer, List<Integer>> edges = new HashMap<>();
        for (int[] connection : connections) {
            if (!edges.containsKey(connection[0])) edges.put(connection[0], new ArrayList<>());
            edges.get(connection[0]).add(connection[1]);
            if (!edges.containsKey(connection[1])) edges.put(connection[1], new ArrayList<>());
            edges.get(connection[1]).add(connection[0]);
        }

        List<Boolean> visited = new ArrayList<>() {
            {
                for (int i = 0; i < n; i++) add(false);
            }
        };
        int islandNum = 0;

        for (int i = 0; i < n; i++) {
            if (!visited.get(i)) {
                ++islandNum;
                DFS_Core(i, edges, visited);
            }
        }

        return islandNum - 1;
    }
}
