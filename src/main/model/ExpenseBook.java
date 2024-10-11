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
     * EFFECTS: construct an expense book containing no expense entries
     */
    public ExpenseBook() {
        expenseRecord = new ArrayList<Expense>();
        totalExpense = 0.0;

    }

    // MODIFIES: this
    // EFFECTS: add an expense income entry into the expensebook list and increase
    // the total expense by a specific amount
    public void addExpense(Expense e) {
        expenseRecord.add(e);
        totalExpense += e.getExpenseMoney();
    }

    // MODIFIES: this
    // EFFECTS: remove an expense income entry into the expensebook list and
    // decrease the total expense by a specific amount
    public void removeExpense(Expense e) {
        expenseRecord.remove(e);
        totalExpense -= e.getExpenseMoney();
    }

    // REQUIRES: the input start and end time should only represent the start/end of
    // a month or the start/end of a year
    // EFFECTS: return the list of expense entries within specific time period
    public ArrayList<Expense> filterExpenseRecordByTime(ArrayList<Expense> epl, LocalDate start, LocalDate end) {
        ArrayList<Expense> ft = new ArrayList<Expense>();

        for (Expense e : epl) {
            LocalDate date = e.getExpenseDate();

            if (date.getYear() >= start.getYear() && date.getYear() <= end.getYear()) {
                if (date.getMonthValue() >= start.getMonthValue() && date.getMonthValue() <= end.getMonthValue()) {
                    if (date.getDayOfMonth() >= start.getDayOfMonth() && date.getDayOfMonth() <= end.getDayOfMonth()) {
                        ft.add(e);
                    }
                }
            }
        }

        return ft;
    }

    // EFFECTS: return the list of expense entries within specific time period
    public ArrayList<Expense> filterExpenseRecordByUsage(ArrayList<Expense> epl, ExpenseUsage eu) {
        ArrayList<Expense> fu = new ArrayList<Expense>();

        for (Expense e : epl) {
            ExpenseUsage usage = e.getExpenseUse();

            if (usage == eu) {
                fu.add(e);
            }
        }

        return fu;
    }

    // REQUIRES: the input start and end time should only represent the start/end of
    // a month or the start/end of a year
    // EFFECTS: return the amount of total expense of a specific time
    public double calculateTotalExpenseByTime(ArrayList<Expense> epl, LocalDate start, LocalDate end) {
        double tet = 0;

        for (Expense e : epl) {
            LocalDate date = e.getExpenseDate();
            double money = e.getExpenseMoney();

            if (date.getYear() >= start.getYear() && date.getYear() <= end.getYear()) {
                if (date.getMonthValue() >= start.getMonthValue() && date.getMonthValue() <= end.getMonthValue()) {
                    if (date.getDayOfMonth() >= start.getDayOfMonth()
                            && date.getDayOfMonth() <= end.getDayOfMonth()) {
                        tet += money;
                    }
                }
            }
        }
        return tet;
    }

    // EFFECTS: return the amount of total expense of a specific usage
    public double calulateTotalExpenseByUsage(ArrayList<Expense> epl, ExpenseUsage eu) {
        double tet = 0;

        for (Expense e : epl) {
            ExpenseUsage usage = e.getExpenseUse();
            double money = e.getExpenseMoney();

            if (usage == eu) {
                tet += money;
            }
        }
        return tet;
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
