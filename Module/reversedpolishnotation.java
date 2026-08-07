package RPNsimpleCalculator;
import java.util.*;

public class reversedpolishnotation {

    Scanner s= new Scanner(System.in);
    String expression = s.nextLine();
    public static int evaluate(String expression){
     Stack<Integer> stackD = new Stack<Integer>();
     Stack<Character> stackO = new Stack<Character>();

    
     String[] tokens = expression.split(" ");

        for(String token : tokens){
         if(token.matches("\\d+")){
             stackD.push(Integer.parseInt(token));
         }
         else{
            stackO.push(token);
            if (token.equals(")")){
                while (stackO.peek() != '('){

                
            
                    int y= stackD.pop();
                    int x= stackD.pop();
                    switch (token){
                        case "+":
                            stackD.push(x+y);
                            break;

                
                        case "-":
                         stackD.push(x-y);
                         break;
            
                         case "*":
                         stackD.push(x*y);
                        break;
                
                        case "/":
                             try{
                                int z=x/y;
                                stackD.push(x/y);
                        
                             }
                            catch (ArithmeticException e){
                                 System.out.println("Error: Division by zero is undifined.");
                        
                            }
                            break;
                    }
                }
                stackO.pop();
            }  
        }
        return stackD.pop();
    }
}
    


