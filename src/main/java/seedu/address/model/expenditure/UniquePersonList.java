package seedu.address.model.expenditure;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.expenditure.exceptions.DuplicatePersonException;
import seedu.address.model.expenditure.exceptions.PersonNotFoundException;

import java.util.Iterator;
import java.util.List;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * A list of persons that enforces uniqueness between its elements and does not allow nulls.
 * A expenditure is considered unique by comparing using {@code Expenditure#isSamePerson(Expenditure)}. As such, adding and updating of
 * persons uses Expenditure#isSamePerson(Expenditure) for equality so as to ensure that the expenditure being added or updated is
 * unique in terms of identity in the UniquePersonList. However, the removal of a expenditure uses Expenditure#equals(Object) so
 * as to ensure that the expenditure with exactly the same fields will be removed.
 * <p>
 * Supports a minimal set of list operations.
 *
 * @see Expenditure#isSamePerson(Expenditure)
 */
public class UniquePersonList implements Iterable<Expenditure> {

    private final ObservableList<Expenditure> internalList = FXCollections.observableArrayList();
    private final ObservableList<Expenditure> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent expenditure as the given argument.
     */
    public boolean contains(Expenditure toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSamePerson);
    }

    /**
     * Adds a expenditure to the list.
     * The expenditure must not already exist in the list.
     */
    public void add(Expenditure toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicatePersonException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the expenditure {@code target} in the list with {@code editedExpenditure}.
     * {@code target} must exist in the list.
     * The expenditure identity of {@code editedExpenditure} must not be the same as another existing expenditure in the list.
     */
    public void setPerson(Expenditure target, Expenditure editedExpenditure) {
        requireAllNonNull(target, editedExpenditure);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new PersonNotFoundException();
        }

        if (!target.isSamePerson(editedExpenditure) && contains(editedExpenditure)) {
            throw new DuplicatePersonException();
        }

        internalList.set(index, editedExpenditure);
    }

    /**
     * Removes the equivalent expenditure from the list.
     * The expenditure must exist in the list.
     */
    public void remove(Expenditure toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new PersonNotFoundException();
        }
    }

    public void setPersons(UniquePersonList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code expenditures}.
     * {@code expenditures} must not contain duplicate expenditures.
     */
    public void setPersons(List<Expenditure> expenditures) {
        requireAllNonNull(expenditures);
        if (!personsAreUnique(expenditures)) {
            throw new DuplicatePersonException();
        }

        internalList.setAll(expenditures);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Expenditure> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Expenditure> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniquePersonList // instanceof handles nulls
                && internalList.equals(((UniquePersonList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code expenditures} contains only unique expenditures.
     */
    private boolean personsAreUnique(List<Expenditure> expenditures) {
        for (int i = 0; i < expenditures.size() - 1; i++) {
            for (int j = i + 1; j < expenditures.size(); j++) {
                if (expenditures.get(i).isSamePerson(expenditures.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
