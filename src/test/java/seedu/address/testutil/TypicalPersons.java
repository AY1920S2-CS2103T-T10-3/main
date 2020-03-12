package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_AMOUNT_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AMOUNT_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ID_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ID_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INFO_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INFO_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.Account;
import seedu.address.model.expenditure.Expenditure;

/**
 * A utility class containing a list of {@code Expenditure} objects to be used in tests.
 */
public class TypicalPersons {

    public static final Expenditure ALICE = new PersonBuilder().withInfo("Alice Pauline")
            .withDate("2019-09-11").withAmount(3.14)
            .withId("94351253")
            .withTags("friends").build();
    public static final Expenditure BENSON = new PersonBuilder().withInfo("Benson Meier")
            .withDate("2019-09-11")
            .withAmount(3.14).withId("98765432")
            .withTags("owesMoney", "friends").build();
    public static final Expenditure CARL = new PersonBuilder().withInfo("Carl Kurz").withId("95352563")
            .withAmount(3.14).withDate("2019-09-11").build();
    public static final Expenditure DANIEL = new PersonBuilder().withInfo("Daniel Meier").withId("87652533")
            .withAmount(3.14).withDate("2019-09-11").withTags("friends").build();
    public static final Expenditure ELLE = new PersonBuilder().withInfo("Elle Meyer").withId("9482224")
            .withAmount(3.14).withDate("2019-09-11").build();
    public static final Expenditure FIONA = new PersonBuilder().withInfo("Fiona Kunz").withId("9482427")
            .withAmount(3.14).withDate("2019-09-11").build();
    public static final Expenditure GEORGE = new PersonBuilder().withInfo("George Best").withId("9482442")
            .withAmount(3.14).withDate("2019-09-11").build();

    // Manually added
    public static final Expenditure HOON = new PersonBuilder().withInfo("Hoon Meier").withId("8482424")
            .withAmount(3.14).withDate("2019-09-11").build();
    public static final Expenditure IDA = new PersonBuilder().withInfo("Ida Mueller").withId("8482131")
            .withAmount(3.14).withDate("2019-09-11").build();

    // Manually added - Expenditure's details found in {@code CommandTestUtil}
    public static final Expenditure AMY = new PersonBuilder().withInfo(VALID_INFO_AMY).withId(VALID_ID_AMY)
            .withAmount(VALID_AMOUNT_AMY).withDate(VALID_DATE_AMY).withTags(VALID_TAG_FRIEND).build();
    public static final Expenditure BOB = new PersonBuilder().withInfo(VALID_INFO_BOB).withId(VALID_ID_BOB)
            .withAmount(VALID_AMOUNT_BOB).withDate(VALID_DATE_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPersons() {} // prevents instantiation

    /**
     * Returns an {@code Account} with all the typical persons.
     */
    public static Account getTypicalAddressBook() {
        Account ab = new Account();
        for (Expenditure expenditure : getTypicalPersons()) {
            ab.addExpenditure(expenditure);
        }
        return ab;
    }

    public static List<Expenditure> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
