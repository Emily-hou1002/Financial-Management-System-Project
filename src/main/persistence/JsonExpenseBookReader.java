package persistence;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.stream.Stream;

import org.json.JSONArray;
import org.json.JSONObject;

import model.ExpenseBook;
import model.Expense;
import model.Expense.ExpenseUsage;

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
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseExpenseBook(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses ExpenseBook from JSON object and returns it
    private ExpenseBook parseExpenseBook(JSONObject jsonObject) {
        ExpenseBook ep = new ExpenseBook();
        addExpenses(ep, jsonObject);
        return ep;

    }

    // MODIFIES: ep
    // EFFECTS: parses expenses from JSON object and adds them to ExpenseBook
    private void addExpenses(ExpenseBook ep, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("expenseRecord");
        for (Object json : jsonArray) {
            JSONObject nextExpense = (JSONObject) json;
            addExpense(ep, nextExpense);
        }
    }

    // MODIFIES: ep
    // EFFECTS: parses expense from JSON object and adds it to ExpenseBook
    private void addExpense(ExpenseBook ep, JSONObject jsonObject) {
        double money = jsonObject.getDouble("money");
        ExpenseUsage use = ExpenseUsage.valueOf(jsonObject.getString("use"));
        String yr = (jsonObject.getString("date")).substring(0, 4);
        String mt = (jsonObject.getString("date")).substring(5, 7);
        String da = (jsonObject.getString("date")).substring(8, 10);
        LocalDate date = LocalDate.of(Integer.parseInt(yr), Integer.parseInt(mt), Integer.parseInt(da));
        Expense e = new Expense(money, use, date);
        if (jsonObject.has("note")) {
            e.attachExpenseNote(jsonObject.getString("note"));
        }
        ep.addExpense(e);
    }

}
