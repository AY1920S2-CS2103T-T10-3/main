package seedu.address.logic.commands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.address.model.AccountManager;
import seedu.address.model.Model;
import seedu.address.model.UserPrefs;
import seedu.address.model.expenditure.Expenditure;
import seedu.address.testutil.PersonBuilder;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new AccountManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newPerson_success() {
        Expenditure validExpenditure = new PersonBuilder().build();

        Model expectedModel = new AccountManager(model.getAccount(), new UserPrefs());
        expectedModel.addPerson(validExpenditure);

        assertCommandSuccess(new AddCommand(validExpenditure), model,
                String.format(AddCommand.MESSAGE_SUCCESS, validExpenditure), expectedModel);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Expenditure expenditureInList = model.getAccount().getAccountList().get(0);
        assertCommandFailure(new AddCommand(expenditureInList), model, AddCommand.MESSAGE_DUPLICATE_PERSON);
    }

}
