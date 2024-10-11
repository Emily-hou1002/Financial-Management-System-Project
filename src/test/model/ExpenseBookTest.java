package model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import model.Expense.ExpenseUsage;

public class ExpenseBookTest {
    private ExpenseBook testExpenseBook;
    private Expense expense1;
    private Expense expense2;
    private Expense expense3;
    private Expense expense4;
    private Expense expense5;
    private Expense expense6;
    private Expense expense7;

    @BeforeEach
    void runBefore() {
        testExpenseBook = new ExpenseBook();
        expense1 = new Expense(100.0, ExpenseUsage.GROCERY, LocalDate.of(2024, 6, 17));
        expense2 = new Expense(2000.0, ExpenseUsage.HOUSING, LocalDate.of(2024, 7, 2));
        expense3 = new Expense(50.0, ExpenseUsage.SHOPPING, LocalDate.of(2024, 6, 5));
        expense4 = new Expense(200.0, ExpenseUsage.SHOPPING, LocalDate.of(2023, 9, 13));
        expense5 = new Expense(30.0, ExpenseUsage.TRANSPORTATION, LocalDate.of(2025, 10, 5));
        expense6 = new Expense(3000.0, ExpenseUsage.HOUSING, LocalDate.of(2024, 4, 21));
        expense7 = new Expense(5.0, ExpenseUsage.TRANSPORTATION, LocalDate.of(2024, 6, 1));
    }

    @Test
    public void testConstructor() {
        ArrayList<Expense> teb = testExpenseBook.getExpenseRecord();
        assertEquals(0, teb.size());
        assertEquals(0, testExpenseBook.getTotalExpense());
    }

    @Test
    public void testAddOneExpense() {
        ArrayList<Expense> teb = testExpenseBook.getExpenseRecord();
        testExpenseBook.addExpense(expense1);

        assertEquals(1, teb.size());
        assertEquals(expense1, teb.get(0));
        assertEquals(100.0, testExpenseBook.getTotalExpense());
    }

    @Test
    public void testAddMultipleExpense() {
        ArrayList<Expense> teb = testExpenseBook.getExpenseRecord();
        testExpenseBook.addExpense(expense1);
        testExpenseBook.addExpense(expense3);
        testExpenseBook.addExpense(expense2);

        assertEquals(3, teb.size());
        assertEquals(expense1, teb.get(0));
        assertEquals(expense3, teb.get(1));
        assertEquals(expense2, teb.get(2));
        assertEquals(2150.0, testExpenseBook.getTotalExpense());
    }

    @Test
    public void testRemoveExpense() {
        ArrayList<Expense> teb = testExpenseBook.getExpenseRecord();
        testExpenseBook.addExpense(expense1);
        testExpenseBook.addExpense(expense3);
        testExpenseBook.addExpense(expense2);
        testExpenseBook.removeExpense(expense1);

        assertEquals(2, teb.size());
        assertEquals(expense3, teb.get(0));
        assertEquals(expense2, teb.get(1));
        assertEquals(2050.0, testExpenseBook.getTotalExpense());
    }

    @Test
    public void testFilterExpenseRecordByYear() {
        testExpenseBook.addExpense(expense5);
        testExpenseBook.addExpense(expense1);
        testExpenseBook.addExpense(expense3);
        testExpenseBook.addExpense(expense4);
        ArrayList<Expense> epl = testExpenseBook.getExpenseRecord();
        ArrayList<Expense> feb = testExpenseBook.filterExpenseRecordByTime(epl, LocalDate.of(2024, 6, 1),
                LocalDate.of(2024, 6, 30));

        assertEquals(2, feb.size());
        assertEquals(expense1, feb.get(0));
        assertEquals(expense3, feb.get(1));
    }

    @Test
    public void testFilterExpenseRecordByMonth() {
        testExpenseBook.addExpense(expense6);
        testExpenseBook.addExpense(expense1);
        testExpenseBook.addExpense(expense2);
        testExpenseBook.addExpense(expense3);
        ArrayList<Expense> epl = testExpenseBook.getExpenseRecord();
        ArrayList<Expense> feb = testExpenseBook.filterExpenseRecordByTime(epl, LocalDate.of(2024, 6, 1),
                LocalDate.of(2024, 6, 30));

        assertEquals(2, feb.size());
        assertEquals(expense1, feb.get(0));
        assertEquals(expense3, feb.get(1));
    }

