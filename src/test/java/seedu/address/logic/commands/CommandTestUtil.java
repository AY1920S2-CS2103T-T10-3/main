package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INFO;

import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.expenditure.ExpEditCommand.EditExpenditureDescriptor;
import seedu.address.model.Account;
import seedu.address.model.Model;
import seedu.address.model.expenditure.Expenditure;
import seedu.address.model.expenditure.InfoContainsKeywordsPredicate;
import seedu.address.testutil.EditPersonDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_INFO_AMY = "Amy Bee";
    public static final String VALID_INFO_BOB = "Bob Choo";
    public static final String VALID_ID_AMY = "11111111";
    public static final String VALID_ID_BOB = "22222222";
    public static final double VALID_AMOUNT_AMY = 3.14;
    public static final double VALID_AMOUNT_BOB = 3.14;
    public static final String VALID_DATE_AMY = "2019-09-11";
    public static final String VALID_DATE_BOB = "2019-09-12";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";

    public static final String INFO_DESC_AMY = " " + PREFIX_INFO + VALID_INFO_AMY;
    public static final String INFO_DESC_BOB = " " + PREFIX_INFO + VALID_INFO_BOB;
    public static final String ID_DESC_AMY = " " + PREFIX_ID + VALID_ID_AMY;
    public static final String ID_DESC_BOB = " " + PREFIX_ID + VALID_ID_BOB;
    public static final String AMOUNT_DESC_AMY = " " + PREFIX_AMOUNT + VALID_AMOUNT_AMY;
    public static final String AMOUNT_DESC_BOB = " " + PREFIX_AMOUNT + VALID_AMOUNT_BOB;
    public static final String DATE_DESC_AMY = " " + PREFIX_DATE + VALID_DATE_AMY;
    public static final String DATE_DESC_BOB = " " + PREFIX_DATE + VALID_DATE_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;

    public static final String INVALID_INFO_DESC = " " + PREFIX_INFO + "James&"; // '&' not allowed in infos
    public static final String INVALID_ID_DESC = " " + PREFIX_ID + "911a"; // 'a' not allowed in ids
    public static final String INVALID_AMOUNT_DESC = " " + PREFIX_AMOUNT + "bob!yahoo"; // a string
    public static final String INVALID_DATE_DESC = " " + PREFIX_DATE; // empty string not allowed for addresses

    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditExpenditureDescriptor DESC_AMY;
    public static final EditExpenditureDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withInfo(VALID_INFO_AMY)
                .withId(VALID_ID_AMY).withAmount(VALID_AMOUNT_AMY).withDate(VALID_DATE_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        DESC_BOB = new EditPersonDescriptorBuilder().withInfo(VALID_INFO_BOB)
                .withId(VALID_ID_BOB).withAmount(VALID_AMOUNT_BOB).withDate(VALID_DATE_BOB)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book, filtered expenditure list and selected expenditure in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.

        Account expectedAccount = new Account(actualModel.getAccountList());
        List<Expenditure> expectedFilteredList = new ArrayList<>(actualModel.getFilteredExpenditureList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedAccount, actualModel.getAccountList());
        assertEquals(expectedFilteredList, actualModel.getFilteredExpenditureList());
    }
    /**
     * Updates {@code model}'s filtered list to show only the expenditure at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showPersonAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredExpenditureList().size());

        Expenditure expenditure = model.getFilteredExpenditureList().get(targetIndex.getZeroBased());
        final String[] splitInfo = expenditure.getInfo().fullInfo.split("\\s+");
        model.updateFilteredExpenditureList(new InfoContainsKeywordsPredicate(Arrays.asList(splitInfo[0])));
        assertEquals(1, model.getFilteredExpenditureList().size());
    }

}
