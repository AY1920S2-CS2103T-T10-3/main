package seedu.address.testutil;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.model.expenditure.Expenditure;
import seedu.address.model.tag.Tag;

import java.util.Set;

import static seedu.address.logic.parser.CliSyntax.*;

/**
 * A utility class for Expenditure.
 */
public class PersonUtil {

    /**
     * Returns an add command string for adding the {@code expenditure}.
     */
    public static String getAddCommand(Expenditure expenditure) {
        return AddCommand.COMMAND_WORD + " " + getPersonDetails(expenditure);
    }

    /**
     * Returns the part of command string for the given {@code expenditure}'s details.
     */
    public static String getPersonDetails(Expenditure expenditure) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + expenditure.getInfo().fullName + " ");
        sb.append(PREFIX_PHONE + expenditure.getAmount().value + " ");
        sb.append(PREFIX_EMAIL + expenditure.getDate().value + " ");
        sb.append(PREFIX_ADDRESS + expenditure.getId().value + " ");
        expenditure.getTags().stream().forEach(
                s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditPersonDescriptor}'s details.
     */
    public static String getEditPersonDescriptorDetails(EditPersonDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getInfo().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getAmount().ifPresent(phone -> sb.append(PREFIX_PHONE).append(phone.value).append(" "));
        descriptor.getDate().ifPresent(email -> sb.append(PREFIX_EMAIL).append(email.value).append(" "));
        descriptor.getId().ifPresent(address -> sb.append(PREFIX_ADDRESS).append(address.value).append(" "));
        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_TAG);
            } else {
                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.tagName).append(" "));
            }
        }
        return sb.toString();
    }
}
