package seedu.address.model;

import java.nio.file.Path;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.budget.Budget;
import seedu.address.model.budget.BudgetMap;
import seedu.address.model.expenditure.BaseExp;
import seedu.address.model.expenditure.Expenditure;
import seedu.address.model.expenditure.Repeat;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Expenditure> PREDICATE_SHOW_ALL_EXPENDITURES = unused -> true;
    Predicate<Repeat> PREDICATE_SHOW_ALL_REPEATS = unused -> true;
    Predicate<BaseExp> PREDICATE_SHOW_ALL_BASEEXP = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Replaces address book data with the data in {@code account}.
     */
    void setAccountList(ReadOnlyAccountList accountList);

    /** Returns the Account */
    ReadOnlyAccountList getAccountList();

    /**
     * Returns true if a expenditure with the same identity as {@code expenditure} exists in the internal list.
     */
    boolean hasExpenditure(Expenditure expenditure);

    /**
     * Deletes the given expenditure.
     * The expenditure must exist in the internal list.
     */
    void deleteExpenditure(Expenditure target);

    /**
     * Adds the given expenditure.
     * {@code expenditure} must not already exist in the internal list.
     */
    void addExpenditure(Expenditure expenditure);

    /**
     * Adds the given repeat.
     */
    void addRepeat(Repeat repeat);

    /**
     * Deletes the given repeat.
     * The repeat must exist in the internal list.
     */
    void deleteRepeat(Repeat target);

    /**
     * Replaces the given expenditure {@code target} with {@code editedExpenditure}.
     * {@code target} must exist in the internal list.
     * The expenditure identity of {@code editedExpenditure} must not be the same as
     * another existing expenditure in the internal list.
     */
    void setExpenditure(Expenditure target, Expenditure editedExpenditure);

    /**
     * Replaces the given repeat {@code target} with {@code editedRepeat}.
     * {@code target} must exist in the internal list.
     */
    void setRepeat(Repeat target, Repeat editedRepeat);

    /** Returns an unmodifiable view of the filtered expenditure list */
    ObservableList<Expenditure> getFilteredExpenditureList();

    /** Returns an unmodifiable view of the filtered repeat list */
    ObservableList<Repeat> getFilteredRepeatList();

    ObservableList<BaseExp> getFilteredBaseExpList();

    /**
     * Updates the filter of the filtered expenditure list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredExpenditureList(Predicate<Expenditure> predicate);

    /**
     * Updates the filter of the filtered baseExp list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredBaseExpList(Predicate<BaseExp> predicate);

    /**
     * Rename the account's name.
     * @param oldName target account's current name
     * @param newName target account's new name
     * @return a string to denote the current active account name.
     */
    String renameAccount(String oldName, String newName);

    /**
     * Delete an account from the accountList.
     * @param name the target account name
     * @return a string to denote the current active account name.
     */
    String deleteAccount(String name);

    void addAccount(Account account) throws CommandException;

    boolean updateActiveAccount(String accountName);

    void clearActiveAccount();

    ReportableAccount getReportableAccount();

    /**
     * Changes the active date to the one stated.
     * @param date New active date.
     */
    void updateActiveDate(LocalDate date);

    /**
     * Obtains the active date being viewed.
     */
    LocalDate getActiveDate();

    /**
     * Updates budget list with a budget detail.
     * @param budget The budget detail.
     */
    void setBudget(Budget budget);

    /**
     * Obtains the hashmap of the budget.
     */
    BudgetMap getBudgets();

    /**
     * Obtains a MonthlySpendingCalculator for the current active yearMonth.
     */
    MonthlySpendingCalculator getMonthlySpending();

    /**
     * Obtains a MonthlySpendingCalculator for the given active yearMonth.
     * To be called in go command.
     */
    MonthlySpendingCalculator getMonthlySpending(YearMonth givenYearMonth);

    /**
     * Obtains a MonthlySpendingCalculator for the new active account.
     * To be called in acc checkout, acc delete command.
     */
    MonthlySpendingCalculator getMonthlySpending(String newActiveAccount);


}
