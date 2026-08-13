package RPNsimpleCalculator;
import java.util.*;


public class ReversedPolishNotation {


    public static double evaluate(String expression) throws Exception {
        String output = shuntingYard(expression);
        Stack<Integer> stackD = new Stack<Integer>(); //stack for digits
        Character operator;

        String[] line = output.split("\\s+");
        for (String token : line){
            if (token.matches("\\d+")){
                stackD.push(Integer.parseInt(token));
            }else{
                operator = token.charAt(0);
                int b = stackD.pop();
                int a = stackD.pop();
                int result = applyOperator(operator, a, b);
                stackD.push(result);
            }
        }

        return stackD.pop();     
      
    }
    public static String shuntingYard(String expression) throws Exception {
        Queue<String> outputQ = new ArrayDeque<>();
        Stack<String> operatorS = new Stack<>();


        for (String token : expression.split("\\s+")){
            if (token.matches("\\d+")){
                outputQ.add(token);
                 } else if (token.equals("(")) {
                      operatorS.push(token);

                 } else if (token.equals(")")) {

                      while (!operatorS.isEmpty() && !operatorS.peek().equals("(")) {
                         outputQ.add(operatorS.pop());
                    }

                 if (!operatorS.isEmpty()) {
                    operatorS.pop(); // remove '('
                 } else {
                    throw new Exception("Mismatched parentheses");
                 }

            }else if (token.matches("[+\\-*/^]")){
                while (!operatorS.isEmpty() && precedence(operatorS.peek()) >= precedence(token)){
                    outputQ.add(operatorS.pop());
                }
                operatorS.push(token);
           }
        }
         while (!operatorS.isEmpty()){
            outputQ.add(operatorS.pop());
            
         }
         return String.join(" ",outputQ);


    }
    public static int precedence(String operator){
        switch(operator){
            case "+":case "-":
                return 1;
            case "*":case "/":
                return 2;
            case "^":
                return 3;
            default:
                throw new IllegalArgumentException("Unknown operator: " + operator);
        }
    }
    public static int applyOperator(char operator, int a, int b) throws Exception {
        switch (operator) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                if (b == 0) {
                    throw new ArithmeticException("Division by zero");
                }
                return a / b;
            case '^':
                return (int) Math.pow(a, b);
            default:
                throw new IllegalArgumentException("Unknown operator: " + operator);
        }
    }
}
