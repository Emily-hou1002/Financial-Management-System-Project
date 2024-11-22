package model;

import java.time.LocalDate;

import org.json.JSONObject;

import persistence.Writable;

//Represents a single expense entry with exact amount of money spent (in $), the use 
// of the expense, the date when the expense occurs
public class Expense implements Writable {

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

    // EFFECTS: return the usage of a specific expense
    public ExpenseUsage getExpenseUse() {
        return use;
    }

    // EFFECTS: return the date of a specific expense
    public LocalDate getExpenseDate() {
        return date;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("money", money);
        json.put("use", use);
        json.put("date", date);
        json.put("note", note);
        return json;
    }

    // EFFECTS: override hashCode and Equals so that all expenses with the same
    // amount, usage, and date are equal
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        long temp;
        temp = Double.doubleToLongBits(money);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + ((use == null) ? 0 : use.hashCode());
        result = prime * result + ((date == null) ? 0 : date.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Expense other = (Expense) obj;
        if (Double.doubleToLongBits(money) != Double.doubleToLongBits(other.money))
            return false;
        if (use != other.use)
            return false;
        if (date == null) {
            if (other.date != null)
                return false;
        } else if (!date.equals(other.date))
            return false;
        return true;
    }

}
