/**
 * @author: Ashwin Ravishankar
 * This program aims at stack implementation using Dynamic Arrays and Linked list
 * An input file of ANSI encoding is read twice, once for each implementation, convert infix expression to postfix
 * by stack implementation using dynamic array and linked list
 * perform arithmetic evaluation of the postfix expression and display the computed output
 * if the expression has any error, mark and flag the error.
 */

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Stack;

public class CalcEx {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        FileReader fileReadOne = new FileReader(args[0]);
        FileReader fileReadTwo = new FileReader(args[0]);
        String[] originalArrayList = new String[110];
        
        String[] stackArray = new String[110];
        String[] stackList = new String[110];
        
        InfixToPostfixArray arrayStackImplementation = new InfixToPostfixArray();
        InfixToPostfixList listStackImplementation = new InfixToPostfixList(); 
        
        ArrayEvaluateExpression arrayEvaluate= new ArrayEvaluateExpression();
        ListEvaluateExpression listEvaluate = new ListEvaluateExpression();
        
                       
        Scanner input = new Scanner(fileReadOne);
        
        char[] operators = {'+', '-', '*', '/', '(', ')'};
        char[] validChracters = {'+', '-', '*', '/', '(', ')', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0'};
        String chrLine;
        String errorMessage = "Error found in input expression";
        String charLineForArray;
        int i,j,k,l,lineCount=0,flagInvalidInput=0, lineCountList=0;
        while (input.hasNext()) {
            flagInvalidInput=0;
            chrLine = input.nextLine(); 
            charLineForArray="";
            originalArrayList[lineCount++]=chrLine;
            charLineForArray="";
        }
        
        //Reading file for linked list
        input = new Scanner(fileReadTwo);
        while (input.hasNext()) {
            chrLine = input.nextLine();
            stackList[lineCountList++]=chrLine;
        }
        
        //Create stack copies for array and list
        for (i=0;i<lineCount;i++) {
            stackArray[i] = originalArrayList[i];
            //stackList[i] = originalArrayList[i];
        }
        
        
        //Begin dynamic array implementation of stack
        System.out.println("                                                      ---Begining of Dynamic Array implementation of Stack---");
        System.out.format("%50s%50s%50s\n", "Infix Expression", "Postfix Expression", "Computational Result");
        String postFixExpression="", infixExpression="", arithmeticComputationResult="";
        for(i=0;i<lineCount;i++) {
            System.out.println();
            infixExpression = stackArray[i];
            //System.out.println("Infix Expression: " + infixExpression);
            postFixExpression = arrayStackImplementation.InfixToPostfixConvert(FormatInputString(stackArray[i]));
            //System.out.println("Postfix Expression: " + FormatInputString(postFixExpression));
            if(!FlagErrorExpression(FormatInputString(stackArray[i]))){
                arithmeticComputationResult = String.valueOf(arrayEvaluate.ArrayComputeValue(FormatInputString(postFixExpression)));
                //System.out.println("Computed Result: " + arithmeticComputationResult);
            }
            else{
                arithmeticComputationResult = errorMessage;
                //System.out.println("Computed Result: " + arithmeticComputationResult);
            }
            
            System.out.format("%50s%50s%50s\n", infixExpression, postFixExpression, arithmeticComputationResult);
        }
        
        System.out.println("                                                      ---Ending of Dynamic Array implementation of Stack---");
        
        System.out.println();
        System.out.println();
        
        //Begin linked list implementation of stack
        System.out.println("                                                      ---Begining of Linked List implementation of Stack---"); 
        System.out.format("%50s%50s%50s\n", "Infix Expression", "Postfix Expression", "Computational Result");
        for(i=0;i<lineCount;i++) {
            System.out.println();
            infixExpression= stackList[i];
            //System.out.println("Infix Expression: " + infixExpression);
            postFixExpression=listStackImplementation.InfixToPostfixConvert(FormatInputString(stackList[i]));
            //System.out.println("Postfix Expression: " + FormatInputString(postFixExpression));
            if(!FlagErrorExpression(FormatInputString(stackList[i]))){
                arithmeticComputationResult=String.valueOf(listEvaluate.ListComputeValue(postFixExpression));
                //System.out.println("Computed Result: " + arithmeticComputationResult);
            }
            else{
                arithmeticComputationResult=errorMessage;
                //System.out.println("Computed Result: " + arithmeticComputationResult);
            }
            System.out.format("%50s%50s%50s\n", infixExpression, postFixExpression, arithmeticComputationResult);        
        }
        System.out.println("                                                      ---Ending of Linked List implementation of Stack---");
        
    }
    
