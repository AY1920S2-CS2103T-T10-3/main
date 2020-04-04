package seedu.saveit.storage;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.saveit.commons.exceptions.IllegalValueException;

import seedu.saveit.model.Account;
import seedu.saveit.model.expenditure.Amount;
import seedu.saveit.model.expenditure.Expenditure;
import seedu.saveit.model.expenditure.Repeat;

/**
 * An Immutable Account that is serializable to JSON format.
 */
@JsonRootName(value = "saveit")
class JsonSerializableAccount {

    public static final String MESSAGE_DUPLICATE_EXPENDITURE = "Expenditures list contains duplicate expenditure(s).";

    private final List<JsonAdaptedExpenditure> expenditures = new ArrayList<>();
    private final List<JsonAdaptedRepeat> repeats = new ArrayList<>();
    private final HashMap<YearMonth, Double> budgets = new HashMap<>();
    private final String accountName;

    /**
     * Constructs a {@code JsonSerializableAccount} with the given expenditures and accountName.
     */
    @JsonCreator
    public JsonSerializableAccount(@JsonProperty("accountName") String accountName,
                                   @JsonProperty("expenditures") List<JsonAdaptedExpenditure> expenditures,
                                   @JsonProperty("repeats") List<JsonAdaptedRepeat> repeats,
                                   @JsonProperty("budgets") HashMap<YearMonth, Double> budgets) {
        this.accountName = accountName;
        this.expenditures.addAll(expenditures);
        this.repeats.addAll(repeats);
        this.budgets.putAll(budgets);
    }

    /**
     * Converts a given {@code Account} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAccount}.
     */
    public JsonSerializableAccount(Account source) {
        expenditures.addAll(source.getExpenditureList().stream().map(JsonAdaptedExpenditure::new)
                .collect(Collectors.toList()));
        repeats.addAll(source.getRepeatList().stream().map(JsonAdaptedRepeat::new).collect(Collectors.toList()));
        accountName = source.getAccountName();
        budgets.putAll(source.getBudgetList().getBudgets());
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
            account.addExpenditure(expenditure);
        }
        for (JsonAdaptedRepeat jsonAdaptedRepeat : repeats) {
            Repeat repeat = jsonAdaptedRepeat.toModelType();
            account.addRepeat(repeat);
        }
        for (YearMonth yearMonth : budgets.keySet()) {
            account.setBudget(yearMonth, new Amount(budgets.get(yearMonth)));
        }
        return account;
    }

}
