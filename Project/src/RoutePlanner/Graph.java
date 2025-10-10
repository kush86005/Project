package RoutePlanner;

import java.util.*;

public class Graph {
    private Map<String, List<Edge>> adjList = new HashMap<>();

    static class Edge {
        String destination;
        int distance;

        Edge(String destination, int distance) {
            this.destination = destination;
            this.distance = distance;
        }
    }

    // Add a city (stored in lowercase)
    public boolean addCity(String city) {
        if (city == null || city.trim().isEmpty()) return false;
        city = city.trim().toLowerCase();
        if (adjList.containsKey(city)) return false;
        adjList.put(city, new ArrayList<>());
        return true;
    }

    // Add an undirected road (case-insensitive)
    public boolean addRoad(String src, String dest, int distance) {
        if (src == null || dest == null) return false;
        src = src.trim().toLowerCase();
        dest = dest.trim().toLowerCase();
        if (src.isEmpty() || dest.isEmpty()) return false;
        if (src.equals(dest) || distance <= 0) return false;

        adjList.putIfAbsent(src, new ArrayList<>());
        adjList.putIfAbsent(dest, new ArrayList<>());

        if (hasEdge(src, dest)) return false;

        adjList.get(src).add(new Edge(dest, distance));
        adjList.get(dest).add(new Edge(src, distance));
        return true;
    }

    private boolean hasEdge(String src, String dest) {
        for (Edge e : adjList.getOrDefault(src, Collections.emptyList())) {
            if (e.destination.equals(dest)) return true;
        }
        return false;
    }

    // Display graph
    public void printGraph() {
        List<String> cities = new ArrayList<>(adjList.keySet());
        Collections.sort(cities);
        for (String city : cities) {
            System.out.print(capitalize(city) + " -> ");
            List<Edge> edges = adjList.get(city);
            edges.sort(Comparator.comparing(e -> e.destination));
            for (Edge e : edges) {
                System.out.print(capitalize(e.destination) + "(" + e.distance + "km) ");
            }
            System.out.println();
        }
    }

    private String capitalize(String str) {
        if (str == null || str.isEmpty()) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    // BFS
    public void bfs(String start) {
        start = start.trim().toLowerCase();
        if (!adjList.containsKey(start)) {
            System.out.println("City not found in graph!");
            return;
        }

        Set<String> visited = new HashSet<>();
        Queue<String> queue = new LinkedList<>();
        visited.add(start);
        queue.add(start);

        System.out.print("BFS Traversal: ");
        while (!queue.isEmpty()) {
            String city = queue.poll();
            System.out.print(capitalize(city) + " ");
            for (Edge e : adjList.get(city)) {
                if (!visited.contains(e.destination)) {
                    visited.add(e.destination);
                    queue.add(e.destination);
                }
            }
        }
        System.out.println();
    }

    // DFS
    public void dfs(String start) {
        start = start.trim().toLowerCase();
        if (!adjList.containsKey(start)) {
            System.out.println("City not found in graph!");
            return;
        }

        Set<String> visited = new HashSet<>();
        System.out.print("DFS Traversal: ");
        dfsHelper(start, visited);
        System.out.println();
    }

    private void dfsHelper(String city, Set<String> visited) {
        visited.add(city);
        System.out.print(capitalize(city) + " ");
        for (Edge e : adjList.get(city)) {
            if (!visited.contains(e.destination)) {
                dfsHelper(e.destination, visited);
            }
        }
    }

    // Dijkstra
    public void dijkstra(String src, String dest) {
        src = src.trim().toLowerCase();
        dest = dest.trim().toLowerCase();

        if (!adjList.containsKey(src) || !adjList.containsKey(dest)) {
            System.out.println("Source or destination city does not exist.");
            return;
        }

        Map<String, Integer> distance = new HashMap<>();
        Map<String, String> parent = new HashMap<>();
        PriorityQueue<Edge> pq = new PriorityQueue<>(Comparator.comparingInt(e -> e.distance));

        for (String city : adjList.keySet()) distance.put(city, Integer.MAX_VALUE);
        distance.put(src, 0);
        pq.add(new Edge(src, 0));

        while (!pq.isEmpty()) {
            Edge current = pq.poll();
            String city = current.destination;

            for (Edge e : adjList.get(city)) {
                int newDist = distance.get(city) + e.distance;
                if (newDist < distance.get(e.destination)) {
                    distance.put(e.destination, newDist);
                    parent.put(e.destination, city);
                    pq.add(new Edge(e.destination, newDist));
                }
            }
        }

        if (distance.get(dest) == Integer.MAX_VALUE) {
            System.out.println("No path exists between " + capitalize(src) + " and " + capitalize(dest));
            return;
        }

        List<String> path = new ArrayList<>();
        String cur = dest;
        while (cur != null) {
            path.add(cur);
            cur = parent.get(cur);
        }
        Collections.reverse(path);

        System.out.println("Shortest path from " + capitalize(src) + " to " + capitalize(dest) + ": " +
                String.join(" -> ", path.stream().map(this::capitalize).toList()));
        System.out.println("Total distance: " + distance.get(dest) + " km");
    }
}