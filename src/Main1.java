package RPNsimpleCalculator;
import java.util.*;
import RPNsimpleCalculator.Calculator.BiOperator;
import RPNsimpleCalculator.Calculator.uniOperator;

public class Main1 {

    private final Stack<Double> stack = new Stack<Double>();
    private final Calculator calc = new Calculator();
    private final Scanner input = new Scanner(System.in);

    public static void main(String[] args){
        Main app= new Main();
        app.run();
    }

    private void run(){
        System.out.println("\t------RPN Calculator-------\n");
        System.out.println("\ttype an arithmetic expression\n");
        System.out.println("Type 'help' for the list of commands.");

        while(true){
            System.out.println("> ");
            if(!input.hasNextLine()){
                break;
            }

            String line= input.nextLine().trim();
            if (line.isEmpty()){
                continue;
            }
        }
    }

    private boolean processLine(String line){
        for (String token : line.split("\\s+")){
            if (isNumber(token))
        }
    }

    
}