    @Test
    public void testFilterExpenseRecordByDate() {
        testExpenseBook.addExpense(expense7);
        testExpenseBook.addExpense(expense1);
        testExpenseBook.addExpense(expense3);
        ArrayList<Expense> epl = testExpenseBook.getExpenseRecord();
        ArrayList<Expense> feb = testExpenseBook.filterExpenseRecordByTime(epl, LocalDate.of(2024, 6, 5),
                LocalDate.of(2024, 6, 16));

        assertEquals(1, feb.size());
        assertEquals(expense3, feb.get(0));
    }

    @Test
    public void testFilterExpenseRecordByUsage() {
        testExpenseBook.addExpense(expense2);
        testExpenseBook.addExpense(expense3);
        testExpenseBook.addExpense(expense4);
        ArrayList<Expense> epl = testExpenseBook.getExpenseRecord();
        ArrayList<Expense> feb = testExpenseBook.filterExpenseRecordByUsage(epl, ExpenseUsage.SHOPPING);
        assertEquals(2, feb.size());
        assertEquals(expense3, feb.get(0));
        assertEquals(expense4, feb.get(1));
    }

    @Test
    public void testCalculateTotalExpenseTimeNull() {
        ArrayList<Expense> epl = testExpenseBook.getExpenseRecord();
        double teb = testExpenseBook.calculateTotalExpenseByTime(epl, LocalDate.of(2024, 1, 1),
                LocalDate.of(2024, 12, 31));

        assertEquals(0.0, teb);
    }

    @Test
    public void testCalculateTotalExpenseByYear() {
        testExpenseBook.addExpense(expense5);
        testExpenseBook.addExpense(expense1);
        testExpenseBook.addExpense(expense3);
        testExpenseBook.addExpense(expense4);
        ArrayList<Expense> epl = testExpenseBook.getExpenseRecord();
        double teb = testExpenseBook.calculateTotalExpenseByTime(epl, LocalDate.of(2024, 6, 1),
                LocalDate.of(2024, 6, 30));

        assertEquals(150.0, teb);
    }

    @Test
    public void testCalculateTotalExpenseByMonth() {
        testExpenseBook.addExpense(expense6);
        testExpenseBook.addExpense(expense1);
        testExpenseBook.addExpense(expense2);
        testExpenseBook.addExpense(expense3);
        ArrayList<Expense> epl = testExpenseBook.getExpenseRecord();
        double teb = testExpenseBook.calculateTotalExpenseByTime(epl, LocalDate.of(2024, 6, 1),
                LocalDate.of(2024, 6, 30));

        assertEquals(150.0, teb);
    }

    @Test
    public void testCalculateTotalExpenseByDate() {
        testExpenseBook.addExpense(expense7);
        testExpenseBook.addExpense(expense1);
        testExpenseBook.addExpense(expense3);
        ArrayList<Expense> epl = testExpenseBook.getExpenseRecord();
        double teb = testExpenseBook.calculateTotalExpenseByTime(epl, LocalDate.of(2024, 6, 5),
                LocalDate.of(2024, 6, 16));

        assertEquals(50.0, teb);

    }

    @Test
    public void testCalculateTotalExpenseUsageNull() {
        ArrayList<Expense> epl = testExpenseBook.getExpenseRecord();
        double teb = testExpenseBook.calulateTotalExpenseByUsage(epl, ExpenseUsage.ENTERTAINMENT);

        assertEquals(0.0, teb);
    }

    @Test
    public void testCalulateTotalExpenseByUsage() {
        testExpenseBook.addExpense(expense2);
        testExpenseBook.addExpense(expense3);
        testExpenseBook.addExpense(expense4);
        ArrayList<Expense> epl = testExpenseBook.getExpenseRecord();
        double teb = testExpenseBook.calulateTotalExpenseByUsage(epl, ExpenseUsage.SHOPPING);

        assertEquals(250.0, teb);
    }

}


