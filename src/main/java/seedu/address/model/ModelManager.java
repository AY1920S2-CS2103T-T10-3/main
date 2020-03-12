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

    private final AccountList accountList;
    private final UserPrefs userPrefs;
    private final FilteredList<Expenditure> filteredExpenditures;

    /**
     * Initializes a ModelManager with the given account and userPrefs.
     */
    public ModelManager(ReadOnlyAccountList accountList, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(accountList, userPrefs);

        logger.fine("Initializing with address book: " + accountList + " and user prefs " + userPrefs);

//        this.account = new Account(account);
        this.userPrefs = new UserPrefs(userPrefs);
        this.accountList = new AccountList(accountList);

        filteredExpenditures = new FilteredList<>(this.accountList.getExpenditureList());

    }

    public ModelManager() {
        this(new AccountList(true), new UserPrefs());
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
    public void setAccountList(ReadOnlyAccountList accountList) {
        this.accountList.resetData(accountList);
    }

    @Override
    public ReadOnlyAccountList getAccountList() {
        return accountList;
    }

    @Override

    public boolean hasExpenditure(Expenditure expenditure) {
        requireNonNull(expenditure);
        return accountList.getActiveAccount().hasExpenditure(expenditure);
    }

    @Override
    public void deleteExpenditure(Expenditure target) {
        accountList.getActiveAccount().removeExpenditure(target);
    }

    @Override
    public void addExpenditure(Expenditure expenditure) {
        accountList.getActiveAccount().addExpenditure(expenditure);

        updateFilteredExpenditureList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setExpenditure(Expenditure target, Expenditure editedExpenditure) {
        requireAllNonNull(target, editedExpenditure);
        accountList.getActiveAccount().setExpenditure(target, editedExpenditure);
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
        return accountList.equals(other.accountList)
                && userPrefs.equals(other.userPrefs)
                && filteredExpenditures.equals(other.filteredExpenditures);
    }

}
