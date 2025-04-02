package seedu.tassist.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
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
    private VBox vboxWithContents;
    @FXML
    private HBox cardPane;
    @FXML
    private Label nameAndMatNum;
    @FXML
    private Label id;
    @FXML
    private TextFlow contact;
    @FXML
    private Label email;
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
        nameAndMatNum.setText(person.getName().fullName + "    " + "(" + person.getMatNum().value + ")");

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

        // Create separate clickable parts
        Text phoneText = new Text(person.getPhone().value);
        phoneText.setCursor(Cursor.HAND);
        phoneText.setStyle("-fx-fill: white; -fx-underline: true;");
        phoneText.setOnMouseClicked(event -> copyToClipboard(phoneText.getText()));

        Text separator = new Text("    "); // Space separator

        Text telegramText = new Text(person.getTeleHandle().value);
        telegramText.setCursor(Cursor.HAND);
        telegramText.setStyle("-fx-fill: white; -fx-underline: true;");
        telegramText.setOnMouseClicked(event -> copyToClipboard(telegramText.getText()));

        // Guaranteed for either phone or telegram Handle to have a value.
        contact.getChildren().clear();
        assert !(person.getPhone().isEmpty() && person.getTeleHandle().isEmpty())
                : "Both phone and teleHandle cannot be empty simultaneously";
        contact.getChildren().clear();
        if (!person.getPhone().isEmpty() && !person.getTeleHandle().isEmpty()) {
            contact.getChildren().addAll(phoneText, separator, telegramText);
        } else if (!person.getPhone().isEmpty()) {
            contact.getChildren().add(phoneText);
        } else if (!person.getTeleHandle().isEmpty()) {
            contact.getChildren().add(telegramText);
        }

        email.setText(person.getEmail().value);

        // Not guaranteed for year or faculty to be present.
        if (person.getYear().isEmpty() && !person.getFaculty().isEmpty()) {
            facAndYear.setText(person.getFaculty().value);
        } else if (!person.getYear().isEmpty() && person.getFaculty().isEmpty()) {
            facAndYear.setText("Y" + person.getYear().value);
        } else if (!person.getYear().isEmpty() && !person.getFaculty().isEmpty()) {
            facAndYear.setText("Y" + person.getYear().value + "  \u2022  "
                    + person.getFaculty().value);
        }


        if (!person.getRemark().value.isEmpty()) {
            remark.setText("Remarks: " + person.getRemark().value);
        } else {
            remark.setVisible(false);
            remark.setManaged(false);
        }

        labScores.getChildren().add(new Label("Lab grades:"));

        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));

        if (person.getAttendanceList().isEmpty()) {
            vboxWithContents.getChildren().remove(attendances);
        } else {
            Label tutorialAttendanceLabel = new Label("Tutorial att:");
            tutorialAttendanceLabel.setStyle("-fx-font-size: 11pt;");
            attendances.getChildren().add(tutorialAttendanceLabel);
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
        }

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

    // Helper function to copy text
    private void copyToClipboard(String text) {
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.putString(text);
        clipboard.setContent(content);
    }
}
