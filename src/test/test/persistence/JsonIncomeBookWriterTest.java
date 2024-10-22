package test.persistence;

import org.junit.jupiter.api.Test;

import model.Income;
import model.IncomeBook;
import model.Income.Incomesource;
import persistence.JsonIncomeBookReader;
import persistence.JsonIncomeBookWriter;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class JsonIncomeBookWriterTest extends JsonIncomeTest {

    @Test
    void testIncomeWriterInvalidFile() {
        try {
            IncomeBook ic = new IncomeBook();
            JsonIncomeBookWriter incomeWriter = new JsonIncomeBookWriter("./data/my\0illegal:fileName.json");
            incomeWriter.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testIncomeWriterEmptyIncomeBook() {
        try {
            IncomeBook ic = new IncomeBook();
            JsonIncomeBookWriter incomeWriter = new JsonIncomeBookWriter("./data/testIncomeWriterEmptyIncomeBook.json");
            incomeWriter.open();
            incomeWriter.write(ic);
            incomeWriter.close();

            JsonIncomeBookReader incomeReader = new JsonIncomeBookReader("./data/testIncomeWriterEmptyIncomeBook.json");
            ic = incomeReader.read();
            assertEquals(0, ic.getTotalIncome());
            assertEquals(0, (ic.getIncomeRecord()).size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testIncomeWriterGeneralIncomeBook() {
        try {
            IncomeBook icb = new IncomeBook();
            icb.addIncome(new Income(2000.0, Incomesource.CASHGIFT, LocalDate.of(2024, 9, 10)));
            Income ic2 = new Income(550, Incomesource.INVESTMENT, LocalDate.of(2024, 4, 25));
            ic2.attachIncomeNote("one-time");
            icb.addIncome(ic2);
            JsonIncomeBookWriter incomeBookWriter = new JsonIncomeBookWriter("./data/testIncomeWriterGeneralIncomeBook.json");
            incomeBookWriter.open();
            incomeBookWriter.write(icb);
            incomeBookWriter.close();

            JsonIncomeBookReader incomeBookReader = new JsonIncomeBookReader("./data/testIncomeWriterGeneralIncomeBook.json");
            icb = incomeBookReader.read();
            assertEquals(2550.0, icb.getTotalIncome());
            ArrayList<Income> incomes = icb.getIncomeRecord();
            assertEquals(2, incomes.size());
            checkIncome(2000.0, Incomesource.CASHGIFT, LocalDate.of(2024, 9, 10), null, incomes.get(0));
            checkIncome(550.0, Incomesource.INVESTMENT, LocalDate.of(2024, 4, 25), "one-time", incomes.get(1));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
