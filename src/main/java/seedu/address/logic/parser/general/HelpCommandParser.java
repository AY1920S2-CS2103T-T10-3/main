package seedu.address.logic.parser.general;

import seedu.address.logic.commands.general.ExitCommand;
import seedu.address.logic.commands.general.FindCommand;
import seedu.address.logic.commands.general.GoCommand;
import seedu.address.logic.commands.general.HelpCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.account.AccLevelParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.expenditure.ExpLevelParser;
import seedu.address.logic.parser.repeat.RepeatLevelParser;
import seedu.address.logic.parser.report.ReportLevelParser;

/**
 * Parse help.
 */
public class HelpCommandParser implements Parser<HelpCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the HelpCommand
     * and returns an HelpCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public HelpCommand parse(String args) throws ParseException {

        switch (args.trim()) {

        case ExpLevelParser.COMMAND_WORD:
            return new HelpCommand(ExpLevelParser.MESSAGE_USAGE);

        case ReportLevelParser.COMMAND_WORD:
            return new HelpCommand(ReportLevelParser.MESSAGE_USAGE);

        case AccLevelParser.COMMAND_WORD:
            return new HelpCommand(AccLevelParser.MESSAGE_USAGE);

        case GoCommand.COMMAND_WORD:
            return new HelpCommand(GoCommand.MESSAGE_USAGE);

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case ExitCommand.COMMAND_WORD:
            return new HelpCommand(ExitCommand.MESSAGE_USAGE);

        case FindCommand.COMMAND_WORD:
            return new HelpCommand(FindCommand.MESSAGE_USAGE);

        case RepeatLevelParser.COMMAND_WORD:
            return new HelpCommand(RepeatLevelParser.MESSAGE_USAGE);

        default:
            return new HelpCommand(args.trim(), true);
        }
    }
}
