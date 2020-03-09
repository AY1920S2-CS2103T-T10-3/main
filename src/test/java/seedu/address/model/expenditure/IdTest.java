package seedu.address.model.expenditure;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

public class IdTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Id(null));
    }

    @Test
    public void constructor_invalidAddress_throwsIllegalArgumentException() {
        String invalidAddress = "";
        assertThrows(IllegalArgumentException.class, () -> new Id(invalidAddress));
    }

    @Test
    public void isValidAddress() {
        // null address
        assertThrows(NullPointerException.class, () -> Id.isValidAddress(null));

        // invalid addresses
        assertFalse(Id.isValidAddress("")); // empty string
        assertFalse(Id.isValidAddress(" ")); // spaces only

        // valid addresses
        assertTrue(Id.isValidAddress("Blk 456, Den Road, #01-355"));
        assertTrue(Id.isValidAddress("-")); // one character
        assertTrue(Id.isValidAddress("Leng Inc; 1234 Market St; San Francisco CA 2349879; USA")); // long address
    }
}
