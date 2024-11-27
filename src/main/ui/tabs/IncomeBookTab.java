package ui.tabs;

import java.awt.*;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import model.Income;
import model.Income.Incomesource;
import model.IncomeBook;
import persistence.JsonIncomeBookReader;
import persistence.JsonIncomeBookWriter;
import ui.FinancialManagementApp;

// Represents an incomebook Tab that allows users to add/remove income, save/load incomebook,
// and display the incomebook within specific time frame
public class IncomeBookTab extends Tab {

    private static final String INCOMEBOOK_TEXT = "What do you want to do with your IncomeBook?";
    private static final String JSON_STORE1 = "./data/IncomeBookStorage.json";
    private static final String askSource = "<html>"
            + "Enter Income Source:<br>"
            + "[0] ALL<br>"
            + "[1] REVENUE<br>"
            + "[2] INVESTMENT<br>"
            + "[3] CASHGIFT<br>"
            + "[4] OTHER"
            + "</html>";
    private JPanel buttonPanel;
    private JPanel addEntryPanel;
    private JPanel removeEntryPanel;
    private JPanel bookDisplayPanel;

    private JsonIncomeBookWriter jsonIncomeWriter;
    private JsonIncomeBookReader jsonIncomeReader;

    // REQUIRES: FinanacialManagementApp controller that holds this tab
    // EFFECTS: creates incomebook tab that add/remove income, save/load incomebook,
    // and display the incomebook within
    // specific time frame

