package seedu.address.model;

import org.junit.jupiter.api.Test;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.expenditure.NameContainsKeywordsPredicate;
import seedu.address.testutil.AddressBookBuilder;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;

public class AccountManagerTest {

    private AccountManager accountManager = new AccountManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), accountManager.getUserPrefs());
        assertEquals(new GuiSettings(), accountManager.getGuiSettings());
        assertEquals(new Account(), new Account(accountManager.getAccount()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> accountManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setAccountFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        accountManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, accountManager.getUserPrefs());

        // Modifying userPrefs should not modify accountManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setAccountFilePath(Paths.get("new/address/book/file/path"));
        assertEquals(oldUserPrefs, accountManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> accountManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        accountManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, accountManager.getGuiSettings());
    }

    @Test
    public void setAddressBookFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> accountManager.setAddressBookFilePath(null));
    }

    @Test
    public void setAddressBookFilePath_validPath_setsAddressBookFilePath() {
        Path path = Paths.get("address/book/file/path");
        accountManager.setAddressBookFilePath(path);
        assertEquals(path, accountManager.getAddressBookFilePath());
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> accountManager.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(accountManager.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        accountManager.addPerson(ALICE);
        assertTrue(accountManager.hasPerson(ALICE));
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> accountManager.getFilteredPersonList().remove(0));
    }

    @Test
    public void equals() {
        Account account = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON).build();
        Account differentAccount = new Account();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        accountManager = new AccountManager(account, userPrefs);
        AccountManager accountManagerCopy = new AccountManager(account, userPrefs);
        assertTrue(accountManager.equals(accountManagerCopy));

        // same object -> returns true
        assertTrue(accountManager.equals(accountManager));

        // null -> returns false
        assertFalse(accountManager.equals(null));

        // different types -> returns false
        assertFalse(accountManager.equals(5));

        // different account -> returns false
        assertFalse(accountManager.equals(new AccountManager(differentAccount, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getInfo().fullName.split("\\s+");
        accountManager.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(accountManager.equals(new AccountManager(account, userPrefs)));

        // resets accountManager to initial state for upcoming tests
        accountManager.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAccountFilePath(Paths.get("differentFilePath"));
        assertFalse(accountManager.equals(new AccountManager(account, differentUserPrefs)));
    }
}
