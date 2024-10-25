package persistence;

import java.io.*;

import org.json.JSONObject;

import model.ExpenseBook;

// Represents a writer that writes JSON representation of ExpenseBook to file
public class JsonExpenseBookWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // EFFETCS: construts expensebook writer to write to destination file
    public JsonExpenseBookWriter(String destination) {
        this.destination = destination;
    }

    // MODIEFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file
    // cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIEFIES: this
    // EFFECTS: writes JSON representation of expensebook to file
    public void write(ExpenseBook ep) {
        JSONObject json = ep.toJson();
        saveToFile(json.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }

}
