import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Stack;

public class pair {

    private static String[] op = { "+", "-", "*", "/" };// Operation set
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        String strsz[] = new String[1001];
        Scanner sc = new Scanner(System.in);
        int  num = sc.nextInt();

        for(int i=0;i<num;i++) {
            String question = MakeFormula();//生成题目
            String ret = Solve(question);//解题
            if(ret==null){//判断是否为负数
                i--;
                continue;
            }
            for(int j=0;j<=i;j++){//防止重复
                if(ret.equals(strsz[j])){
                    i--;
                    continue;
                }
            }
            strsz[i] = ret;
            System.out.println(ret);//打印
        }
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter("strarray.txt"));
            for(int i = 0;i < num;i++){
                out.write(strsz[i]+"\n");
            }
            out.close();
            System.out.println("文件创建成功！");
        } catch (IOException e) {
        }
        long endTime = System.currentTimeMillis();
        long runTime = endTime - startTime;
        System.out.println("当前程序耗时" + runTime + "ms");
    }

    public static String MakeFormula(){
        StringBuilder build = new StringBuilder();
        int count = (int) (Math.random() * 10) + 1; // generate random count
        int start = 0;
        int number1 = (int) (Math.random() * 99) + 1;
        build.append(number1);
        while (start <= count){
            int operation = (int) (Math.random() * 3); // generate operator
            int number2 = (int) (Math.random() * 99) + 1;
            build.append(op[operation]).append(number2);
            start ++;
        }
        return build.toString();
    }

    public static String Solve(String formula){
        Stack<String> tempStack = new Stack<>();//Store number or operator
        Stack<Character> operatorStack = new Stack<>();//Store operator
        int len = formula.length();
        int k = 0;
        for(int j = -1; j < len - 1; j++){
            char formulaChar = formula.charAt(j + 1);
            if(j == len - 2 || formulaChar == '+' || formulaChar == '-' || formulaChar == '/' || formulaChar == '*') {
                if (j == len - 2) {
                    tempStack.push(formula.substring(k));
                }
                else {
                    if(k < j){
                        tempStack.push(formula.substring(k, j + 1));
                    }
                    if(operatorStack.empty()){
                        operatorStack.push(formulaChar); //if operatorStack is empty, store it
                    }else{
                        char stackChar = operatorStack.peek();
                        if ((stackChar == '+' || stackChar == '-')
                                && (formulaChar == '*' || formulaChar == '/')){
                            operatorStack.push(formulaChar);
                        }else {
                            tempStack.push(operatorStack.pop().toString());
                            operatorStack.push(formulaChar);
                        }
                    }
                }
                k = j + 2;
            }
        }
        while (!operatorStack.empty()){ // Append remaining operators
            tempStack.push(operatorStack.pop().toString());
        }
        Stack<String> calcStack = new Stack<>();
        for(String peekChar : tempStack){ // Reverse traversing of stack
            if(!peekChar.equals("+") && !peekChar.equals("-") && !peekChar.equals("/") && !peekChar.equals("*")) {
                calcStack.push(peekChar); // Push number to stack
            }else{
                int a1 = 0;
                int b1 = 0;
                if(!calcStack.empty()){
                    b1 = Integer.parseInt(calcStack.pop());
                }
                if(!calcStack.empty()){
                    a1 = Integer.parseInt(calcStack.pop());
                }
                switch (peekChar) {
                    case "+":
                        calcStack.push(String.valueOf(a1 + b1));
                        break;
                    case "-":
                        calcStack.push(String.valueOf(a1 - b1));
                        break;
                    case "*":
                        calcStack.push(String.valueOf(a1 * b1));
                        break;
                    default:
                        calcStack.push(String.valueOf(a1 / b1));
                        break;
                }
            }
        }
        String str = calcStack.pop();
        int a=0;
        try {
            a = Integer.parseInt(str);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        if(a<0) {
            return null;
        }
        return formula + "=" + str;
    }
}
