package model;

// Represents all income entries into the system, and categorize them 
// based on their source.
import java.util.ArrayList;

import model.Income.Incomesource;

import java.time.LocalDate;

public class IncomeBook {
    private ArrayList<Income> incomeRecord;
    private double totalIncome;

    /*
     * EFFECTS: construct an income book containing no income entries with a total
     * Income of 0
     */

    public IncomeBook() {
        //stub;
    }

    // MODIFIES: this
    // EFFECTS: add a new income entry into the incomebook list and increase the total income accordingly
    public void addIncome(Income i) {
        // stub;
    }

    // MODIFIES: this
    // EFFECTS: remove a speific income entry from the incomebook list and decrease the total income accordingly
    public void removeIncome(Income i) {
        // stub;
    }

    // REQUIRES: the input start and end time should only represent the start/end of a month or the start/end of a year
    // EFFECTS: return the list of income entries within specific time period
    public ArrayList<Income> filterIncomeRecordByTime(ArrayList<Income> icl, LocalDate start, LocalDate end) {
        return null;

    }

    // EFFECTS: return the list of income entries within specific source
    public ArrayList<Income> filterIncomeRecordBySource(ArrayList<Income> icl, Incomesource is) {
        return null;
    }

    // REQUIRES: the input start and end time should only represent the start/end of a month or the start/end of a year
    // EFFECTS: return the amount of total income of a specific time
    public double calculateTotalIncomeByTime(ArrayList<Income> icl, LocalDate start, LocalDate end) {
        return 0.0;
    }

    // EFFECTS: return the amount of total income of a specific source
    public double calulateTotalIncomeBySource(ArrayList<Income> icl, Incomesource is) {
        return 0.0;

    }

    public ArrayList<Income> getIncomeRecord() {
        return incomeRecord;
    }

    public double getTotalIncome() {
        return totalIncome;
    }
}
