package com.otus.spring.ui.impl.shell;

import com.otus.spring.model.TestReport;
import com.otus.spring.ui.api.MessageSourceHolder;
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

    private String username;

    private TestReport report;

    public TestingCommands(WelcomeCommand welcomeCommand, MessageSourceHolder messageSourceHolder,
                           TestingCommand testingCommand, PrintResultsCommand printResultsCommand) {
        this.welcomeCommand = welcomeCommand;
        this.messageSourceHolder = messageSourceHolder;
        this.testingCommand = testingCommand;
        this.printResultsCommand = printResultsCommand;
    }

    @ShellMethod(value = "Login command", key = "login")
    public void login(@ShellOption(defaultValue = "anonymous") String username) {
        this.username = username;
        welcomeCommand.run(username);
    }

    @ShellMethod(value = "Start test command", key = "start-test")
    @ShellMethodAvailability("isUserAllowedToStartTest")
    public void startTest() {
        testingCommand.run(username);
        report = testingCommand.getResult();
    }

    @ShellMethod(value = "Show results", key = "show-results")
    @ShellMethodAvailability("isTestTaken")
    public void showResults() {
        printResultsCommand.run(report);
    }

    private Availability isUserAllowedToStartTest() {
        return username == null ? Availability.unavailable(messageSourceHolder.getMessage("login"))
                                : Availability.available();
    }

    private Availability isTestTaken() {
        return report == null ? Availability.unavailable(messageSourceHolder.getMessage("show.results.failed"))
                              : Availability.available();
    }

}
