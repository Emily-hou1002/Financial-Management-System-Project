package ui;

import model.Expense;
import model.ExpenseBook;
import model.Income;
import model.IncomeBook;
import persistence.JsonExpenseBookReader;
import persistence.JsonExpenseBookWriter;
import persistence.JsonIncomeBookReader;
import persistence.JsonIncomeBookWriter;
import ui.tabs.ExpenseBookTab;
import ui.tabs.HomeTab;
import ui.tabs.IncomeBookTab;
import ui.tabs.ReportTab;
import model.Expense.ExpenseUsage;
import model.Income.Incomesource;

import javax.swing.*;

import java.util.Scanner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;

import java.io.FileNotFoundException;
import java.io.IOException;

// Financial Management application
public class FinancialManagementApp extends JFrame {
    private static final String JSON_STORE1 = "./data/IncomeBookStorage.json";
    private static final String JSON_STORE2 = "./data/ExpenseBookStorage.json";

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private JTabbedPane sidebar;
    public static final int HOME_TAB_INDEX = 0;
    public static final int INCOMEBOOK_TAB_INDEX = 1;
    public static final int EXPENSEOOK_TAB_INDEX = 2;
    public static final int REPORT_TAB_INDEX = 3;

    private IncomeBook icbk;
    private ExpenseBook epbk;
    private Scanner input;
    private JsonIncomeBookWriter jsonIncomeWriter;
    private JsonIncomeBookReader jsonIncomeReader;
    private JsonExpenseBookWriter jsonExpenseWriter;
    private JsonExpenseBookReader jsonExpenseReader;

    // EFFECTS: runs the finanial management application
    public FinancialManagementApp() throws FileNotFoundException {
        super("Financial Management System");
        icbk = new IncomeBook();
        epbk = new ExpenseBook();
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation((WindowConstants.EXIT_ON_CLOSE));
        sidebar = new JTabbedPane();
        sidebar.setTabPlacement(JTabbedPane.LEFT);
        try {
            loadTabs();
        } catch (IOException e) {
            System.out.println("Cannot load it");
        }
        add(sidebar);
        setVisible(true);

        input = new Scanner(System.in);
        jsonIncomeWriter = new JsonIncomeBookWriter(JSON_STORE1);
        jsonIncomeReader = new JsonIncomeBookReader(JSON_STORE1);
        jsonExpenseWriter = new JsonExpenseBookWriter(JSON_STORE2);
        jsonExpenseReader = new JsonExpenseBookReader(JSON_STORE2);
        runFMA();
    }

    // MODIFIES: this
    // EFFECTS: adds home tab, incomebook tab, expensebook tab, and report tab to
    // this UI
    private void loadTabs() throws IOException {
        JPanel homeTab = new HomeTab(this);
        JPanel incomebookTab = new IncomeBookTab(this, icbk);
        JPanel expensebookTab = new ExpenseBookTab(this, epbk);
        IncomeBook updatedIncomeBook = ((IncomeBookTab) incomebookTab).loadIncomeBook();
        ExpenseBook updatedExpenseBook = ((ExpenseBookTab) expensebookTab).loadExpenseBook();
        JPanel reportTab = new ReportTab(this, updatedIncomeBook, updatedExpenseBook);

        sidebar.add(homeTab, HOME_TAB_INDEX);
        sidebar.setTitleAt(HOME_TAB_INDEX, "Home");
        sidebar.add(incomebookTab, INCOMEBOOK_TAB_INDEX);
        sidebar.setTitleAt(INCOMEBOOK_TAB_INDEX, "IncomeBook");
        sidebar.add(expensebookTab, EXPENSEOOK_TAB_INDEX);
        sidebar.setTitleAt(EXPENSEOOK_TAB_INDEX, "ExpenseBook");
        sidebar.add(reportTab, REPORT_TAB_INDEX);
        sidebar.setTitleAt(REPORT_TAB_INDEX, "Report");
    }

