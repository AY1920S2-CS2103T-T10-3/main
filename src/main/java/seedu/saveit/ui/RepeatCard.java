package seedu.saveit.ui;

import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.saveit.model.expenditure.Repeat;

/**
 * An UI component that displays information of a {@code Repeat}.
 */
public class RepeatCard extends UiPart<Region> {

    private static final String FXML = "RepeatListCard.fxml";
    private static final DateTimeFormatter DTF = DateTimeFormatter.ISO_DATE;
    private static final DecimalFormat TWO_DP = new DecimalFormat("0.00");

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on Account level 4</a>
     */

    public final Repeat repeat;

    @FXML
    private HBox repeatCardPane;
    @FXML
    private Label repeatInfo;
    @FXML
    private Label repeatNumber;
    @FXML
    private Label repeatAmount;
    @FXML
    private Label repeatDetails;
    @FXML
    private Label repeatTag;

    public RepeatCard(Repeat repeat, int displayedNumber) {
        super(FXML);
        this.repeat = repeat;
        repeatNumber.setText(displayedNumber + ". ");
        repeatInfo.setText(repeat.getInfo().fullInfo);
        repeatAmount.setText("$" + TWO_DP.format(repeat.getAmount().value));
        repeatTag.setText(repeat.getTag().tagName);

        if (repeat.getInfo().fullInfo.length() <= 10) {
            repeatInfo.setMinWidth(20 + (10 * repeat.getInfo().fullInfo.length()));
        } else {
            repeatInfo.setMinWidth(120);
        }

        if (repeat.getTag().tagName.length() <= 8) {
            repeatTag.setMinWidth(20 + (10 * repeat.getTag().tagName.length()));
        } else {
            repeatTag.setMinWidth(100);
        }

        String startDate = repeat.getStartDate().toString();
        String endDate = repeat.getEndDate().toString();
        String period = repeat.getPeriod().name();
        repeatDetails.setText(String.format("Repeated %s from %s to %s", period, startDate, endDate));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RepeatCard)) {
            return false;
        }

        // state check
        RepeatCard card = (RepeatCard) other;
        return repeatNumber.getText().equals(card.repeatNumber.getText())
                && repeat.equals(card.repeat);
    }
}
