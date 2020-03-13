package seedu.address.logic.commands.account;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;

/**
 * Clears the account.
 */
public class AccClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "account has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        // model.setAccount(new Account());
        // return new CommandResult(MESSAGE_SUCCESS);
        return new CommandResult("NOT IMPLEMENTED YET");
    }
}
