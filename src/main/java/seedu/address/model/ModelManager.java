package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.expenditure.Expenditure;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final Account account;
    private final UserPrefs userPrefs;
    private final FilteredList<Expenditure> filteredExpenditures;

    /**
     * Initializes a ModelManager with the given account and userPrefs.
     */
    public ModelManager(ReadOnlyAccount account, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(account, userPrefs);

        logger.fine("Initializing with address book: " + account + " and user prefs " + userPrefs);

        this.account = new Account(account);
        this.userPrefs = new UserPrefs(userPrefs);

        filteredExpenditures = new FilteredList<>(this.account.getExpenditureList());

    }

    public ModelManager() {
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
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
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

    public boolean hasExpenditure(Expenditure expenditure) {
        requireNonNull(expenditure);
        return account.hasExpenditure(expenditure);
    }

    @Override
    public void deleteExpenditure(Expenditure target) {
        account.removeExpenditure(target);
    }

    @Override
    public void addExpenditure(Expenditure expenditure) {
        account.addExpenditure(expenditure);

        updateFilteredExpenditureList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setExpenditure(Expenditure target, Expenditure editedExpenditure) {
        requireAllNonNull(target, editedExpenditure);
        account.setExpenditure(target, editedExpenditure);
    }

    //=========== Filtered Expenditure List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Expenditure} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Expenditure> getFilteredExpenditureList() {
        return filteredExpenditures;
    }

    @Override
    public void updateFilteredExpenditureList(Predicate<Expenditure> predicate) {
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
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return account.equals(other.account)
                && userPrefs.equals(other.userPrefs)
                && filteredExpenditures.equals(other.filteredExpenditures);
    }

}
