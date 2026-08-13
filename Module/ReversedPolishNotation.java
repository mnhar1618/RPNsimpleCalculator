package RPNsimpleCalculator;
import java.util.*;

public class ReversedPolishNotation{

    public static int evaluate(String expression) throws Exception{

     Stack<Integer> stackD = new Stack<Integer>(); //stack for digits
     Stack<Character> stackO = new Stack<Character>(); //stack for operators
     List<String> output = new ArrayList(); //list for an RPN output 
     Character operator;

     String[] tokens = expression.split(" ");
        //case where expression has parenthesis
        for(String token : tokens){
         if(token.matches("\\d+")){

             stackD.push(Integer.parseInt(token)); //push integers to stackD
         }
         else{
            stackO.push(token.charAt(0)); //push operator as a char in stackO
            if (token.equals(")")){
                while (stackO.peek() != '('){

                
            
                    int y= stackD.pop();
                    int x= stackD.pop();
                    switch (stackO.peek()){
                        case '+':
                            stackD.push(x+y);
                            break;

                
                        case '-':
                         stackD.push(x-y);
                         break;
            
                         case '*':
                         stackD.push(x*y);
                        break;
                
                        case '/':
                             try{
                                int z=x/y;
                                stackD.push(z);
                        
                             }
                            catch (ArithmeticException e){
                                 System.out.println("Error: Division by zero is undifined.");
                        
                            }
                            break;
                    }
                }
                stackO.pop();
                stackO.pop();
                
            }

        //case where expression has no parenthesis
         }
        }
        
        for (String ch : tokens){
            if (token.matches("\\d+")){
                output.add(token);
            }
                else{
                    stackO.push(ch.charAt(0));
                    }
                
        }  

        
        while(!stackO.isEmpty()){
            operator=stackO.pop();
            output.add(String.valueOf(operator));
        }
        for (String s: output){
            if (s.matches("\\d+")){
                stackD.push(Integer.parseInt(s));
            }
            else{
                int y= stackD.pop();
                int x= stackD.pop();
                switch (s){
                    case "String.valueOf('+')":
                        stackD.push(x+y);
                        break;
                    case "String.valueOf('-')":
                        stackD.push(x-y);
                        break;
                    case "String.valueOf('*')":
                        stackD.push(x*y);
                        break;
                    case "String.valueOf('/')":
                        try{
                            int z=x/y;
                            stackD.push(z);
                        }
                        catch (ArithmeticException e){
                            System.out.println("Error: Division by zero is undifined.");
                        }
                        break;
                }
            }
            
        }
        return stackD.pop();
    }
}

    


