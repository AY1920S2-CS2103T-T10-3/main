package seedu.saveit.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.saveit.commons.core.LogsCenter;
import seedu.saveit.commons.exceptions.DataConversionException;
import seedu.saveit.commons.exceptions.IllegalValueException;
import seedu.saveit.commons.util.FileUtil;
import seedu.saveit.commons.util.JsonUtil;
import seedu.saveit.model.ReadOnlyAccountList;

/**
 * A class to access Account data stored as a json file on the hard disk.
 */
public class JsonAccountListStorage implements SaveItStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonAccountListStorage.class);

    private Path filePath;

    public JsonAccountListStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getAddressBookFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyAccountList> readAddressBook() throws DataConversionException {
        return readAddressBook(filePath);
    }

    /**
     * Similar to {@link #readAddressBook()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyAccountList> readAddressBook(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableAccountList> jsonAddressBook = JsonUtil.readJsonFile(
                filePath, JsonSerializableAccountList.class);
        if (!jsonAddressBook.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonAddressBook.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveSaveIt(ReadOnlyAccountList addressBook) throws IOException {
        saveSaveIt(addressBook, filePath);
    }

    /**
     * Similar to {@link #saveSaveIt(ReadOnlyAccountList)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveSaveIt(ReadOnlyAccountList addressBook, Path filePath) throws IOException {
        requireNonNull(addressBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableAccountList(addressBook), filePath);
    }

}
