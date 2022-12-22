package hr.fer.oprpp1.custom.collections.demo;

import hr.fer.oprpp1.custom.collections.ObjectStack;

public class StackDemo {

    public static void main(String[] args) {
        String[] elements = args[0].split(" ");
        ObjectStack stack = new ObjectStack();

        for(String element : elements) {
            try {
                Integer.parseInt(element);
            } catch (NumberFormatException e) {
                int num1 = 0; int num2 = 0;
                try {
                    num2 = (int) stack.pop();
                    num1 = (int) stack.pop();
                } catch(Exception e1) {
                    System.out.println("Expression is invalid.");
                    System.exit(-1);
                }

                switch(element) {
                    case "+" -> num1 = num1 + num2;
                    case "-" -> num1 = num1 - num2;
                    case "/" -> {
                        if(num2 == 0) {
                            System.out.println("Cannot divide by zero");
                            System.exit(-1);
                        }
                             num1 = num1 / num2;
                        }
                        case "*" -> num1 = num1 * num2;
                        case "%" -> {
                            if(num2 == 0) {
                                System.out.println("Cannot divide by zero");
                                System.exit(-1);
                        }
                        num1 = num1 % num2;
                    }
                    default -> throw new UnsupportedOperationException();
                }
                stack.push(num1);
                continue;
            }

            stack.push(Integer.parseInt(element));
        }

        System.out.println(stack.pop());
    }
}
