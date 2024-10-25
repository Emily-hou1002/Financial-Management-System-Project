package test.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import model.ExpenseBook;
import model.Expense;
import model.Expense.ExpenseUsage;
import persistence.JsonExpenseBookReader;
import persistence.JsonExpenseBookWriter;

public class JsonExpenseBookWriterTest extends JsonExpenseTest {
    @Test
    void testExpenseWriterInvalidFile() {
        try {
            new ExpenseBook();
            JsonExpenseBookWriter expenseWriter = new JsonExpenseBookWriter("./data/my\0illegal:fileName.json");
            expenseWriter.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testExpenseWriterEmptyExpenseBook() {
        try {
            ExpenseBook epb = new ExpenseBook();
            JsonExpenseBookWriter expenseWriter = new JsonExpenseBookWriter(
                    "./data/testExpenseWriterEmptyExpenseBook.json");
            expenseWriter.open();
            expenseWriter.write(epb);
            expenseWriter.close();

            JsonExpenseBookReader expenseReader = new JsonExpenseBookReader(
                    "./data/testExpenseWriterEmptyExpenseBook.json");
            epb = expenseReader.read();
            assertEquals(0, epb.getTotalExpense());
            assertEquals(0, (epb.getExpenseRecord()).size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testExpenseWriterGeneralExpenseBook() {
        try {
            ExpenseBook epb = new ExpenseBook();
            epb.addExpense(new Expense(2000.0, ExpenseUsage.HOUSING, LocalDate.of(2024, 1, 10)));
            Expense ep2 = new Expense(55.0, ExpenseUsage.TRANSPORTATION, LocalDate.of(2024, 7, 19));
            ep2.attachExpenseNote("bus");
            epb.addExpense(ep2);
            JsonExpenseBookWriter expenseBookWriter = new JsonExpenseBookWriter(
                    "./data/testExpenseWriterGeneralExpenseBook.json");
            expenseBookWriter.open();
            expenseBookWriter.write(epb);
            expenseBookWriter.close();

            JsonExpenseBookReader expenseBookReader = new JsonExpenseBookReader(
                    "./data/testExpenseWriterGeneralExpenseBook.json");
            epb = expenseBookReader.read();
            assertEquals(2055.0, epb.getTotalExpense());
            ArrayList<Expense> expenses = epb.getExpenseRecord();
            assertEquals(2, expenses.size());
            checkExpense(2000.0, ExpenseUsage.HOUSING, LocalDate.of(2024, 1, 10), null, expenses.get(0));
            checkExpense(55.0, ExpenseUsage.TRANSPORTATION, LocalDate.of(2024, 7, 19), "bus", expenses.get(1));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
