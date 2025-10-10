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

    // Solve any linear equation like "2x + 3x - 4 = 21"
    public static double solveEquation(String eq) {
        eq = eq.replaceAll(" ", "");          // remove spaces
        String[] sides = eq.split("=");       // split LHS and RHS
        if (sides.length != 2)
            throw new IllegalArgumentException("Equation format not supported!");

        String lhs = sides[0];
        double rhs = Double.parseDouble(sides[1]);

        // convert "-" to "+-" so we can split by "+"
        lhs = lhs.replace("-", "+-");
        String[] terms = lhs.split("\\+");

        double coef = 0;      // sum of x coefficients
        double constant = 0;  // sum of constant numbers

        for (String term : terms) {
            if (term.isEmpty()) continue;

            if (term.contains("x")) {
                String c = term.replace("x", "");
                if (c.equals("") || c.equals("+")) c = "1";
                else if (c.equals("-")) c = "-1";
                coef += Double.parseDouble(c);
            } else {
                constant += Double.parseDouble(term);
            }
        }

        if (coef == 0)
            throw new IllegalArgumentException("No x term found or invalid equation.");

        return (rhs - constant) / coef;
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
