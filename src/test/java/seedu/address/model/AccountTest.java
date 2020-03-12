package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.expenditure.Expenditure;
import seedu.address.model.expenditure.exceptions.DuplicatePersonException;
import seedu.address.testutil.PersonBuilder;

public class AccountTest {

    private final Account account = new Account();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), account.getExpenditureList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> account.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        Account newData = getTypicalAddressBook();
        account.resetData(newData);
        assertEquals(newData, account);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two expenditures with the same identity fields
        Expenditure editedAlice = new PersonBuilder(ALICE).build();
        List<Expenditure> newExpenditures = Arrays.asList(ALICE, editedAlice);
        AccountStub newData = new AccountStub(newExpenditures);
        assertThrows(DuplicatePersonException.class, () -> account.resetData(newData));
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> account.hasExpenditure(null));
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(account.hasExpenditure(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        account.addExpenditure(ALICE);
        assertTrue(account.hasExpenditure(ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInAddressBook_returnsTrue() {
        account.addExpenditure(ALICE);
        Expenditure editedAlice = new PersonBuilder(ALICE).withDate(VALID_DATE_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(account.hasExpenditure(editedAlice));
    }

    @Test
    public void getExpenditureList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> account.getExpenditureList().remove(0));
    }

    /**
     * A stub ReadOnlyAccount whose persons list can violate interface constraints.
     */
    private static class AccountStub implements ReadOnlyAccount {
        private final ObservableList<Expenditure> expenditures = FXCollections.observableArrayList();

        AccountStub(Collection<Expenditure> expenditures) {
            this.expenditures.setAll(expenditures);
        }

        @Override
        public ObservableList<Expenditure> getExpenditureList() {
            return expenditures;
        }
    }

}
