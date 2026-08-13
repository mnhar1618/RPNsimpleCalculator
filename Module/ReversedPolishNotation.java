package RPNsimpleCalculator;
import java.util.*;

public class ReversedPolishNotation {

    public static double evaluate(String expression) throws Exception {
        Stack<Double> values = new Stack<>();
        Stack<String> ops = new Stack<>();
        List<String> output = new ArrayList<>();

        for (String token : expression.split("\\s+")) {
            if (isNumber(token)) {
                values.push(Double.parseDouble(token));
                output.add(token);
            } else if (token.equals("(")) {
                ops.push(token);
            } else if (token.equals(")")) {
                while (!ops.isEmpty() && !ops.peek().equals("(")) {
                    applyOp(values, ops.pop(), output);
                }
                if (ops.isEmpty()) {
                    throw new Exception("Mismatched parentheses");
                }
                ops.pop();
            } else {
                while (!ops.isEmpty() && !ops.peek().equals("(")
                        && precedence(ops.peek()) >= precedence(token)
                        && !(token.equals("^") && ops.peek().equals("^"))) {
                    applyOp(values, ops.pop(), output);
                }
                ops.push(token);
            }
        }

        while (!ops.isEmpty()) {
            if (ops.peek().equals("(")) {
                throw new Exception("Mismatched parentheses");
            }
            applyOp(values, ops.pop(), output);
        }

        System.out.println("RPN: " + String.join(" ", output));
        return values.pop();
    }

    private static void applyOp(Stack<Double> values, String op, List<String> output) {
        double b = values.pop();
        double a = values.pop();
        double result;
        switch (op) {
            case "+": result = a + b; break;
            case "-": result = a - b; break;
            case "*": result = a * b; break;
            case "/":
                if (b == 0) {
                    throw new ArithmeticException("Division by zero");
                }
                result = a / b;
                break;
            case "%":
                if (b == 0) {
                    throw new ArithmeticException("Division by zero");
                }
                result = a % b;
                break;
            case "^": result = Math.pow(a, b); break;
            default: throw new IllegalArgumentException("Unknown operator: " + op);
        }
        values.push(result);
        output.add(op);
    }

    private static int precedence(String op) {
        switch (op) {
            case "+": case "-": return 1;
            case "*": case "/": case "%": return 2;
            case "^": return 3;
            default: return 0;
        }
    }

    private static boolean isNumber(String token) {
        try {
            Double.parseDouble(token);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
