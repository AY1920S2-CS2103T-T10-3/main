package seedu.address.model.util;

import seedu.address.model.Account;
import seedu.address.model.ReadOnlyAccount;
import seedu.address.model.expenditure.Amount;
import seedu.address.model.expenditure.Date;
import seedu.address.model.expenditure.Expenditure;
import seedu.address.model.expenditure.Id;
import seedu.address.model.expenditure.Info;
import seedu.address.model.tag.Tag;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Contains utility methods for populating {@code Account} with sample data.
 */
public class SampleDataUtil {
    public static Expenditure[] getSamplePersons() {
        return new Expenditure[]{
                new Expenditure(new Info("Alex Yeoh"), new Amount("87438807"), new Date("alexyeoh@example.com"),
                        new Id("Blk 30 Geylang Street 29, #06-40"),
                        getTagSet("friends")),
                new Expenditure(new Info("Bernice Yu"), new Amount("99272758"), new Date("berniceyu@example.com"),
                        new Id("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                        getTagSet("colleagues", "friends")),
                new Expenditure(new Info("Charlotte Oliveiro"), new Amount("93210283"), new Date("charlotte@example.com"),
                        new Id("Blk 11 Ang Mo Kio Street 74, #11-04"),
                        getTagSet("neighbours")),
                new Expenditure(new Info("David Li"), new Amount("91031282"), new Date("lidavid@example.com"),
                        new Id("Blk 436 Serangoon Gardens Street 26, #16-43"),
                        getTagSet("family")),
                new Expenditure(new Info("Irfan Ibrahim"), new Amount("92492021"), new Date("irfan@example.com"),
                        new Id("Blk 47 Tampines Street 20, #17-35"),
                        getTagSet("classmates")),
                new Expenditure(new Info("Roy Balakrishnan"), new Amount("92624417"), new Date("royb@example.com"),
                        new Id("Blk 45 Aljunied Street 85, #11-31"),
                        getTagSet("colleagues"))
        };
    }

    public static ReadOnlyAccount getSampleAddressBook() {
        Account sampleAb = new Account();
        for (Expenditure sampleExpenditure : getSamplePersons()) {
            sampleAb.addPerson(sampleExpenditure);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
