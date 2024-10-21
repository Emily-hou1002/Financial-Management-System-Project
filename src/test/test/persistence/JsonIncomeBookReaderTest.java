package test.persistence;

import org.junit.Test;

import model.Income;
import model.IncomeBook;
import model.Income.Incomesource;
import persistence.JsonIncomeBookReader;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class JsonIncomeBookReaderTest extends JsonIncomeTest {

    @Test
    void testIncomeReaderNonExistentFile() {
        JsonIncomeBookReader icreader = new JsonIncomeBookReader("./data/noSuchFile.json");
        try {
            IncomeBook ic = icreader.read();
            fail("IOEception expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testIncomeReaderEmptyIncomeBook() {
        JsonIncomeBookReader icreader = new JsonIncomeBookReader("./data/testIncomeReaderEmptyIncomeBook.json");
        try {
            IncomeBook ic = icreader.read();
            assertEquals(0, ic.getTotalIncome());
            assertEquals(0, (ic.getIncomeRecord()).size());
        } catch (IOException e) {
            fail("Couln't read from file");
        }
    }

    @Test
    void testIncomeReaderGeneralIncomeBook() {
        JsonIncomeBookReader icreader = new JsonIncomeBookReader("./data/testIncomeReaderGeneralIncomeBook.json");
        try {
            IncomeBook ic = icreader.read();
            assertEquals(153.2, ic.getTotalIncome());
            ArrayList<Income> incomes = ic.getIncomeRecord();
            assertEquals(2, incomes.size());
            checkIncome(100.0, Incomesource.REVENUE, LocalDate.of(2024, 10, 10), "part-time", incomes.get(0));
            checkIncome(53.2, Incomesource.INVESTMENT, LocalDate.of(2024, 8, 3), null, incomes.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

}
