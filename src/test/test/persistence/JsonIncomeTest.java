package test.persistence;
import java.time.LocalDate;

import model.Income;
import model.Income.Incomesource;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonIncomeTest {
    protected void checkIncome(double money, Incomesource source, LocalDate date, String note, Income i) {
        assertEquals(money, i.getIncomeMoney());
        assertEquals(source, i.getIncomeSource());
        assertEquals(date, i.getIncomeDate());
        assertEquals(note, i.checkIncomeNote());
    }
}
