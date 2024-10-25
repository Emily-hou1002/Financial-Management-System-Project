package persistence;

import java.io.*;
import org.json.JSONObject;

import model.IncomeBook;

// Represents a writer that writes JSON representation of IncomeBook to file
public class JsonIncomeBookWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // EFFETCS: construts incomebook writer to write to destination file
    public JsonIncomeBookWriter(String destination) {
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
    // EFFECTS: writes JSON representation of incomebook to file
    public void write(IncomeBook ic) {
        JSONObject json = ic.toJson();
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
