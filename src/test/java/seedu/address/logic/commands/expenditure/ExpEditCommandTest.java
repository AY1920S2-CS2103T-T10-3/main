package seedu.address.logic.commands.expenditure;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ID_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INFO_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.account.AccClearCommand;
import seedu.address.logic.commands.expenditure.ExpEditCommand.EditExpenditureDescriptor;
import seedu.address.model.Account;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.expenditure.Expenditure;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand)
 * and unit tests for ExpEditCommand.
 */
public class ExpEditCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Expenditure editedExpenditure = new PersonBuilder().build();
        EditExpenditureDescriptor descriptor =
                new EditPersonDescriptorBuilder(editedExpenditure).build();
        ExpEditCommand expEditCommand = new ExpEditCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(ExpEditCommand.MESSAGE_EDIT_EXPENDITURE_SUCCESS, editedExpenditure);

        Model expectedModel = new ModelManager(new Account(model.getAccountList()), new UserPrefs());
        expectedModel.setExpenditure(model.getFilteredExpenditureList().get(0), editedExpenditure);

        assertCommandSuccess(expEditCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastPerson = Index.fromOneBased(model.getFilteredExpenditureList().size());
        Expenditure lastExpenditure = model.getFilteredExpenditureList().get(indexLastPerson.getZeroBased());

        PersonBuilder personInList = new PersonBuilder(lastExpenditure);
        Expenditure editedExpenditure = personInList.withInfo(VALID_INFO_BOB).withId(VALID_ID_BOB)
                .withTags(VALID_TAG_HUSBAND).build();

        EditExpenditureDescriptor descriptor = new EditPersonDescriptorBuilder().withInfo(VALID_INFO_BOB)
                .withId(VALID_ID_BOB).withTags(VALID_TAG_HUSBAND).build();
        ExpEditCommand expEditCommand = new ExpEditCommand(indexLastPerson, descriptor);

        String expectedMessage = String.format(ExpEditCommand.MESSAGE_EDIT_EXPENDITURE_SUCCESS, editedExpenditure);

        Model expectedModel = new ModelManager(new Account(model.getAccountList()), new UserPrefs());
        expectedModel.setExpenditure(lastExpenditure, editedExpenditure);

        assertCommandSuccess(expEditCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {

        ExpEditCommand expEditCommand = new ExpEditCommand(INDEX_FIRST_PERSON,
                new EditExpenditureDescriptor());
        Expenditure editedExpenditure = model.getFilteredExpenditureList().get(INDEX_FIRST_PERSON.getZeroBased());

        String expectedMessage = String.format(ExpEditCommand.MESSAGE_EDIT_EXPENDITURE_SUCCESS, editedExpenditure);

        Model expectedModel = new ModelManager(new Account(model.getAccountList()), new UserPrefs());

        assertCommandSuccess(expEditCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Expenditure expenditureInFilteredList = model.getFilteredExpenditureList()
                .get(INDEX_FIRST_PERSON.getZeroBased());
        Expenditure editedExpenditure = new PersonBuilder(expenditureInFilteredList).withInfo(VALID_INFO_BOB).build();
        ExpEditCommand expEditCommand = new ExpEditCommand(INDEX_FIRST_PERSON,
                new EditPersonDescriptorBuilder().withInfo(VALID_INFO_BOB).build());

        String expectedMessage = String.format(ExpEditCommand.MESSAGE_EDIT_EXPENDITURE_SUCCESS, editedExpenditure);

        Model expectedModel = new ModelManager(new Account(model.getAccountList()), new UserPrefs());
        expectedModel.setExpenditure(model.getFilteredExpenditureList().get(0), editedExpenditure);

        assertCommandSuccess(expEditCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicatePersonUnfilteredList_failure() {
        Expenditure firstExpenditure = model.getFilteredExpenditureList().get(INDEX_FIRST_PERSON.getZeroBased());
        EditExpenditureDescriptor descriptor = new EditPersonDescriptorBuilder(firstExpenditure).build();
        ExpEditCommand expEditCommand = new ExpEditCommand(INDEX_SECOND_PERSON, descriptor);
        assertCommandFailure(expEditCommand, model, ExpEditCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_duplicatePersonFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        // edit expenditure in filtered list into a duplicate in address book
        Expenditure expenditureInList = model.getAccountList().getExpenditureList().get(INDEX_SECOND_PERSON.getZeroBased());
        ExpEditCommand expEditCommand = new ExpEditCommand(INDEX_FIRST_PERSON,
                new EditPersonDescriptorBuilder(expenditureInList).build());

        assertCommandFailure(expEditCommand, model, ExpEditCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredExpenditureList().size() + 1);
        EditExpenditureDescriptor descriptor = new EditPersonDescriptorBuilder()
                .withInfo(VALID_INFO_BOB).build();
        ExpEditCommand expEditCommand = new ExpEditCommand(outOfBoundIndex, descriptor);
        assertCommandFailure(expEditCommand, model, Messages.MESSAGE_INVALID_EXPENDITURE_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAccountList().getExpenditureList().size());

        ExpEditCommand expEditCommand = new ExpEditCommand(outOfBoundIndex,
                new EditPersonDescriptorBuilder().withInfo(VALID_INFO_BOB).build());

        assertCommandFailure(expEditCommand, model, Messages.MESSAGE_INVALID_EXPENDITURE_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final ExpEditCommand standardCommand = new ExpEditCommand(INDEX_FIRST_PERSON, DESC_AMY);

        // same values -> returns true
        EditExpenditureDescriptor copyDescriptor = new ExpEditCommand
                .EditExpenditureDescriptor(DESC_AMY);
        ExpEditCommand commandWithSameValues = new ExpEditCommand(INDEX_FIRST_PERSON, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new AccClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new ExpEditCommand(INDEX_SECOND_PERSON, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new ExpEditCommand(INDEX_FIRST_PERSON, DESC_BOB)));
    }

}
