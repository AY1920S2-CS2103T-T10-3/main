package seedu.address.logic.commands.account;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Change to another account.
 */
public class AccCheckoutCommand extends Command {

    public static final String COMMAND_WORD = "checkout";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Checks out the account with the specified name.\n"
            + "Parameters: ACCOUNT_NAME (must be one word containing any characters)\n"
            + "Example: " + COMMAND_WORD + " default";
    public static final String MESSAGE_CHECKOUT_SUCCESS = "Successfully checked out account: %1$s";

    private final String accountName;

    public AccCheckoutCommand(String accountName) {
        this.accountName = accountName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (!model.updateActiveAccount(accountName)){
            throw new CommandException(Messages.MESSAGE_INVALID_ACCOUNT_NAME);
        }
        return new CommandResult(String.format(MESSAGE_CHECKOUT_SUCCESS, accountName));
    }
}
