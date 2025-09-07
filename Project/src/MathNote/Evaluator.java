package MathNote;

import java.util.*;

public class Evaluator {

    // Evaluate arithmetic expressions like "5 + 3 * 2"
    public static int evaluateExpression(String expr, List<String> steps) {
        String[] tokens = expr.split(" ");
        Stack<Integer> values = new Stack<>();
        Stack<String> ops = new Stack<>();

        for (String token : tokens) {
            if (isOperator(token)) {
                while (!ops.isEmpty() && precedence(ops.peek()) >= precedence(token)) {
                    int b = values.pop();
                    int a = values.pop();
                    String op = ops.pop();
                    int res = applyOp(a, b, op);
                    steps.add("Step: " + a + " " + op + " " + b + " = " + res);
                    values.push(res);
                }
                ops.push(token);
            } else {
                values.push(Integer.parseInt(token));
            }
        }

        while (!ops.isEmpty()) {
            int b = values.pop();
            int a = values.pop();
            String op = ops.pop();
            int res = applyOp(a, b, op);
            steps.add("Step: " + a + " " + op + " " + b + " = " + res);
            values.push(res);
        }

        return values.pop();
    }

    // Solve simple linear equation like "x + 5 = 12"
    public static double solveEquation(String eq) {
        eq = eq.replaceAll(" ", "");
        String[] parts = eq.split("=");
        String lhs = parts[0];
        double rhs = Double.parseDouble(parts[1]);

        if (lhs.contains("x")) {
            lhs = lhs.replace("x", "");
            if (lhs.equals("+") || lhs.isEmpty()) return rhs;
            else if (lhs.startsWith("+")) return rhs - Double.parseDouble(lhs.substring(1));
            else if (lhs.startsWith("-")) return rhs + Double.parseDouble(lhs.substring(1));
        }

        throw new IllegalArgumentException("Equation format not supported!");
    }

    private static boolean isOperator(String token) {
        return token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/");
    }

    private static int precedence(String op) {
        switch (op) {
            case "+": case "-": return 1;
            case "*": case "/": return 2;
        }
        return 0;
    }

    private static int applyOp(int a, int b, String op) {
        switch (op) {
            case "+": return a + b;
            case "-": return a - b;
            case "*": return a * b;
            case "/": return a / b;
        }
        return 0;
    }
}
