package seedu.saveit.logic.commands.expenditure;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.saveit.commons.core.Messages;
import seedu.saveit.commons.core.index.Index;
import seedu.saveit.logic.commands.Command;
import seedu.saveit.logic.commands.CommandResult;
import seedu.saveit.logic.commands.exceptions.CommandException;
import seedu.saveit.logic.parser.expenditure.ExpLevelParser;
import seedu.saveit.model.Model;
import seedu.saveit.model.MonthlySpendingCalculator;
import seedu.saveit.model.expenditure.BaseExp;
import seedu.saveit.model.expenditure.Expenditure;

/**
 * Deletes a expenditure identified using it's displayed index from the address book.
 */
public class ExpDeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = ExpLevelParser.COMMAND_WORD + " " + COMMAND_WORD
            + ": Deletes the expenditure identified by the index number used in the displayed list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + ExpLevelParser.COMMAND_WORD + " " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_EXPENDITURE_SUCCESS = "Deleted Expenditure: %1$s";

    private final Index targetIndex;

    public ExpDeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<BaseExp> lastShownList = model.getFilteredBaseExpList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EXPENDITURE_DISPLAYED_INDEX);
        }

        BaseExp baseExp = lastShownList.get(targetIndex.getZeroBased());
        if (!(baseExp instanceof Expenditure)) {
            throw new CommandException(String.format(Messages.MESSAGE_INVALID_TYPE_AT_INDEX,
                    Expenditure.class.getSimpleName()));
        }
        Expenditure expenditureToDelete = (Expenditure) baseExp;
        model.deleteExpenditure(expenditureToDelete);
        MonthlySpendingCalculator monthlyCalculator = model.getMonthlySpending();
        return new CommandResult(String.format(MESSAGE_DELETE_EXPENDITURE_SUCCESS, expenditureToDelete),
                monthlyCalculator.getBudget(), monthlyCalculator.getTotalSpending());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ExpDeleteCommand // instanceof handles nulls
                && targetIndex.equals(((ExpDeleteCommand) other).targetIndex)); // state check
    }
}
