package seedu.address.logic.commands;

import org.junit.jupiter.api.Test;
import seedu.address.model.Account;
import seedu.address.model.AccountManager;
import seedu.address.model.Model;
import seedu.address.model.UserPrefs;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

public class ClearCommandTest {

    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new AccountManager();
        Model expectedModel = new AccountManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = new AccountManager(getTypicalAddressBook(), new UserPrefs());
        Model expectedModel = new AccountManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.setAccount(new Account());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
