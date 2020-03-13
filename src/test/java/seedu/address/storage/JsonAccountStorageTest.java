package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalAccounts.getTypicalAccountList;
import static seedu.address.testutil.TypicalExpenditures.ALICE;
import static seedu.address.testutil.TypicalExpenditures.HOON;
import static seedu.address.testutil.TypicalExpenditures.IDA;
import static seedu.address.testutil.TypicalExpenditures.getTypicalAccount;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.Account;
import seedu.address.model.AccountList;
import seedu.address.model.ReadOnlyAccount;
import seedu.address.model.ReadOnlyAccountList;

public class JsonAccountStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonAccountStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readAddressBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readAddressBook(null));
    }

    private java.util.Optional<ReadOnlyAccountList> readAddressBook(String filePath) throws Exception {
        return new JsonAccountListStorage(Paths.get(filePath)).readAddressBook(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readAddressBook("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readAddressBook("notJsonFormatAddressBook.json"));
    }

    @Test
    public void readAddressBook_invalidPersonAddressBook_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readAddressBook("invalidPersonAddressBook.json"));
    }

    @Test
    public void readAddressBook_invalidAndValidPersonAddressBook_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readAddressBook("invalidAndValidPersonAddressBook.json"));
    }

    @Test
    public void readAndSaveAddressBook_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempAddressBook.json");
        AccountList original = getTypicalAccountList();
        JsonAccountListStorage jsonAccountListStorage = new JsonAccountListStorage(filePath);

        // Save in new file and read back
        jsonAccountListStorage.saveAddressBook(original, filePath);
        ReadOnlyAccountList readBack = jsonAccountListStorage.readAddressBook(filePath).get();
        assertEquals(original, new AccountList(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addExpenditure(HOON);
        original.removeExpenditure(ALICE);
        jsonAccountListStorage.saveAddressBook(original, filePath);
        readBack = jsonAccountListStorage.readAddressBook(filePath).get();
        assertEquals(original, new AccountList(readBack));

        // Save and read without specifying file path
        original.addExpenditure(IDA);
        jsonAccountListStorage.saveAddressBook(original); // file path not specified
        readBack = jsonAccountListStorage.readAddressBook().get(); // file path not specified
        assertEquals(original, new AccountList(readBack));

    }

    @Test
    public void saveAddressBook_nullAddressBook_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveAddressBook(null, "SomeFile.json"));
    }

    /**
     * Saves {@code addressBook} at the specified {@code filePath}.
     */
    private void saveAddressBook(ReadOnlyAccountList addressBook, String filePath) {
        try {
            new JsonAccountListStorage(Paths.get(filePath))
                    .saveAddressBook(addressBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveAddressBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveAddressBook(new AccountList(false), null));
    }
}
