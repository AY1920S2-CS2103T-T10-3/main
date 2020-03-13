package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.Account;
import seedu.address.model.AccountList;
import seedu.address.model.ReadOnlyAccountList;

/**
 * An Immutable AccountList that is serializable to JSON format.
 */
@JsonRootName(value = "saveit")
public class JsonSerializableAccountList {

    public static final String MESSAGE_DUPLICATE_ACCOUNT = "Accounts list contains duplicate account(s).";

    private final List<JsonSerializableAccount> accounts = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAccountList} with the given accounts.
     */
    @JsonCreator
    public JsonSerializableAccountList(@JsonProperty("accounts") List<JsonSerializableAccount> accounts) {
        this.accounts.addAll(accounts);
    }

    /**
     * Converts a given {@code AccountList} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAccountList}.
     */
    public JsonSerializableAccountList(ReadOnlyAccountList source) {
        accounts.addAll(source.getAccounts().values().stream()
                .map(JsonSerializableAccount::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code AccountList} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public AccountList toModelType() throws IllegalValueException {
        AccountList accountList = new AccountList(false);
        for (JsonSerializableAccount jsonAdaptedAccount: accounts) {
            Account account = jsonAdaptedAccount.toModelType();
            if (accountList.hasAccount(account)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_ACCOUNT);
            }
            accountList.addAccount(account);
        }
        return accountList;
    }
}
