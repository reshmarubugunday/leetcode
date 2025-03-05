package packagemanager;


import java.util.ArrayList;
import java.util.*;
import java.util.HashMap;

/*
* Dependency list
* A : B, C
* D : E, F, A
* B : G I
*
*
* input List<String> = A, D
* output List<String> = B, C, A, E, F, D
*
*
* */
public class Solution {
    static HashMap<String, List<String>> dependencies = new HashMap<>();

    public static void main(String[] args) {
        // Define the dependency list
        Map<String, List<String>> dependencyList = new HashMap<>();
        dependencyList.put("A", Arrays.asList("B", "C"));
        dependencyList.put("D", Arrays.asList("E", "F", "A"));
        dependencyList.put("B", Arrays.asList("G", "I"));
        dependencyList.put("C", Arrays.asList("J", "K"));

        // Define the input list
        List<String> inputList = Arrays.asList("A", "D");

        // Resolve dependencies
        List<String> output = resolveDependencies(dependencyList, inputList);

        // Print the output
        System.out.println(output); // Output: [B, C, A, E, F, D]
    }

    public static List<String> resolveDependencies(Map<String, List<String>> dependencyList, List<String> inputList) {
        // Step 1: Build the dependency graph
        Map<String, List<String>> graph = new HashMap<>(dependencyList);

        // Step 2: Perform topological sort
        Set<String> visited = new HashSet<>();
        List<String> result = new ArrayList<>();

        for (String item : inputList) {
            topologicalSort(item, graph, visited, result);
        }

        return result;
    }

    private static void topologicalSort(String node, Map<String, List<String>> graph, Set<String> visited, List<String> result) {
        if (!visited.contains(node)) {
            visited.add(node);
            for (String neighbor : graph.getOrDefault(node, Collections.emptyList())) {
                topologicalSort(neighbor, graph, visited, result);
            }
            result.add(node);
        }
    }
}