    // EFFECTS: returns sidebar of this UI
    public JTabbedPane getTabbedPane() {
        return sidebar;
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runFMA() {
        boolean continueGoing = true;
        String userCommand = null;

        init();

        while (continueGoing) {
            displayMenu();
            userCommand = input.next();
            userCommand = userCommand.toLowerCase();

            if (userCommand.equals("q")) {
                continueGoing = false;
            } else {
                processUserCommand(userCommand);
            }

        }

        System.out.println("Have a nice day!Bye~");
    }

    // MODIFIES: this
    // EFFETCS: initializes the IncomeBook and ExpenseBook
    private void init() {
        input = new Scanner(System.in);
        input.useDelimiter("\r?\n|\r");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processUserCommand(String command) {
        if (command.equals("ic")) {
            loadIncomeEntry();
        } else if (command.equals("id")) {
            deleteIncomeEntry();
        } else if (command.equals("ec")) {
            loadExpenseEntry();
        } else if (command.equals("ed")) {
            deleteExpenseEntry();
        } else if (command.equals("b")) {
            setBudget();
        } else if (command.equals("ib")) {
            checkIncomeBook();
        } else if (command.equals("eb")) {
            checkExpenseBook();
        } else if (command.equals("nb")) {
            calculateBalance();
        } else {
            processUserSaveLoadCommand(command);
        }

    }

    // MODIFIES: this
    // EFFETCS: proesses user ommand to store or load historial daat
    private void processUserSaveLoadCommand(String command) {
        if (command.equals("si")) {
            saveIncomeBook();
        } else if (command.equals("li")) {
            loadIncomeBook();
        } else if (command.equals("se")) {
            saveExpenseBook();
        } else if (command.equals("le")) {
            loadExpenseBook();
        }
    }

    // EFFECTS: displays menu of feature options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\tic -> create Income Entry");
        System.out.println("\tid -> delete Income Entry");
        System.out.println("\tec -> create Expense Entry");
        System.out.println("\ted -> delete Expense Entry");
        System.out.println("\tb -> set your budget");
        System.out.println("\tib -> check your IncomeBook");
        System.out.println("\teb -> check your ExpenseBook");
        System.out.println("\tsi -> save IncomeBook to file");
        System.out.println("\tli -> load IncomeBook from file");
        System.out.println("\tse -> save ExpenseBook to file");
        System.out.println("\tle -> load ExpenseBook from file");
        System.out.println("\tnb -> calculate your Net Balance");
        System.out.println("\tq -> quit the app");

    }

    // MODIFIES: this
    // EFFECTS: load an income entry into the IncomeBook
    private void loadIncomeEntry() {
        System.out.print("Enter your Income amount: $");
        double money = input.nextDouble();

        if (money >= 0.0) {
            System.out.println("Enter the corresponding number for the source of this income: ");
            System.out.println("[1]REVENUE; [2]INVESTMENT; [3]CAHSGIFT; [4]OTHER");
            int s = input.nextInt();
            Incomesource is = selectIncomesource(s);

            System.out.println("Enter the date for this income in YYYYMMDD: ");
            int d = input.nextInt();
            LocalDate date = converttoLocalDate(d);

            Income i = new Income(money, is, date);

            System.out.println("Do you want to add a note? yes/no: ");
            String answer = input.next();
            if (answer.equals("yes")) {
                System.out.println("Please type in your note: ");
                String note = input.next();
                i.attachIncomeNote(note);
            }

            icbk.addIncome(i);
        } else {
            System.out.println("Income amount cannot be negative...\n");
        }
    }

    // MODIFIES: this
    // EFFECTS: remove an income entry from the IncomeBook
    private void deleteIncomeEntry() {
        ArrayList<Income> ik = icbk.getIncomeRecord();

        if (!ik.isEmpty()) {
            System.out.print("What is the source of the income entry? Please type the number");
            System.out.println("[1]REVENUE; [2]INVESTMENT; [3]CAHSGIFT; [4]OTHER");
            int s = input.nextInt();

            System.out.print("What is the amount of the income entry?: $");
            double m = input.nextDouble();
            System.out.print("What is the date of the income entry? Please enter it in YYYYMMDD: ");
            int d = input.nextInt();

            findIncomeEntry(s, m, d);

        } else {
            System.out.print("The IncomeBook is already empty");
        }
    }

    // EFFECTS: find the specific income entry in the IncomeBook based on income
    // detail input by the user
    private void findIncomeEntry(int s, double m, int d) {
        ArrayList<Income> ik = icbk.getIncomeRecord();

        Iterator<Income> iterator = ik.iterator();
        boolean entryFound = false;

        while (iterator.hasNext()) {
            Income i = iterator.next();
            int is = matchUserIncomeOption(i.getIncomeSource());
            int dt = convertDateToInt(i.getIncomeDate());

            if (is == s && i.getIncomeMoney() == m && dt == d) {
                iterator.remove();
                icbk.removeIncome(i);
                entryFound = true;
                System.out.println("Income entry successfully removed");
                break;
            }
        }
        if (!entryFound) {
            System.out.println("Income entry not found");
        }
    }

    // MODIFIES: this
    // EFFECTS: load an expense entry into the ExpenseBook
    private void loadExpenseEntry() {
        System.out.print("Enter your Expense amount: $");
        double money = input.nextDouble();
        if (money >= 0.0) {
            System.out.println("Enter the corresponding number for the usage of this expense: ");
            System.out.println("[1]GROCERY; [2]HOUSING; [3]SHOPPING; [4]TRANSPORTATION [5] ENTERTAINMENT [6]OTHER");
            int u = input.nextInt();
            ExpenseUsage eu = selectExpenseusage(u);

            System.out.println("Enter the date for this expense in YYYYMMDD: ");
            int d = input.nextInt();
            LocalDate date = converttoLocalDate(d);

            Expense e = new Expense(money, eu, date);

            System.out.println("Do you want to add a note? yes/no: ");
            String answer = input.next();
            if (answer.equals("yes")) {
                System.out.println("Please type in your note: ");
                String note = input.next();
                e.attachExpenseNote(note);
            }
            epbk.addExpense(e);
        } else {
            System.out.println("Expense amount cannot be negative...\n");
        }

    }

    // MODIFIES: this
    // EFFECTS: remove an expense entry from the ExpenseBook
    private void deleteExpenseEntry() {
        ArrayList<Expense> ek = epbk.getExpenseRecord();

        if (!ek.isEmpty()) {
            System.out.print("What is the source of the expense entry? Please type the number: ");
            System.out.println("[1]GROCERY; [2]HOUSING; [3]SHOPPING; [4]TRANSPORTATION [5] ENTERTAINMENT [6]OTHER");
            int u = input.nextInt();

            System.out.print("What is the amount of the expense entry?: $");
            double m = input.nextInt();
            System.out.print("What is the date of the expense entry? Please enter it in YYYYMMDD: ");
            int d = input.nextInt();

            findExpenseEntry(u, m, d);

        } else {
            System.out.print("The ExpenseBook is already empty");
        }

    }

    // EFFECTS: find the specific expense entry in the ExpenseBook based on expense
    // detail input by the user
    private void findExpenseEntry(int u, double m, int d) {
        ArrayList<Expense> ek = epbk.getExpenseRecord();

        Iterator<Expense> iterator = ek.iterator();
        boolean entryFound = false;

        while (iterator.hasNext()) {
            Expense e = iterator.next();
            int eu = matchUserExpenseOption(e.getExpenseUse());
            int dt = convertDateToInt(e.getExpenseDate());

            if (eu == u && e.getExpenseMoney() == m && dt == d) {
                iterator.remove();
                epbk.removeExpense(e);
                entryFound = true;
                System.out.println("Expense entry successfully removed");
                break;
            }

        }
        if (!entryFound) {
            System.out.println("Income entry not found");
        }
    }

    // EFFECTS: set an annual/ monthly budget for either the total all expense usage
    // or a specific type of usage
    private void setBudget() {
        System.out.print("Do you want to set an annual or monthly expense budget? enter annual/monthly: ");
        String answer = input.next();

        if (answer.equals("annual")) {
            setAnnualBudget();

        } else {
            setMonthlyBudget();
        }

    }

    // EFFECTS: set annual expense budget on either the total expense of a specific
    // usage type
    private void setAnnualBudget() {
        System.out.print("Do you want to set for the [1] total expense or [2] a specific usage type?");
        System.out.print("Please enter the corresponding number: ");
        int n = input.nextInt();
        System.out.print("For which year do you want to set your total expense budget on?");
        int y = input.nextInt();
        LocalDate start = LocalDate.of(y, 1, 1);
        LocalDate end = LocalDate.of(y, 12, 31);
        System.out.print("How much budget would you like to set?: $");
        double m = input.nextDouble();
        System.out.print("Budget Successfully Set!");

        if (n == 1) {
            trackTotalBudgetByTime(start, end, m);
        } else {
            System.out.print("For which expense usage do you want to set your budget on?");
            System.out.println("[1]GROCERY; [2]HOUSING; [3]SHOPPING; [4]TRANSPORTATION [5] ENTERTAINMENT [6]OTHER");
            int u = input.nextInt();
            ExpenseUsage eu = selectExpenseusage(u);
            trackUsageBudgetByTime(start, end, m, eu);
        }
    }

    // EFFECTS: set monthly expense budget on either the total expense of a specific
    // usage type
    private void setMonthlyBudget() {
        System.out.print("Do you want to set for the [1] total expense or [2] a specific usage type?");
        System.out.print("Please enter the corresponding number: ");
        int n = input.nextInt();
        System.out.print("For which month do you want to set your total expense budget on?");
        int mm = input.nextInt();
        System.out.print("Which year is this month in?");
        int y = input.nextInt();
        LocalDate start = LocalDate.of(y, mm, 1);
        LocalDate end = LocalDate.of(y, mm, 31);
        System.out.print("How much budget would you like to set?: $");
        double m = input.nextDouble();

        if (n == 1) {
            System.out.print("Budget Successfully Set!");
            trackTotalBudgetByTime(start, end, m);
        } else {
            System.out.print("For which expense usage do you want to set your budget on?");
            System.out.println("[1]GROCERY; [2]HOUSING; [3]SHOPPING; [4]TRANSPORTATION [5] ENTERTAINMENT [6]OTHER");
            int u = input.nextInt();
            ExpenseUsage eu = selectExpenseusage(u);
            System.out.print("Budget Successfully Set!");
            trackUsageBudgetByTime(start, end, m, eu);
        }
    }

    // EFFETCS: track the progress of the total expense within a specific time
    // period towards the set budget m
    private void trackTotalBudgetByTime(LocalDate start, LocalDate end, double m) {
        ArrayList<Expense> ek = epbk.getExpenseRecord();
        double te = epbk.calculateTotalExpenseByTime(ek, start, end);
        double d = m - te;
        double de = te - m;

        if (te < m) {
            System.out.print("current expense is $" + Double.toString(te) + ". $" + Double.toString(d) + "left");
        } else if (te == m) {
            System.out.print("Reach Budget! Manage your expense wisely!");
        } else {
            System.out.print("EXCEED YOUR BUDGET BY $" + Double.toString(de));
        }

    }

    // EFFETCS: track the progress of a specific usage type of expense (eu) within a
    // specific time period towards the set budget m
    private void trackUsageBudgetByTime(LocalDate start, LocalDate end, double m, ExpenseUsage eu) {
        ArrayList<Expense> ek = epbk.getExpenseRecord();
        ArrayList<Expense> fek = epbk.filterExpenseRecordByTime(ek, start, end);
        double ue = epbk.calulateTotalExpenseByUsage(fek, eu);
        double d = m - ue;
        double de = ue - m;

        if (ue < m) {
            System.out.print("current expense is $" + Double.toString(ue) + ". $" + Double.toString(d) + "left");
        } else if (ue == m) {
            System.out.print("Reach Budget! Manage your expense wisely!");
        } else {
            System.out.print("EXCEED YOUR BUDGET BY $" + Double.toString(de));
        }
    }

    // EFFECTS: display the incomebook based on the time period set by the user
    private void checkIncomeBook() {
        ArrayList<Income> ik = icbk.getIncomeRecord();
        System.out.print(
                "Only monthly or annual IncomeBook Record is provided. Please carefully type your date entries! ");
        System.out.print("Enter the start date with which the entires in the incomebook will start in YYYYMMDD: ");
        int d1 = input.nextInt();
        LocalDate startDate = converttoLocalDate(d1);

        System.out.print("Enter the end date with which the entires in the incomebook will end in YYYYMMDD: ");
        int d2 = input.nextInt();
        LocalDate endDate = converttoLocalDate(d2);

        ArrayList<Income> tib = icbk.filterIncomeRecordByTime(ik, startDate, endDate);

        printIncomeBook(tib);
    }

    // EFFECTS: display the expensebook based on the time period set by the user
    private void checkExpenseBook() {
        ArrayList<Expense> ek = epbk.getExpenseRecord();
        System.out.print(
                "Only monthly or annual ExpenseBook Record is provided. Please carefully type your date entries! ");
        System.out.print(
                "Please enter the start date with which the entires in the expensebook will start in YYYYMMDD: ");
        int d1 = input.nextInt();
        LocalDate startDate = converttoLocalDate(d1);

        System.out.print("Please enter the end date with which the entires in the expensebook will end in YYYYMMDD: ");
        int d2 = input.nextInt();
        LocalDate endDate = converttoLocalDate(d2);

        ArrayList<Expense> teb = epbk.filterExpenseRecordByTime(ek, startDate, endDate);

        printExpenseBook(teb);
    }

    // EFFECTS: calculate anuual/monthly balance (income - expense)
    private void calculateBalance() {

        System.out.print("Do you want to calculate monthly or annual balance?: ");
        System.out.print("Please enter either monthly or annual: ");
        String answer = input.next();

        if (answer.equals("annual")) {
            calculateAnnualBalance();

        } else {
            calculateMonthlyBalance();
        }
    }

    // EFFECTS: calculate the annual balance for a specific year set by the user
    private void calculateAnnualBalance() {
        ArrayList<Income> ik = icbk.getIncomeRecord();
        ArrayList<Expense> ek = epbk.getExpenseRecord();

        System.out.print("Please enter the year(YYYY) that you want to work on: ");
        int year1 = input.nextInt();
        double ai1 = icbk.calculateTotalIncomeByTime(ik, LocalDate.of(year1, 1, 1),
                LocalDate.of(year1, 12, 31));
        double ae1 = epbk.calculateTotalExpenseByTime(ek, LocalDate.of(year1, 1, 1),
                LocalDate.of(year1, 12, 31));
        double balance = ai1 - ae1;
        System.out.println("$" + balance);
    }

    // EFFECTS: calculate the monthly balance for a specific year in a year set by
    // the user
    private void calculateMonthlyBalance() {
        System.out.print("Please enter month(M) that you want to work on: ");
        int month = input.nextInt();
        System.out.print("Whih year's (YYYY) monthly balance do you want to work on?: ");
        int year2 = input.nextInt();

        double ai2 = processIncomeDateBasedOnMonth(month, year2);
        double ae2 = processExpenseDateBasedOnMonth(month, year2);

        double balance = ai2 - ae2;
        System.out.println("$" + balance);
    }

    // EFFECTS: calculate the total monthly income amount based on specific month
    private double processIncomeDateBasedOnMonth(int month, int year) {
        ArrayList<Income> ik = icbk.getIncomeRecord();

        double ai2 = 0.0;
        if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
            ai2 = icbk.calculateTotalIncomeByTime(ik, LocalDate.of(year, month, 1),
                    LocalDate.of(year, month, 31));
        } else if (month == 4 || month == 6 || month == 9 || month == 11) {
            ai2 = icbk.calculateTotalIncomeByTime(ik, LocalDate.of(year, month, 1),
                    LocalDate.of(year, month, 30));
        } else {
            ai2 = icbk.calculateTotalIncomeByTime(ik, LocalDate.of(year, month, 1),
                    LocalDate.of(year, month, 28));
        }
        return ai2;
    }

