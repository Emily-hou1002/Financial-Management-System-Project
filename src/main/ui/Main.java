package ui;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {
        SplashScreen splash = new SplashScreen();
        splash.showSplash(4000);
        
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            System.out.println("Splash screen interrupted");
        }
        try {
            new FinancialManagementApp();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run application: file not found");
        }
            

    }
}

