package seedu.address.logic.commands.expenditure;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalAccounts.getTypicalAccountList;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalExpenditures.getTypicalAccount;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.expenditure.Expenditure;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code ExpDeleteCommand}.
 */
public class ExpDeleteCommandTest {

    private Model model = new ModelManager(getTypicalAccountList(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Expenditure expenditureToDelete = model.getFilteredExpenditureList().get(INDEX_FIRST_PERSON.getZeroBased());
        ExpDeleteCommand expDeleteCommand = new ExpDeleteCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(ExpDeleteCommand.MESSAGE_DELETE_EXPENDITURE_SUCCESS,
                        expenditureToDelete);
        ModelManager expectedModel = new ModelManager(model.getAccountList(), new UserPrefs());
        expectedModel.deleteExpenditure(expenditureToDelete);

        assertCommandSuccess(expDeleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredExpenditureList().size() + 1);
        ExpDeleteCommand expDeleteCommand = new ExpDeleteCommand(outOfBoundIndex);

        assertCommandFailure(expDeleteCommand, model, Messages.MESSAGE_INVALID_EXPENDITURE_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Expenditure expenditureToDelete = model.getFilteredExpenditureList().get(INDEX_FIRST_PERSON.getZeroBased());
        ExpDeleteCommand expDeleteCommand = new ExpDeleteCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(ExpDeleteCommand.MESSAGE_DELETE_EXPENDITURE_SUCCESS,
                        expenditureToDelete);
        Model expectedModel = new ModelManager(model.getAccountList(), new UserPrefs());
        expectedModel.deleteExpenditure(expenditureToDelete);
        showNoPerson(expectedModel);

        assertCommandSuccess(expDeleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAccountList().getExpenditureList().size());

        ExpDeleteCommand expDeleteCommand = new ExpDeleteCommand(outOfBoundIndex);

        assertCommandFailure(expDeleteCommand, model, Messages.MESSAGE_INVALID_EXPENDITURE_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        ExpDeleteCommand deleteFirstCommand = new ExpDeleteCommand(INDEX_FIRST_PERSON);
        ExpDeleteCommand deleteSecondCommand = new ExpDeleteCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        ExpDeleteCommand deleteFirstCommandCopy = new ExpDeleteCommand(INDEX_FIRST_PERSON);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different expenditure -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredExpenditureList(p -> false);

        assertTrue(model.getFilteredExpenditureList().isEmpty());
    }
}
