package ui.tabs;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import model.ExpenseBook;
import model.Expense;
import model.Expense.ExpenseUsage;
import persistence.JsonExpenseBookReader;
import persistence.JsonExpenseBookWriter;
import ui.FinancialManagementApp;

// Represents an expensebook Tab that allows users to add/remove expense, save/load expensebook,
// and display the expensebook within specific time frame
public class ExpenseBookTab extends Tab {

    private static final String EXPENSEBOOK_TEXT = "What do you want to do with your ExpenseBook?";
    private static final String JSON_STORE2 = "./data/ExpenseBookStorage.json";
    private static final String usage = "<html>"
            + "Enter Expense Usage:<br>"
            + "[1] GROCERY;<br>"
            + "[2] HOUSING;<br>"
            + "[3] SHOPPING;<br>"
            + "[4] TRANSPORTATION"
            + "</html>";
    private static final String usage2 = "<html>"
            + "Enter Usage of Expense You Want To Check:<br>"
            + "[0] All;<br>"
            + "[1] GROCERY;<br>"
            + "[2] HOUSING;<br>"
            + "[3] SHOPPING;<br>"
            + "[4] TRANSPORTATION"
            + "</html>";
    private JPanel buttonPanel;
    private JPanel addEntryPanel;
    private JPanel removeEntryPanel;
    private JPanel bookDisplayPanel;

    private JsonExpenseBookWriter jsonExpenseWriter;
    private JsonExpenseBookReader jsonExpenseReader;

    // REQUIRES: FinanacialManagementApp controller that holds this tab
    // EFFECTS: creates expensebook tab that add/remove expense, save/load
    // expensebook,
    // display the expensebook within specific time frame, and set budget

