package ui.tabs;

public enum ButtonNames {
    IncomeBook("Go to IncomeBook"),
    ExpenseBook("Go to ExpenseBook"),
    GO_TO_REPORT("Current Report"),
    ADD("Add new record"),
    Remove("Remove record"),
    SAVE("Save record"),
    LOAD("Load record"),
    DISPLAY("Display book"),
    ANNUAL("Annual Balance"),
    MONTHLY("Monthly Balance");

    private final String name;

    ButtonNames(String name) {
        this.name = name;
    }

    // EFFECTS: returns name value of this button
    public String getValue() {
        return name;
    }

}

