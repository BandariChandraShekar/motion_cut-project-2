import java.util.Scanner;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// Main class for the Smart Calculator
public class SmartCalculator {
    public static void main(String[] args) {
        // Prompt the user to choose between console and GUI mode
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose mode: 1 for Console, 2 for GUI");
        int choice = scanner.nextInt();

        if (choice == 1) {
            runConsoleCalculator();
        } else if (choice == 2) {
            SwingUtilities.invokeLater(() -> new SmartCalculatorGUI());
        } else {
            System.out.println("Invalid choice!");
        }
    }

    // Method to run the console-based calculator
    public static void runConsoleCalculator() {
        Scanner scanner = new Scanner(System.in);
        double num1, num2;
        char operator;
        double result = 0;
        boolean validOperation = true;

        // Input first number
        System.out.print("Enter first number: ");
        num1 = scanner.nextDouble();

        // Input operator
        System.out.print("Enter an operator (+, -, *, /): ");
        operator = scanner.next().charAt(0);

        // Input second number
        System.out.print("Enter second number: ");
        num2 = scanner.nextDouble();

        // Perform calculation
        switch (operator) {
            case '+':
                result = num1 + num2;
                break;
            case '-':
                result = num1 - num2;
                break;
            case '*':
                result = num1 * num2;
                break;
            case '/':
                if (num2 != 0) {
                    result = num1 / num2;
                } else {
                    System.out.println("Error: Division by zero!");
                    validOperation = false;
                }
                break;
            default:
                System.out.println("Invalid operator!");
                validOperation = false;
        }

        // Output result
        if (validOperation) {
            System.out.println("The result is: " + result);
        }
    }
}

// GUI Version of the Calculator
class SmartCalculatorGUI {
    private JFrame frame;
    private JTextField textField;
    private String num1 = "", num2 = "", operator = "";

    public SmartCalculatorGUI() {
        // Create JFrame and text field
        frame = new JFrame("Smart Calculator");
        textField = new JTextField();
        textField.setEditable(false);

        // Create a panel with GridLayout to hold buttons
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 4));

        // Button labels for the calculator
        String[] buttons = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", "C", "=", "+"
        };

        // Add buttons to the panel
        for (String text : buttons) {
            JButton button = new JButton(text);
            button.addActionListener(new ButtonClickListener());
            panel.add(button);
        }

        // Add components to the frame
        frame.setLayout(new BorderLayout());
        frame.add(textField, BorderLayout.NORTH);
        frame.add(panel, BorderLayout.CENTER);

        // Set frame properties
        frame.setSize(300, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    // Inner class to handle button click events
    private class ButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            // Handle number input
            if (command.charAt(0) >= '0' && command.charAt(0) <= '9') {
                if (!operator.equals("")) {
                    num2 += command;
                } else {
                    num1 += command;
                }
                textField.setText(num1 + operator + num2);
            } 
            // Handle clear
            else if (command.equals("C")) {
                num1 = operator = num2 = "";
                textField.setText("");
            } 
            // Handle equals
            else if (command.equals("=")) {
                if (!num1.isEmpty() && !num2.isEmpty()) {
                    double result = calculate(Double.parseDouble(num1), Double.parseDouble(num2), operator);
                    textField.setText(num1 + operator + num2 + " = " + result);
                    num1 = Double.toString(result);
                    operator = num2 = "";
                }
            } 
            // Handle operator input
            else {
                if (operator.isEmpty() || num2.isEmpty()) {
                    operator = command;
                } else {
                    double result = calculate(Double.parseDouble(num1), Double.parseDouble(num2), operator);
                    num1 = Double.toString(result);
                    operator = command;
                    num2 = "";
                }
                textField.setText(num1 + operator);
            }
        }

        // Perform calculation based on operator
        public double calculate(double num1, double num2, String operator) {
            switch (operator) {
                case "+": return num1 + num2;
                case "-": return num1 - num2;
                case "*": return num1 * num2;
                case "/": return num2 != 0 ? num1 / num2 : 0;
                default: return 0;
            }
        }
    }
}
