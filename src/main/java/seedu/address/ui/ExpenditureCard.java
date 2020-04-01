package seedu.address.ui;

import java.text.DecimalFormat;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.expenditure.Expenditure;

/**
 * An UI component that displays information of a {@code Expenditure}.
 */
public class ExpenditureCard extends UiPart<Region> {

    private static final String FXML = "ExpenditureListCard.fxml";

    private static final DecimalFormat TWO_DP = new DecimalFormat("0.00");

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on Account level 4</a>
     */

    public final Expenditure expenditure;

    @FXML
    private HBox cardPane;
    @FXML
    private Label info;
    @FXML
    private Label number;
    @FXML
    private Label amount;
    @FXML
    private Label tag;

    public ExpenditureCard(Expenditure expenditure, int displayedNumber) {
        super(FXML);

        this.expenditure = expenditure;
        number.setText(displayedNumber + ". ");
        info.setText(expenditure.getInfo().fullInfo);
        amount.setText("$" + TWO_DP.format(expenditure.getAmount().value));
        tag.setText(expenditure.getTag().tagName);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ExpenditureCard)) {
            return false;
        }

        // state check
        ExpenditureCard card = (ExpenditureCard) other;
        return number.getText().equals(card.number.getText())
                && expenditure.equals(card.expenditure);
    }
}
