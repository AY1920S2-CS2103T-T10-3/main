package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.expenditure.Expenditure;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Manages all accounts of the user.
 */
public class AccountList implements ReadOnlyAccountList, ReadOnlyAccount {
    private Map<String, Account> accounts = new HashMap<>();
    private Account activeAccount;

    /**
     * Creates an AccountList using the accounts in the {@code toBeCopied}
     */
    public AccountList(ReadOnlyAccountList toBeCopied) {
        this();
        resetData(toBeCopied);
        activeAccount = accounts.get("default"); // TODO
    }

    public AccountList(boolean createDefaultAccount) {
        if (createDefaultAccount){
            activeAccount = new Account("default");
            addAccount(activeAccount);
        }
    }

    public AccountList() { }

    //// list overwrite operations

    /**
     * Resets the existing data of this {@code Account} with {@code newData}.
     */
    public void resetData(ReadOnlyAccountList newData) {
        requireNonNull(newData);
        setAccounts(newData.getAccounts());
    }

    private void setAccounts(Map<String, Account> accountHashMap){
        requireAllNonNull(accountHashMap);
        accounts = new HashMap<>();
        for (Map.Entry<String, Account> entry : accountHashMap.entrySet()){
            accounts.put(entry.getKey(), entry.getValue());
        }
    }

    //// account-level operations

    /**
     * Returns true if a account with the same identity as {@code account} exists in the account list.
     */
    public boolean hasAccount(Account account) {
        requireNonNull(account);
        return accounts.containsKey(account.getAccountName());
    }

    /**
     * Adds an account to the account list.
     * The account must not already exist in the account list.
     */
    public void addAccount(Account account) {
        accounts.put(account.getAccountName(), account);
    }

    //// util methods

    public Account getActiveAccount() {
        return activeAccount;
    }

    @Override
    public Map<String, Account> getAccounts() {
        return Collections.unmodifiableMap(accounts);
    }

    @Override
    public ObservableList<Expenditure> getExpenditureList() {
        return activeAccount.getExpenditureList();
    }
}
