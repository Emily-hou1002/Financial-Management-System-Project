package model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import model.Expense.ExpenseUsage;

public class ExpenseTest {
    private Expense testExpense1;
    private Expense testExpense2;
    private Expense testExpense3;
    private Expense testExpense4;
    private Expense testExpense5;
    private Expense testExpense6;

    @BeforeEach
    void runBefore() {
        testExpense1 = new Expense(300.0, ExpenseUsage.GROCERY, LocalDate.of(2024, 10, 3));
        testExpense2 = new Expense(300.0, ExpenseUsage.GROCERY, LocalDate.of(2024, 10, 3));
        testExpense3 = new Expense(2000.0, ExpenseUsage.ENTERTAINMENT, LocalDate.of(2023, 6, 3));
        testExpense4 = new Expense(200.0, ExpenseUsage.GROCERY, LocalDate.of(2024, 10, 3));
        testExpense5 = new Expense(300.0, ExpenseUsage.OTHER, LocalDate.of(2024, 10, 3));
        testExpense6 = new Expense(300.0, ExpenseUsage.GROCERY, LocalDate.of(2024, 11, 3));
    }

    @Test
    public void testConstructor() {
        assertEquals(300.0, testExpense1.getExpenseMoney());
        assertEquals(ExpenseUsage.GROCERY, testExpense1.getExpenseUse());
        assertEquals(LocalDate.of(2024, 10, 3), testExpense1.getExpenseDate());
        assertEquals(null, testExpense1.checkExpenseNote());
    }

    @Test
    public void testAttachNote() {
        testExpense1.attachExpenseNote("meat, fruit, and vegies");
        assertEquals("meat, fruit, and vegies", testExpense1.checkExpenseNote());
        testExpense1.attachExpenseNote("meat, fruit, vegies, and seafood");
        assertEquals("meat, fruit, vegies, and seafood", testExpense1.checkExpenseNote());
    }

    @Test
    public void testEqualTrue() {
        assertTrue(testExpense1.equals(testExpense2));
    }

    @Test
    public void testEqualFalse() {
        assertFalse(testExpense1.equals(testExpense3));
    }


    @Test
    public void testEqualFalseMoney() {
        assertFalse(testExpense1.equals(testExpense4));
    }

    @Test
    public void testEqualFalseUsage() {
        assertFalse(testExpense1.equals(testExpense5));
    }

    @Test
    public void testEqualFalseDate() {
        assertFalse(testExpense1.equals(testExpense6));
    }

}


