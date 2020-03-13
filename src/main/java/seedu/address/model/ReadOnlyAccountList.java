package seedu.address.model;

import java.util.Map;

/**
 * Unmodifiable view of an account list.
 */
public interface ReadOnlyAccountList extends ReadOnlyAccount {
    /**
     * Returns a list of accounts.
     */
    Map<String, Account> getAccounts();
}
