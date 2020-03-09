package seedu.address.model.expenditure;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

public class AmountTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Amount(null));
    }

    @Test
    public void constructor_invalidPhone_throwsIllegalArgumentException() {
        String invalidPhone = "";
        assertThrows(IllegalArgumentException.class, () -> new Amount(invalidPhone));
    }

    @Test
    public void isValidPhone() {
        // null phone number
        assertThrows(NullPointerException.class, () -> Amount.isValidPhone(null));

        // invalid phone numbers
        assertFalse(Amount.isValidPhone("")); // empty string
        assertFalse(Amount.isValidPhone(" ")); // spaces only
        assertFalse(Amount.isValidPhone("91")); // less than 3 numbers
        assertFalse(Amount.isValidPhone("phone")); // non-numeric
        assertFalse(Amount.isValidPhone("9011p041")); // alphabets within digits
        assertFalse(Amount.isValidPhone("9312 1534")); // spaces within digits

        // valid phone numbers
        assertTrue(Amount.isValidPhone("911")); // exactly 3 numbers
        assertTrue(Amount.isValidPhone("93121534"));
        assertTrue(Amount.isValidPhone("124293842033123")); // long phone numbers
    }
}
