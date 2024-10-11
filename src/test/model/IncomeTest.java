package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Income.Incomesource;

public class IncomeTest {
    private Income testIncome1;
    private Income testIncome2;

    @BeforeEach
    void runBefore() {
        testIncome1 = new Income(2000.0, Incomesource.REVENUE, LocalDate.of(2024, 6, 5));
        testIncome2 = new Income(500.0, Incomesource.INVESTMENT, LocalDate.of(2023, 7, 13));
    }

    @Test
    public void testConstructor() {
        assertEquals(2000.0, testIncome1.getIncomeMoney());
        assertEquals(Incomesource.REVENUE, testIncome1.getIncomeSource());
        assertEquals(LocalDate.of(2024, 6, 5), testIncome1.getIncomeDate());
        assertEquals(null, testIncome1.checkIncomeNote());
    }

    @Test
    public void testAttachNote() {
        testIncome1.attachIncomeNote("revenue from part-time");
        assertEquals("revenue from part-time", testIncome1.checkIncomeNote());
        testIncome1.attachIncomeNote("revenue from internship");
        assertEquals("revenue from internship", testIncome1.checkIncomeNote());

        testIncome2.attachIncomeNote("trust fund return");
        assertEquals("trust fund return", testIncome2.checkIncomeNote());
    }
}
