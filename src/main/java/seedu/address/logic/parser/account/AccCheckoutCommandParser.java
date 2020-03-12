package seedu.address.logic.parser.account;

import seedu.address.logic.commands.account.AccCheckoutCommand;
import seedu.address.logic.commands.expenditure.ExpFindCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.expenditure.InfoContainsKeywordsPredicate;

import java.util.Arrays;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

/**
 * Parse change account.
 */
public class AccCheckoutCommandParser implements Parser<AccCheckoutCommand> {
    @Override
    public AccCheckoutCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AccCheckoutCommand.MESSAGE_USAGE));
        }
        String[] accountName = trimmedArgs.split("\\s+");
        if (accountName.length != 1) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AccCheckoutCommand.MESSAGE_USAGE));
        }
        return new AccCheckoutCommand(accountName[0]);
    }
}
