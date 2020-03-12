package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.expenditure.Expenditure;
import seedu.address.model.expenditure.UniquePersonList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class Account implements ReadOnlyAccount {

    private final UniquePersonList persons;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniquePersonList();
    }

    public Account() {}

    /**
     * Creates an Account using the Persons in the {@code toBeCopied}
     */
    public Account(ReadOnlyAccount toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the expenditure list with {@code expenditures}.
     * {@code expenditures} must not contain duplicate expenditures.
     */

    public void setExpenditures(List<Expenditure> expenditures) {
        this.persons.setPersons(expenditures);
    }

    /**
     * Resets the existing data of this {@code Account} with {@code newData}.
     */
    public void resetData(ReadOnlyAccount newData) {
        requireNonNull(newData);
        setExpenditures(newData.getExpenditureList());
    }

    //// expenditure-level operations

    /**
     * Returns true if a expenditure with the same identity as {@code expenditure} exists in the address book.
     */

    public boolean hasExpenditure(Expenditure expenditure) {
        requireNonNull(expenditure);
        return persons.contains(expenditure);
    }


    /**
     * Adds a expenditure to the address book.
     * The expenditure must not already exist in the address book.
     */
    public void addExpenditure(Expenditure expenditure) {
        persons.add(expenditure);
    }

    /**
     * Replaces the given expenditure {@code target} in the list with {@code editedExpenditure}.
     * {@code target} must exist in the address book.
     * The expenditure identity of {@code editedExpenditure} must not be the same as another
     * existing expenditure in the address book.
     */
    public void setExpenditure(Expenditure target, Expenditure editedExpenditure) {
        requireNonNull(editedExpenditure);

        persons.setPerson(target, editedExpenditure);
    }

    /**
     * Removes {@code key} from this {@code Account}.
     * {@code key} must exist in the address book.
     */
    public void removeExpenditure(Expenditure key) {
        persons.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return persons.asUnmodifiableObservableList().size() + " persons";
        // TODO: refine later
    }

    @Override
    public ObservableList<Expenditure> getExpenditureList() {
        return persons.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Account // instanceof handles nulls
                && persons.equals(((Account) other).persons));
    }

    @Override
    public int hashCode() {
        return persons.hashCode();
    }
}
