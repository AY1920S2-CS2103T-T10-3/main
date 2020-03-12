package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.Account;
import seedu.address.model.ReadOnlyAccount;
import seedu.address.model.expenditure.Amount;
import seedu.address.model.expenditure.Date;
import seedu.address.model.expenditure.Expenditure;
import seedu.address.model.expenditure.Id;
import seedu.address.model.expenditure.Info;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code Account} with sample data.
 */
public class SampleDataUtil {

    public static Expenditure[] getSamplePersons() {
        return new Expenditure[] {
            new Expenditure(new Info("Alex Yeoh"), new Id("87438807"), new Amount(3.1),
                    new Date("2019-09-11"), getTagSet("friends")),
            new Expenditure(new Info("Bernice Yu"), new Id("99272758"), new Amount(3.1),
                    new Date("2019-09-12"), getTagSet("colleagues", "friends")),
            new Expenditure(new Info("Charlotte Oliveiro"), new Id("93210283"), new Amount(3.1),
                    new Date("2019-09-13"), getTagSet("neighbours")),
            new Expenditure(new Info("David Li"), new Id("91031282"), new Amount(3.1),
                    new Date("2019-09-14"), getTagSet("family")),
            new Expenditure(new Info("Irfan Ibrahim"), new Id("92492021"), new Amount(3.1),
                    new Date("2019-09-15"), getTagSet("classmates")),
            new Expenditure(new Info("Roy Balakrishnan"), new Id("92624417"), new Amount(3.1),
                    new Date("2019-09-11"), getTagSet("colleagues"))
        };
    }


    public static ReadOnlyAccount getSampleAddressBook() {
        Account sampleAb = new Account();
        for (Expenditure sampleExpenditure : getSamplePersons()) {
            sampleAb.addExpenditure(sampleExpenditure);

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
