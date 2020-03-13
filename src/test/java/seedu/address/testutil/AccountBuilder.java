package seedu.address.testutil;

import seedu.address.model.Account;

import seedu.address.model.expenditure.Expenditure;


/**
 * A utility class to help with building Account objects.
 * Example usage: <br>
 *     {@code Account ab = new AccountBuilder().withPerson("John", "Doe").build();}
 */
public class AccountBuilder {

    private Account account;

    public AccountBuilder(String accountName) {
        account = new Account(accountName);
    }

    public AccountBuilder(Account account) {
        this.account = account;
    }

    /**
     * Adds a new {@code Expenditure} to the {@code Account} that we are building.
     */
    public AccountBuilder withExpenditure(Expenditure expenditure) {
        account.addExpenditure(expenditure);
        return this;
    }

    public Account build() {
        return account;
    }
}
