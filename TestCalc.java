package simplecalculator;
import java.util.Scanner;

public class TestCalc {
    public static void main(String[] args){
        Calculator calc = new Calculator();
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter first number:");
        double num1 = sc.nextDouble();
        System.out.println("Enter second number:");
        double num2 = sc.nextDouble();
        System.out.println("Enter operator (+, -, *, /, %, ^):");
        String operator = sc.next();
        switch(operator){
            case "+":
                calc.Op = Calculator.BiOperator.Add;
                break;
            case "-":
                calc.Op = Calculator.BiOperator.Sub;
                break;
            case "*":
                calc.Op = Calculator.BiOperator.Mult;
                break;
            case "/":
                calc.Op = Calculator.BiOperator.Div;
                break;
            case "%":
                calc.Op = Calculator.BiOperator.Mod;
                break;
            case "^":
                calc.Op = Calculator.BiOperator.xpowy;
                break;
            default:
                System.out.println("Invalid operator");
                return;
        }
        calc.num1 = num1;
        calc.num2 = num2;
        try{
            double result = calc.Calculate();
            System.out.println("Result: " + result);
        }catch(ArithmeticException e){
            System.out.println("Error: " + e.getMessage());
        }
    }
}
