package persistence;

import java.io.IOException;

import org.json.JSONObject;

import model.ExpenseBook;

// Represents a reader that reads the expensebook from JSON data stored in file
public class JsonExpenseBookReader {
    private String source;

    // EFFECTS: constructs reader to read the incomebook from source file
    public JsonExpenseBookReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads expensebook from file and returns it;
    // throws IOException if an error occurs reading data from file
    public ExpenseBook read() throws IOException {
        return null; //
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        return "";
    }

    // EFFECTS: parses ExpenseBook from JSON object and returns it
    private ExpenseBook parseExpenseBook(JSONObject jsonObeject) {
        return null;
    }

    // MODIFIES: ic
    // EFFECTS: parses expenses from JSON object and adds them to ExpenseBook
    private void addExpenses(ExpenseBook ep, JSONObject jsonObject) {
        //stub;
    }

    // MODIFIES: ic
    // EFFECTS: parses income from JSON object and adds it to IncomeBook
    private void addExpense(ExpenseBook ep, JSONObject jsonObject) {
        //stub;
    }

}

