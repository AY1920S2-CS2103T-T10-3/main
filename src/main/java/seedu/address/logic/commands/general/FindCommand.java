package seedu.address.logic.commands.general;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.parser.expenditure.ExpLevelParser;
import seedu.address.model.Model;
import seedu.address.model.expenditure.BaseExp;

/**
 * Finds and lists all expenditures in address book whose info contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = ExpLevelParser.COMMAND_WORD + " " + COMMAND_WORD
            + ": Finds all expenditures and repeats which contain any of "
            + "the specified keyword (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameter: KEYWORD \n"
            + "Example: " + ExpLevelParser.COMMAND_WORD + " " + COMMAND_WORD + " alice";

    private final Predicate<BaseExp> predicate;

    public FindCommand(Predicate<BaseExp> predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredBaseExpList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_EXPENDITURES_LISTED_OVERVIEW,
                        model.getFilteredBaseExpList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && predicate.equals(((FindCommand) other).predicate)); // state check
    }
}