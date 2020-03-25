package seedu.address.logic.commands.account;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Rename account.
 */
public class AccRenameCommand extends Command {

    public static final String COMMAND_WORD = "rename";

    public static final String MESSAGE_SUCCESS = "The account's name has changed from :";

    public static final String INVALID_NAME_INPUT = "Please key in the right input";

    private final String newName;

    private final String oldName;

    public AccRenameCommand(String oldName, String newName) {
        this.newName = newName;
        this.oldName = oldName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        try {
            model.renameAccount(this.oldName, this.newName);
            return new CommandResult(MESSAGE_SUCCESS + " " + oldName + " to " + newName);
        } catch (Exception e) {
            throw new CommandException(Messages.MESSAGE_INVALID_ACCOUNT_NAME);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AccRenameCommand // instanceof handles nulls
                && newName.equals(((AccRenameCommand) other).newName)
                && oldName.equals(((AccRenameCommand) other).oldName));
    }
}
