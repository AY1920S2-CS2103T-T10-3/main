package seedu.saveit.logic.parser.expenditure;

import static seedu.saveit.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.saveit.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.saveit.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.saveit.logic.parser.CliSyntax.PREFIX_INFO;
import static seedu.saveit.logic.parser.CliSyntax.PREFIX_TAG;

import java.time.LocalDate;
import java.util.stream.Stream;

import seedu.saveit.logic.commands.expenditure.ExpAddCommand;
import seedu.saveit.logic.parser.ArgumentMultimap;
import seedu.saveit.logic.parser.ArgumentTokenizer;
import seedu.saveit.logic.parser.Parser;
import seedu.saveit.logic.parser.ParserUtil;
import seedu.saveit.logic.parser.Prefix;
import seedu.saveit.logic.parser.exceptions.ParseException;
import seedu.saveit.model.expenditure.Amount;
import seedu.saveit.model.expenditure.Date;
import seedu.saveit.model.expenditure.Expenditure;
import seedu.saveit.model.expenditure.Info;
import seedu.saveit.model.tag.Tag;

/**
 * Parses input arguments and creates a new ExpAddCommand object
 */
public class ExpAddCommandParser implements Parser<ExpAddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ExpAddCommand
     * and returns an ExpAddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ExpAddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_INFO, PREFIX_AMOUNT, PREFIX_DATE, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_INFO, PREFIX_AMOUNT)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExpAddCommand.MESSAGE_USAGE));
        }


        Info info = ParserUtil.parseInfo(argMultimap.getValue(PREFIX_INFO).get());
        Amount amount = ParserUtil.parseAmount(argMultimap.getValue(PREFIX_AMOUNT).get());
        Date date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).orElseGet(() -> LocalDate.now().toString()));
        Tag tag = ParserUtil.parseTag(argMultimap.getValue(PREFIX_TAG).orElseGet(() -> "Others"));

        Expenditure expenditure = new Expenditure(info, amount, date, tag);

        boolean getActiveDate = !arePrefixesPresent(argMultimap, PREFIX_DATE);

        return new ExpAddCommand(expenditure, getActiveDate);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
