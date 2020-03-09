package seedu.address.model.expenditure;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

public class InfoTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Info(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new Info(invalidName));
    }

    @Test
    public void isValidName() {
        // null name
        assertThrows(NullPointerException.class, () -> Info.isValidName(null));

        // invalid name
        assertFalse(Info.isValidName("")); // empty string
        assertFalse(Info.isValidName(" ")); // spaces only
        assertFalse(Info.isValidName("^")); // only non-alphanumeric characters
        assertFalse(Info.isValidName("peter*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(Info.isValidName("peter jack")); // alphabets only
        assertTrue(Info.isValidName("12345")); // numbers only
        assertTrue(Info.isValidName("peter the 2nd")); // alphanumeric characters
        assertTrue(Info.isValidName("Capital Tan")); // with capital letters
        assertTrue(Info.isValidName("David Roger Jackson Ray Jr 2nd")); // long names
    }
}
