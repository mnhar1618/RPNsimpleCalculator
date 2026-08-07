package RPNsimpleCalculator;
import java.lang.Math;
import static java.lang.Double.NaN;

public class Calculator {   
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

    public double Calculate(double num1, BiOperator op, double num2){
        this.num1 = num1;
        this.num2 = num2;
        this.Op = op;
        return Calculate();
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
            case abs:
                return Math.abs(num);
            default:
                return 0;
        }
    }
}

    