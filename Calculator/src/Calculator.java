import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.util.Arrays;
import javax.swing.border.*;
import java.math.*;

public class Calculator extends JFrame{

    Color customBlack = new Color(32, 32, 32);
    Color customWhite = new Color(255, 255, 255);
    Color customGrey = new Color(50, 50, 50);
    Color customLightGrey = new Color(59, 59, 59);
    Color purple = new Color(219, 158, 229);

    // Button Arrays
    String[] buttonValues = {
            "%", "CE", "C", "⌫",
            "1/x", "x²", "²√x", "÷",
            "7", "8", "9", "×",
            "4", "5", "6", "−",
            "1", "2", "3", "+",
            "±", "0", ".", "="
    };

    String[] topButtons = {
            "%", "CE", "C",
            "1/x", "x²", "²√x"
    };

    String[] rightButtons = {
            "⌫",
            "÷",
            "×",
            "−",
            "+",
            "="
    };

    // UI Components
    JButton hamburgerButton = new JButton("☰");
    JLabel calculatorSet = new JLabel();
    JLabel displayLabel = new JLabel();

    JPanel topPanel = new JPanel();
    JPanel topMenu = new JPanel();
    JPanel displayPanel = new JPanel();
    JPanel buttonsPanel = new JPanel();

    int boardWidth = 340;
    int boardHeight = 540;

    String A = "0";
    String operator = null;
    String B = null;

