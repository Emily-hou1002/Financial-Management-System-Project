package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import model.Expense.ExpenseUsage;

public class ExpenseTest {
    private Expense testExpense1;

    @BeforeEach
    void runBefore() {
        testExpense1 = new Expense(300.0, ExpenseUsage.GROCERY, LocalDate.of(2024, 10, 3));
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

}


