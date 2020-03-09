package seedu.address.model.expenditure;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

public class DateTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Date(null));
    }

    @Test
    public void constructor_invalidEmail_throwsIllegalArgumentException() {
        String invalidEmail = "";
        assertThrows(IllegalArgumentException.class, () -> new Date(invalidEmail));
    }

    @Test
    public void isValidEmail() {
        // null email
        assertThrows(NullPointerException.class, () -> Date.isValidEmail(null));

        // blank email
        assertFalse(Date.isValidEmail("")); // empty string
        assertFalse(Date.isValidEmail(" ")); // spaces only

        // missing parts
        assertFalse(Date.isValidEmail("@example.com")); // missing local part
        assertFalse(Date.isValidEmail("peterjackexample.com")); // missing '@' symbol
        assertFalse(Date.isValidEmail("peterjack@")); // missing domain name

        // invalid parts
        assertFalse(Date.isValidEmail("peterjack@-")); // invalid domain name
        assertFalse(Date.isValidEmail("peterjack@exam_ple.com")); // underscore in domain name
        assertFalse(Date.isValidEmail("peter jack@example.com")); // spaces in local part
        assertFalse(Date.isValidEmail("peterjack@exam ple.com")); // spaces in domain name
        assertFalse(Date.isValidEmail(" peterjack@example.com")); // leading space
        assertFalse(Date.isValidEmail("peterjack@example.com ")); // trailing space
        assertFalse(Date.isValidEmail("peterjack@@example.com")); // double '@' symbol
        assertFalse(Date.isValidEmail("peter@jack@example.com")); // '@' symbol in local part
        assertFalse(Date.isValidEmail("peterjack@example@com")); // '@' symbol in domain name
        assertFalse(Date.isValidEmail("peterjack@.example.com")); // domain name starts with a period
        assertFalse(Date.isValidEmail("peterjack@example.com.")); // domain name ends with a period
        assertFalse(Date.isValidEmail("peterjack@-example.com")); // domain name starts with a hyphen
        assertFalse(Date.isValidEmail("peterjack@example.com-")); // domain name ends with a hyphen

        // valid email
        assertTrue(Date.isValidEmail("PeterJack_1190@example.com"));
        assertTrue(Date.isValidEmail("a@bc")); // minimal
        assertTrue(Date.isValidEmail("test@localhost")); // alphabets only
        assertTrue(Date.isValidEmail("!#$%&'*+/=?`{|}~^.-@example.org")); // special characters local part
        assertTrue(Date.isValidEmail("123@145")); // numeric local part and domain name
        assertTrue(Date.isValidEmail("a1+be!@example1.com")); // mixture of alphanumeric and special characters
        assertTrue(Date.isValidEmail("peter_jack@very-very-very-long-example.com")); // long domain name
        assertTrue(Date.isValidEmail("if.you.dream.it_you.can.do.it@example.com")); // long local part
    }
}
