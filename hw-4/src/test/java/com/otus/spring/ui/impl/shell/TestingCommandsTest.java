package com.otus.spring.ui.impl.shell;

import com.otus.spring.model.TestReport;
import com.otus.spring.ui.api.MessageSourceHolder;
import com.otus.spring.ui.impl.commands.ChangeLocaleCommand;
import com.otus.spring.ui.impl.commands.PrintResultsCommand;
import com.otus.spring.ui.impl.commands.TestingCommand;
import com.otus.spring.ui.impl.commands.WelcomeCommand;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.shell.CommandNotCurrentlyAvailable;
import org.springframework.shell.Shell;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest(properties = "spring.shell.interactive.enabled=false")
class TestingCommandsTest {

    @MockBean
    WelcomeCommand welcomeCommand;

    @MockBean
    TestingCommand testingCommand;

    @MockBean
    PrintResultsCommand printResultsCommand;

    @MockBean
    MessageSourceHolder messageSourceHolder;

    @MockBean
    ChangeLocaleCommand changeLocaleCommand;

    @Autowired
    Shell shell;

    private static final String WELCOME_COMMAND = "login";
    private static final String START_TESTING_COMMAND = "take-test";
    private static final String SHOW_RESULTS_COMMAND = "show-results";
    private static final String CHANGE_LANGUAGE_COMMAND = "change-language";

    @Test
    void login_test() {
        shell.evaluate(() -> WELCOME_COMMAND + " username");
        verify(welcomeCommand).run("username");
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    void startTestNotAvailable_test() {
        when(messageSourceHolder.getMessage(anyString())).thenReturn("unavailable");
        Object result = shell.evaluate(() -> START_TESTING_COMMAND);
        assertThat(result).isInstanceOf(CommandNotCurrentlyAvailable.class);
        verify(messageSourceHolder).getMessage("take.test.failed");
    }

    @Test
    void startTest_test() {
        shell.evaluate(() -> WELCOME_COMMAND + " username");
        shell.evaluate(() -> START_TESTING_COMMAND);
        verify(testingCommand).run("username");
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    void showResultsNotAvailable_test() {
        when(messageSourceHolder.getMessage(anyString())).thenReturn("unavailable");
        Object result = shell.evaluate(() -> SHOW_RESULTS_COMMAND);
        assertThat(result).isInstanceOf(CommandNotCurrentlyAvailable.class);
        verify(messageSourceHolder).getMessage("show.results.failed");
    }

    @Test
    void showResults_test() {
        var report = new TestReport(new TestReport.Credentials("username"));
        when(testingCommand.getResult()).thenReturn(report);

        shell.evaluate(() -> WELCOME_COMMAND + " username");
        shell.evaluate(() -> START_TESTING_COMMAND);
        shell.evaluate(() -> SHOW_RESULTS_COMMAND);

        verify(printResultsCommand).run(report);
    }

    @Test
    void changeLanguage_test() {
        shell.evaluate(() -> CHANGE_LANGUAGE_COMMAND);
        verify(changeLocaleCommand).run(null);
    }

}
