package seedu.address.testutil;

import seedu.address.model.Account;
import seedu.address.model.expenditure.Expenditure;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 * {@code Account ab = new AddressBookBuilder().withPerson("John", "Doe").build();}
 */
public class AddressBookBuilder {

    private Account account;

    public AddressBookBuilder() {
        account = new Account();
    }

    public AddressBookBuilder(Account account) {
        this.account = account;
    }

    /**
     * Adds a new {@code Expenditure} to the {@code Account} that we are building.
     */
    public AddressBookBuilder withPerson(Expenditure expenditure) {
        account.addPerson(expenditure);
        return this;
    }

    public Account build() {
        return account;
    }
}
