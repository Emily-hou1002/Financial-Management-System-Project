package persistence;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.JSONObject;

import model.IncomeBook;

// Represents a reader that reads the incomebook from JSON data stored in file
public class JsonIncomeBookReader {
    private String source;

    // EFFECTS: constructs reader to read the incomebook from source file
    public JsonIncomeBookReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads incomebook from file and returns it;
    // throws IOException if an error occurs reading data from file
    public IncomeBook read() throws IOException {
        return null; //
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        return "";
    }

    // EFFECTS: parses IncomeBook from JSON object and returns it
    private IncomeBook parseIncomeBook(JSONObject jsonObeject) {
        return null;
    }

    // MODIFIES: ic
    // EFFECTS: parses incomes from JSON object and adds them to IncomeBook
    private void addIncomes(IncomeBook ic, JSONObject jsonObject) {
        //stub;
    }

    // MODIFIES: ic
    // EFFECTS: parses income from JSON object and adds it to IncomeBook
    private void addIncome(IncomeBook ic, JSONObject jsonObject) {
        //stub;
    }
}
