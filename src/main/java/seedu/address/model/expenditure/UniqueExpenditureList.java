package seedu.address.model.expenditure;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.expenditure.exceptions.DuplicateExpenditureException;
import seedu.address.model.expenditure.exceptions.ExpenditureNotFoundException;

/**
 * A list of expenditures that enforces uniqueness between its elements and does not allow nulls.
 * A expenditure is considered unique by comparing using {@code Expenditure#equals(Expenditure)}.
 * As such, adding and updating of
 * expenditures uses Expenditure#equals(Expenditure) for equality
 * so as to ensure that the expenditure being added or updated is
 * unique in terms of identity in the UniqueExpenditureList. However,
 * the removal of a expenditure uses Expenditure#equals(Object) so
 * as to ensure that the expenditure with exactly the same fields will be removed.
 * <p>
 * Supports a minimal set of list operations.
 *
 * @see Expenditure#isSameExpenditure(Expenditure)
 */
public class UniqueExpenditureList implements Iterable<Expenditure> {

    private final ObservableList<Expenditure> internalList = FXCollections.observableArrayList();
    private final ObservableList<Expenditure> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    public UniqueExpenditureList() {
    }

    public UniqueExpenditureList(List<Expenditure> expenditures) {
        setExpenditures(expenditures);
    }

    /**
     * Returns true if the list contains an equivalent expenditure as the given argument.
     */
    public boolean contains(Expenditure toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::equals);
    }

    /**
     * Adds a expenditure to the list.
     * The expenditure must not already exist in the list.
     */
    public void add(Expenditure toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateExpenditureException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the expenditure {@code target} in the list with {@code editedExpenditure}.
     * {@code target} must exist in the list.
     * The expenditure identity of {@code editedExpenditure} must not be the same as another
     * existing expenditure in the list.
     */
    public void setExpenditure(Expenditure target, Expenditure editedExpenditure) {
        requireAllNonNull(target, editedExpenditure);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new ExpenditureNotFoundException();
        }

        if (!target.equals(editedExpenditure) && contains(editedExpenditure)) {
            throw new DuplicateExpenditureException();
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
            throw new ExpenditureNotFoundException();
        }
    }

    public void setExpenditures(UniqueExpenditureList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code expenditures}.
     * {@code expenditures} must not contain duplicate expenditures.
     */
    public void setExpenditures(List<Expenditure> expenditures) {
        requireAllNonNull(expenditures);
        if (!expendituresAreUnique(expenditures)) {
            throw new DuplicateExpenditureException();
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
                || (other instanceof UniqueExpenditureList // instanceof handles nulls
                && internalList.equals(((UniqueExpenditureList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code expenditures} contains only unique expenditures.
     */
    private boolean expendituresAreUnique(List<Expenditure> expenditures) {
        for (int i = 0; i < expenditures.size() - 1; i++) {
            for (int j = i + 1; j < expenditures.size(); j++) {
                if (expenditures.get(i).equals(expenditures.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