    //Reset the expression with correct space formatting
    private static String FormatInputString(String expression){
        char[] validChracters = {'+', '-', '*', '/', '(', ')', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0'};
        char[] operators = {'+', '-', '*', '/', '(', ')'};
        
        String buildExpression="", number=""; char ch; int counter=0;
        for (int i=0;i<expression.length();i++){
            ch=expression.charAt(i);
            if (Character.isWhitespace(expression.charAt(i))){
                if(number!="")  {
                    buildExpression+=number +" ";
                    number="";
                }
                counter++;
            }
            else if (Character.isDigit(ch)){
                number+=ch;
                counter++;
                if (counter==expression.length()){
                    buildExpression+=number+ "";
                }
            }
            else if (new String(operators).contains(String.valueOf(ch))) {
                if(number!="")  {
                    buildExpression+=number +" ";
                    number="";
                }
                buildExpression+=ch +" ";
                counter++;
            }
            else {
                buildExpression+=ch +" ";
                counter++;
            }
        }
        expression=buildExpression;
        return expression;
    }
    
    //Flag Invalid Input Expression
    private static boolean FlagErrorExpression(String expression){
        boolean errorExpression=false;
        char[] validChracters = {'+', '-', '*', '/', '(', ')', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', ' '};
        char[] operators = {'+', '-', '*', '/', '(', ')'};
        int lPar=0,rPar=0; char ch=' ';
        lPar=0;rPar=0;
        for (int i=0;i<expression.length();i++){
            ch=expression.charAt(i);
            //Invalid character check
            if(!(new String(validChracters).contains(String.valueOf(expression.charAt(i))))){
                errorExpression=true;
            }
            if(expression.charAt(i)=='(')
                lPar++;
            if(expression.charAt(i)==')')
                rPar++;
        }

        //mismatch in count of paranthesis
        if(lPar!=rPar)
            errorExpression=true;

        //Check if the first input character in expression is operator
        if((new String(operators).contains(String.valueOf(expression.charAt(0))))){
            if((expression.charAt(0)!='('))
            errorExpression=true;
        }        
        
        return errorExpression;
    }
}

//Class to convert Infix Expression to Postfix Expression - Dynamic Array
class InfixToPostfixArray {    
    DynamicArrayStack theStack = new DynamicArrayStack();
    float x, y, val;
    char temp;
    private String input;
    private String output;
    private float finalVal=0;
    
    public void popTillParanthesis(char ch) { 
      while (!theStack.isEmpty()) {
         char chx = theStack.pop().charAt(0);
         if (chx == '(') 
         break; 
         else output = output + chx; 
      }
   }
    
    public String InfixToPostfixConvert(String expression) {
        input = expression;
        output = "";
        
      for (int j = 0; j < input.length(); j++) {
         char ch = input.charAt(j);
         switch (ch) {
            case '+': 
            case '-':
               BuildStack(ch, 1); 
               break; 
            case '*': 
            case '/':
                BuildStack(ch, 2); 
               break; 
            case '(': 
               theStack.push(String.valueOf(ch));
               break;
            case ')': 
               popTillParanthesis(ch); 
               break;
            default: 
               output = output + ch; 
               break;
         }
      }
      while (!theStack.isEmpty()) {
         output = output + theStack.pop();
      }
      return output; 
   }
    public void BuildStack(char currentCharacter, int newPrecedence) {
      while (!theStack.isEmpty()) {
         char topOfStack = theStack.pop().charAt(0);
         if (topOfStack == '(') {
            theStack.push(String.valueOf(topOfStack));
            break;
         } else {
            int stackPrecedence;
            if (topOfStack == '+' || topOfStack == '-')
             stackPrecedence = 1;
            else
                stackPrecedence = 2;
            if (stackPrecedence < newPrecedence) { 
               theStack.push(String.valueOf(topOfStack));
               break;
            } 
            else output = output + topOfStack;
         }
      }
      theStack.push(String.valueOf(currentCharacter));
   }
}

//Adapted & modified from Weiss Fig. 16.10 - Dynamic Array Implementation of stack
class DynamicArrayStack {
    public String [ ] theArray;
    public int topOfStack;
    public static final int DEFAULT_CAPACITY = 10;
    
    public DynamicArrayStack( ) {
        theArray =  new String [ DEFAULT_CAPACITY ];
        topOfStack = -1;
    }    
    public boolean isEmpty( ){
        return topOfStack == -1;
    }
    public void makeEmpty( ) {
        topOfStack = -1;
    }    
    public void push( String x ) {
        if( topOfStack + 1 == theArray.length )
            doubleArray( );
        theArray[ ++topOfStack ] = x;
    }
    public String top( ) {
        if( isEmpty( ) )
            System.out.println("Stack is empty");
        return theArray[ topOfStack ];
    }   
    public String pop( ) {
        if( isEmpty( ) )
            System.out.println("Stack is empty");
        return theArray[ topOfStack-- ];
    }    
    public void doubleArray(){
        String [] tempArray = new String[2*theArray.length];
        System.arraycopy(theArray, 0, tempArray, 0, theArray.length);
        theArray = tempArray;
    }    
}
    
    //Adapted & modified from Weiss Fig. 16.19 - Linked list implementation of stack
class LinkedListStack {

    public boolean isEmpty( ){ 
        return topOfStack == null; 
    }
    public void makeEmpty( ) { 
        topOfStack = null; 
    }
    public void push( String x ){ 
        topOfStack = new ListNode( x, topOfStack );
    }
    public String top( ){
        if( isEmpty( ) )
            System.out.println("List is empty");
        return topOfStack.element;
    }
    public String pop( ){
        if( isEmpty( ) )
            System.out.println("List is empty");
        String topItem = topOfStack.element;
        topOfStack = topOfStack.next;
        return topItem;
    }

    private ListNode topOfStack = null;

}
class ListNode {
    public ListNode( String theElement ) {
        this( theElement, null ); 
    }
    public ListNode( String theElement, ListNode n ) { 
        element = theElement; next = n; 
    }
    public String element;
    public ListNode next;
}
    
//Class to convert Infix Expression to Postfix Expression - Linked List
class InfixToPostfixList {    
    LinkedListStack theStack = new LinkedListStack();
    
    private String input;
    private String output;
    
    public void PopTillParanthesis(char ch) { 
      while (!theStack.isEmpty()) {
         char chx = theStack.pop().charAt(0);
         if (chx == '(') 
         break; 
         else output = output + chx; 
      }
   }

    public String InfixToPostfixConvert(String expression) {
        input = expression;
        output = "";
      for (int j = 0; j < input.length(); j++) {
         char ch = input.charAt(j);
         switch (ch) {
            case '+': 
            case '-':
               BuildStack(ch, 1); 
               break; 
            case '*': 
            case '/':
               BuildStack(ch, 2); 
               break; 
            case '(': 
               theStack.push(String.valueOf(ch));
               break;
            case ')': 
               PopTillParanthesis(ch); 
               break;
            default: 
               output = output + ch; 
               break;
         }
      }
      while (!theStack.isEmpty()) {
         output = output + theStack.pop();
      }
      return output; 
   }
    public void BuildStack(char currentCharacter, int newPrecedence) {
      while (!theStack.isEmpty()) {
         char topOfStack = theStack.pop().charAt(0);
         if (topOfStack == '(') {
            theStack.push(String.valueOf(topOfStack));
            break;
         } else {
            int stackPrecedence;
            if (topOfStack == '+' || topOfStack == '-')
             stackPrecedence = 1;
            else
                stackPrecedence = 2;
            if (stackPrecedence < newPrecedence) { 
               theStack.push(String.valueOf(topOfStack));
               break;
            } 
            else output = output + topOfStack;
         }
      }
      theStack.push(String.valueOf(currentCharacter));
   }
}

//Arithmetic evaluation of postfix expression - Dynamic Array
class ArrayEvaluateExpression{
    public Double ArrayComputeValue(String expression){
        String operand = "";
        DynamicArrayStack s = new DynamicArrayStack();
        char ch;
        for (int i=0; i<expression.length();i++) {
            ch=expression.charAt(i);
            if(Character.isWhitespace(expression.charAt(i))){
                if(operand != "") {
                    s.push(String.valueOf(operand));
                    operand="";
                }
            }
            if(Character.isDigit(expression.charAt(i))) {
                operand+=expression.charAt(i);
            }
            else if (isOperator(expression.charAt(i))) {
                if(operand != "") {
                    s.push(operand);
                    operand="";
                }
                switch (ch) {
                    case '+': s.push(String.valueOf(Double.valueOf(s.pop()) + Double.valueOf(s.pop())));     break;
                    case '*': s.push(String.valueOf(Double.valueOf(s.pop()) * Double.valueOf(s.pop())));     break;
                    case '-': s.push(String.valueOf(-Double.valueOf(s.pop()) + Double.valueOf(s.pop())));    break;
                    case '/': s.push(String.valueOf(1 / Double.valueOf(s.pop()) * Double.valueOf(s.pop()))); break;
                }
            }
        }
        if (!s.isEmpty()) 
            return Double.valueOf(s.pop());
        else
            return 0.0;
    }
    private static boolean isOperator(char ch) {
        return ch == '*' || ch == '/' || ch == '+' || ch == '-';
    }
}

//Arithmetic evaluation of postfix expression - Linked List
class ListEvaluateExpression{
    public Double ListComputeValue(String expression){
        String operand = "";
        LinkedListStack s = new LinkedListStack();
        char ch;
        for (int i=0; i<expression.length();i++) {
            ch=expression.charAt(i);
            if(Character.isWhitespace(expression.charAt(i))){
                if(operand != "") {
                    s.push(String.valueOf(operand));
                    operand="";
                }
            }
            if(Character.isDigit(expression.charAt(i))) {
                operand+=expression.charAt(i);
            }
            else if (isOperator(expression.charAt(i))) {
                if(operand != "") {
                    s.push(operand);
                    operand="";
                }
                switch (ch) {
                    case '+': s.push(String.valueOf(Double.valueOf(s.pop()) + Double.valueOf(s.pop())));     break;
                    case '*': s.push(String.valueOf(Double.valueOf(s.pop()) * Double.valueOf(s.pop())));     break;
                    case '-': s.push(String.valueOf(-Double.valueOf(s.pop()) + Double.valueOf(s.pop())));    break;
                    case '/': s.push(String.valueOf(1 / Double.valueOf(s.pop()) * Double.valueOf(s.pop()))); break;
                }
            }
        }
        if (!s.isEmpty()) 
            return Double.valueOf(s.pop());
        else
            return 0.0;
    }
    private static boolean isOperator(char ch) {
        return ch == '*' || ch == '/' || ch == '+' || ch == '-';
    }
}

