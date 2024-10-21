package test.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import model.Expense;
import model.Expense.ExpenseUsage;



public class JsonExpenseTest {
    protected void checkExpense(double money, ExpenseUsage use, LocalDate date, String note, Expense e) {
        assertEquals(money, e.getExpenseMoney());
        assertEquals(use, e.getExpenseUse());
        assertEquals(date, e.getExpenseDate());
        assertEquals(note, e.checkExpenseNote());
    }

}
