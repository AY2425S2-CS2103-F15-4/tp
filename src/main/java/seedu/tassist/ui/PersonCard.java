package seedu.tassist.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.tassist.model.person.Attendance;
import seedu.tassist.model.person.Person;

/**
 * A UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">
     *     The issue on AddressBook level 4</a>
     */

    public final Person person;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label contact;
    @FXML
    private Label email;
    @FXML
    private Label matNum;
    @FXML
    private Label classGroup;
    @FXML
    private Label facAndYear;
    @FXML
    private Label remark;
    @FXML
    private FlowPane tags;
    @FXML
    private FlowPane attendances;
    @FXML
    private FlowPane labScores;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + "");
        name.setText(person.getName().fullName);
        matNum.setText("(" + person.getMatNum().value + ")");

        // Guaranteed for either tutGroup or labGroup to have a value.
        assert !(person.getTutGroup().isEmpty() && person.getLabGroup().isEmpty())
                : "Both tutGroup and labGroup cannot be empty simultaneously";
        if (person.getTutGroup().isEmpty() && !person.getLabGroup().isEmpty()) {
            classGroup.setText(person.getLabGroup().value);
        } else if (!person.getTutGroup().isEmpty() && person.getLabGroup().isEmpty()) {
            classGroup.setText(person.getTutGroup().value);
        } else {
            classGroup.setText(person.getTutGroup().value + " | " + person.getLabGroup().value);
        }

        // Guaranteed for either phone or telegram Handle to have a value
        assert !(person.getPhone().isEmpty() && person.getTeleHandle().isEmpty())
                : "Both phone and teleHandle cannot be empty simultaneously";
        if (person.getPhone().isEmpty() && !person.getTeleHandle().isEmpty()) {
            contact.setText(person.getTeleHandle().value);
        } else if (!person.getPhone().isEmpty() && person.getTeleHandle().isEmpty()) {
            contact.setText(person.getPhone().value);
        } else {
            contact.setText(person.getPhone().value + "    " + person.getTeleHandle().value);
        }

        email.setText(person.getEmail().value);

        // Not guaranteed for year or faculty to be present
        if (person.getYear().isEmpty() && !person.getFaculty().isEmpty()) {
            facAndYear.setText(person.getFaculty().value);
        } else if (!person.getYear().isEmpty() && person.getFaculty().isEmpty()) {
            facAndYear.setText("Y" + person.getYear().value);
        } else if (!person.getYear().isEmpty() && !person.getFaculty().isEmpty()) {
            facAndYear.setText("Y" + person.getYear().value + "  \u2022  "
                    + person.getFaculty().value);
        }


        if (!person.getRemark().value.isEmpty()) {
            remark.setText(person.getRemark().value);
        } else {
            remark.setVisible(false);
            remark.setManaged(false);
        }

        labScores.getChildren().add(new Label("Lab grades:"));

        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));

        person.getAttendanceList().getAttendanceStream()
                .forEach(attendance -> {
                    String tagPrefix = attendance.getWeekAsTagPrefix();
                    HBox hBox = new HBox(2);
                    hBox.getStyleClass().add("hbox");
                    switch (attendance.getAttendance()) {
                    case Attendance.ON_MC:
                        hBox.setStyle("-fx-background-color: #df6d14;");
                        hBox.getChildren().add(new Label(tagPrefix + " MC"));
                        break;
                    case Attendance.ATTENDED:
                        hBox.setStyle("-fx-background-color: #5cb338;");
                        hBox = addLabelAndImageViewToHBox(hBox, tagPrefix, Attendance.ATTENDED_IMAGE_PATH);
                        break;
                    case Attendance.NOT_ATTENDED:
                        hBox.setStyle("-fx-background-color: #d70654;");
                        hBox = addLabelAndImageViewToHBox(hBox, tagPrefix, Attendance.NOT_ATTENDED_IMAGE_PATH);
                        break;
                    case Attendance.NO_TUTORIAL:
                        hBox.setStyle("-fx-background-color: #A9A9A9;");
                        hBox = addLabelAndImageViewToHBox(hBox, tagPrefix, Attendance.NO_TUTORIAL_IMAGE_PATH);
                        break;
                    default:
                        break;
                    }
                    attendances.getChildren().add(hBox);
                });

        final int[] labCounter = {1};
        person.getLabScoreList().getLabScores().forEach(
                labScore -> {
                    Label newLabel = new Label(
                            String.format("Lab %d: %s", labCounter[0], labScore.toString()));
                    newLabel.getStyleClass().add("lab-score");
                    labScores.getChildren().add(newLabel);

                    labCounter[0]++;
                }
        );
    }

    /**
     * Adds a Label containing the provided labelText, and an ImageView generated
     * from the provided imagePath, to the hBox provided, and returns the
     * hBox with the added Label and ImageView.
     *
     * @param hBox HBox to add the Label and ImageView to.
     * @param labelText Text to be shown on the Label.
     * @param imagePath Path of the Image to be shown in the ImageView.
     * @return HBox containing the added Label and ImageView.
     */
    public HBox addLabelAndImageViewToHBox(HBox hBox, String labelText, String imagePath) {
        ImageView imageView = new ImageView(new Image(imagePath));
        imageView.setFitHeight(11);
        imageView.setFitWidth(11);
        hBox.getChildren().addAll(new Label(labelText), imageView);
        return hBox;
    }

    /**
     * Controls the visibility of detailed information about the person.
     *
     * @param show True to show details, false to hide them
     */
    public void showDetails(boolean show) {
        contact.setVisible(show);
        contact.setManaged(show);

        email.setVisible(show);
        email.setManaged(show);

        if (facAndYear.getText().isEmpty()) {
            facAndYear.setVisible(false);
            facAndYear.setManaged(false);
        } else {
            facAndYear.setVisible(show);
            facAndYear.setManaged(show);
        }

        cardPane.requestLayout();
    }
}
