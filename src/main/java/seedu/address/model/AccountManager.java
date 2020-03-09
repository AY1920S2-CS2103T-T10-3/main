package seedu.address.model;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.expenditure.Expenditure;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents the in-memory model of the address book data.
 */
public class AccountManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(AccountManager.class);

    private final Account account;
    private final UserPrefs userPrefs;
    private final FilteredList<Expenditure> filteredExpenditures;

    /**
     * Initializes a AccountManager with the given account and userPrefs.
     */
    public AccountManager(ReadOnlyAccount addressBook, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.account = new Account(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredExpenditures = new FilteredList<>(this.account.getAccountList());
    }

    public AccountManager() {
        this(new Account(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAccountFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAccountFilePath(addressBookFilePath);
    }

    //=========== Account ================================================================================

    @Override
    public void setAccount(ReadOnlyAccount account) {
        this.account.resetData(account);
    }

    @Override
    public ReadOnlyAccount getAccount() {
        return account;
    }

    @Override
    public boolean hasPerson(Expenditure expenditure) {
        requireNonNull(expenditure);
        return account.hasPerson(expenditure);
    }

    @Override
    public void deletePerson(Expenditure target) {
        account.removePerson(target);
    }

    @Override
    public void addPerson(Expenditure expenditure) {
        account.addPerson(expenditure);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Expenditure target, Expenditure editedExpenditure) {
        requireAllNonNull(target, editedExpenditure);

        account.setPerson(target, editedExpenditure);
    }

    //=========== Filtered Expenditure List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Expenditure} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Expenditure> getFilteredPersonList() {
        return filteredExpenditures;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Expenditure> predicate) {
        requireNonNull(predicate);
        filteredExpenditures.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof AccountManager)) {
            return false;
        }

        // state check
        AccountManager other = (AccountManager) obj;
        return account.equals(other.account)
                && userPrefs.equals(other.userPrefs)
                && filteredExpenditures.equals(other.filteredExpenditures);
    }

}
