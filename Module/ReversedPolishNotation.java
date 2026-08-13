package RPNsimpleCalculator;
import java.util.*;


public class ReversedPolishNotation {


    public static double evaluate(String output) throws Exception {
        s

        
      
    }
    public static String shuntingYard(String expression) throws Exception {
        Queue<String> outputQ = new ArrayDeque<>();
        Stack<String> operatorS = new Stack<>();


        for (String token : expression.split("\\s+")){
            if (token.matches("[0-9]")){
                outputQ.add(token.charAt(0));
            }elif (token.matches("[+\\-*/^]")){
                while (!operatorS.isEmpty() && precedence(operatorS.peek()) >= precedence(token)){
                    outputQ.add(operatorS.pop());
                }
                operatorS.push(token);
           }
        }
         while (!opereatorS.isEmpty()){
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
}
