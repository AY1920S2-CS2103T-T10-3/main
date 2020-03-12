package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;

import seedu.address.model.Account;
import seedu.address.model.ReadOnlyAccount;
import seedu.address.model.expenditure.Expenditure;

/**
 * An Immutable Account that is serializable to JSON format.
 */
@JsonRootName(value = "saveit")
class JsonSerializableAccount {

    public static final String MESSAGE_DUPLICATE_EXPENDITURE = "Expenditures list contains duplicate expenditure(s).";

    private final List<JsonAdaptedExpenditure> expenditures = new ArrayList<>();
    private final String accountName;

    /**
     * Constructs a {@code JsonSerializableAccount} with the given expenditures and accountName.
     */
    @JsonCreator
    public JsonSerializableAccount(@JsonProperty("accountName") String accountName, @JsonProperty("expenditures") List<JsonAdaptedExpenditure> expenditures) {
        this.accountName = accountName;
        this.expenditures.addAll(expenditures);
    }

    /**
     * Converts a given {@code Account} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAccount}.
     */
    public JsonSerializableAccount(Account source) {
        expenditures.addAll(source.getExpenditureList().stream().map(JsonAdaptedExpenditure::new)
                .collect(Collectors.toList()));
        accountName = source.getAccountName();
    }

    /**
     * Converts this address book into the model's {@code Account} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public Account toModelType() throws IllegalValueException {
        Account account = new Account(accountName);
        for (JsonAdaptedExpenditure jsonAdaptedExpenditure : expenditures) {
            Expenditure expenditure = jsonAdaptedExpenditure.toModelType();
            if (account.hasExpenditure(expenditure)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_EXPENDITURE);
            }
            account.addExpenditure(expenditure);

        }
        return account;
    }

}
