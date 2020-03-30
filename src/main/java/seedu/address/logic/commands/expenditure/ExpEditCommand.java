package seedu.address.logic.commands.expenditure;

import static java.util.Objects.requireNonNull;

import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INFO;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EXPENDITURES;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.expenditure.ExpLevelParser;
import seedu.address.model.Model;
import seedu.address.model.expenditure.Amount;
import seedu.address.model.expenditure.BaseExp;
import seedu.address.model.expenditure.Date;
import seedu.address.model.expenditure.Expenditure;
import seedu.address.model.expenditure.Info;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing expenditure in the address book.
 */
public class ExpEditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = ExpLevelParser.COMMAND_WORD + " " + COMMAND_WORD
            + ": Edits the details of the expenditure identified "
            + "by the index number used in the displayed list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_INFO + "INFO] "
            + "[" + PREFIX_AMOUNT + "AMOUNT] "
            + "[" + PREFIX_DATE + "DATE] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + ExpLevelParser.COMMAND_WORD + " " + COMMAND_WORD + " 1 "
            + PREFIX_AMOUNT + "4.3";

    public static final String MESSAGE_EDIT_EXPENDITURE_SUCCESS = "Edited Expenditure: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_EXPENDITURE = "This expenditure already exists in $AVE IT.";

    private final Index index;
    private final EditExpenditureDescriptor editExpenditureDescriptor;

    /**
     * @param index                     of the expenditure in the filtered expenditure list to edit
     * @param editExpenditureDescriptor details to edit the expenditure with
     */
    public ExpEditCommand(Index index, EditExpenditureDescriptor editExpenditureDescriptor) {
        requireNonNull(index);
        requireNonNull(editExpenditureDescriptor);

        this.index = index;
        this.editExpenditureDescriptor = new EditExpenditureDescriptor(editExpenditureDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<BaseExp> lastShownList = model.getFilteredBaseExpList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EXPENDITURE_DISPLAYED_INDEX);
        }

        BaseExp baseExp = lastShownList.get(index.getZeroBased());
        if (!(baseExp instanceof Expenditure)) {
            throw new CommandException(String.format(Messages.MESSAGE_INVALID_TYPE_AT_INDEX, Expenditure.class.getSimpleName()));
        }
        Expenditure expenditureToEdit = (Expenditure) baseExp;
        Expenditure editedExpenditure = createEditedExpenditure(expenditureToEdit, editExpenditureDescriptor);

        if (!expenditureToEdit.equals(editedExpenditure) && model.hasExpenditure(editedExpenditure)) {
            throw new CommandException(MESSAGE_DUPLICATE_EXPENDITURE);
        }

        model.setExpenditure(expenditureToEdit, editedExpenditure);
        return new CommandResult(String.format(MESSAGE_EDIT_EXPENDITURE_SUCCESS, editedExpenditure));
    }

    /**
     * Creates and returns a {@code Expenditure} with the details of {@code expenditureToEdit}
     * edited with {@code editExpenditureDescriptor}.
     */
    private static Expenditure createEditedExpenditure(Expenditure expenditureToEdit,
                                                       EditExpenditureDescriptor editExpenditureDescriptor) {
        assert expenditureToEdit != null;

        Info updatedInfo = editExpenditureDescriptor.getInfo().orElse(expenditureToEdit.getInfo());
        Amount updatedAmount = editExpenditureDescriptor.getAmount().orElse(expenditureToEdit.getAmount());
        Date updatedDate = editExpenditureDescriptor.getDate().orElse(expenditureToEdit.getDate());
        Tag updatedTags = editExpenditureDescriptor.getTag().orElse(expenditureToEdit.getTag());
        return new Expenditure(updatedInfo, updatedAmount, updatedDate, updatedTags);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ExpEditCommand)) {
            return false;
        }

        // state check
        ExpEditCommand e = (ExpEditCommand) other;
        return index.equals(e.index)
                && editExpenditureDescriptor.equals(e.editExpenditureDescriptor);
    }

    /**
     * Stores the details to edit the expenditure with. Each non-empty field value will replace the
     * corresponding field value of the expenditure.
     */
    public static class EditExpenditureDescriptor {
        private Info info;
        private Amount amount;
        private Date date;
        private Tag tag;

        public EditExpenditureDescriptor() {
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditExpenditureDescriptor(EditExpenditureDescriptor toCopy) {
            setInfo(toCopy.info);
            setAmount(toCopy.amount);
            setDate(toCopy.date);
            setTag(toCopy.tag);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(info, amount, date, tag);
        }

        public void setInfo(Info info) {
            this.info = info;
        }

        public Optional<Info> getInfo() {
            return Optional.ofNullable(info);
        }

        public void setAmount(Amount amount) {
            this.amount = amount;
        }

        public Optional<Amount> getAmount() {
            return Optional.ofNullable(amount);
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public Optional<Date> getDate() {
            return Optional.ofNullable(date);
        }

        public Optional<Tag> getTag() {
            return Optional.ofNullable(tag);
        }

        public void setTag(Tag tag) {
            this.tag = tag;
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditExpenditureDescriptor)) {
                return false;
            }

            // state check
            EditExpenditureDescriptor e = (EditExpenditureDescriptor) other;

            return getInfo().equals(e.getInfo())
                    && getAmount().equals(e.getAmount())
                    && getDate().equals(e.getDate())
                    && getTag().equals(e.getTag());

        }
    }
}