    public Calculator() {
        setSize(boardWidth, boardHeight);
        setLayout(new BorderLayout());
        setTitle("Calculator");
        getContentPane().setBackground(customBlack);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        hamburgerButton.setFont(new Font("Segue UI Symbol", Font.PLAIN, 20));
        hamburgerButton.setForeground(customWhite);
        hamburgerButton.setBackground(customBlack);
        hamburgerButton.setBorderPainted(false);
        hamburgerButton.setFocusPainted(false);
        hamburgerButton.setContentAreaFilled(false);
        hamburgerButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover Effects
        hamburgerButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                hamburgerButton.setContentAreaFilled(true);
                hamburgerButton.setBackground(customGrey);

            }
            @Override
            public void mousePressed(MouseEvent e) {
                hamburgerButton.setBackground(customLightGrey);
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                hamburgerButton.setContentAreaFilled(false);
                hamburgerButton.setBackground(customBlack);
            }
        });

        calculatorSet.setForeground(customWhite);
        calculatorSet.setFont(new Font("Segue UI", Font.BOLD, 20));
        calculatorSet.setText("Standard");

        topMenu.setLayout(new FlowLayout(FlowLayout.LEFT, 2, 5));
        topMenu.setBackground(customBlack);
        topMenu.add(hamburgerButton);
        topMenu.add(calculatorSet);

        displayLabel.setForeground(customWhite);
        displayLabel.setFont(new Font("Segue UI", Font.PLAIN, 48));
        displayLabel.setHorizontalAlignment(JLabel.RIGHT);
        displayLabel.setText("0");


        displayPanel.setLayout(new BorderLayout());
        displayPanel.setBackground(customBlack);
        displayPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        displayPanel.add(displayLabel, BorderLayout.CENTER);

        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBackground(customBlack);
        topPanel.add(topMenu);
        topPanel.add(displayPanel);

        add(topPanel, BorderLayout.NORTH);
        buttonsPanel.setLayout(new GridLayout(6, 4));
        buttonsPanel.setBackground(customBlack);
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
        add(buttonsPanel, BorderLayout.CENTER);

        for (String value : buttonValues) {
            JButton buttons = getJButton(
                    value,
                    customBlack,
                    topButtons,
                    customLightGrey,
                    customWhite,
                    rightButtons,
                    customGrey,
                    purple
            );
            buttonsPanel.add(buttons);
            buttons.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JButton button = (JButton) e.getSource();
                    String buttonValue = button.getText();
                    if (Arrays.asList(rightButtons).contains(buttonValue)) {
                        if (buttonValue.equals("=")) {
                            if (A != null) {
                                B = displayLabel.getText();
                                double numA = Double.parseDouble(A);
                                double numB = Double.parseDouble(B);

                                switch (operator) {
                                    case "⌫" -> {
                                        // Confusion
                                    }
                                    case "+" -> displayLabel.setText(removeZeroDecimal(numA + numB));
                                    case "−" -> displayLabel.setText(removeZeroDecimal(numA - numB));
                                    case "×" -> displayLabel.setText(removeZeroDecimal(numA * numB));
                                    case "÷" -> displayLabel.setText(removeZeroDecimal(numA / numB));
                                }
                                clearAll();
                            }
                        }
                        else if ("+−×÷".contains(buttonValue)) {
                            if (operator == null) {
                                A = displayLabel.getText();
                                displayLabel.setText("0");
                                B = "0";
                            }
                            operator = buttonValue;
                        }
                    }
                    else if (Arrays.asList(topButtons).contains(buttonValue)) {
                        switch (buttonValue) {
                            case "%" -> {
                                double numDis = Double.parseDouble(displayLabel.getText());
                                numDis /= 100;
                                displayLabel.setText(removeZeroDecimal(numDis));
                            }
                            case "C" -> {
                                clearAll();
                                displayLabel.setText("0");
                            }
                            case "1/x" -> {
                                double numDis = Double.parseDouble(displayLabel.getText());
                                if (numDis == 0) {
                                    displayLabel.setText("Cannot divide by zero");
                                } else {
                                    numDis = 1 / numDis;
                                    displayLabel.setText(removeZeroDecimal(numDis));
                                }
                            }
                            case "x²" -> {
                                double numDis = Double.parseDouble(displayLabel.getText());
                                numDis = Math.pow(numDis, 2);
                                displayLabel.setText(removeZeroDecimal(numDis));
                            }
                            case "²√x" -> {
                                double numDis = Double.parseDouble(displayLabel.getText());
                                numDis = Math.sqrt(numDis);
                                displayLabel.setText(removeZeroDecimal(numDis));
                            }
                        }
                    }
                    else {
                        if (buttonValue.equals(".")) {
                            if (!displayLabel.getText().contains(buttonValue)) {
                                displayLabel.setText(displayLabel.getText() + buttonValue);
                            }
                        }
                        else if ("±".contains(buttonValue)) {
                            double numDis = Double.parseDouble(displayLabel.getText());
                            numDis *= -1;
                            displayLabel.setText(removeZeroDecimal(numDis));
                        }
                        else if ("012345678".contains(buttonValue)) {
                            if (displayLabel.getText().equals("0")) {
                                displayLabel.setText(buttonValue);
                            }
                            else {
                                displayLabel.setText(displayLabel.getText() + buttonValue);
                            }
                        }
                    }
                }
            });
        }

        setVisible(true);
    }

    private static JButton getJButton(
            String value,
            Color customBlack,
            String[] topButtons,
            Color customLightGrey,
            Color customWhite,
            String[] rightButtons,
            Color customGrey,
            Color purple
    )
    {
        JButton button = new JButton();
        button.setFont(new Font("Segue UI", Font.PLAIN, 17));
        button.setText(value);
        button.setFocusable(false);
        button.setBorder(new LineBorder(customBlack));

        if (Arrays.asList(topButtons).contains(value)) {
            button.setBackground(customGrey);
            button.setForeground(customWhite);
        } else if (Arrays.asList(rightButtons).contains(value)) {
            if ("=".contains(value)) {
                button.setBackground(purple);
                button.setForeground(customBlack);
            } else {
                button.setBackground(customGrey);
                button.setForeground(customWhite);
            }
        } else {
            button.setBackground(customLightGrey);
            button.setForeground(customWhite);
        }
        return button;
    }
    public void clearAll() {
        A = "0";
        operator = null;
        B = null;
    }
    String removeZeroDecimal(double num) {
        if (num % 1 == 0) {
            return Integer.toString((int) num);
        }
        return Double.toString(num);
    }
}
