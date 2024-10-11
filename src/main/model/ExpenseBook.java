package model;

import java.time.LocalDate;
// Represents all expense entries into the system, and categorize them 
// based on their usage.
import java.util.ArrayList;

import model.Expense.ExpenseUsage;

public class ExpenseBook {
    private ArrayList<Expense> expenseRecord;
    private double totalExpense;

    /*
     * EFFECTS: construct an expense book containing no expense entries and an total expense of 0
     */
    public ExpenseBook() {
        //stub;
    }

    // MODIFIES: this
    // EFFECTS: add an expense income entry into the expensebook list and increase the total expense accordingly
    public void addExpense(Expense e) {
        // stub;
    }

    // MODIFIES: this
    // EFFECTS: remove an expense income entry from the expensebook list and decrease the total expense accordingly
    public void removeExpense(Expense e) {
        //stub;
    }

    // REQUIRES: the input start and end time should only represent the start/end of a month or the start/end of a
    // year
    // EFFECTS: return the list of expense entries within specific time period
    public ArrayList<Expense> filterExpenseRecordByTime(ArrayList<Expense> epl, LocalDate start, LocalDate end) {
        return null;
    }

    // EFFECTS: return the list of expense entries within specific time period
    public ArrayList<Expense> filterExpenseRecordByUsage(ArrayList<Expense> epl, ExpenseUsage eu) {
        return null;
    }

    // REQUIRES: the input start and end time should only represent the start/end of a month or the start/end of a year
    // EFFECTS: return the amount of total expense of a specific time
    public double calculateTotalExpenseByTime(ArrayList<Expense> epl, LocalDate start, LocalDate end) {
        return 0.0;
    }

    // EFFECTS: return the amount of total expense of a specific usage
    public double calulateTotalExpenseByUsage(ArrayList<Expense> epl, ExpenseUsage eu) {
        return 0.0;
    }

    // EFFECTS: return the total amount of expense in the Expense Book
    public double getTotalExpense() {
        return totalExpense;
    }

    // EFFECTS: return the list of expense entries within a given time period
    public ArrayList<Expense> getExpenseRecord() {
        return expenseRecord;
    }

}
