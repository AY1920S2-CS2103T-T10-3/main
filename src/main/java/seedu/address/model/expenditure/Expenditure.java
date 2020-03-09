package seedu.address.model.expenditure;

import seedu.address.model.tag.Tag;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents a Expenditure in the id book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Expenditure {

    // Identity fields
    private final Info info;
    private final Amount amount;
    private final Date date;

    // Data fields
    private final Id id;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Expenditure(Info info, Amount amount, Date date, Id id, Set<Tag> tags) {
        requireAllNonNull(info, amount, date, id, tags);
        this.info = info;
        this.amount = amount;
        this.date = date;
        this.id = id;
        this.tags.addAll(tags);
    }

    public Info getInfo() {
        return info;
    }

    public Amount getAmount() {
        return amount;
    }

    public Date getDate() {
        return date;
    }

    public Id getId() {
        return id;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both persons of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Expenditure otherExpenditure) {
        if (otherExpenditure == this) {
            return true;
        }

        return otherExpenditure != null
                && otherExpenditure.getInfo().equals(getInfo())
                && (otherExpenditure.getAmount().equals(getAmount()) || otherExpenditure.getDate().equals(getDate()));
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Expenditure)) {
            return false;
        }

        Expenditure otherExpenditure = (Expenditure) other;
        return otherExpenditure.getInfo().equals(getInfo())
                && otherExpenditure.getAmount().equals(getAmount())
                && otherExpenditure.getDate().equals(getDate())
                && otherExpenditure.getId().equals(getId())
                && otherExpenditure.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(info, amount, date, id, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getInfo())
                .append(" Amount: ")
                .append(getAmount())
                .append(" Date: ")
                .append(getDate())
                .append(" Id: ")
                .append(getId())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
