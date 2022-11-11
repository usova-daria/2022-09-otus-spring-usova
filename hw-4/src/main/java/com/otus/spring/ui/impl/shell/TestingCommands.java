package com.otus.spring.ui.impl.shell;

import com.otus.spring.ui.impl.commands.WelcomeCommand;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class TestingCommands {

    private String username;

    private final WelcomeCommand welcomeCommand;

    public TestingCommands(WelcomeCommand welcomeCommand) {
        this.welcomeCommand = welcomeCommand;
    }

    @ShellMethod(value = "Login command", key = "login")
    public String login(@ShellOption(defaultValue = "anonymous") String username) {
        this.username = username;
        welcomeCommand.run(username);
        return "";
    }

}
