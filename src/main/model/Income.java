package model;

import java.time.LocalDate;

import org.json.JSONObject;

import persistence.Writable;

// Represents a single income entry with the exact money amount (in $), its
// soure, and the date
public class Income implements Writable {

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
        this.money = money;
        this.source = source;
        this.date = date;
        this.note = null;
    }

    /*
     * REQUIRES: note string has a non-negative length
     * MODIFIES: this
     * EFFECTS: attach a single user note to the given income entry
     */
    public void attachIncomeNote(String note) {
        this.note = note;
    }

    // EFFECTS: return the user note attached to a specific income
    public String checkIncomeNote() {
        return note;
    }

    // EFFECTS: return the money earned from a specific income
    public double getIncomeMoney() {
        return money;
    }

    // EFFECTS: return the source number of a specific income
    public Incomesource getIncomeSource() {
        return source;
    }

    // EFFECTS: return the date of a specific income
    public LocalDate getIncomeDate() {
        return date;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("money", money);
        json.put("source", source);
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
        result = prime * result + ((source == null) ? 0 : source.hashCode());
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
        Income other = (Income) obj;
        if (Double.doubleToLongBits(money) != Double.doubleToLongBits(other.money))
            return false;
        if (source != other.source)
            return false;
        if (date == null) {
            if (other.date != null)
                return false;
        } else if (!date.equals(other.date))
            return false;
        return true;
    }

}
