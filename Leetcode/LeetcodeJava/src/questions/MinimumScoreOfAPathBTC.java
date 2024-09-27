package questions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Link: <a href="https://leetcode.com/problems/minimum-score-of-a-path-between-two-cities/">Minimum Score of a Path Between Two Cities</a>
 */
public class MinimumScoreOfAPathBTC {
    public int minScore(int n, int[][] roads) {
        // find the path at the minimum cost
//        int minIndex = 0;
//        for(int i = 0; i < n ;i++){
//            if(roads[i][2] < roads[minIndex][2]) minIndex = i;
//        }
//        int target1 = roads[minIndex][0];
//        int target2 = roads[minIndex][1];
//        boolean[] visited = new boolean[n];
//        for(int i = 0; i < n; i++) visited[i] = false;
//
//        DFS_Core(n, roads, 0);

        //Map<Integer, List<Integer>> neighbors = new HashMap<>();
        Map<Integer, List<Map.Entry<Integer, Integer>>> neighbors = new HashMap<>();

        for (int[] road : roads) {
            if (!neighbors.containsKey(road[0])) {
                neighbors.put(road[0], new ArrayList<>());
            }
            neighbors.get(road[0]).add(Map.entry(road[1], road[2]));

            if (!neighbors.containsKey(road[1])) {
                neighbors.put(road[1], new ArrayList<>());
            }
            neighbors.get(road[1]).add(Map.entry(road[0], road[2]));
        }

        boolean[] visited = new boolean[n];


        return DFS_Core(n, roads, 0, visited, neighbors, Integer.MAX_VALUE);
    }

    private int DFS_Core(int n, int[][] roads, int index, boolean[] visited, Map<Integer, List<Map.Entry<Integer, Integer>>> neighbors, int minDis) {
        visited[index] = true;
        for (Map.Entry<Integer, Integer> neighbor : neighbors.get(index + 1)) {
//            minDis = Math.min(minDis, lookDis(index + 1, neighbor, roads));
            minDis = Math.min(minDis, neighbor.getValue());
            if (!visited[neighbor.getKey() - 1]) {
                minDis = Math.min(minDis, DFS_Core(n, roads, neighbor.getKey() - 1, visited, neighbors, minDis));
            }
        }
        return minDis;
    }

    private int lookDis(int a, int b, int[][] roads) {
        for (int[] road : roads) {
            if ((road[0] == a && road[1] == b) || (road[1] == a && road[0] == b))
                return road[2];
        }
        return Integer.MAX_VALUE;
    }
}
