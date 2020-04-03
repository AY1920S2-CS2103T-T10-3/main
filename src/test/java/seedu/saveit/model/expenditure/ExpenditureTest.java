package seedu.saveit.model.expenditure;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.saveit.logic.commands.CommandTestUtil.VALID_DATE_BOB;
import static seedu.saveit.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.saveit.testutil.TypicalExpenditures.ALICE;
import static seedu.saveit.testutil.TypicalExpenditures.BOB;

import org.junit.jupiter.api.Test;

import seedu.saveit.testutil.ExpenditureBuilder;

public class ExpenditureTest {

    @Test
    public void isSameExpenditure() {
        Expenditure aliceCopy = new ExpenditureBuilder(ALICE).build();
        assertFalse(ALICE.isSameExpenditure(aliceCopy));
    }


    @Test
    public void equals() {
        // different object but same fields -> true
        Expenditure aliceCopy = new ExpenditureBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different expenditure -> returns false
        assertFalse(ALICE.equals(BOB));

        // different object and fields-> returns false
        Expenditure editedAlice = new ExpenditureBuilder(ALICE).withAmount(3.00).build();
        assertFalse(ALICE.equals(editedAlice));

        // different object and fields -> returns false
        editedAlice = new ExpenditureBuilder(ALICE).withDate(VALID_DATE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different object and fields-> returns false
        editedAlice = new ExpenditureBuilder(ALICE).withTag(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.equals(editedAlice));
    }
}
