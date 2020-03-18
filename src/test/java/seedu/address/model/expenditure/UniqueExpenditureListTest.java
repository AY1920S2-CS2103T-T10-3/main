package seedu.address.model.expenditure;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalExpenditures.ALICE;
import static seedu.address.testutil.TypicalExpenditures.BOB;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.expenditure.exceptions.DuplicateExpenditureException;
import seedu.address.model.expenditure.exceptions.ExpenditureNotFoundException;
import seedu.address.testutil.ExpenditureBuilder;

public class UniqueExpenditureListTest {

    private final UniqueExpenditureList uniqueExpenditureList = new UniqueExpenditureList();

    @Test
    public void contains_nullExpenditure_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueExpenditureList.contains(null));
    }

    @Test
    public void contains_expenditureNotInList_returnsFalse() {
        assertFalse(uniqueExpenditureList.contains(ALICE));
    }

    @Test
    public void contains_expenditureInList_returnsTrue() {
        uniqueExpenditureList.add(ALICE);
        assertTrue(uniqueExpenditureList.contains(ALICE));
    }

    @Test
    public void contains_expenditureWithSameIdentityFieldsInList_returnsTrue() {
        uniqueExpenditureList.add(ALICE);

        Expenditure editedAlice = new ExpenditureBuilder(ALICE).withDate(VALID_DATE_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(uniqueExpenditureList.contains(editedAlice));
    }

    @Test
    public void add_nullExpenditure_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueExpenditureList.add(null));
    }

    @Test
    public void add_duplicateExpenditure_throwsDuplicateExpenditureException() {
        uniqueExpenditureList.add(ALICE);
        assertThrows(DuplicateExpenditureException.class, () -> uniqueExpenditureList.add(ALICE));
    }

    @Test
    public void setExpenditure_nullTargetExpenditure_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueExpenditureList.setExpenditure(null, ALICE));
    }

    @Test
    public void setExpenditure_nullEditedExpenditure_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueExpenditureList.setExpenditure(ALICE, null));
    }

    @Test
    public void setExpenditure_targetExpenditureNotInList_throwsExpenditureNotFoundException() {
        assertThrows(ExpenditureNotFoundException.class, () -> uniqueExpenditureList.setExpenditure(ALICE, ALICE));
    }

    @Test
    public void setExpenditure_editedExpenditureEquals_success() {
        uniqueExpenditureList.add(ALICE);
        uniqueExpenditureList.setExpenditure(ALICE, ALICE);
        UniqueExpenditureList expectedUniqueExpenditureList = new UniqueExpenditureList();
        expectedUniqueExpenditureList.add(ALICE);
        assertEquals(expectedUniqueExpenditureList, uniqueExpenditureList);
    }

    @Test
    public void setExpenditure_editedExpenditureHasSameIdentity_success() {
        uniqueExpenditureList.add(ALICE);

        Expenditure editedAlice = new ExpenditureBuilder(ALICE).withDate(VALID_DATE_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        uniqueExpenditureList.setExpenditure(ALICE, editedAlice);
        UniqueExpenditureList expectedUniqueExpenditureList = new UniqueExpenditureList();
        expectedUniqueExpenditureList.add(editedAlice);
        assertEquals(expectedUniqueExpenditureList, uniqueExpenditureList);
    }

    @Test
    public void setExpenditure_editedExpenditureHasDifferentIdentity_success() {
        uniqueExpenditureList.add(ALICE);
        uniqueExpenditureList.setExpenditure(ALICE, BOB);
        UniqueExpenditureList expectedUniqueExpenditureList = new UniqueExpenditureList();
        expectedUniqueExpenditureList.add(BOB);
        assertEquals(expectedUniqueExpenditureList, uniqueExpenditureList);
    }

    @Test
    public void setExpenditure_editedExpenditureHasNonUniqueIdentity_throwsDuplicateExpenditureException() {
        uniqueExpenditureList.add(ALICE);
        uniqueExpenditureList.add(BOB);
        assertThrows(DuplicateExpenditureException.class, () -> uniqueExpenditureList.setExpenditure(ALICE, BOB));
    }

    @Test
    public void remove_nullExpenditure_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueExpenditureList.remove(null));
    }

    @Test
    public void remove_expenditureDoesNotExist_throwsExpenditureNotFoundException() {
        assertThrows(ExpenditureNotFoundException.class, () -> uniqueExpenditureList.remove(ALICE));
    }

    @Test
    public void remove_existingExpenditure_removesExpenditure() {
        uniqueExpenditureList.add(ALICE);
        uniqueExpenditureList.remove(ALICE);
        UniqueExpenditureList expectedUniqueExpenditureList = new UniqueExpenditureList();
        assertEquals(expectedUniqueExpenditureList, uniqueExpenditureList);
    }

    @Test
    public void setExpenditures_nullUniqueExpenditureList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueExpenditureList.setExpenditures((UniqueExpenditureList) null));
    }

    @Test
    public void setExpenditures_uniqueExpenditureList_replacesOwnListWithProvidedUniqueExpenditureList() {
        uniqueExpenditureList.add(ALICE);
        UniqueExpenditureList expectedUniqueExpenditureList = new UniqueExpenditureList();
        expectedUniqueExpenditureList.add(BOB);
        uniqueExpenditureList.setExpenditures(expectedUniqueExpenditureList);
        assertEquals(expectedUniqueExpenditureList, uniqueExpenditureList);
    }

    @Test
    public void setExpenditures_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueExpenditureList.setExpenditures((List<Expenditure>) null));
    }

    @Test
    public void setExpenditures_list_replacesOwnListWithProvidedList() {
        uniqueExpenditureList.add(ALICE);
        List<Expenditure> expenditureList = Collections.singletonList(BOB);
        uniqueExpenditureList.setExpenditures(expenditureList);
        UniqueExpenditureList expectedUniqueExpenditureList = new UniqueExpenditureList();
        expectedUniqueExpenditureList.add(BOB);
        assertEquals(expectedUniqueExpenditureList, uniqueExpenditureList);
    }

    @Test
    public void setExpenditures_listWithDuplicateExpenditures_throwsDuplicateExpenditureException() {
        List<Expenditure> listWithDuplicateExpenditures = Arrays.asList(ALICE, ALICE);
        assertThrows(DuplicateExpenditureException.class, () -> uniqueExpenditureList.setExpenditures(listWithDuplicateExpenditures));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueExpenditureList.asUnmodifiableObservableList().remove(0));
    }
}
