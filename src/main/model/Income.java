package model;

import java.time.LocalDate;

// Represents a single income entry with the exact money amount (in $), its
// soure, and the date
public class Income {

    public enum Incomesource {
        REVENUE,
        INVESTMENT,
        CASHGIFT,
        OTHER
    }

    private double money; // money amount
    private Incomesource source; // income source: income,investment, cash gift, other
    private LocalDate date; // date of getting the income
    private String note; // user notes that is attached to an income

    /*
     * REQUIRES: money must be >0
     * EFFECTS: construct an income entry with its speific money amount called
     * money, its source called soure, the date of getting this income
     * called date, and a null user note.
     */
    public Income(double money, Incomesource source, LocalDate date) {
        //stub;
    }

    /*
     * REQUIRES: note string has a non-negative length
     * MODIFIES: this
     * EFFECTS: attach a single user note to the given income entry
     */
    public void attachIncomeNote(String note) {
       //stub;
    }

    // EFFECTS: return the user note attached to a specific income
    public String checkIncomeNote() {
        return null;
    }

    // EFFECTS: return the money earned from a specific income
    public double getIncomeMoney() {
        return 0.0;
    }

    // EFFECTS: return the source type of a specific income
    public Incomesource getIncomeSource() {
        return source;
    }

    // EFFECTS: return the date of a specific income
    public LocalDate getIncomeDate() {
        return date;
    }

}
