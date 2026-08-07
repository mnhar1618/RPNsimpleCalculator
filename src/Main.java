package RPNsimpleCalculator;
import java.util.*;
import RPNsimpleCalculator.Calculator.BiOperator;
import RPNsimpleCalculator.Calculator.uniOperator;

public class Main {

    private final Stack<Double> stack = new Stack<Double>();
    private final Calculator calc = new Calculator();
    private final Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        Main app = new Main();
        app.run();
    }

    private void run() {
        System.out.println("RPN Calculator");
        System.out.println("Type a number to push it, or an operator/function/command.");
        System.out.println("Type 'help' for the list of commands.");
        while (true) {
            System.out.print("> ");
            if (!input.hasNextLine()) {
                break;
            }
            String line = input.nextLine().trim();
            if (line.isEmpty()) {
                continue;
            }
            boolean quit = processLine(line);
            if (quit) {
                break;
            }
            showStack();
        }
        System.out.println("Bye!");
    }

    private boolean processLine(String line) {
        for (String token : line.split("\\s+")) {
            if (isNumber(token)) {
                stack.push(Double.parseDouble(token));
                continue;
            }
            switch (token.toLowerCase()) {
                case "+": case "-": case "*": case "/": case "%": case "^":
                    applyBinary(token);
                    break;
                case "sin": case "cos": case "tan":
                case "log": case "ln": case "exp":
                case "sqr": case "sqrt": case "inv": case "abs":
                    applyUnary(token);
                    break;
                case "stack":
                    showStack();
                    break;
                case "swap":
                    swap();
                    break;
                case "drop":
                    if (!stack.isEmpty()) {
                        stack.pop();
                    } else {
                        System.out.println("Stack is empty.");
                    }
                    break;
                case "clear":
                    stack.clear();
                    System.out.println("Stack cleared.");
                    break;
                case "help":
                    showHelp();
                    break;
                case "quit": case "exit":
                    return true;
                default:
                    System.out.println("Unknown input: '" + token + "' (type 'help' for commands)");
            }
        }
        return false;
    }

    private void applyBinary(String symbol) {
        if (stack.size() < 2) {
            System.out.println("Error: need at least two numbers for '" + symbol + "'.");
            return;
        }
        double num2 = stack.pop();
        double num1 = stack.pop();
        BiOperator op;
        switch (symbol) {
            case "+": op = BiOperator.Add; break;
            case "-": op = BiOperator.Sub; break;
            case "*": op = BiOperator.Mult; break;
            case "/": op = BiOperator.Div; break;
            case "%": op = BiOperator.Mod; break;
            case "^": op = BiOperator.xpowy; break;
            default: return;
        }
        try {
            stack.push(calc.Calculate(num1, op, num2));
        } catch (ArithmeticException e) {
            System.out.println("Error: " + symbol + " is not defined for these values.");
        }
    }

    private void applyUnary(String symbol) {
        if (stack.isEmpty()) {
            System.out.println("Error: need a number on the stack for '" + symbol + "'.");
            return;
        }
        double num = stack.pop();
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
            default: return;
        }
        try {
            stack.push(calc.CalculateBi(op, num));
        } catch (ArithmeticException e) {
            System.out.println("Error: " + symbol + " is not defined for " + fmt(num) + ".");
        }
    }

    private void swap() {
        if (stack.size() < 2) {
            System.out.println("Error: need at least two numbers to swap.");
            return;
        }
        double a = stack.pop();
        double b = stack.pop();
        stack.push(a);
        stack.push(b);
    }

    private void showStack() {
        if (stack.isEmpty()) {
            System.out.println("Stack: (empty)");
            return;
        }
        System.out.print("Stack:");
        for (int i = stack.size() - 1; i >= 0; i--) {
            System.out.print("  " + fmt(stack.get(i)));
        }
        System.out.println();
    }

    private static boolean isNumber(String token) {
        try {
            Double.parseDouble(token);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static String fmt(double value) {
        if (Double.isInfinite(value) || Double.isNaN(value)) {
            return String.valueOf(value);
        }
        if (value == Math.floor(value)) {
            return String.valueOf((long) value);
        }
        String s = String.format("%.10g", value);
        return s;
    }

    private void showHelp() {
        System.out.println("Numbers: type any number to push it onto the stack.");
        System.out.println("Operators:  +  -  *  /  %  ^   (use two top numbers)");
        System.out.println("Functions:  sin  cos  tan  log  ln  exp  sqr  sqrt  inv  abs");
        System.out.println("Commands:   stack  swap  drop  clear  help  quit");
        System.out.println("Example:   5  3  *  2  ^   ->  10 ^ 2 = 100");
    }
}
