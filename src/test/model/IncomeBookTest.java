package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import model.Income.Incomesource;

import java.util.ArrayList;
import java.time.LocalDate;

public class IncomeBookTest {
    private IncomeBook testIncomeBook;
    private Income income1;
    private Income income2;
    private Income income3;
    private Income income4;
    private Income income5;
    private Income income6;
    private Income income7;

    @BeforeEach
    void runBefore() {
        testIncomeBook = new IncomeBook();
        income1 = new Income(2000.0, Incomesource.REVENUE, LocalDate.of(2024, 5, 6));
        income2 = new Income(10000.0, Incomesource.INVESTMENT, LocalDate.of(2024, 5, 23));
        income3 = new Income(50.0, Incomesource.CASHGIFT, LocalDate.of(2024, 10, 15));
        income4 = new Income(100.0, Incomesource.CASHGIFT, LocalDate.of(2023, 2, 5));
        income5 = new Income(300.0, Incomesource.OTHER, LocalDate.of(2025, 9, 12));
        income6 = new Income(10000.0, Incomesource.INVESTMENT, LocalDate.of(2024, 3, 9));
        income7 = new Income(60.0, Incomesource.OTHER, LocalDate.of(2024, 5, 3));
    }

    @Test
    public void testConstructor() {
        ArrayList<Income> tib = testIncomeBook.getIncomeRecord();
        assertEquals(0, tib.size());
        assertEquals(0, testIncomeBook.getTotalIncome());
    }

    @Test
    public void testAddOneIncome() {
        ArrayList<Income> tib = testIncomeBook.getIncomeRecord();
        testIncomeBook.addIncome(income1);

        assertEquals(1, tib.size());
        assertEquals(income1, tib.get(0));
        assertEquals(2000.0, testIncomeBook.getTotalIncome());
    }

    @Test
    public void testAddMultipleIncome() {
        ArrayList<Income> tib = testIncomeBook.getIncomeRecord();
        testIncomeBook.addIncome(income1);
        testIncomeBook.addIncome(income3);
        testIncomeBook.addIncome(income2);

        assertEquals(3, tib.size());
        assertEquals(income1, tib.get(0));
        assertEquals(income3, tib.get(1));
        assertEquals(income2, tib.get(2));

        assertEquals(12050.0, testIncomeBook.getTotalIncome());
    }

    @Test
    public void testRemoveIncome() {
        ArrayList<Income> tib = testIncomeBook.getIncomeRecord();
        testIncomeBook.addIncome(income1);
        testIncomeBook.addIncome(income2);
        testIncomeBook.addIncome(income3);
        testIncomeBook.removeIncome(income1);

        assertEquals(2, tib.size());
        assertEquals(income2, tib.get(0));
        assertEquals(income3, tib.get(1));

        assertEquals(10050.0, testIncomeBook.getTotalIncome());
    }

    @Test
    public void testFilterIncomeRecordByYear() {
        testIncomeBook.addIncome(income4);
        testIncomeBook.addIncome(income1);
        testIncomeBook.addIncome(income5);
        testIncomeBook.addIncome(income2);
        ArrayList<Income> icl = testIncomeBook.getIncomeRecord();
        ArrayList<Income> fib = testIncomeBook.filterIncomeRecordByTime(icl, LocalDate.of(2024, 5, 1),
                LocalDate.of(2024, 5, 31));

        assertEquals(2, fib.size());
        assertEquals(income1, fib.get(0));
        assertEquals(income2, fib.get(1));

    }

    @Test
    public void testFilterIncomeRecordByMonth() {
        testIncomeBook.addIncome(income6);
        testIncomeBook.addIncome(income1);
        testIncomeBook.addIncome(income2);
        testIncomeBook.addIncome(income3);
        ArrayList<Income> icl = testIncomeBook.getIncomeRecord();
        ArrayList<Income> fib = testIncomeBook.filterIncomeRecordByTime(icl, LocalDate.of(2024, 5, 1),
                LocalDate.of(2024, 5, 31));

        assertEquals(2, fib.size());
        assertEquals(income1, fib.get(0));
        assertEquals(income2, fib.get(1));

    }

