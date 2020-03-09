package seedu.address.storage;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.Account;
import seedu.address.model.ReadOnlyAccount;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

/**
 * Represents a storage for {@link Account}.
 */
public interface AddressBookStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getAddressBookFilePath();

    /**
     * Returns Account data as a {@link ReadOnlyAccount}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException             if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyAccount> readAddressBook() throws DataConversionException, IOException;

    /**
     * @see #getAddressBookFilePath()
     */
    Optional<ReadOnlyAccount> readAddressBook(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyAccount} to the storage.
     *
     * @param addressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveAddressBook(ReadOnlyAccount addressBook) throws IOException;

    /**
     * @see #saveAddressBook(ReadOnlyAccount)
     */
    void saveAddressBook(ReadOnlyAccount addressBook, Path filePath) throws IOException;

}
