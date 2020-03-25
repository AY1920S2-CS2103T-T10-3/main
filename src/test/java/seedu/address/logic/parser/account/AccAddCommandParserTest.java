package seedu.address.logic.parser.account;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.account.AccAddCommand;
import seedu.address.model.Account;
import seedu.address.testutil.AccountBuilder;

public class AccAddCommandParserTest {
    private final AccAddCommandParser parser = new AccAddCommandParser();

    @Test
    public void parse_validValue_success() {
        Account expectedAccount = new AccountBuilder("school").build();
        
        assertParseSuccess(parser, "school",
                new AccAddCommand(expectedAccount));

        // with preamble whitespace
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + "school",
                new AccAddCommand(expectedAccount));
    }

    @Test
    public void parse_invalidValue_nameContainSpace_parseException() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AccAddCommand.NAME_CONTAIN_SPACE);

        // contains space
        assertParseFailure(parser, "my account", expectedMessage);

        // empty
        assertParseFailure(parser, "", expectedMessage);
    }

    @Test
    public void parse_invalidValue_nameTooLong_parseException() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AccAddCommand.NAME_TOO_LONG);

        assertParseFailure(parser, "thisIsAVeryLongAccountName", expectedMessage);
    }
}
