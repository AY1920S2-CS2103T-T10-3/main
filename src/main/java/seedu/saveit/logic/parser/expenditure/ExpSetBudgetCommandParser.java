package seedu.saveit.logic.parser.expenditure;

import static seedu.saveit.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.saveit.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.saveit.logic.parser.CliSyntax.PREFIX_YEARMONTH;

import java.time.YearMonth;
import java.util.stream.Stream;

import seedu.saveit.logic.commands.expenditure.ExpSetBudgetCommand;
import seedu.saveit.logic.parser.ArgumentMultimap;
import seedu.saveit.logic.parser.ArgumentTokenizer;
import seedu.saveit.logic.parser.Parser;
import seedu.saveit.logic.parser.ParserUtil;
import seedu.saveit.logic.parser.Prefix;
import seedu.saveit.logic.parser.exceptions.ParseException;
import seedu.saveit.model.expenditure.Amount;

/**
 * Parse set budget.
 */
public class ExpSetBudgetCommandParser implements Parser<ExpSetBudgetCommand> {
    public ExpSetBudgetCommandParser() {
    }

    @Override
    public ExpSetBudgetCommand parse(String userInput) throws ParseException {

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(userInput, PREFIX_AMOUNT, PREFIX_YEARMONTH);

        if (!arePrefixesPresent(argMultimap, PREFIX_AMOUNT) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExpSetBudgetCommand.MESSAGE_FAIL));
        }

        Amount amount = ParserUtil.parseAmount(argMultimap.getValue(PREFIX_AMOUNT).get());
        YearMonth yearMonth = ParserUtil.parseYearMonth(argMultimap.getValue(PREFIX_YEARMONTH)
                .orElseGet(() -> new StringBuilder()
                        .toString()));
        return new ExpSetBudgetCommand(yearMonth, amount);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
