package test.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import persistence.JsonExpenseBookReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import model.Expense;
import model.ExpenseBook;
import model.Expense.ExpenseUsage;

public class JsonExpenseBookReaderTest extends JsonExpenseTest {
    @Test
    void testExpenseReaderNonExistentFile() {
        JsonExpenseBookReader epreader = new JsonExpenseBookReader("./data/noSuchFile.json");
        try {
            ExpenseBook ep = epreader.read();
            fail("IOEception expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testExpenseReaderEmptyExpenseBook() {
        JsonExpenseBookReader epreader = new JsonExpenseBookReader("./data/testExpenseReaderEmptyExpenseBook.json");
        try {
            ExpenseBook ep = epreader.read();
            assertEquals(0, ep.getTotalExpense());
            assertEquals(0, (ep.getExpenseRecord()).size());
        } catch (IOException e) {
            fail("Couln't read from file");
        }
    }

    @Test
    void testExpenseReaderGeneralExpenseBook() {
        JsonExpenseBookReader epreader = new JsonExpenseBookReader("./data/testExpenseReaderGeneralExpenseBook.json");
        try {
            ExpenseBook ep = epreader.read();
            assertEquals(128.3, ep.getTotalExpense());
            ArrayList<Expense> expenses = ep.getExpenseRecord();
            assertEquals(2, expenses.size());
            checkExpense(30.0, ExpenseUsage.GROCERY, LocalDate.of(2024, 11, 29), "fruit&vegies", expenses.get(0));
            checkExpense(98.3, ExpenseUsage.SHOPPING, LocalDate.of(2024, 6, 10), "null", expenses.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

}
