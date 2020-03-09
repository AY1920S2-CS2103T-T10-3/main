package seedu.address.logic.commands;

import org.junit.jupiter.api.Test;
import seedu.address.model.AccountManager;
import seedu.address.model.Model;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.HelpCommand.SHOWING_HELP_MESSAGE;

public class HelpCommandTest {
    private Model model = new AccountManager();
    private Model expectedModel = new AccountManager();

    @Test
    public void execute_help_success() {
        CommandResult expectedCommandResult = new CommandResult(SHOWING_HELP_MESSAGE, true, false);
        assertCommandSuccess(new HelpCommand(), model, expectedCommandResult, expectedModel);
    }
}
