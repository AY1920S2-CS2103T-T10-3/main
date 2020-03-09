package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.expenditure.Expenditure;

/**
 * Panel containing the list of persons.
 */
public class PersonListPanel extends UiPart<Region> {
    private static final String FXML = "PersonListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);

    @FXML
    private ListView<Expenditure> personListView;

    public PersonListPanel(ObservableList<Expenditure> expenditureList) {
        super(FXML);
        personListView.setItems(expenditureList);
        personListView.setCellFactory(listView -> new PersonListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Expenditure} using a {@code PersonCard}.
     */
    class PersonListViewCell extends ListCell<Expenditure> {
        @Override
        protected void updateItem(Expenditure expenditure, boolean empty) {
            super.updateItem(expenditure, empty);

            if (empty || expenditure == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new PersonCard(expenditure, getIndex() + 1).getRoot());
            }
        }
    }

}