    public ExpenseBookTab(FinancialManagementApp controller, ExpenseBook expenseBook) {
        super(controller);
        this.epbk = expenseBook;
        jsonExpenseWriter = new JsonExpenseBookWriter(JSON_STORE2);
        jsonExpenseReader = new JsonExpenseBookReader(JSON_STORE2);

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 1));
        buttonPanel.setVisible(true);
        JLabel text = new JLabel(EXPENSEBOOK_TEXT, JLabel.CENTER);
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

        JPanel usageRow = new JPanel();
        JTextField usageField = new JTextField(1);
        usageRow.add(new JLabel(usage));
        usageRow.add(usageField);

        JPanel dateRow = new JPanel();
        JTextField dateField = new JTextField(8);
        dateRow.add(new JLabel("Enter Date(YYYYMMDD): "));
        dateRow.add(dateField);

        JPanel noteRow = new JPanel();
        JTextField noteField = new JTextField(10);
        noteRow.add(new JLabel("Add A Note(Optional): "));
        noteRow.add(noteField);

        JPanel forButtonPanel = addButton(amountField, usageField, dateField, noteField);
        addContentToAddEntryPanel(amountRow, usageRow, dateRow, noteRow, forButtonPanel);

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

        JPanel usageRow = new JPanel();
        JTextField usageField = new JTextField(1);
        usageRow.add(new JLabel(usage));
        usageRow.add(usageField);

        JPanel dateRow = new JPanel();
        JTextField dateField = new JTextField(8);
        dateRow.add(new JLabel("Enter Date of Entry: YYYYMMDD"));
        dateRow.add(dateField);

        JPanel forButtonPanel = deleteButton(amountField, usageField, dateField);
        addContentToRemoveEntryPanel(askPanel, amountRow, usageRow, dateRow, forButtonPanel);

        add(removeEntryPanel, BorderLayout.CENTER);
    }

    // MODIFIES: this
    // EFFECTS: create an expensebook display panel that is not visible to the user
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

        JPanel usagePanel = new JPanel();
        JTextField usageField = new JTextField(1);
        usagePanel.add(new JLabel(usage2));
        usagePanel.add(usageField);

        JPanel forButtonPanel = addCheckExpenseBookButton(startDateField, endDateField, usageField, inputPanel,
                tablePanel);
        addContentToBookDisplayPanel(inputPanel, tablePanel, startDatePanel, endDatePanel, usagePanel, forButtonPanel);

        add(bookDisplayPanel, BorderLayout.CENTER);
    }

    // MODIFIES: this
    // EFFECTS: place the add and remove button to the button panel which once being
    // clicked will
    // trigger add/remove expense features in the corresponding panel
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
                    addExpense();
                }
            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String buttonPressed = e.getActionCommand();
                if (buttonPressed.equals(ButtonNames.Remove.getValue())) {
                    removeExpense();
                }
            }
        });

        buttonPanel.add(buttonRow1);
    }

    // MODIFIES: this
    // EFFECTS: place the save and load button to the button panel which once being
    // clicked will
    // trigger the save and load expensebook feature in the corresponding panel
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
    // expense book based on user's criterion in the corresponding panel
    private void placeDisplayBookButton() {
        JPanel displayBlock = new JPanel();
        JButton displayButton = new JButton(ButtonNames.DISPLAY.getValue());
        displayBlock.add(formateButtonRow(displayButton));
        displayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String buttonPressed = e.getActionCommand();
                if (buttonPressed.equals(ButtonNames.DISPLAY.getValue())) {
                    displayExpenseBook();
                }
            }
        });

        buttonPanel.add(displayBlock);
    }

    // MODIFIES: this
    // EFFECTS: set the visbility of the button panel to false, and make the add
    // entry panel visible to user
    // to add expense
    private void addExpense() {
        buttonPanel.setVisible(false);
        addEntryPanel.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: add the expense entry based on the information in the textfields
    // from
    // users
    private void finishAddExpenseEntry(String amount, String usage, String date, String note) {
        double amountS = Integer.valueOf(amount);
        ExpenseUsage eu = FinancialManagementApp.selectExpenseusage(Integer.valueOf(usage));
        LocalDate dateS = FinancialManagementApp.converttoLocalDate(Integer.valueOf(date));

        Expense e = new Expense(amountS, eu, dateS);
        e.attachExpenseNote(note);
        epbk.addExpense(e);
    }

    // MODIFIES: this
    // EFFECTS: set the visibility of the button panel to false, and make the remove
    // entry panel visible to user
    // to remove expense
    private void removeExpense() {
        buttonPanel.setVisible(false);
        removeEntryPanel.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: remove an expense entry based on the textfield information from the
    // users
    private void finishRemoveExpenseEntry(String amount, String usage, String date) {
        double amountS = Integer.valueOf(amount);
        ExpenseUsage eu = FinancialManagementApp.selectExpenseusage(Integer.valueOf(usage));
        LocalDate dateS = FinancialManagementApp.converttoLocalDate(Integer.valueOf(date));

        Expense e = new Expense(amountS, eu, dateS);
        epbk.removeExpense(e);
    }

    // EFFECTS: save the current expensebook entries
    private void saveExpenseBook() throws FileNotFoundException {
        jsonExpenseWriter.open();
        jsonExpenseWriter.write(epbk);
        jsonExpenseWriter.close();
    }

    // MODIFIES: this
    // EFFECTS: load the current incomebook entries
    public ExpenseBook loadExpenseBook() throws IOException {
        this.epbk = jsonExpenseReader.read();
        return epbk;
    }

    // MODIFIES: this
    // EFFETCS: set the visibility of the button panel to false and make the
    // bookDisplay panel
    // visible to the user
    private void displayExpenseBook() {
        buttonPanel.setVisible(false);
        bookDisplayPanel.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: display the expensebook based on the criterion of the user
    private void checkExpenseBook(String startDate, String endDate, String usage, JPanel inputPanel,
            JPanel tablePanel) {
        inputPanel.setVisible(false);

        LocalDate start = LocalDate.parse(startDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate end = LocalDate.parse(endDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        List<Expense> filteredExpenses = filterExpenses(start, end, usage);
        printBook(filteredExpenses, inputPanel, tablePanel);
        tablePanel.setVisible(true);

        bookDisplayPanel.revalidate();
        bookDisplayPanel.repaint();
    }

    // MODIFIES: this
    // EFFECTS: display the filtered expensebook to the panel
    private void printBook(List<Expense> filterExpenses, JPanel inputPanel, JPanel tablePanel) {
        tablePanel.removeAll();

        if (!filterExpenses.isEmpty()) {
            String[] columns = { "Amount", "Date", "Usage", "Note" };
            Object[][] data = new Object[filterExpenses.size()][4];

            for (int i = 0; i < filterExpenses.size(); i++) {
                Expense eu = filterExpenses.get(i);
                data[i][0] = eu.getExpenseMoney();
                data[i][1] = eu.getExpenseDate();
                data[i][2] = eu.getExpenseUse();
                data[i][3] = eu.checkExpenseNote();
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

    // EFFECTS: creates the add button panel that adds a new expense entry into the
    // book
    private JPanel addButton(JTextField amountField, JTextField usageField, JTextField dateField,
            JTextField noteField) {
        JPanel forButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton entryFinishButton = new JButton("Add");
        entryFinishButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                finishAddExpenseEntry(amountField.getText(), usageField.getText(), dateField.getText(),
                        noteField.getText());
                JOptionPane.showMessageDialog(null, "Expense Successfully Added");
                amountField.setText("");
                usageField.setText("");
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
    // EFFECTS: add the amount row, usage row, date row, note row, and the button
    // row on the addEntryPanel
    private void addContentToAddEntryPanel(JPanel amountRow, JPanel usageRow, JPanel dateRow, JPanel noteRow,
            JPanel forButtonPanel) {
        addEntryPanel.add(amountRow);
        addEntryPanel.add(usageRow);
        addEntryPanel.add(dateRow);
        addEntryPanel.add(noteRow);
        addEntryPanel.add(forButtonPanel);
        attachBackButton(addEntryPanel);
    }

    // EFFECTS: creates the delete button panel that remove a new expense entry into
    // the book
    private JPanel deleteButton(JTextField amountField, JTextField usageField, JTextField dateField) {
        JPanel forButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton entryRemoveButton = new JButton("Delete");
        entryRemoveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                finishRemoveExpenseEntry(amountField.getText(), usageField.getText(), dateField.getText());
                JOptionPane.showMessageDialog(null, "Expense Successfully Removed");
                amountField.setText("");
                usageField.setText("");
                dateField.setText("");
                removeEntryPanel.setVisible(false);
                buttonPanel.setVisible(true);
            }
        });
        forButtonPanel.add(entryRemoveButton);
        return forButtonPanel;
    }

    // MODIFIES: this
    // EFFECTS: add the ask panel, amount row, usage row, date row, and the button
    // row on the removeEntryPanel
    private void addContentToRemoveEntryPanel(JPanel askPanel, JPanel amountRow, JPanel usageRow, JPanel dateRow,
            JPanel forButtonPanel) {
        removeEntryPanel.add(askPanel);
        removeEntryPanel.add(amountRow);
        removeEntryPanel.add(usageRow);
        removeEntryPanel.add(dateRow);
        removeEntryPanel.add(forButtonPanel);
        attachBackButton(removeEntryPanel);
    }

    // EFFECTS: creates the check expensebook button panel that allows the user to
    // check their expensebook based on their needs
    private JPanel addCheckExpenseBookButton(JTextField startDateField, JTextField endDateField, JTextField usageField,
            JPanel inputPanel, JPanel tablePanel) {
        JPanel forButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton checkExpenseBookButton = new JButton("Check");
        checkExpenseBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String startDate = startDateField.getText();
                String endDate = endDateField.getText();
                String usage = usageField.getText();
                startDateField.setText("");
                endDateField.setText("");
                usageField.setText("");
                checkExpenseBook(startDate, endDate, usage, inputPanel, tablePanel);
            }
        });
        forButtonPanel.add(checkExpenseBookButton);
        return forButtonPanel;
    }

    // MODIFIES: this
    // EFFECTS: add the startdate panel, enddate panel, source panel, and button
    // panel on the input panel, and place both input panel and table panel to the
    // bookdisplay panel
    private void addContentToBookDisplayPanel(JPanel inputPanel, JPanel tablePanel, JPanel startDatePanel,
            JPanel endDatePanel, JPanel usagePanel, JPanel forButtonPanel) {
        inputPanel.add(startDatePanel);
        inputPanel.add(endDatePanel);
        inputPanel.add(usagePanel);
        inputPanel.add(forButtonPanel);
        bookDisplayPanel.add(inputPanel, BorderLayout.CENTER);
        bookDisplayPanel.add(tablePanel, BorderLayout.SOUTH);

    }

    // EFFECTS: creates the save button that allows the user to save their current
    // expensebook into the file
    private void addSaveButton(JButton saveButton) {
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String buttonPressed = e.getActionCommand();
                if (buttonPressed.equals(ButtonNames.SAVE.getValue())) {
                    try {
                        saveExpenseBook();
                        JOptionPane.showMessageDialog(null, "Saved IncomeBook to" + JSON_STORE2);
                    } catch (FileNotFoundException e1) {
                        JOptionPane.showMessageDialog(null, "Unable to write to file: " + JSON_STORE2);
                    }
                }
            }
        });
    }

    // EFFECTS: creates the load button that allows the user to load their historial
    // expensebook from the file
    private void addLoadButton(JButton loadButton) {
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String buttonPressed = e.getActionCommand();
                if (buttonPressed.equals(ButtonNames.LOAD.getValue())) {
                    try {
                        loadExpenseBook();
                        JOptionPane.showMessageDialog(null, "Loaded IncomeBook from " + JSON_STORE2);
                    } catch (IOException e1) {
                        JOptionPane.showMessageDialog(null, "Unable to read from file: " + JSON_STORE2);
                    }
                }
            }
        });
    }

    // EFFECTS: filters the expensebook based on user's instrution of start date,
    // end date, and usage for display purpose
    private List<Expense> filterExpenses(LocalDate start, LocalDate end, String usage) {
        List<Expense> originaList = epbk.getExpenseRecord();
        List<Expense> filteredExpenses = new ArrayList<>();

        for (Expense e : originaList) {
            LocalDate entryDate = e.getExpenseDate();
            if (Integer.valueOf(usage) == 0) {
                if ((entryDate.isEqual(start) || entryDate.isAfter((start)))
                        && (entryDate.isEqual(end) || entryDate.isBefore(end))) {
                    filteredExpenses.add(e);
                }
            } else {
                ExpenseUsage eu = FinancialManagementApp.selectExpenseusage(Integer.valueOf(usage));
                if (e.getExpenseUse() == eu) {
                    if ((entryDate.isEqual(start) || entryDate.isAfter((start)))
                            && (entryDate.isEqual(end) || entryDate.isBefore(end))) {
                        filteredExpenses.add(e);
                    }
                }
            }
        }
        return filteredExpenses;
    }

    // EFFECTS: returns the expensebook of this tab
    public ExpenseBook getExpenseBook() {
        return epbk;
    }

    // EFFECTS: set a new expensebook of this tab
    public void setNewExpenseBook() {
        this.epbk = new ExpenseBook();
    }
}
