package model;

import java.time.LocalDate;

//Represents a single expense entry with exact amount of money spent (in $), the use 
// of the expense, the date when the expense occurs
public class Expense {

    public enum ExpenseUsage {
        GROCERY,
        HOUSING,
        SHOPPING,
        TRANSPORTATION,
        ENTERTAINMENT,
        OTHER
    }

    private double money; // money amount
    private ExpenseUsage use; // expense usage: grocery, housing, shopping,
    // transportation, entertainment, and other expense
    private LocalDate date; // date of spending the expense
    private String note; // user note which is attahed to an expense

    /*
     * REQUIRES: money spent must be >0
     * EFFECTS: construct an expense entry with its speific money amount called
     * money,
     * its usage called use, the date of spending this expense
     * called date, and a null user note.
     */
    public Expense(double money, ExpenseUsage use, LocalDate date) {
        this.money = money;
        this.use = use;
        this.date = date;
        this.note = null;
    }

    /*
     * REQUIRES: notes string has a non-negative length
     * MODIFIES: this
     * EFFECTS: attach a user note to the given expense entry
     */
    public void attachExpenseNote(String note) {
        this.note = note;
    }

    // EFFECTS: return the user note attached to a specific expense
    public String checkExpenseNote() {
        return note;
    }

    // EFFECTS: return the money spent from a specific expense
    public double getExpenseMoney() {
        return money;
    }

    // EFFECTS: return the usage number of a specific expense
    public ExpenseUsage getExpenseUse() {
        return use;
    }

    // EFFECTS: return the date of a specific expense
    public LocalDate getExpenseDate() {
        return date;
    }

}

