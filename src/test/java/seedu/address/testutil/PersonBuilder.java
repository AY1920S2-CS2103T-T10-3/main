package seedu.address.testutil;

import seedu.address.model.expenditure.Amount;
import seedu.address.model.expenditure.Date;
import seedu.address.model.expenditure.Expenditure;
import seedu.address.model.expenditure.Id;
import seedu.address.model.expenditure.Info;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

import java.util.HashSet;
import java.util.Set;

/**
 * A utility class to help with building Expenditure objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "alice@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";

    private Info info;
    private Amount amount;
    private Date date;
    private Id id;
    private Set<Tag> tags;

    public PersonBuilder() {
        info = new Info(DEFAULT_NAME);
        amount = new Amount(DEFAULT_PHONE);
        date = new Date(DEFAULT_EMAIL);
        id = new Id(DEFAULT_ADDRESS);
        tags = new HashSet<>();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code expenditureToCopy}.
     */
    public PersonBuilder(Expenditure expenditureToCopy) {
        info = expenditureToCopy.getInfo();
        amount = expenditureToCopy.getAmount();
        date = expenditureToCopy.getDate();
        id = expenditureToCopy.getId();
        tags = new HashSet<>(expenditureToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Expenditure} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.info = new Info(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Expenditure} that we are building.
     */
    public PersonBuilder withTags(String... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Id} of the {@code Expenditure} that we are building.
     */
    public PersonBuilder withAddress(String address) {
        this.id = new Id(address);
        return this;
    }

    /**
     * Sets the {@code Amount} of the {@code Expenditure} that we are building.
     */
    public PersonBuilder withPhone(String phone) {
        this.amount = new Amount(phone);
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code Expenditure} that we are building.
     */
    public PersonBuilder withEmail(String email) {
        this.date = new Date(email);
        return this;
    }

    public Expenditure build() {
        return new Expenditure(info, amount, date, id, tags);
    }

}
