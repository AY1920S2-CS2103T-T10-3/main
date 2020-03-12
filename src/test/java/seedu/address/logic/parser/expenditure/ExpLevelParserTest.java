package seedu.address.logic.parser.expenditure;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.expenditure.ExpAddCommand;

import seedu.address.logic.commands.expenditure.ExpDeleteCommand;
import seedu.address.logic.commands.expenditure.ExpEditCommand;
import seedu.address.logic.commands.expenditure.ExpEditCommand.EditExpenditureDescriptor;
import seedu.address.logic.commands.expenditure.ExpFindCommand;
import seedu.address.logic.commands.general.HelpCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.expenditure.Expenditure;
import seedu.address.model.expenditure.InfoContainsKeywordsPredicate;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonUtil;

public class ExpLevelParserTest {
    private final ExpLevelParser parser = new ExpLevelParser();

    //TODO: REPEAT AND SETBUDGET

    @Test
    public void parseExpCommand_add() throws Exception {
        Expenditure expenditure = new PersonBuilder().build();
        ExpAddCommand command = (ExpAddCommand) parser.expParseCommand(PersonUtil.getAddCommand(expenditure));
        assertEquals(new ExpAddCommand(expenditure), command);
    }


    @Test
    public void parseExpCommand_delete() throws Exception {
        ExpDeleteCommand command = (ExpDeleteCommand) parser.expParseCommand(
                seedu.address.logic.commands.expenditure.ExpDeleteCommand.COMMAND_WORD
                        + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new ExpDeleteCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        ExpFindCommand command = (ExpFindCommand) parser.expParseCommand(
                ExpFindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new ExpFindCommand(new InfoContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseExpCommand_edit() throws Exception {
        Expenditure person = new PersonBuilder().build();
        EditExpenditureDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        ExpEditCommand command = (ExpEditCommand) parser.expParseCommand(ExpEditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PersonUtil.getEditPersonDescriptorDetails(descriptor));
        assertEquals(new ExpEditCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void expParseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.expParseCommand(""));
    }

    @Test
    public void expParseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.expParseCommand("unknownCommand"));
    }

}
