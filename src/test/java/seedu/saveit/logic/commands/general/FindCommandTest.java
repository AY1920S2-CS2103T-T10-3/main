package seedu.saveit.logic.commands.general;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.saveit.testutil.TypicalAccounts.getTypicalAccountList;
// import static seedu.saveit.testutil.TypicalExpenditures.CARL;
// import static seedu.saveit.testutil.TypicalExpenditures.ELLE;
// import static seedu.saveit.testutil.TypicalExpenditures.FIONA;
// import static seedu.saveit.testutil.TypicalExpenditures.getTypicalAccount;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.saveit.model.Model;
import seedu.saveit.model.ModelManager;
import seedu.saveit.model.UserPrefs;
import seedu.saveit.model.expenditure.InfoContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelManager(getTypicalAccountList(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAccountList(), new UserPrefs());

    @Test
    public void equals() {
        InfoContainsKeywordsPredicate firstPredicate =
                new InfoContainsKeywordsPredicate(Collections.singletonList("first"));
        InfoContainsKeywordsPredicate secondPredicate =
                new InfoContainsKeywordsPredicate(Collections.singletonList("second"));

        FindCommand findFirstCommand = new FindCommand(firstPredicate);
        FindCommand findSecondCommand = new FindCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different expenditure -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    /*
    @Test
    public void execute_zeroKeywords_noExpenditureFound() {
        String expectedMessage = String.format(MESSAGE_EXPENDITURES_LISTED_OVERVIEW, 0);
        InfoContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredBaseExpList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredBaseExpList());
    }
     */

    // TODO: update test case
    // @Test
    // public void execute_multipleKeywords_multipleExpendituresFound() {
    //     String expectedMessage = String.format(MESSAGE_EXPENDITURES_LISTED_OVERVIEW, 3);
    //     InfoContainsKeywordsPredicate predicate = preparePredicate("Kurz Elle Kunz");
    //     FindCommand command = new FindCommand(predicate);
    //     expectedModel.updateFilteredExpenditureList(predicate);
    //     assertCommandSuccess(command, model, expectedMessage, expectedModel);
    //     assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredExpenditureList());
    // }

    /**
     * Parses {@code userInput} into a {@code InfoContainsKeywordsPredicate}.
     */
    private InfoContainsKeywordsPredicate preparePredicate(String userInput) {
        return new InfoContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
