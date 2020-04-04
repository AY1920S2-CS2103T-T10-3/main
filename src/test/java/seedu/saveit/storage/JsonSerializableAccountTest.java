package seedu.saveit.storage;

// import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.saveit.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.saveit.commons.exceptions.IllegalValueException;
import seedu.saveit.commons.util.JsonUtil;
// import seedu.saveit.model.Account;
// import seedu.saveit.model.AccountList;
// import seedu.saveit.testutil.TypicalAccounts;
// import seedu.saveit.testutil.TypicalExpenditures;

public class JsonSerializableAccountTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableAccountTest");
    private static final Path TYPICAL_EXPENDITURES_FILE =
            TEST_DATA_FOLDER.resolve("typicalExpendituresAddressBook.json");
    private static final Path INVALID_EXPENDITURE_FILE =
            TEST_DATA_FOLDER.resolve("invalidExpenditureAddressBook.json");
    // private static final Path DUPLICATE_EXPENDITURE_FILE =
    //        TEST_DATA_FOLDER.resolve("duplicateExpenditureAddressBook.json");

    // TODO: update test case to reflect updated account
    // @Test
    // public void toModelType_typicalExpendituresFile_success() throws Exception {
    //     JsonSerializableAccountList dataFromFile = JsonUtil.readJsonFile(TYPICAL_EXPENDITURES_FILE,
    //             JsonSerializableAccountList.class).get();
    //     AccountList accountFromFile = dataFromFile.toModelType();
    //     AccountList typicalExpendituresAccount = TypicalAccounts.getTypicalAccountList();
    //     assertEquals(accountFromFile, typicalExpendituresAccount);
    // }

    @Test
    public void toModelType_invalidExpenditureFile_throwsIllegalValueException() throws Exception {
        JsonSerializableAccountList dataFromFile = JsonUtil.readJsonFile(INVALID_EXPENDITURE_FILE,
                JsonSerializableAccountList.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    // TODO: check for duplicate account instead of expenditures
    // @Test
    // public void toModelType_duplicateExpenditures_throwsIllegalValueException() throws Exception {
    //     JsonSerializableAccountList dataFromFile = JsonUtil.readJsonFile(DUPLICATE_EXPENDITURE_FILE,
    //             JsonSerializableAccountList.class).get();
    //     assertThrows(IllegalValueException.class, JsonSerializableAccount.MESSAGE_DUPLICATE_EXPENDITURE,
    //             dataFromFile::toModelType);
    // }

}