    public IncomeBookTab(FinancialManagementApp controller, IncomeBook incomeBook) {
        super(controller);
        this.icbk = incomeBook;
        jsonIncomeWriter = new JsonIncomeBookWriter(JSON_STORE1);
        jsonIncomeReader = new JsonIncomeBookReader(JSON_STORE1);

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 1));
        buttonPanel.setVisible(true);
        JLabel text = new JLabel(INCOMEBOOK_TEXT, JLabel.CENTER);
        buttonPanel.add(text);

        createAddEntryPanel();
        createRemoveEntryPanel();
        createbookDisplayPanel();

        placeAddRemoveButton();
        placeSaveLoadButton();
        placeDisplayBookButton();

        add(buttonPanel);
    }

    // MODIFIES: this
    // EFFECTS: create an add entry panel that is not visible until add button is
    // pressed
    private void createAddEntryPanel() {
        addEntryPanel = new JPanel();
        addEntryPanel.setLayout(new GridLayout(6, 1));
        addEntryPanel.setVisible(false);

        JPanel amountRow = new JPanel();
        JTextField amountField = new JTextField(15);
        amountRow.add(new JLabel("Enter Amount: "));
        amountRow.add(amountField);

        JPanel sourceRow = new JPanel();
        JTextField sourceField = new JTextField(1);
        sourceRow.add(new JLabel(
                "<html>Enter Income Source:<br>[1] REVENUE<br>[2] INVESTMENT<br>[3] CASHGIFT<br>[4] OTHER</html>"));
        sourceRow.add(sourceField);

        JPanel dateRow = new JPanel();
        JTextField dateField = new JTextField(8);
        dateRow.add(new JLabel("Enter Date (YYYYMMDD): "));
        dateRow.add(dateField);

        JPanel noteRow = new JPanel();
        JTextField noteField = new JTextField(10);
        noteRow.add(new JLabel("Add A Note (Optional): "));
        noteRow.add(noteField);

        JPanel forButtonPanel = addButton(amountField, sourceField, dateField, noteField);
        addContentToAddEntryPanel(amountRow, sourceRow, dateRow, noteRow, forButtonPanel);

        add(addEntryPanel, BorderLayout.CENTER);
    }

    // MODIFIES: this
    // EFFECTS: create an remove entry panel that is not visible until the remove
    // button is pressed
    private void createRemoveEntryPanel() {
        removeEntryPanel = new JPanel();
        removeEntryPanel.setLayout(new GridLayout(6, 1));
        removeEntryPanel.setVisible(false);

        JPanel askPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel instruction = new JLabel("Please enter information of the entry you wannt to delete");
        askPanel.add(instruction);

        JPanel amountRow = new JPanel();
        JTextField amountField = new JTextField(15);
        amountRow.add(new JLabel("Enter Amount of the Entry: "));
        amountRow.add(amountField);

        JPanel sourceRow = new JPanel();
        JTextField sourceField = new JTextField(1);
        sourceRow.add(new JLabel(
                "<html>Enter Income Source:<br>[1] REVENUE<br>[2] INVESTMENT<br>[3] CASHGIFT<br>[4] OTHER</html>"));
        sourceRow.add(sourceField);

        JPanel dateRow = new JPanel();
        JTextField dateField = new JTextField(8);
        dateRow.add(new JLabel("Enter Date (YYYYMMDD): "));
        dateRow.add(dateField);

        JPanel forButtonPanel = deleteButton(amountField, sourceField, dateField);
        addContentToRemoveEntryPanel(askPanel, amountRow, sourceRow, dateRow, forButtonPanel);

        add(removeEntryPanel, BorderLayout.CENTER);
    }

    // MODIFIES: this
    // EFFECTS: create an incomebook display panel that is not visible to the user
    // unless the
    // DISPLAY panel is clicked
    private void createbookDisplayPanel() {
        bookDisplayPanel = new JPanel(new BorderLayout());
        bookDisplayPanel.setVisible(false);

        JPanel inputPanel = new JPanel(new GridLayout(5, 1));
        inputPanel.setVisible(true);
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setVisible(false);

        JPanel startDatePanel = new JPanel();
        JTextField startDateField = new JTextField(10);
        startDatePanel.add(new JLabel("Enter Start Date (YYYY-MM-DD): "));
        startDatePanel.add(startDateField);

        JPanel endDatePanel = new JPanel();
        JTextField endDateField = new JTextField(10);
        endDatePanel.add(new JLabel("Enter End Date (YYYY-MM-DD): "));
        endDatePanel.add(endDateField);

        JPanel sourcePanel = new JPanel();
        JTextField sourceField = new JTextField(1);
        sourcePanel.add(new JLabel(askSource));
        sourcePanel.add(sourceField);

        JPanel forButtonPanel = addcheckIncomeBookButton(startDateField, endDateField, sourceField, inputPanel,
                tablePanel);
        addContentToBookDisplayPanel(inputPanel, tablePanel, startDatePanel, endDatePanel, sourcePanel, forButtonPanel);

        add(bookDisplayPanel, BorderLayout.CENTER);
    }

    // MODIFIES: this
    // EFFECTS: place the add and remove button to the button panel which once being
    // clicked will
    // trigger add/remove income features in the corresponding panel
    private void placeAddRemoveButton() {
        JButton addButton = new JButton(ButtonNames.ADD.getValue());
        JButton removeButton = new JButton(ButtonNames.Remove.getValue());

        JPanel buttonRow1 = formateButtonRow(addButton);
        buttonRow1.add(removeButton);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String buttonPressed = e.getActionCommand();
                if (buttonPressed.equals(ButtonNames.ADD.getValue())) {
                    addIncome();
                }
            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String buttonPressed = e.getActionCommand();
                if (buttonPressed.equals(ButtonNames.Remove.getValue())) {
                    removeIncome();
                }
            }
        });

        buttonPanel.add(buttonRow1);
    }

    // MODIFIES: this
    // EFFECTS: place the save and load button to the button panel which once being
    // clicked will
    // trigger the save and load incomebook feature in the corresponding panel
    private void placeSaveLoadButton() {
        JButton saveButton = new JButton(ButtonNames.SAVE.getValue());
        JButton loadButton = new JButton(ButtonNames.LOAD.getValue());

        JPanel buttonRow2 = formateButtonRow(saveButton);
        buttonRow2.add(loadButton);
        buttonRow2.setSize(WIDTH, HEIGHT / 8);

        addSaveButton(saveButton);
        addLoadButton(loadButton);

        buttonPanel.add(buttonRow2);
    }

    // MODIFIES: this
    // EFFECTS: place the display button to the button panel which once being
    // clicked will display the
    // income book based on user's criterion in the corresponding panel
    private void placeDisplayBookButton() {
        JPanel displayBlock = new JPanel();
        JButton displayButton = new JButton(ButtonNames.DISPLAY.getValue());
        displayBlock.add(formateButtonRow(displayButton));

        displayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String buttonPressed = e.getActionCommand();
                if (buttonPressed.equals(ButtonNames.DISPLAY.getValue())) {
                    displayIncomeBook();
                }
            }
        });

        buttonPanel.add(displayBlock);
    }

    // MODIFIES: this
    // EFFECTS: set the visbility of the button panel to false, and make the add
    // entry panel visible to user
    // to add income
    private void addIncome() {
        buttonPanel.setVisible(false);
        addEntryPanel.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: add the income entry based on the information in the textfields from
    // users
    private void finishAddIncomeEntry(String amount, String source, String date, String note) {
        double amountS = Integer.valueOf(amount);
        Incomesource is = FinancialManagementApp.selectIncomesource(Integer.valueOf(source));
        LocalDate dateS = FinancialManagementApp.converttoLocalDate(Integer.valueOf(date));

        Income i = new Income(amountS, is, dateS);
        i.attachIncomeNote(note);
        icbk.addIncome(i);
    }

    // MODIFIES: this
    // EFFECTS: set the visibility of the button panel to false, and make the remove
    // entry panel visible to user
    // to remove income
    private void removeIncome() {
        buttonPanel.setVisible(false);
        removeEntryPanel.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: remove an income entry based on the textfield information from the
    // users
    private void finishRemoveIncomeEntry(String amount, String source, String date) {
        double amountS = Integer.valueOf(amount);
        Incomesource is = FinancialManagementApp.selectIncomesource(Integer.valueOf(source));
        LocalDate dateS = FinancialManagementApp.converttoLocalDate(Integer.valueOf(date));

        Income i = new Income(amountS, is, dateS);
        icbk.removeIncome(i);
    }

    // EFFECTS: save the current incomebook entries
    private void saveIncomeBook() throws FileNotFoundException {
        jsonIncomeWriter.open();
        jsonIncomeWriter.write(icbk);
        jsonIncomeWriter.close();
    }

    // MODIFIES: this
    // EFFECTS: load the current incomebook entries
    public IncomeBook loadIncomeBook() throws IOException {
        this.icbk = jsonIncomeReader.read();
        return icbk;
    }

    // MODIFIES: this
    // EFFETCS: set the visibility of the button panel to false and make the
    // bookDisplay panel
    // visible to the user
    private void displayIncomeBook() {
        buttonPanel.setVisible(false);
        bookDisplayPanel.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: display the incomebook based on the criterion of the user
    private void checkIncomeBook(String startDate, String endDate, String source, JPanel inputPanel,
            JPanel tablePanel) {
        inputPanel.setVisible(false);

        LocalDate start = LocalDate.parse(startDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate end = LocalDate.parse(endDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        List<Income> filteredIncomes = filterIncomes(start, end, source);

        printBook(filteredIncomes, inputPanel, tablePanel);
        tablePanel.setVisible(true);

        bookDisplayPanel.revalidate();
        bookDisplayPanel.repaint();
    }

    // MODIFIES: this
    // EFFECTS: display the filtered incomebook to the panel
    private void printBook(List<Income> filterIncomes, JPanel inputPanel, JPanel tablePanel) {
        tablePanel.removeAll();

        if (!filterIncomes.isEmpty()) {
            String[] columns = { "Amount", "Date", "Source", "Note" };
            Object[][] data = new Object[filterIncomes.size()][4];

            for (int i = 0; i < filterIncomes.size(); i++) {
                Income ic = filterIncomes.get(i);
                data[i][0] = ic.getIncomeMoney();
                data[i][1] = ic.getIncomeDate();
                data[i][2] = ic.getIncomeSource();
                data[i][3] = ic.checkIncomeNote();
            }

            DefaultTableModel model = new DefaultTableModel(data, columns);
            JTable table = new JTable(model);
            JScrollPane scrollPane = new JScrollPane(table);

            tablePanel.add(scrollPane, BorderLayout.CENTER);

        }
        attachBackButtonForPrint(bookDisplayPanel, inputPanel, tablePanel);
    }

    // EFFECTS: create a back button that turns the current panel invisible and
    // turns the buttonPanel visible
    private void attachBackButtonForPrint(JPanel panel, JPanel input, JPanel table) {
        // JPanel forbuttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String buttonPressed = e.getActionCommand();
                if (buttonPressed.equals("Back")) {
                    panel.setVisible(false);
                    table.setVisible(false);
                    input.setVisible(true);
                    buttonPanel.setVisible(true);
                }
            }
        });
        table.add(backButton, BorderLayout.SOUTH);
    }

    // EFFECTS: create a back button that turns the current panel invisible and
    // turns the buttonPanel visible
    private void attachBackButton(JPanel panel) {
        JPanel forbuttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.setVisible(false);
                buttonPanel.setVisible(true);
            }
        });
        forbuttonPanel.add(backButton, BorderLayout.SOUTH);
        panel.add(forbuttonPanel);
    }

    // EFFECTS: creates the add button panel that adds a new income entry into the
    // book
    private JPanel addButton(JTextField amountField, JTextField sourceField, JTextField dateField,
            JTextField noteField) {
        JPanel forButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton entryFinishButton = new JButton("Add");
        entryFinishButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                finishAddIncomeEntry(amountField.getText(), sourceField.getText(), dateField.getText(),
                        noteField.getText());
                JOptionPane.showMessageDialog(null, "Income Successfully Added");
                amountField.setText("");
                sourceField.setText("");
                dateField.setText("");
                noteField.setText("");
                addEntryPanel.setVisible(false);
                buttonPanel.setVisible(true);
            }
        });
        forButtonPanel.add(entryFinishButton);
        return forButtonPanel;
    }

    // MODIFIES: this
    // EFFECTS: add the amount row, source row, date row, note row, and the button
    // row on the addEntryPanel
    private void addContentToAddEntryPanel(JPanel amountRow, JPanel sourceRow, JPanel dateRow, JPanel noteRow,
            JPanel forButtonPanel) {
        addEntryPanel.add(amountRow);
        addEntryPanel.add(sourceRow);
        addEntryPanel.add(dateRow);
        addEntryPanel.add(noteRow);
        addEntryPanel.add(forButtonPanel);
        attachBackButton(addEntryPanel);
    }

    // EFFECTS: creates the delete button panel that remove a new income entry into
    // the book
    private JPanel deleteButton(JTextField amountField, JTextField sourceField, JTextField dateField) {
        JPanel forButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton entryRemoveButton = new JButton("Delete");
        entryRemoveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                finishRemoveIncomeEntry(amountField.getText(), sourceField.getText(), dateField.getText());
                JOptionPane.showMessageDialog(null, "Income Successfully Removed");
                amountField.setText("");
                sourceField.setText("");
                dateField.setText("");
                removeEntryPanel.setVisible(false);
                buttonPanel.setVisible(true);
            }
        });
        forButtonPanel.add(entryRemoveButton);
        return forButtonPanel;
    }

    // MODIFIES: this
    // EFFECTS: add the ask panel, amount row, source row, date row, and the button
    // row on the removeEntryPanel
    private void addContentToRemoveEntryPanel(JPanel askPanel, JPanel amountRow, JPanel sourceRow, JPanel dateRow,
            JPanel forButtonPanel) {
        removeEntryPanel.add(askPanel);
        removeEntryPanel.add(amountRow);
        removeEntryPanel.add(sourceRow);
        removeEntryPanel.add(dateRow);
        removeEntryPanel.add(forButtonPanel);
        attachBackButton(removeEntryPanel);
    }

    // EFFECTS: creates the check incomebook button panel that allows the user to
    // check their incombook based on their needs
    private JPanel addcheckIncomeBookButton(JTextField startDateField, JTextField endDateField, JTextField sourceField,
            JPanel inputPanel, JPanel tablePanel) {
        JPanel forButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton checkIncomeBookButton = new JButton("Check");
        checkIncomeBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String startDate = startDateField.getText();
                String endDate = endDateField.getText();
                String source = sourceField.getText();
                startDateField.setText("");
                endDateField.setText("");
                sourceField.setText("");
                checkIncomeBook(startDate, endDate, source, inputPanel, tablePanel);
            }
        });
        forButtonPanel.add(checkIncomeBookButton);

        return forButtonPanel;
    }

    // MODIFIES: this
    // EFFECTS: add the startdate panel, enddate panel, source panel, and button
    // panel on the input panel, and place both input panel and table panel to the
    // bookdisplay panel
    private void addContentToBookDisplayPanel(JPanel inputPanel, JPanel tablePanel, JPanel startDatePanel,
            JPanel endDatePanel, JPanel sourcePanel, JPanel forButtonPanel) {
        inputPanel.add(startDatePanel);
        inputPanel.add(endDatePanel);
        inputPanel.add(sourcePanel);
        inputPanel.add(forButtonPanel);
        bookDisplayPanel.add(inputPanel, BorderLayout.CENTER);
        bookDisplayPanel.add(tablePanel, BorderLayout.SOUTH);
    }

    // EFFECTS: creates the save button that allows the user to save their current
    // incomebook into the file
    private void addSaveButton(JButton saveButton) {
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String buttonPressed = e.getActionCommand();
                if (buttonPressed.equals(ButtonNames.SAVE.getValue())) {
                    try {
                        saveIncomeBook();
                        JOptionPane.showMessageDialog(null, "Saved IncomeBook to" + JSON_STORE1);
                    } catch (FileNotFoundException e1) {
                        JOptionPane.showMessageDialog(null, "Unable to write to file: " + JSON_STORE1);
                    }
                }
            }
        });

    }

    // EFFECTS: creates the load button that allows the user to load their historial
    // incomebook from the file
    private void addLoadButton(JButton loadButton) {
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String buttonPressed = e.getActionCommand();
                if (buttonPressed.equals(ButtonNames.LOAD.getValue())) {
                    try {
                        loadIncomeBook();
                        JOptionPane.showMessageDialog(null, "Loaded IncomeBook from " + JSON_STORE1);
                    } catch (IOException e1) {
                        JOptionPane.showMessageDialog(null, "Unable to read from file: " + JSON_STORE1);
                    }
                }
            }
        });

    }

    // EFFECTS: filters the incomebook based on user's instrution of start date, end
    // date, and source for display purpose
    private List<Income> filterIncomes(LocalDate start, LocalDate end, String source) {
        ArrayList<Income> originaList = icbk.getIncomeRecord();
        List<Income> filteredIncomes = new ArrayList<>();
        if (Integer.valueOf(source) == 0) {
            filteredIncomes = icbk.filterIncomeRecordByTime(originaList, start, end);
        } else {
            Incomesource is = FinancialManagementApp.selectIncomesource(Integer.valueOf(source));
            ArrayList<Income> filteredSource = icbk.filterIncomeRecordBySource(originaList, is);
            filteredIncomes = icbk.filterIncomeRecordByTime(filteredSource, start, end);
        }

        return filteredIncomes;
    }

    // EFFECTS: returns the incomebook of this tab
    public IncomeBook getIncomeBook() {
        return icbk;
    }

    // EFFECTS: set a new incomebook of this tab
    public void setNewIncomeBook() {
        this.icbk = new IncomeBook();
    }
}
