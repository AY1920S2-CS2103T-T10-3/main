package seedu.address.model;

import java.util.Map;

public interface ReadOnlyAccountList {
    Map<String, Account> getAccounts();
}
