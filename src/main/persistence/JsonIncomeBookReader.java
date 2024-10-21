package persistence;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.stream.Stream;

import org.json.JSONArray;
import org.json.JSONObject;

import model.IncomeBook;
import model.Income;
import model.Income.Incomesource;

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
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseIncomeBook(jsonObject);
        
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses IncomeBook from JSON object and returns it
    private IncomeBook parseIncomeBook(JSONObject jsonObject) {
        IncomeBook ic = new IncomeBook();
        addIncomes(ic, jsonObject);
        return ic;
    }

    // MODIFIES: ic
    // EFFECTS: parses incomes from JSON object and adds them to IncomeBook
    private void addIncomes(IncomeBook ic, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("incomeRecord");
        for (Object json : jsonArray) {
            JSONObject nextIncome = (JSONObject) json;
            addIncome(ic, nextIncome);
        }
    }

    // MODIFIES: ic
    // EFFECTS: parses income from JSON object and adds it to IncomeBook
    private void addIncome(IncomeBook ic, JSONObject jsonObject) {
        double money = jsonObject.getDouble("money");
        Incomesource source = Incomesource.valueOf(jsonObject.getString("source"));
        String yr = (jsonObject.getString("date")).substring(0,4);
        String mt =  (jsonObject.getString("date")).substring(4,6);
        String da =  (jsonObject.getString("date")).substring(6,8);
        LocalDate date = LocalDate.of(Integer.parseInt(yr), Integer.parseInt(mt), Integer.parseInt(da));
        Income i = new Income(money, source, date);
        i.attachIncomeNote(jsonObject.getString("note"));
        ic.addIncome(i);

    }
}
