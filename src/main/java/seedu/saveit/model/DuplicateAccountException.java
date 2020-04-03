package seedu.saveit.model;

/**
 * Signals that the operation will result in duplicate Expenditures
 * (Expenditures are considered duplicates if they have the same identity).
 */
public class DuplicateAccountException extends RuntimeException {
    public DuplicateAccountException() {
        super("Operation would result in duplicate accounts");
    }
}
