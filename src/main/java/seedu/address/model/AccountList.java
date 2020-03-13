package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.expenditure.Expenditure;
import seedu.address.model.expenditure.UniqueExpenditureList;

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
    private final UniqueExpenditureList internalList = new UniqueExpenditureList();

    /**
     * Creates an AccountList using the accounts in the {@code toBeCopied}
     */
    public AccountList(ReadOnlyAccountList toBeCopied) {
        this();
        resetData(toBeCopied);
        activeAccount = accounts.get("default"); // TODO
        internalList.setExpenditures(activeAccount.getExpenditureList());
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

    //// expenditure-level operations

    public boolean hasExpenditure(Expenditure expenditure) {
        requireNonNull(expenditure);
        return internalList.contains(expenditure);
    }

    public void removeExpenditure(Expenditure target) {
        activeAccount.removeExpenditure(target);
        internalList.remove(target);
    }

    public void addExpenditure(Expenditure expenditure) {
        activeAccount.addExpenditure(expenditure);
        internalList.add(expenditure);
    }

    public void setExpenditure(Expenditure target, Expenditure editedExpenditure) {
        requireAllNonNull(target, editedExpenditure);
        activeAccount.setExpenditure(target, editedExpenditure);
        internalList.setExpenditure(target, editedExpenditure);
    }

    //// util methods


    public boolean updateActiveAccount(String accountName){
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
}
