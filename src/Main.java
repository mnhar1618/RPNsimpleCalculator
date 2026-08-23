package RPNsimpleCalculator;
import java.util.*;
import RPNsimpleCalculator.Calculator;
import RPNsimpleCalculator.Calculator.uniOperator;
import RPNsimpleCalculator.Calculator.BiOperator;

public class Main {

    private final Calculator calc = new Calculator();
    private final Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        Main app = new Main();
        app.run();
    }

    private void run() {
        System.out.println("\t------RPN Calculator-------\n");
        System.out.println("Type a normal arithmetic expression, e.g.  3 + 4 * (2 - 1)");
        System.out.println("Functions: sin cos tan log ln exp sqr sqrt inv abs");
        System.out.println("Type 'quit' to exit.\n");
        while (true) {
            System.out.print("> ");
            if (!input.hasNextLine()) {
                break;
            }
            String line = input.nextLine().trim();
            if (line.isEmpty()) {
                continue;
            }
            if (line.equalsIgnoreCase("quit") || line.equalsIgnoreCase("exit")) {
                break;
            }
            process(line);
        }
        System.out.println("Bye!");
    }

    private void process(String line) {
        try {
            List<String> rpn = shuntingYard(tokenize(line));
            double result = evaluateRpn(rpn);
            System.out.println("RPN:    " + String.join(" ", rpn));
            System.out.println("Result: " + result + "\n");
        } catch (ArithmeticException e) {
            System.out.println("Error: operation is not defined for these values.\n");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage() + "\n");
        }
    }

    private static List<String> tokenize(String expr) {
        List<String> tokens = new ArrayList<>();
        int i = 0;
        while (i < expr.length()) {
            char c = expr.charAt(i);
            if (Character.isWhitespace(c)) {
                i++;
            } else if (Character.isDigit(c)) {
                StringBuilder sb = new StringBuilder();
                while (i < expr.length() && (Character.isDigit(expr.charAt(i)) || expr.charAt(i) == '.')) {
                    sb.append(expr.charAt(i++));
                }
                tokens.add(sb.toString());
            } else if (Character.isLetter(c)) {
                StringBuilder sb = new StringBuilder();
                while (i < expr.length() && Character.isLetter(expr.charAt(i))) {
                    sb.append(expr.charAt(i++));
                }
                String word = sb.toString().toLowerCase();
                if (!isFunction(word)) {
                    throw new IllegalArgumentException("Unknown function: '" + word + "'");
                }
                tokens.add(word);
            } else if (c == '(' || c == ')') {
                tokens.add(String.valueOf(c));
                i++;
            } else if ("+-*/%^".indexOf(c) >= 0) {
                tokens.add(String.valueOf(c));
                i++;
            } else {
                throw new IllegalArgumentException("Unexpected character: '" + c + "'");
            }
        }
        return tokens;
    }

    private static List<String> shuntingYard(List<String> tokens) {
        Deque<String> operators = new ArrayDeque<>();
        List<String> output = new ArrayList<>();
        for (String token : tokens) {
            if (isNumber(token)) {
                output.add(token);
            } else if (isFunction(token)) {
                operators.push(token);
            } else if (token.equals("(")) {
                operators.push(token);
            } else if (token.equals(")")) {
                while (!operators.isEmpty() && !operators.peek().equals("(")) {
                    output.add(operators.pop());
                }
                if (operators.isEmpty()) {
                    throw new IllegalArgumentException("Mismatched parentheses");
                }
                operators.pop();
                if (!operators.isEmpty() && isFunction(operators.peek())) {
                    output.add(operators.pop());
                }
            } else {
                while (!operators.isEmpty() && !operators.peek().equals("(")
                        && (precedence(operators.peek()) > precedence(token)
                            || (precedence(operators.peek()) == precedence(token) && !token.equals("^")))) {
                    output.add(operators.pop());
                }
                operators.push(token);
            }
        }
        while (!operators.isEmpty()) {
            if (operators.peek().equals("(")) {
                throw new IllegalArgumentException("Mismatched parentheses");
            }
            output.add(operators.pop());
        }
        return output;
    }

    private double evaluateRpn(List<String> rpn) {
        Deque<Double> stack = new ArrayDeque<>();
        for (String token : rpn) {
            if (isNumber(token)) {
                stack.push(Double.parseDouble(token));
            } else if (isFunction(token)) {
                double a = stack.pop();
                stack.push(applyUnary(token, a));
            } else {
                double b = stack.pop();
                double a = stack.pop();
                stack.push(applyBinary(token, a, b));
            }
        }
        return stack.pop();
    }

    private double applyBinary(String symbol, double num1, double num2) {
        BiOperator op;
        switch (symbol) {
            case "+": op = BiOperator.Add; break;
            case "-": op = BiOperator.Sub; break;
            case "*": op = BiOperator.Mult; break;
            case "/": op = BiOperator.Div; break;
            case "%": op = BiOperator.Mod; break;
            case "^": op = BiOperator.xpowy; break;
            default: throw new IllegalArgumentException("Unknown operator: '" + symbol + "'");
        }
        return calc.Calculate(num1, op, num2);
    }

    private double applyUnary(String symbol, double num) {
        uniOperator op;
        switch (symbol) {
            case "sin": op = uniOperator.sin; break;
            case "cos": op = uniOperator.cos; break;
            case "tan": op = uniOperator.tan; break;
            case "log": op = uniOperator.log; break;
            case "ln": op = uniOperator.ln; break;
            case "exp": op = uniOperator.exp; break;
            case "sqr": op = uniOperator.sqr; break;
            case "sqrt": op = uniOperator.sqrt; break;
            case "inv": op = uniOperator.inv; break;
            case "abs": op = uniOperator.abs; break;
            default: throw new IllegalArgumentException("Unknown function: '" + symbol + "'");
        }
        return calc.CalculateBi(op, num);
    }

    private static boolean isNumber(String token) {
        try {
            Double.parseDouble(token);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean isFunction(String token) {
        switch (token) {
            case "sin": case "cos": case "tan":
            case "log": case "ln": case "exp":
            case "sqr": case "sqrt": case "inv": case "abs":
                return true;
            default:
                return false;
        }
    }

    private static int precedence(String token) {
        switch (token) {
            case "+": case "-": return 1;
            case "*": case "/": case "%": return 2;
            case "^": return 3;
            default: return 0;
        }
    }
}
