package com.otus.spring.ui.impl.shell;

import com.otus.spring.model.TestReport;
import com.otus.spring.ui.api.MessageSourceHolder;
import com.otus.spring.ui.impl.commands.ChangeLocaleCommand;
import com.otus.spring.ui.impl.commands.PrintResultsCommand;
import com.otus.spring.ui.impl.commands.TestingCommand;
import com.otus.spring.ui.impl.commands.WelcomeCommand;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class TestingCommands {

    private final WelcomeCommand welcomeCommand;

    private final TestingCommand testingCommand;

    private final PrintResultsCommand printResultsCommand;

    private final MessageSourceHolder messageSourceHolder;

    private final ChangeLocaleCommand changeLocaleCommand;

    private String username;

    private TestReport report;

    public TestingCommands(WelcomeCommand welcomeCommand, MessageSourceHolder messageSourceHolder,
                           TestingCommand testingCommand, PrintResultsCommand printResultsCommand,
                           ChangeLocaleCommand changeLocaleCommand) {
        this.welcomeCommand = welcomeCommand;
        this.messageSourceHolder = messageSourceHolder;
        this.testingCommand = testingCommand;
        this.printResultsCommand = printResultsCommand;
        this.changeLocaleCommand = changeLocaleCommand;
    }

    @ShellMethod(value = "Login command", key = "login")
    public void login(@ShellOption(defaultValue = "anonymous") String username) {
        this.username = username;
        welcomeCommand.run(username);
    }

    @ShellMethod(value = "Take a test command", key = "take-test")
    @ShellMethodAvailability("isUserAllowedToStartTest")
    public void startTest() {
        testingCommand.run(username);
        report = testingCommand.getResult();
    }

    @ShellMethod(value = "Show results command", key = "show-results")
    @ShellMethodAvailability("isTestTaken")
    public void showResults() {
        printResultsCommand.run(report);
    }

    @ShellMethod(value = "Change language command", key = "change-language")
    public void changeLanguage() {
        changeLocaleCommand.run(null);
    }

    private Availability isUserAllowedToStartTest() {
        return username == null ? Availability.unavailable(messageSourceHolder.getMessage("take.test.failed"))
                                : Availability.available();
    }

    private Availability isTestTaken() {
        return report == null ? Availability.unavailable(messageSourceHolder.getMessage("show.results.failed"))
                              : Availability.available();
    }

}
