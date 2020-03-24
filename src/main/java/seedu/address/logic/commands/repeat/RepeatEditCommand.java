package seedu.address.logic.commands.repeat;

import static java.util.Objects.requireNonNull;

import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INFO;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.expenditure.ExpLevelParser;
import seedu.address.model.Model;
import seedu.address.model.expenditure.Amount;
import seedu.address.model.expenditure.Date;
import seedu.address.model.expenditure.Info;
import seedu.address.model.expenditure.Repeat;
import seedu.address.model.expenditure.Repeat.Period;
import seedu.address.model.tag.Tag;

import java.util.Optional;


/**
 * Edit repeat object.
 * TODO: NEED MODIFY
 */
public class RepeatEditCommand extends Command {
    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = ExpLevelParser.COMMAND_WORD + " " + COMMAND_WORD
            + ": Edits the details of the repeat identified "
            + "by the index number used in the displayed repeat list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_INFO + "INFO] "
            + "[" + PREFIX_AMOUNT + "AMOUNT] "
            + "[" + PREFIX_DATE + "DATE] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + ExpLevelParser.COMMAND_WORD + " " + COMMAND_WORD + " 1 "
            + PREFIX_AMOUNT + "4.3";

    public static final String MESSAGE_EDIT_REPEAT_SUCCESS = "Edited Repeat Expenditure: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";


    private final Index index;
    private final RepeatEditCommand.EditRepeatDescriptor editRepeatDescriptor;

    public RepeatEditCommand(Index index, RepeatEditCommand.EditRepeatDescriptor editRepeatDescriptor) {
        requireNonNull(index);
        requireNonNull(editRepeatDescriptor);

        this.index = index;
        this.editRepeatDescriptor = new RepeatEditCommand.EditRepeatDescriptor(editRepeatDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        //TODO
        return null;
    }

    /**
     * Creates and returns a {@code Expenditure} with the details of {@code expenditureToEdit}
     * edited with {@code editExpenditureDescriptor}.
     */
    private static Repeat createEditedRepeat(Repeat repeatToEdit,
                                                       RepeatEditCommand.EditRepeatDescriptor editRepeatDescriptor) {
        assert repeatToEdit != null;

        Info updatedInfo = editRepeatDescriptor.getInfo().orElse(repeatToEdit.getInfo());
        Amount updatedAmount = editRepeatDescriptor.getAmount().orElse(repeatToEdit.getAmount());
        Date updatedStartDate = editRepeatDescriptor.getStartDate().orElse(repeatToEdit.getStartDate());
        Date updatedEndDate = editRepeatDescriptor.getEndDate().orElse(repeatToEdit.getEndDate());
        Tag updatedTags = editRepeatDescriptor.getTag().orElse(repeatToEdit.getTag());
        Period updatedPeriod = editRepeatDescriptor.getPeriod().orElse(repeatToEdit.getPeriod());
        return new Repeat(updatedInfo, updatedAmount, updatedStartDate, updatedEndDate, updatedTags, updatedPeriod.toString());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RepeatEditCommand)) {
            return false;
        }

        // state check
        RepeatEditCommand e = (RepeatEditCommand) other;
        return index.equals(e.index)
                && editRepeatDescriptor.equals(e.editRepeatDescriptor);
    }

    /**
     * Stores the details to edit the Repeat with. Each non-empty field value will replace the
     * corresponding field value of the repeat.
     */
    public static class EditRepeatDescriptor {
        private Info info;
        private Amount amount;
        private Date startDate;
        private Date endDate;
        private Tag tag;
        private Period period;

        public EditRepeatDescriptor() {
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditRepeatDescriptor(RepeatEditCommand.EditRepeatDescriptor toCopy) {
            setInfo(toCopy.info);
            setAmount(toCopy.amount);
            setStartDate(toCopy.startDate);
            setEndDate(toCopy.endDate);
            setTag(toCopy.tag);
            setPeriod(toCopy.period);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(info, amount, startDate, endDate, period, tag);
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

        public void setStartDate(Date date) {
            this.startDate = date;
        }

        public void setEndDate(Date date) {
            this.endDate = date;
        }

        public void setPeriod(Period period) {
            this.period = period;
        }

        public Optional<Period> getPeriod() {
            return Optional.ofNullable(period);
        }

        public Optional<Date> getStartDate() {
            return Optional.ofNullable(startDate);
        }

        public Optional<Date> getEndDate() {
            return Optional.ofNullable(endDate);
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
            if (!(other instanceof RepeatEditCommand.EditRepeatDescriptor)) {
                return false;
            }

            // state check
            RepeatEditCommand.EditRepeatDescriptor e = (RepeatEditCommand.EditRepeatDescriptor) other;

            return getInfo().equals(e.getInfo())
                    && getAmount().equals(e.getAmount())
                    && getStartDate().equals(e.getStartDate())
                    && getEndDate().equals(e.getEndDate())
                    && getPeriod().equals(e.getPeriod())
                    && getTag().equals(e.getTag());

        }
    }

}
