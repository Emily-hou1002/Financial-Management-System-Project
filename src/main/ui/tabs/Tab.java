package ui.tabs;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import model.ExpenseBook;
import model.IncomeBook;
import ui.FinancialManagementApp;

// Represents a general tab used to represent different books in the system
public abstract class Tab extends JPanel {

    private final FinancialManagementApp controller;
    protected IncomeBook icbk;
    protected ExpenseBook epbk;

    // REQUIRES: FinancialManagementApp controller holds this tab
    // EFFECTS: creates a general tab that set the financial management app as the
    // controller
    public Tab(FinancialManagementApp controller) {
        this.controller = controller;
    }

    // EFFECTS: creates and returns row with button included
    public JPanel formateButtonRow(JButton b) {
        JPanel p = new JPanel();
        p.setLayout(new FlowLayout());
        p.add(b);

        return p;
    }

    // EFFECTS: returns the FinancialManagementApp controller for this tab
    public FinancialManagementApp getController() {
        return controller;
    }

}
