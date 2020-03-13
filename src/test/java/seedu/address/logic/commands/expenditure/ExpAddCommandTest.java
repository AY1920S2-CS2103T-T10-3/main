package seedu.address.logic.commands.expenditure;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Account;
import seedu.address.model.AccountList;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAccount;
import seedu.address.model.ReadOnlyAccountList;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.expenditure.Expenditure;
import seedu.address.testutil.ExpenditureBuilder;

public class ExpAddCommandTest {

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ExpAddCommand(null));
    }

    @Test
    public void execute_personAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingExpenditureAdded modelStub = new ModelStubAcceptingExpenditureAdded();
        Expenditure validPerson = new ExpenditureBuilder().build();

        CommandResult commandResult = new ExpAddCommand(validPerson).execute(modelStub);

        assertEquals(String.format(ExpAddCommand.MESSAGE_SUCCESS, validPerson), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validPerson), modelStub.personsAdded);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Expenditure validExpenditure = new ExpenditureBuilder().build();
        ExpAddCommand expAddCommand = new ExpAddCommand(validExpenditure);
        ModelStub modelStub = new ModelStubWithExpenditure(validExpenditure);

        assertThrows(CommandException.class,
                ExpAddCommand.MESSAGE_DUPLICATE_PERSON, () -> expAddCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Expenditure alice = new ExpenditureBuilder().withInfo("Alice").build();
        Expenditure bob = new ExpenditureBuilder().withInfo("Bob").build();
        ExpAddCommand addAliceCommand = new ExpAddCommand(alice);
        ExpAddCommand addBobCommand = new ExpAddCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        ExpAddCommand addAliceCommandCopy = new ExpAddCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different person -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addExpenditure(Expenditure expenditure) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAccountList(ReadOnlyAccountList newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAccountList getAccountList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasExpenditure(Expenditure person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteExpenditure(Expenditure target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setExpenditure(Expenditure target, Expenditure editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Expenditure> getFilteredExpenditureList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredExpenditureList(Predicate<Expenditure> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean updateActiveAccount(String accountName) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single person.
     */
    private class ModelStubWithExpenditure extends ModelStub {
        private final Expenditure expenditure;

        ModelStubWithExpenditure(Expenditure expenditure) {
            requireNonNull(expenditure);
            this.expenditure = expenditure;
        }

        @Override
        public boolean hasExpenditure(Expenditure expenditure) {
            requireNonNull(expenditure);
            return this.expenditure.isSamePerson(expenditure);
        }
    }

    /**
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingExpenditureAdded extends ModelStub {
        final ArrayList<Expenditure> personsAdded = new ArrayList<>();

        @Override
        public boolean hasExpenditure(Expenditure expenditure) {
            requireNonNull(expenditure);
            return personsAdded.stream().anyMatch(expenditure::isSamePerson);
        }

        @Override
        public void addExpenditure(Expenditure expenditure) {
            requireNonNull(expenditure);
            personsAdded.add(expenditure);
        }

        @Override
        public ReadOnlyAccountList getAccountList() {
//            return new AccountList();
            return null;
        }
    }

}
