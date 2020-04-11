package seedu.saveit.model;

import static java.util.Objects.requireNonNull;
import static seedu.saveit.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.saveit.commons.core.GuiSettings;
import seedu.saveit.commons.core.LogsCenter;
import seedu.saveit.logic.commands.exceptions.CommandException;
import seedu.saveit.model.budget.Budget;
import seedu.saveit.model.budget.BudgetMap;
import seedu.saveit.model.expenditure.BaseExp;
import seedu.saveit.model.expenditure.Expenditure;
import seedu.saveit.model.expenditure.Repeat;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AccountList accountList;
    private final UserPrefs userPrefs;
    private final FilteredList<Expenditure> filteredExpenditures;
    private final FilteredList<Repeat> filteredRepeats;
    private final FilteredList<BaseExp> filteredBaseExp;

    /**
     * Initializes a ModelManager with the given account and userPrefs.
     */
    public ModelManager(ReadOnlyAccountList accountList, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(accountList, userPrefs);

        logger.fine("Initializing with address book: " + accountList + " and user prefs " + userPrefs);

        this.userPrefs = new UserPrefs(userPrefs);
        this.accountList = new AccountList(accountList);

        filteredExpenditures = this.accountList.getExpenditureList().filtered(PREDICATE_SHOW_ALL_EXPENDITURES);
        filteredRepeats = this.accountList.getRepeatList().filtered(PREDICATE_SHOW_ALL_REPEATS);
        filteredBaseExp = this.accountList.getBaseExpList().filtered(PREDICATE_SHOW_ALL_BASEEXP);
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
        return accountList.hasExpenditure(expenditure);
    }

    @Override
    public void deleteExpenditure(Expenditure target) {
        accountList.removeExpenditure(target);
    }

    @Override
    public void addExpenditure(Expenditure expenditure) {
        accountList.addExpenditure(expenditure);
        showAll();
    }

    @Override
    public void addRepeat(Repeat repeat) {
        accountList.addRepeat(repeat);
        showAll();
    }

    @Override
    public void deleteRepeat(Repeat target) {
        accountList.removeRepeat(target);
    }

    @Override
    public void setExpenditure(Expenditure target, Expenditure editedExpenditure) {
        requireAllNonNull(target, editedExpenditure);
        accountList.setExpenditure(target, editedExpenditure);
    }

    @Override
    public void setRepeat(Repeat target, Repeat editedRepeat) {
        requireAllNonNull(target, editedRepeat);
        accountList.setRepeat(target, editedRepeat);
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
    public ObservableList<Repeat> getFilteredRepeatList() {
        return filteredRepeats;
    }

    @Override
    public ObservableList<BaseExp> getFilteredBaseExpList() {
        return filteredBaseExp;
    }

    @Override
    public void updateFilteredExpenditureList(Predicate<Expenditure> predicate) {
        requireNonNull(predicate);
        filteredExpenditures.setPredicate(predicate);
    }

    @Override
    public void updateFilteredBaseExpList(Predicate<BaseExp> predicate) {
        requireNonNull(predicate);
        filteredBaseExp.setPredicate(predicate);
    }

    /**
     * Displays all repeats and expenditures
     */
    private void showAll() {
        updateFilteredExpenditureList(PREDICATE_SHOW_ALL_EXPENDITURES);
        updateFilteredBaseExpList(PREDICATE_SHOW_ALL_BASEEXP);
    }

    @Override
    public boolean updateActiveAccount(String accountName) {
        if (!accountList.updateActiveAccount(accountName)) {
            return false;
        } else {
            showAll();
            return true;
        }
    }

    @Override
    public String renameAccount(String oldName, String newName) throws CommandException {
        return this.accountList.renameAccount(oldName, newName);
    }

    @Override
    public String deleteAccount(String name) throws CommandException {
        return this.accountList.deleteAccount(name);
    }

    public void clearActiveAccount() {
        accountList.clearActiveAccount();
    }

    @Override
    public ReportableAccount getReportableAccount() {
        return accountList.getReportableAccount();
    }

    @Override
    public void updateActiveDate(LocalDate date) {
        accountList.updateActiveDate(date);
    }

    @Override
    public LocalDate getActiveDate() {
        return accountList.getActiveDate();
    }

    @Override
    public void setBudget(Budget budget) {
        this.accountList.setBudget(budget);
    }

    @Override
    public BudgetMap getBudgets() {
        return this.accountList.getBudgets();
    }

    @Override
    public MonthlySpendingCalculator getMonthlySpending() {
        return this.accountList.getMonthlySpending();
    }

    @Override
    public MonthlySpendingCalculator getMonthlySpending(YearMonth givenYearMonth) {
        return this.accountList.getMonthlySpending(givenYearMonth);
    }

    @Override
    public MonthlySpendingCalculator getMonthlySpending(String newActiveAccount) {
        return this.accountList.getMonthlySpending(newActiveAccount);
    }

    @Override
    public void addAccount(Account account) throws CommandException {
        try {
            this.accountList.addAccount(account);
        } catch (DuplicateAccountException e) {
            throw new CommandException("Account " + account.getAccountName()
                    + " already exists! Unable to add.");
        }
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
        // The test is failing because of expenditure
        ModelManager other = (ModelManager) obj;
        return accountList.equals(other.accountList)
                && userPrefs.equals(other.userPrefs)
                && filteredExpenditures.equals(other.filteredExpenditures);
    }

}
