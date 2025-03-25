package seedu.tassist.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.tassist.commons.core.LogsCenter;
import seedu.tassist.model.person.Person;

/**
 * Panel containing the list of persons.
 */
public class PersonListPanel extends UiPart<Region> {
    private static final String FXML = "PersonListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);
    private final Map<Person, PersonCard> displayedCards = new HashMap<>();

    @FXML
    private ListView<Person> personListView;

    /**
     * Creates a {@code PersonListPanel} with the given {@code ObservableList}.
     */
    public PersonListPanel(ObservableList<Person> personList) {
        super(FXML);
        personListView.setItems(personList);
        personListView.setCellFactory(listView -> new PersonListViewCell());

        personListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (oldValue != null && displayedCards.containsKey(oldValue)) {
                        displayedCards.get(oldValue).showDetails(false);
                    }

                    if (newValue != null && displayedCards.containsKey(newValue)) {
                        PersonCard selectedCard = displayedCards.get(newValue);
                        selectedCard.showDetails(true);
                    }
                });
    }

    /**
     * Custom {@code ListCell} that displays the graphics of
     * a {@code Person} using a {@code PersonCard}.
     */
    class PersonListViewCell extends ListCell<Person> {
        @Override
        protected void updateItem(Person person, boolean empty) {
            super.updateItem(person, empty);

            if (empty || person == null) {
                setGraphic(null);
                setText(null);
                displayedCards.remove(person);
            } else {
                PersonCard personCard = new PersonCard(person, getIndex() + 1);
                setGraphic(personCard.getRoot());

                // Store reference to the card.
                displayedCards.put(person, personCard);

                // Check if this item is currently selected and update visibility accordingly.
                boolean isSelected = getListView().getSelectionModel().getSelectedItem() == person;
                personCard.showDetails(isSelected);
            }
        }
    }

}
