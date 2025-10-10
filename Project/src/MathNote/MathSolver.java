package MathNote;

import java.util.*;

public class MathSolver {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("--- Math Notes Solver ---");

        while (true) {
            System.out.println("\n1. Evaluate Expression (e.g., 5 + 3 * 2)");
            System.out.println("2. Solve Equation (e.g., 2x + 3x - 4= 21)");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");
            String line = sc.nextLine().trim();

            if (line.equals("1")) {
                System.out.print("Enter expression (space-separated): ");
                String expr = sc.nextLine();
                List<String> steps = new ArrayList<>();
                try {
                    int result = Evaluator.evaluateExpression(expr, steps);
                    System.out.println("\n--- Steps ---");
                    for (String s : steps) System.out.println(s);
                    System.out.println("Result: " + result);
                } catch (Exception e) {
                    System.out.println("Invalid expression! " + e.getMessage());
                }

            } else if (line.equals("2")) {
                System.out.print("Enter equation (e.g., 2x + 3x - 4= 21): ");
                String eq = sc.nextLine();
                try {
                    double x = Evaluator.solveEquation(eq);
                    System.out.println("Solution: x = " + x);
                } catch (Exception e) {
                    System.out.println("Unsupported equation format!");
                }

            } else if (line.equals("3")) {
                System.out.println("Exiting...");
                sc.close();
                break;
            } else {
                System.out.println("Invalid choice! Enter 1-3.");
            }
        }
    }
}
