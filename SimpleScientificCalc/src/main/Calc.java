package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calc extends JFrame implements ActionListener {

    private JTextField textField;
    private String input = "";

    public Calc() {
        initializeUI(); //initiate the ui of the calculator
    }

    private void initializeUI() {
        setTitle("Scientific Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        textField = new JTextField();
        textField.setEditable(false);
        textField.setFont(new Font("Arial Bold", Font.PLAIN, 30));
        add(textField, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 4, 5, 5));

        String[] buttons = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", ".", "=", "+",
                "sin", "cos", "tan", "C"/*,
                "√"*/
        };

        //get button inputs
        for (String button : buttons) {
            JButton jButton = new JButton(button);
            jButton.addActionListener(this);
            buttonPanel.add(jButton);
        }

        //frame the buttons
        add(buttonPanel, BorderLayout.CENTER);
        pack(); //frame them tight around the buttons created
        setLocationRelativeTo(null);
        setVisible(true);
    }

    //
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        switch (action) {
            case "=":
                calculate();
                break;
            case "C":
                clear();
                break;
            default:
                input += action;
                textField.setText(input);
        }
    }

    private void calculate() {
        try {
            double result = evaluateExpression(input);
            textField.setText(String.valueOf(result));
            input = "";
        } catch (Exception e) {
            textField.setText("Error");
        }
    }
    private void clear() {
        input = "";
        textField.setText("");
    }

    private double evaluateExpression(String expression) {
        try {

            //checking if the user is using a trigonometry operator
            if (expression.startsWith("sin")) {
                String getNumber = expression.substring(3);
                return Math.sin(Math.toRadians(Double.parseDouble(getNumber)));

            } else if (expression.startsWith("cos")) {
                String getNumber = expression.substring(3);
                return Math.cos(Math.toRadians(Double.parseDouble(getNumber)));

            } else if (expression.startsWith("tan")) {
                String getNumber = expression.substring(3);
                return Math.tan(Math.toRadians(Double.parseDouble(getNumber)));

          /*  } else if (expression.startsWith("√")) {
                String getNumber = expression.substring(3);
                return Math.sqrt(Double.parseDouble(getNumber)); */ //cant make the square root work will fix it later

            } else {
                String[] parts = expression.split("[+\\-*/]"); // Splitting the expression based on the given operator

                double num1 = Double.parseDouble(parts[0]);
                double num2 = Double.parseDouble(parts[1]);

                char operator = expression.charAt(parts[0].length());

                //executing the operations based on the split operator
                switch (operator) {
                    case '+':
                        return num1 + num2;
                    case '-':
                        return num1 - num2;
                    case '*':
                        return num1 * num2;
                    case '/':
                        if (num2 == 0) {
                            throw new ArithmeticException("Can't divide by zero.");
                        }
                        return num1 / num2;

                    default:
                        throw new IllegalArgumentException("Invalid operator");
                }
            }
        } catch (Exception ex) {
            throw new IllegalArgumentException("Invalid expression");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Calc());
    }
}