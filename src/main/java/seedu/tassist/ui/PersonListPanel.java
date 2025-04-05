package seedu.tassist.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javafx.animation.FadeTransition;
import javafx.beans.property.BooleanProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import javafx.util.Duration;
import seedu.tassist.commons.core.LogsCenter;
import seedu.tassist.model.person.Person;

/**
 * Panel containing the list of persons.
 */
public class PersonListPanel extends UiPart<Region> {
    private static final String FXML = "PersonListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);
    private final Map<Person, PersonCard> displayedCards = new HashMap<>();
    private final BooleanProperty compactView;

    @FXML
    private ListView<Person> personListView;

    /**
     * Creates a {@code PersonListPanel} with the given {@code ObservableList}.
     */
    public PersonListPanel(ObservableList<Person> personList, BooleanProperty compactView) {
        super(FXML);
        this.compactView = compactView;
        personListView.setItems(personList);
        personListView.setCellFactory(listView -> new PersonListViewCell());

        compactView.addListener((obs,
                                 oldVal, newVal) -> personListView.refresh());

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
                personCard.getRoot().setOpacity(0);
                setGraphic(personCard.getRoot());
                FadeTransition fade = new FadeTransition(Duration.millis(200), personCard.getRoot());
                fade.setToValue(1);
                fade.play();

                boolean isCompactView = compactView.get();
                boolean isSelected = getListView().getSelectionModel().getSelectedItem() == person;
                personCard.showDetails(!isCompactView || isSelected);
            }
        }
    }

}
