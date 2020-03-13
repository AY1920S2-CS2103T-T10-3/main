package seedu.address.testutil;

import seedu.address.model.Account;
import seedu.address.model.AccountList;
import seedu.address.model.expenditure.Expenditure;


/**
 * A utility class to help with building AccountList objects.
 * Example usage: <br>
 *     {@code Account ab = new AccountBuilder().withPerson("John", "Doe").build();}
 */
public class AccountListBuilder {

    private AccountList accountList;

    public AccountListBuilder() {
        accountList = new AccountList(false);
    }

    public AccountListBuilder(AccountList accountList) {
        this.accountList = accountList;
    }

    /**
     * Adds a new {@code Expenditure} to the {@code Account} that we are building.
     */
    public AccountListBuilder withAccount(Account account) {
        accountList.addAccount(account);
        return this;
    }

    public AccountList build() {
        return accountList;
    }
}
