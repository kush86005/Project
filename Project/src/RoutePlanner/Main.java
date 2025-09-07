package RoutePlanner;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Graph graph = new Graph();

        // Preloaded demo graph
        graph.addCity("Delhi");
        graph.addCity("Mumbai");
        graph.addCity("Pune");
        graph.addCity("Bangalore");
        graph.addCity("Chennai");

        graph.addRoad("Delhi", "Mumbai", 1400);
        graph.addRoad("Delhi", "Pune", 1200);
        graph.addRoad("Mumbai", "Pune", 150);
        graph.addRoad("Pune", "Bangalore", 1000);
        graph.addRoad("Bangalore", "Chennai", 350);
        graph.addRoad("Mumbai", "Chennai", 1300);

        while (true) {
            System.out.println("\n--- Graph Route Planner ---");
            System.out.println("1. Add City");
            System.out.println("2. Add Road");
            System.out.println("3. Display Graph");
            System.out.println("4. BFS Traversal");
            System.out.println("5. DFS Traversal");
            System.out.println("6. Find Shortest Path");
            System.out.println("7. Exit");
            System.out.print("Enter choice: ");

            String line = sc.nextLine().trim();
            int choice;
            try {
                choice = Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number (1-7).");
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.print("Enter city name: ");
                    String city = sc.nextLine().trim();
                    if (graph.addCity(city)) System.out.println("City added: " + city);
                    else System.out.println("City already exists or invalid input.");
                    break;

                case 2:
                    System.out.print("Enter source city: ");
                    String src = sc.nextLine().trim();
                    System.out.print("Enter destination city: ");
                    String dest = sc.nextLine().trim();
                    System.out.print("Enter distance: ");
                    int dist;
                    try {
                        dist = Integer.parseInt(sc.nextLine().trim());
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid distance.");
                        break;
                    }
                    if (graph.addRoad(src, dest, dist)) System.out.println("Road added: " + src + " <-> " + dest);
                    else System.out.println("Road already exists or invalid input.");
                    break;

                case 3:
                    graph.printGraph();
                    break;

                case 4:
                    System.out.print("Enter start city for BFS: ");
                    String bfsStart = sc.nextLine().trim();
                    graph.bfs(bfsStart);
                    break;

                case 5:
                    System.out.print("Enter start city for DFS: ");
                    String dfsStart = sc.nextLine().trim();
                    graph.dfs(dfsStart);
                    break;

                case 6:
                    System.out.print("Enter source city: ");
                    String srcCity = sc.nextLine().trim();
                    System.out.print("Enter destination city: ");
                    String destCity = sc.nextLine().trim();
                    graph.dijkstra(srcCity, destCity);
                    break;

                case 7:
                    System.out.println("Exiting...");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice. Enter 1-7.");
            }
        }
    }
}