    // EFFECTS: calculate the total monthly expense amount based on specific month
    private double processExpenseDateBasedOnMonth(int month, int year) {
        ArrayList<Expense> ek = epbk.getExpenseRecord();

        double ae2 = 0.0;
        if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
            ae2 = epbk.calculateTotalExpenseByTime(ek, LocalDate.of(year, month, 1),
                    LocalDate.of(year, month, 31));
        } else if (month == 4 || month == 6 || month == 9 || month == 11) {
            ae2 = epbk.calculateTotalExpenseByTime(ek, LocalDate.of(year, month, 1),
                    LocalDate.of(year, month, 30));
        } else {
            ae2 = epbk.calculateTotalExpenseByTime(ek, LocalDate.of(year, month, 1),
                    LocalDate.of(year, month, 28));
        }
        return ae2;
    }

    // REQUIRES: s must be one of 1, 2, 3, 4
    // EFFECTS: return the correct incomesoure based on user's choice of number
    public static Incomesource selectIncomesource(int s) {
        Incomesource is = Incomesource.REVENUE;
        if (s == 1) {
            is = Incomesource.REVENUE;
        } else if (s == 2) {
            is = Incomesource.INVESTMENT;
        } else if (s == 3) {
            is = Incomesource.CASHGIFT;
        } else {
            is = Incomesource.OTHER;
        }

        return is;
    }

    // REQUIRES: u must be one of 1, 2, 3, 4, 5, 6
    // EFFECTS: return the correct expense usage based on user's choice of number
    public static ExpenseUsage selectExpenseusage(int u) {
        ExpenseUsage eu = ExpenseUsage.GROCERY;
        if (u == 1) {
            eu = ExpenseUsage.GROCERY;
        } else if (u == 2) {
            eu = ExpenseUsage.HOUSING;
        } else if (u == 3) {
            eu = ExpenseUsage.SHOPPING;
        } else if (u == 4) {
            eu = ExpenseUsage.TRANSPORTATION;
        } else if (u == 5) {
            eu = ExpenseUsage.ENTERTAINMENT;
        } else {
            eu = ExpenseUsage.OTHER;
        }

        return eu;
    }

    // EFFECTS: covert the user input date digit into LocalDate parameter
    public static LocalDate converttoLocalDate(int d) {
        String dt = Integer.toString(d);
        String yr = dt.substring(0, 4);
        String mt = dt.substring(4, 6);
        String da = dt.substring(6, 8);
        LocalDate date = LocalDate.of(Integer.parseInt(yr), Integer.parseInt(mt), Integer.parseInt(da));

        return date;
    }

    // EFFECTS: print the selected incomebook to the screen
    private void printIncomeBook(ArrayList<Income> ib) {
        for (Income i : ib) {
            System.out.println("$" + i.getIncomeMoney());
            System.out.println(i.getIncomeSource());
            System.out.println((i.getIncomeDate()).toString());
            System.out.println(i.checkIncomeNote());
            System.out.println("-------");
        }
    }

    // EFFECTS: print the selected expensebook to the screen
    private void printExpenseBook(ArrayList<Expense> eb) {
        for (Expense e : eb) {
            System.out.println("$" + e.getExpenseMoney());
            System.out.println(e.getExpenseUse());
            System.out.println((e.getExpenseDate()).toString());
            System.out.println(e.checkExpenseNote());
            System.out.println("-------");
        }
    }

    // EFFECTS: convert the Incomesoure of certain Income entry into one of 1, 2, 3,
    // 4 that match user option
    private int matchUserIncomeOption(Incomesource ic) {
        int is = 0;

        if (ic == Incomesource.REVENUE) {
            is = 1;
        } else if (ic == Incomesource.INVESTMENT) {
            is = 2;
        } else if (ic == Incomesource.CASHGIFT) {
            is = 3;
        } else {
            is = 4;
        }
        return is;
    }

    // EFFECTS: convert the Expense usage of certain Expense entry into one of 1, 2,
    // 3,4, 5, 6 that match user option
    private int matchUserExpenseOption(ExpenseUsage u) {
        int eu = 0;

        if (u == ExpenseUsage.GROCERY) {
            eu = 1;
        } else if (u == ExpenseUsage.HOUSING) {
            eu = 2;
        } else if (u == ExpenseUsage.SHOPPING) {
            eu = 3;
        } else if (u == ExpenseUsage.TRANSPORTATION) {
            eu = 4;
        } else if (u == ExpenseUsage.ENTERTAINMENT) {
            eu = 5;
        } else {
            eu = 6;
        }
        return eu;
    }

    // EFFECTS: convert the LocalDate of each Income and Expense entry into integer
    // digital format that matches user input
    private int convertDateToInt(LocalDate d) {
        int yr = d.getYear();
        int mt = d.getMonthValue();
        int de = d.getDayOfMonth();

        int dt = (yr * 10000) + (mt * 100) + de;
        return dt;
    }

    // EFFECTS: saves the incomebook to file
    private void saveIncomeBook() {
        try {
            jsonIncomeWriter.open();
            jsonIncomeWriter.write(icbk);
            jsonIncomeWriter.close();
            System.out.println("Saved IncomeBook to " + JSON_STORE1);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE1);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads the incomebook from file
    private void loadIncomeBook() {
        try {
            icbk = jsonIncomeReader.read();
            System.out.println("Loaded IncomeBook from " + JSON_STORE1);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE1);
        }
    }

    // EFFECTS: saves the expensebook to file
    private void saveExpenseBook() {
        try {
            jsonExpenseWriter.open();
            jsonExpenseWriter.write(epbk);
            jsonExpenseWriter.close();
            System.out.println("Saved ExpensedBook to " + JSON_STORE2);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE2);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads the expensebook from file
    private void loadExpenseBook() {
        try {
            epbk = jsonExpenseReader.read();
            System.out.println("Loaded ExpenseBook from " + JSON_STORE2);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE2);
        }
    }

}
