package seedu.address.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class CommandBox extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "CommandBox.fxml";

    private final CommandExecutor commandExecutor;

    @FXML
    private TextArea commandTextArea;

    /**
     * Creates a {@code CommandBox} with the given {@code CommandExecutor}.
     */
    public CommandBox(CommandExecutor commandExecutor) {
        super(FXML);
        this.commandExecutor = commandExecutor;

        commandTextArea.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER && !event.isShiftDown()) {
                event.consume(); // stop TextArea from inserting newline
                handleCommandEntered(event);
            }
        });

        // Reset style when user types
        commandTextArea.textProperty().addListener((
                unused1, unused2, unused3) -> setStyleToDefault());
    }

    /**
     * Handles the action triggered when the "Find ID" button is clicked.
     * Executes a command to find the entity associated with the given ID.
     * Clears the command text area on successful execution. If an exception
     * occurs during command execution or parsing, updates the UI to indicate
     * the failure.
     *
     * @param id The ID of the entity to be searched. It should not be null and
     *           should be properly trimmed before passing to the method.
     */
    public void handleFindIdClicked(String id) {
        try {
            commandExecutor.execute("find " + id.trim());
            commandTextArea.clear();
        } catch (CommandException | ParseException e) {
            setStyleToIndicateCommandFailure();
        }
    }


    /**
     * Handles the Enter button pressed event.
     */
    @FXML
    private void handleCommandEntered(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER && !event.isShiftDown()) {
            String commandText = commandTextArea.getText().trim();

            if (commandText.isEmpty()) {
                commandTextArea.clear();
                return;
            }

            try {
                commandExecutor.execute(commandText);
                commandTextArea.clear();
            } catch (CommandException | ParseException e) {
                setStyleToIndicateCommandFailure();
            }
        }
    }

    /**
     * Sets the command box style to use the default style.
     */
    private void setStyleToDefault() {
        commandTextArea.getStyleClass().remove(ERROR_STYLE_CLASS);
    }

    /**
     * Sets the command box style to indicate a failed command.
     */
    private void setStyleToIndicateCommandFailure() {
        ObservableList<String> styleClass = commandTextArea.getStyleClass();

        if (styleClass.contains(ERROR_STYLE_CLASS)) {
            return;
        }

        styleClass.add(ERROR_STYLE_CLASS);
    }

    /**
     * Represents a function that can execute commands.
     */
    @FunctionalInterface
    public interface CommandExecutor {
        /**
         * Executes the command and returns the result.
         *
         * @see seedu.address.logic.Logic#execute(String)
         */
        CommandResult execute(String commandText) throws CommandException, ParseException;
    }

}
