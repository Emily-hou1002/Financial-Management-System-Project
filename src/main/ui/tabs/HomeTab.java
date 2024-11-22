package ui.tabs;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import ui.FinancialManagementApp;

// Represents a home tab that contains the gotoincomebook button and the gotoexpensebook button
public class HomeTab extends Tab {

    private static final String INIT_GREETING = "Welcome To Financial Management System. What would you like to do?";
    private JLabel greeting;

    // EFFECTS: constructs a home tab for console with buttons and a greeting
    public HomeTab(FinancialManagementApp controller) {
        super(controller);

        setLayout(new GridLayout(3, 1));

        placeGreeting();
        placeBookButtons();
        placeReportButton();
    }

    // MODIFIES: this
    // EFFECTS: creates greeting at top of console
    private void placeGreeting() {
        greeting = new JLabel(INIT_GREETING, JLabel.CENTER);
        greeting.setSize(WIDTH, HEIGHT / 3);
        this.add(greeting);
    }

    // MODIFIES: this
    // EFFECTS: construts Income and ExpenseBook buttons that switches tab on the
    // console
    private void placeBookButtons() {
        JButton b1 = new JButton(ButtonNames.IncomeBook.getValue());
        JButton b2 = new JButton(ButtonNames.ExpenseBook.getValue());

        JPanel buttonRow = formateButtonRow(b1);
        buttonRow.add(b2);

        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String buttonPressed = e.getActionCommand();
                if (buttonPressed.equals(ButtonNames.IncomeBook.getValue())) {
                    getController().getTabbedPane().setSelectedIndex(FinancialManagementApp.INCOMEBOOK_TAB_INDEX);
                }
            }
        });

        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String buttonPressed = e.getActionCommand();
                if (buttonPressed.equals(ButtonNames.ExpenseBook.getValue())) {
                    getController().getTabbedPane().setSelectedIndex(FinancialManagementApp.EXPENSEOOK_TAB_INDEX);
                }
            }
        });

        this.add(buttonRow);
    }

    // MODIFIES: this
    // EFFECTS: construts a button that switches to the report tab on the console
    private void placeReportButton() {
        JPanel reportBlock = new JPanel();
        JButton reportButton = new JButton(ButtonNames.GO_TO_REPORT.getValue());
        reportBlock.add(formateButtonRow(reportButton));

        reportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String buttonPressed = e.getActionCommand();
                if (buttonPressed.equals(ButtonNames.GO_TO_REPORT.getValue())) {
                    getController().getTabbedPane().setSelectedIndex(FinancialManagementApp.REPORT_TAB_INDEX);
                }
            }
        });

        this.add(reportBlock);
    }
}
