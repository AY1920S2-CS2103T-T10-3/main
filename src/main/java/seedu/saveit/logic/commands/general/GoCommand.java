package seedu.saveit.logic.commands.general;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.YearMonth;

import seedu.saveit.logic.commands.Command;
import seedu.saveit.logic.commands.CommandResult;
import seedu.saveit.logic.commands.exceptions.CommandException;

import seedu.saveit.model.Model;
import seedu.saveit.model.MonthlySpendingCalculator;


/**
 * Switch to a date.
 */
public class GoCommand extends Command {

    public static final String COMMAND_WORD = "go";

    public static final String MESSAGE_SUCCESS = "We are at : %1$s";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Go to a specific date\n"
            + "the specified date format YYYY-MM-DD.\n"
            + "Example: " + COMMAND_WORD + " 2001-09-11";


    private final LocalDate toDate;
    private final boolean fromUi;

    public GoCommand(LocalDate toDate, boolean fromUi) {
        this.toDate = toDate;
        this.fromUi = fromUi;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public boolean getFromUi() {
        return fromUi;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateActiveDate(toDate);
        YearMonth target = YearMonth.of(toDate.getYear(), toDate.getMonthValue());
        MonthlySpendingCalculator monthlyCalculator = model.getMonthlySpending(target);
        if (fromUi) {
            return new CommandResult(String.format(MESSAGE_SUCCESS, toDate),
                    monthlyCalculator.getBudget(), monthlyCalculator.getTotalSpending());

        } else {
            return new CommandResult(String.format(MESSAGE_SUCCESS, toDate), toDate,
                    monthlyCalculator.getBudget(), monthlyCalculator.getTotalSpending());

        }
    }

    @Override
    public boolean equals(Object obj) {
        requireNonNull(obj);
        if (obj == this) {
            return true;
        } else {
            return ((GoCommand) obj).getToDate().equals(this.toDate)
                    && (((GoCommand) obj).getFromUi() == this.fromUi);
        }
    }
}