    @Test
    public void testFilterIncomeRecordByDate() {
        testIncomeBook.addIncome(income7);
        testIncomeBook.addIncome(income1);
        testIncomeBook.addIncome(income2);
        ArrayList<Income> icl = testIncomeBook.getIncomeRecord();
        ArrayList<Income> fib = testIncomeBook.filterIncomeRecordByTime(icl, LocalDate.of(2024, 5, 6),
                LocalDate.of(2024, 5, 22));

        assertEquals(1, fib.size());
        assertEquals(income1, fib.get(0));
    }

    @Test
    public void testFilterIncomeRecordBySource() {
        testIncomeBook.addIncome(income2);
        testIncomeBook.addIncome(income3);
        testIncomeBook.addIncome(income4);
        ArrayList<Income> icl = testIncomeBook.getIncomeRecord();
        ArrayList<Income> fib = testIncomeBook.filterIncomeRecordBySource(icl, Incomesource.CASHGIFT);

        assertEquals(2, fib.size());
        assertEquals(income3, fib.get(0));
        assertEquals(income4, fib.get(1));
    }

    @Test
    public void testCalculateTotalIncomeTimeNull() {
        ArrayList<Income> icl = testIncomeBook.getIncomeRecord();
        double tib = testIncomeBook.calculateTotalIncomeByTime(icl, LocalDate.of(2024, 1, 1),
                LocalDate.of(2024, 12, 31));

        assertEquals(0.0, tib);
    }

    @Test
    public void testCalculateTotalIncomeByYear() {
        testIncomeBook.addIncome(income4);
        testIncomeBook.addIncome(income1);
        testIncomeBook.addIncome(income5);
        testIncomeBook.addIncome(income2);
        ArrayList<Income> icl = testIncomeBook.getIncomeRecord();
        double tib = testIncomeBook.calculateTotalIncomeByTime(icl, LocalDate.of(2024, 5, 1),
                LocalDate.of(2024, 5, 31));

        assertEquals(12000.0, tib);
    }

    @Test
    public void testCalculateTotalIncomeByMonth() {
        testIncomeBook.addIncome(income6);
        testIncomeBook.addIncome(income1);
        testIncomeBook.addIncome(income2);
        testIncomeBook.addIncome(income3);

        ArrayList<Income> icl = testIncomeBook.getIncomeRecord();
        double tib = testIncomeBook.calculateTotalIncomeByTime(icl, LocalDate.of(2024, 5, 1),
                LocalDate.of(2024, 5, 31));

        assertEquals(12000.0, tib);
    }

    @Test
    public void testCalculateTotalIncomeByDate() {
        testIncomeBook.addIncome(income7);
        testIncomeBook.addIncome(income1);
        testIncomeBook.addIncome(income2);
        
        ArrayList<Income> icl = testIncomeBook.getIncomeRecord();
        double tib = testIncomeBook.calculateTotalIncomeByTime(icl, LocalDate.of(2024, 5, 6),
                LocalDate.of(2024, 5, 22));

        assertEquals(2000.0, tib);
    }

    @Test
    public void testCalculateTotalIncomeSourceNull() {
        ArrayList<Income> icl = testIncomeBook.getIncomeRecord();
        double tib = testIncomeBook.calulateTotalIncomeBySource(icl, Incomesource.REVENUE);

        assertEquals(0.0, tib);
    }

    @Test
    public void testCalculateTotalIncomeBySource() {
        testIncomeBook.addIncome(income2);
        testIncomeBook.addIncome(income3);
        testIncomeBook.addIncome(income4);
        ArrayList<Income> icl = testIncomeBook.getIncomeRecord();
        double tib = testIncomeBook.calulateTotalIncomeBySource(icl, Incomesource.CASHGIFT);

        assertEquals(150.0, tib);
    }

}

