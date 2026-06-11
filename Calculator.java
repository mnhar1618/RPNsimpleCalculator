package simplecalculator;
import java.lang.Math;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import static java.lang.Double.NaN;
public class Calculator {   
    public Calculator(){
        num1=0;
        num2=0;
        Op=BiOperator.equal;
    }
    public enum BiOperator{
        equal,Add,Sub,Mult,Div,Mod,xpowy;
    }
    public enum uniOperator{
        sin,cos,tan,log,abs,ln,exp,sqr,sqrt,inv;
    }
    protected double num1,num2;
    protected BiOperator Op;
    public double Calculate(){
        switch(Op){
            case Add:
                return num1+num2;
            case Sub:
                return num1-num2;
            case Mult:
                return num1*num2;
            case Div:
                if(num2==0){
                    throw new ArithmeticException();
                }
                return num1/num2;
            case Mod:
                if(num2==0){
                    throw new ArithmeticException();
                }
                return num1%num2;
            case xpowy:
                return Math.pow(num1,num2);
            
            default:
                return 0;

        }
        
    }


    public double CalculateBi(uniOperator Op,double num){
        switch(Op){
            case sin:
                return Math.sin(num);
            case cos:
                return Math.cos(num);
            case tan:
                if (num == 0 || num % 180 == 0 ) {
                return 0.0;
            }
                if (num % 90 == 0.0 && num % 180 != 0.0) {
                return NaN;
                }
                return Math.tan(Math.toRadians(num));
            case log:
                if(num<=0){
                    throw new ArithmeticException();
                }
                return Math.log10(num);
            case ln:
                if(num<=0){
                    throw new ArithmeticException();
                }
                return Math.log(num);
            case exp:
                return Math.exp(num);
            case sqr:
                return num*num;
            case sqrt:
                if(num<0){
                    throw new ArithmeticException();
                }
                return Math.sqrt(num);
            case inv:
                if(num==0){
                    throw new ArithmeticException();
                }
                return 1/num;
            default:
                return 0;
        }
    }

    public double CalculateExpression(String expression) {
        List<String> tokens = tokenizeExpression(expression);
        Deque<Double> values = new ArrayDeque<>();
        Deque<BiOperator> ops = new ArrayDeque<>();

        for (int i = 0; i < tokens.size(); i++) {
            String token = tokens.get(i);

            if (token.isEmpty()) {
                continue;
            }

            if (isNumeric(token)) {
                values.push(Double.parseDouble(token));
                continue;
            }

            if ("(".equals(token)) {
                ops.push(null);
                continue;
            }

            if (")".equals(token)) {
                while (!ops.isEmpty() && ops.peek() != null) {
                    applyTopOperator(values, ops);
                }
                if (!ops.isEmpty() && ops.peek() == null) {
                    ops.pop();
                }
                continue;
            }

            BiOperator currentOp = toBiOperator(token);
            if (currentOp == null) {
                throw new IllegalArgumentException("Unsupported operator: " + token);
            }

            while (!ops.isEmpty() && ops.peek() != null && precedence(ops.peek()) >= precedence(currentOp)) {
                applyTopOperator(values, ops);
            }
            ops.push(currentOp);
        }

        while (!ops.isEmpty()) {
            applyTopOperator(values, ops);
        }

        if (values.isEmpty()) {
            throw new IllegalArgumentException("Expression is empty or invalid: " + expression);
        }

        return values.pop();
    }

    private List<String> tokenizeExpression(String expression) {
        List<String> tokens = new ArrayList<>();
        StringBuilder number = new StringBuilder();
        boolean expectUnary = true;

        for (char ch : expression.toCharArray()) {
            if (Character.isWhitespace(ch)) {
                continue;
            }

            if (Character.isDigit(ch) || ch == '.') {
                number.append(ch);
                expectUnary = false;
                continue;
            }

            if (ch == '-' && expectUnary) {
                number.append(ch);
                continue;
            }

            if (number.length() > 0) {
                tokens.add(number.toString());
                number.setLength(0);
            }

            if (ch == '+' || ch == '-' || ch == '*' || ch == '/' || ch == '%' || ch == '^' || ch == '(' || ch == ')') {
                tokens.add(String.valueOf(ch));
                expectUnary = ch == '(';
                continue;
            }

            throw new IllegalArgumentException("Invalid character in expression: " + ch);
        }

        if (number.length() > 0) {
            tokens.add(number.toString());
        }

        return tokens;
    }

    private boolean isNumeric(String token) {
        try {
            Double.parseDouble(token);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    private BiOperator toBiOperator(String token) {
        switch (token) {
            case "+":
                return BiOperator.Add;
            case "-":
                return BiOperator.Sub;
            case "*":
                return BiOperator.Mult;
            case "/":
                return BiOperator.Div;
            case "%":
                return BiOperator.Mod;
            case "^":
                return BiOperator.xpowy;
            default:
                return null;
        }
    }

    private int precedence(BiOperator op) {
        switch (op) {
            case Add:
            case Sub:
                return 1;
            case Mult:
            case Div:
            case Mod:
                return 2;
            case xpowy:
                return 3;
            default:
                return 0;
        }
    }

    private void applyTopOperator(Deque<Double> values, Deque<BiOperator> ops) {
        if (values.size() < 2) {
            throw new IllegalArgumentException("Invalid expression: not enough values for operator");
        }
        double right = values.pop();
        double left = values.pop();
        BiOperator op = ops.pop();
        values.push(applyOperator(op, left, right));
    }

    private double applyOperator(BiOperator op, double left, double right) {
        switch (op) {
            case Add:
                return left + right;
            case Sub:
                return left - right;
            case Mult:
                return left * right;
            case Div:
                if (right == 0) {
                    throw new ArithmeticException("Division by zero");
                }
                return left / right;
            case Mod:
                if (right == 0) {
                    throw new ArithmeticException("Modulo by zero");
                }
                return left % right;
            case xpowy:
                return Math.pow(left, right);
            default:
                throw new IllegalArgumentException("Unknown operator: " + op);
        }
    }
}
