package ui.tabs;

import ui.FinancialManagementApp;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.time.LocalDate;

import javax.swing.*;

import model.Expense;
import model.ExpenseBook;
import model.Income;
import model.IncomeBook;

// Represents a report tab which allows user to calculate annual or monthly balance of their account
public class ReportTab extends Tab {
    private static final String BALANCE_TEXT = "Do you want to calculate annual or monthly balance?";
    private JPanel buttonPanel;
    private JPanel annualBalancePanel;
    private JPanel monthlyBalancePanel;

    // REQUIRES: FinanacialManagementApp controller that holds this tab
    // EFFECTS: creates report tab which allows user to calculate annual or
    // monthly balance of their account

    public ReportTab(FinancialManagementApp controller, IncomeBook incomeBook, ExpenseBook expenseBook) {
        super(controller);
        this.icbk = incomeBook;
        this.epbk = expenseBook;
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 1));
        buttonPanel.setVisible(true);
        JLabel text = new JLabel(BALANCE_TEXT);
        buttonPanel.add(text);

        createAnnualBalancePanel();
        createMonthlyBalancePanel();

        placeAnnualMonthlyButton();
        add(buttonPanel);
    }

    // EFFECTS: create the annual Balance Panel which user can get the annual
    // balance
    // amount of their intended year;
    private void createAnnualBalancePanel() {

        annualBalancePanel = new JPanel();
        annualBalancePanel.setLayout(new GridLayout(2, 1));
        annualBalancePanel.setVisible(false);

        JTextField yearField = new JTextField(4);
        annualBalancePanel.add(new JLabel("Enter Specific Year"));
        annualBalancePanel.add(yearField);

        JButton calculateButton = new JButton("Calculate");
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double balance = calculateAnnualBalance(Integer.valueOf(yearField.getText()));
                JOptionPane.showMessageDialog(null,
                        "Your " + yearField.getText() + " balance is " + Double.toString(balance));
                yearField.setText("");
                annualBalancePanel.setVisible(false);
                buttonPanel.setVisible(true);
            }
        });

        annualBalancePanel.add(calculateButton);
        add(annualBalancePanel, BorderLayout.CENTER);
    }

    // EFFECTS: create the monthly Balance Panel which user can get the monthly
    // balance amount
    // of their intended month in intended year
    private void createMonthlyBalancePanel() {
        monthlyBalancePanel = new JPanel();
        monthlyBalancePanel.setLayout(new GridLayout(5, 1));
        monthlyBalancePanel.setVisible(false);

        JTextField yearField = new JTextField(4);
        monthlyBalancePanel.add(new JLabel("Enter Specific Year"));
        monthlyBalancePanel.add(yearField);
        JTextField monthField = new JTextField(2);
        monthlyBalancePanel.add(new JLabel("Enter Specific Month(MM)"));
        monthlyBalancePanel.add(monthField);

        JButton calculateButton = addMonthlyCalculateButton(yearField, monthField);

        monthlyBalancePanel.add(calculateButton);
        add(monthlyBalancePanel, BorderLayout.CENTER);
    }

    // EFFECTS: place the annual and monthly button on the button panel
    private void placeAnnualMonthlyButton() {
        JButton annualButton = new JButton(ButtonNames.ANNUAL.getValue());
        JButton monthlyButton = new JButton(ButtonNames.MONTHLY.getValue());

        JPanel buttonRow = formateButtonRow(annualButton);
        buttonRow.add(monthlyButton);

        annualButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String buttonPressed = e.getActionCommand();
                if (buttonPressed.equals(ButtonNames.ANNUAL.getValue())) {
                    calculateAnnual();
                }
            }
        });

        monthlyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String buttonPressed = e.getActionCommand();
                if (buttonPressed.equals(ButtonNames.MONTHLY.getValue())) {
                    calculateMonthly();
                }
            }
        });

        buttonPanel.add(buttonRow);
    }

    // EFFETCS: set the button panel not visible to user and the calculate annual
    // balance panel visible
    // to user
    private void calculateAnnual() {
        buttonPanel.setVisible(false);
        annualBalancePanel.setVisible(true);
    }

    // EFFETCS: set the button panel not visible to user and the calculate monthly
    // balance panel visible
    // to user
    private void calculateMonthly() {
        buttonPanel.setVisible(false);
        monthlyBalancePanel.setVisible(true);
    }

    // EFFECTS: calculate the annual balance of the given year from user
    private double calculateAnnualBalance(int year) {
        ArrayList<Income> ik = icbk.getIncomeRecord();
        ArrayList<Expense> ek = epbk.getExpenseRecord();

        double ai1 = icbk.calculateTotalIncomeByTime(ik, LocalDate.of(year, 1, 1),
                LocalDate.of(year, 12, 31));
        double ae1 = epbk.calculateTotalExpenseByTime(ek, LocalDate.of(year, 1, 1),
                LocalDate.of(year, 12, 31));

        double balance = ai1 - ae1;

        return balance;
    }

    // EFFECTS: calculate the monthly balance of the given month in the given year
    // from user
    private double calculateMonthlyBalance(int year, int month) {
        double ai2 = processIncomeDateBasedOnMonth(month, year);
        double ae2 = processExpenseDateBasedOnMonth(month, year);

        double balance = ai2 - ae2;
        return balance;
    }

    // EFFECTS: calculate the total monthly income amount based on specific month
    private double processIncomeDateBasedOnMonth(int month, int year) {
        ArrayList<Income> ik = icbk.getIncomeRecord();

        double ai2 = 0.0;
        if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
            ai2 = icbk.calculateTotalIncomeByTime(ik, LocalDate.of(year, month, 1),
                    LocalDate.of(year, month, 31));
        } else if (month == 4 || month == 6 || month == 9 || month == 11) {
            ai2 = icbk.calculateTotalIncomeByTime(ik, LocalDate.of(year, month, 1),
                    LocalDate.of(year, month, 30));
        } else {
            ai2 = icbk.calculateTotalIncomeByTime(ik, LocalDate.of(year, month, 1),
                    LocalDate.of(year, month, 28));
        }
        return ai2;
    }

    // EFFECTS: calculate the total monthly expense amount based on specific month
    private double processExpenseDateBasedOnMonth(int month, int year) {
        ArrayList<Expense> ek = epbk.getExpenseRecord();

        double ae2 = 0.0;
        if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
            ae2 = epbk.calculateTotalExpenseByTime(ek, LocalDate.of(year, month, 1),
                    LocalDate.of(year, month, 31));
        } else if (month == 4 || month == 6 || month == 9 || month == 11) {
            ae2 = epbk.calculateTotalExpenseByTime(ek, LocalDate.of(year, month, 1),
                    LocalDate.of(year, month, 30));
        } else {
            ae2 = epbk.calculateTotalExpenseByTime(ek, LocalDate.of(year, month, 1),
                    LocalDate.of(year, month, 28));
        }
        return ae2;
    }

    // EFFECTS: creates the calculate button that allows the user to calculate
    // specific monthly balance
    private JButton addMonthlyCalculateButton(JTextField yearField, JTextField monthField) {
        JButton calculateButton = new JButton("Calculate");
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double balance = calculateMonthlyBalance(Integer.valueOf(yearField.getText()),
                        Integer.valueOf(monthField.getText()));
                JOptionPane.showMessageDialog(null,
                        "Your " + yearField.getText() + "." + monthField.getText() + " balance is "
                                + Double.toString(balance));
                yearField.setText("");
                monthField.setText("");
                monthlyBalancePanel.setVisible(false);
                buttonPanel.setVisible(true);
            }
        });
        return calculateButton;
    }

}
