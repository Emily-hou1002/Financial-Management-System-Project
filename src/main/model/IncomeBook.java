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
        incomeRecord = new ArrayList<Income>();
        totalIncome = 0.0;
    }

    // MODIFIES: this
    // EFFECTS: add a new income entry into the incomebook list based on
    // chronologial order
    public void addIncome(Income i) {
        incomeRecord.add(i);
        this.totalIncome += i.getIncomeMoney();
    }

    // MODIFIES: this
    // EFFECTS: remove a speific income entry from the incomebook list
    public void removeIncome(Income i) {
        incomeRecord.remove(i);
        this.totalIncome -= i.getIncomeMoney();
    }

    // REQUIRES: the input start and end time should only represent the start/end of
    // a month or the start/end of a year
    // EFFECTS: return the list of income entries within specific time period
    public ArrayList<Income> filterIncomeRecordByTime(ArrayList<Income> icl, LocalDate start, LocalDate end) {
        ArrayList<Income> ft = new ArrayList<Income>();

        for (Income i : icl) {
            LocalDate date = i.getIncomeDate();

            if (date.getYear() >= start.getYear() && date.getYear() <= end.getYear()) {
                if (date.getMonthValue() >= start.getMonthValue() && date.getMonthValue() <= end.getMonthValue()) {
                    if (date.getDayOfMonth() >= start.getDayOfMonth() && date.getDayOfMonth() <= end.getDayOfMonth()) {
                        ft.add(i);
                    }
                }
            }
        }

        return ft;

    }

    // EFFECTS: return the list of income entries within specific souce
    public ArrayList<Income> filterIncomeRecordBySource(ArrayList<Income> icl, Incomesource is) {
        ArrayList<Income> fs = new ArrayList<Income>();

        for (Income i : icl) {
            Incomesource source = i.getIncomeSource();

            if (source == is) {
                fs.add(i);
            }
        }

        return fs;
    }

    // REQUIRES: the input start and end time should only represent
    // the start/end of a month or the start/end of a year
    // EFFECTS: return the amount of total income of a specific time
    public double calculateTotalIncomeByTime(ArrayList<Income> icl, LocalDate start, LocalDate end) {
        double tit = 0;

        for (Income i : icl) {
            LocalDate date = i.getIncomeDate();
            double money = i.getIncomeMoney();

            if (date.getYear() >= start.getYear() && date.getYear() <= end.getYear()) {
                if (date.getMonthValue() >= start.getMonthValue() && date.getMonthValue() <= end.getMonthValue()) {
                    if (date.getDayOfMonth() >= start.getDayOfMonth()
                            && date.getDayOfMonth() <= end.getDayOfMonth()) {
                        tit += money;
                    }
                }
            }
        }
        return tit;
    }

    // EFFECTS: return the amount of total income of a specific source
    public double calulateTotalIncomeBySource(ArrayList<Income> icl, Incomesource is) {
        double tis = 0;

        for (Income i : icl) {
            Incomesource source = i.getIncomeSource();
            double money = i.getIncomeMoney();

            if (source == is) {
                tis += money;
            }
        }
        return tis;

    }

    public ArrayList<Income> getIncomeRecord() {
        return incomeRecord;
    }

    public double getTotalIncome() {
        return totalIncome;
    }
}
