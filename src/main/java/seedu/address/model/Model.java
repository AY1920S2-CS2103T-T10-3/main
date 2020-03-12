package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.expenditure.Expenditure;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Expenditure> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

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
     * Returns true if a expenditure with the same identity as {@code expenditure} exists in the address book.
     */
    boolean hasExpenditure(Expenditure expenditure);

    /**
     * Deletes the given expenditure.
     * The expenditure must exist in the address book.
     */
    void deleteExpenditure(Expenditure target);

    /**
     * Adds the given expenditure.
     * {@code expenditure} must not already exist in the address book.
     */
    void addExpenditure(Expenditure expenditure);

    /**
     * Replaces the given expenditure {@code target} with {@code editedExpenditure}.
     * {@code target} must exist in the address book.
     * The expenditure identity of {@code editedExpenditure} must not be the same as
     * another existing expenditure in the address book.
     */
    void setExpenditure(Expenditure target, Expenditure editedExpenditure);

    /** Returns an unmodifiable view of the filtered expenditure list */
    ObservableList<Expenditure> getFilteredExpenditureList();

    /**
     * Updates the filter of the filtered expenditure list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredExpenditureList(Predicate<Expenditure> predicate);
}
