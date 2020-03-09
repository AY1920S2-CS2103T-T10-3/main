package seedu.address.logic.commands;

import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Account;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAccount;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.expenditure.Expenditure;
import seedu.address.testutil.PersonBuilder;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.*;
import static seedu.address.testutil.Assert.assertThrows;

public class AddCommandTest {

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommand(null));
    }

    @Test
    public void execute_personAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
        Expenditure validExpenditure = new PersonBuilder().build();

        CommandResult commandResult = new AddCommand(validExpenditure).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validExpenditure), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validExpenditure), modelStub.personsAdded);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Expenditure validExpenditure = new PersonBuilder().build();
        AddCommand addCommand = new AddCommand(validExpenditure);
        ModelStub modelStub = new ModelStubWithPerson(validExpenditure);

        assertThrows(CommandException.class, AddCommand.MESSAGE_DUPLICATE_PERSON, () -> addCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Expenditure alice = new PersonBuilder().withName("Alice").build();
        Expenditure bob = new PersonBuilder().withName("Bob").build();
        AddCommand addAliceCommand = new AddCommand(alice);
        AddCommand addBobCommand = new AddCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different expenditure -> returns false
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
        public void addPerson(Expenditure expenditure) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAccount(ReadOnlyAccount newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAccount getAccount() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Expenditure expenditure) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Expenditure target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Expenditure target, Expenditure editedExpenditure) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Expenditure> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Expenditure> predicate) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single expenditure.
     */
    private class ModelStubWithPerson extends ModelStub {
        private final Expenditure expenditure;

        ModelStubWithPerson(Expenditure expenditure) {
            requireNonNull(expenditure);
            this.expenditure = expenditure;
        }

        @Override
        public boolean hasPerson(Expenditure expenditure) {
            requireNonNull(expenditure);
            return this.expenditure.isSamePerson(expenditure);
        }
    }

    /**
     * A Model stub that always accept the expenditure being added.
     */
    private class ModelStubAcceptingPersonAdded extends ModelStub {
        final ArrayList<Expenditure> personsAdded = new ArrayList<>();

        @Override
        public boolean hasPerson(Expenditure expenditure) {
            requireNonNull(expenditure);
            return personsAdded.stream().anyMatch(expenditure::isSamePerson);
        }

        @Override
        public void addPerson(Expenditure expenditure) {
            requireNonNull(expenditure);
            personsAdded.add(expenditure);
        }

        @Override
        public ReadOnlyAccount getAccount() {
            return new Account();
        }
    }

}
