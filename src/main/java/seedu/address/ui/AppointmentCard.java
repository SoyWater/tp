package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.appointment.Appointment;

/**
 * A UI component that displays information of an {@code Appointment}.
 */
public class AppointmentCard extends UiPart<Region> {

    private static final String FXML = "AppointmentCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Appointment appointment;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Hyperlink patientId;
    @FXML
    private Label dateTime;
    @FXML
    private Label notes;

    /**
     * Creates an {@code AppointmentCard} with the given {@code Appointment} and index to display.
     */
    public AppointmentCard(Appointment appointment, int displayedIndex, CommandBox commandBox) {
        super(FXML);
        this.appointment = appointment;

        id.setText(displayedIndex + ". ");
        patientId.setText(appointment.getPatientId().toString());
        patientId.setOnMouseClicked((event) -> {
            commandBox.handleFindIdClicked(patientId.getText());
        });
        dateTime.setText("Date and Time: " + appointment.getDateTime().toString());

        String notesText = appointment.getNotes().toString();
        if (notesText.isEmpty()) {
            notes.setText("Notes: None");
            notes.setStyle("-fx-font-style: italic; -fx-text-fill: grey;"); // Styling for empty notes
        } else {
            notes.setText("Notes: " + notesText);
            notes.setStyle(""); // Reset style if notes are present
        }
    }
}
