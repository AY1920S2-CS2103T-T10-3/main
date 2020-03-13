package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javafx.collections.ObservableList;
import seedu.address.model.expenditure.Expenditure;
import seedu.address.model.expenditure.UniqueExpenditureList;

/**
 * Manages all accounts of the user.
 */
public class AccountList implements ReadOnlyAccountList, ReadOnlyAccount {
    private Map<String, Account> accounts = new HashMap<>();
    private Account activeAccount;
    private final UniqueExpenditureList internalList = new UniqueExpenditureList();
    private String initialAccountName = "default"; // TODO

    /**
     * Creates an AccountList using the accounts in the {@code toBeCopied}
     */
    public AccountList(ReadOnlyAccountList toBeCopied) {
        resetData(toBeCopied);
        activeAccount = accounts.get(initialAccountName);
        if (accounts.size() == 0) {
            activeAccount = new Account("default");
            accounts.put("default", activeAccount);
        } else if (!accounts.containsKey(initialAccountName)) {
            activeAccount = accounts.values().iterator().next();
        } else {
            activeAccount = accounts.get(initialAccountName);
        }
        internalList.setExpenditures(activeAccount.getExpenditureList());
    }

    public AccountList(boolean createDefaultAccount) {
        if (createDefaultAccount) {
            activeAccount = new Account("default");
            addAccount(activeAccount);
        }
    }

    public AccountList(Account activeAccount) {
        this.activeAccount = activeAccount;
    }

    //// list overwrite operations

    /**
     * Resets the existing data of this {@code Account} with {@code newData}.
     */
    public void resetData(ReadOnlyAccountList newData) {
        requireNonNull(newData);
        setAccounts(newData.getAccounts());
    }

    private void setAccounts(Map<String, Account> accountHashMap) {
        requireAllNonNull(accountHashMap);
        accounts = new HashMap<>();
        for (Map.Entry<String, Account> entry : accountHashMap.entrySet()) {
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
        requireNonNull(account);
        if (accounts.containsKey(account.getAccountName())) {
            throw new DuplicateAccountException();
        }
        accounts.put(account.getAccountName(), account);
    }

    //// expenditure-level operations

    /**
     * Returns true if a expenditure with the same identity as {@code expenditure} exists in the internal list.
     */
    public boolean hasExpenditure(Expenditure expenditure) {
        requireNonNull(expenditure);
        return internalList.contains(expenditure);
    }

    /**
     * Deletes the given expenditure.
     * The expenditure must exist in the internal list.
     */
    public void removeExpenditure(Expenditure target) {
        activeAccount.removeExpenditure(target);
        internalList.remove(target);
    }

    /**
     * Adds the given expenditure.
     * {@code expenditure} must not already exist in the internal list.
     */
    public void addExpenditure(Expenditure expenditure) {
        activeAccount.addExpenditure(expenditure);
        internalList.add(expenditure);
    }

    /**
     * Replaces the given expenditure {@code target} with {@code editedExpenditure}.
     * {@code target} must exist in the internal list.
     * The expenditure identity of {@code editedExpenditure} must not be the same as
     * another existing expenditure in the internal list.
     */
    public void setExpenditure(Expenditure target, Expenditure editedExpenditure) {
        requireAllNonNull(target, editedExpenditure);
        activeAccount.setExpenditure(target, editedExpenditure);
        internalList.setExpenditure(target, editedExpenditure);
    }

    //// util methods


    /**
     * Updates the active account to the one with the specified accountName.
     * @param accountName the name of the account
     * @return if the update was successful
     */
    public boolean updateActiveAccount(String accountName) {
        if (!accounts.containsKey(accountName)) {
            return false;
        } else {
            activeAccount = accounts.get(accountName);
            internalList.setExpenditures(activeAccount.getExpenditureList());
            return true;
        }
    }

    @Override
    public Map<String, Account> getAccounts() {
        return Collections.unmodifiableMap(accounts);
    }

    @Override
    public ObservableList<Expenditure> getExpenditureList() {
        return internalList.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AccountList // instanceof handles nulls
                && accounts.equals(((AccountList) other).accounts));
    }

    @Override
    public String toString() {
        return "AccountList: " + internalList.toString();
    }

}
