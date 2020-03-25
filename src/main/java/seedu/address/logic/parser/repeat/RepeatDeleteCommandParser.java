package seedu.address.logic.parser.repeat;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.repeat.RepeatDeleteCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new RepeatDeleteCommand object
 */
public class RepeatDeleteCommandParser implements Parser<RepeatDeleteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RepeatDeleteCommand
     * and returns a RepeatDeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public RepeatDeleteCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new RepeatDeleteCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                            seedu.address.logic.commands.repeat.RepeatDeleteCommand.MESSAGE_USAGE), pe);
        }
    }
}
