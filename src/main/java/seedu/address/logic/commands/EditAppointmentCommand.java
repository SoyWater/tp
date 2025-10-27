package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPOINTMENT_NOTE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPOINTMENT_TIME;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_APPOINTMENTS;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.AppointmentNotes;
import seedu.address.model.appointment.AppointmentTime;
import seedu.address.model.person.IdentityNumber;

/**
 * Edits the details of an existing appointment in the address book.
 */
public class EditAppointmentCommand extends Command {
    public static final String COMMAND_WORD = "eappt";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the appointment identified "
            + "by the index number used in the displayed appointment list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_APPOINTMENT_TIME + "TIME] "
            + "[" + PREFIX_APPOINTMENT_NOTE + "NOTE] "
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_APPOINTMENT_TIME + "11-11-2011 11:11 "
            + PREFIX_APPOINTMENT_NOTE + "Needs physiotherapy";

    public static final String MESSAGE_EDIT_APPOINTMENT_SUCCESS = "Edited Appointment: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_APPOINTMENT = "This appointment already exists in the address book.";

    private final Index index;
    private final EditAppointmentDescriptor editAppointmentDescriptor;

    /**
     * @param index of the appointment in the filtered appointment list to edit
     * @param editAppointmentDescriptor details to edit the appointment with
     */
    public EditAppointmentCommand(Index index, EditAppointmentDescriptor editAppointmentDescriptor) {
        requireNonNull(index);
        requireNonNull(editAppointmentDescriptor);

        this.index = index;
        this.editAppointmentDescriptor = new EditAppointmentDescriptor(editAppointmentDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Appointment> lastShownList = model.getFilteredAppointmentList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_APPOINTMENT_DISPLAYED_INDEX);
        }

        Appointment appointmentToEdit = lastShownList.get(index.getZeroBased());
        Appointment editedAppointment = createEditedAppointment(appointmentToEdit, editAppointmentDescriptor);

        if (!appointmentToEdit.isSameAppointment(editedAppointment) && model.hasAppointment(editedAppointment)) {
            throw new CommandException(MESSAGE_DUPLICATE_APPOINTMENT);
        }

        model.setAppointment(appointmentToEdit, editedAppointment);
        model.updateFilteredAppointmentList(PREDICATE_SHOW_ALL_APPOINTMENTS);
        return new CommandResult(String.format(MESSAGE_EDIT_APPOINTMENT_SUCCESS, Messages.format(editedAppointment)));
    }

    /**
     * Creates and returns a {@code Appointment} with the details of {@code appointmentToEdit}
     * edited with {@code editAppointmentDescriptor}.
     */
    private static Appointment createEditedAppointment(
            Appointment appointmentToEdit,
            EditAppointmentCommand.EditAppointmentDescriptor editAppointmentDescriptor
    ) {
        assert appointmentToEdit != null;

        IdentityNumber updatedIdentityNumber = editAppointmentDescriptor.getIdentityNumber()
                .orElse(appointmentToEdit.getPatientId());
        AppointmentTime updatedAppointmentTime = editAppointmentDescriptor.getAppointmentTime()
                .orElse(appointmentToEdit.getDateTime());
        AppointmentNotes updatedAppointmentNotes = editAppointmentDescriptor.getAppointmentNotes()
                .orElse(appointmentToEdit.getNotes());

        return new Appointment(updatedAppointmentNotes, updatedAppointmentTime, updatedIdentityNumber);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditAppointmentCommand otherEditAppointmentCommand)) {
            return false;
        }

        return index.equals(otherEditAppointmentCommand.index)
                && editAppointmentDescriptor.equals(otherEditAppointmentCommand.editAppointmentDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("editAppointmentDescriptor", editAppointmentDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the appointment with. Each non-empty field value will replace the
     * corresponding field value of the appointment.
     */
    public static class EditAppointmentDescriptor {
        private AppointmentTime appointmentTime;
        private IdentityNumber identityNumber;
        private AppointmentNotes notes;

        public EditAppointmentDescriptor() {}

        /**
         * Copy constructor.
         */
        public EditAppointmentDescriptor(EditAppointmentCommand.EditAppointmentDescriptor toCopy) {
            setIdentityNumber(toCopy.identityNumber);
            setAppointmentNotes(toCopy.notes);
            setAppointmentTime(toCopy.appointmentTime);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(appointmentTime, identityNumber, notes);
        }

        public void setIdentityNumber(IdentityNumber identityNumber) {
            this.identityNumber = identityNumber;
        }

        public Optional<IdentityNumber> getIdentityNumber() {
            return Optional.ofNullable(identityNumber);
        }

        public void setAppointmentTime(AppointmentTime time) {
            this.appointmentTime = time;
        }

        public Optional<AppointmentTime> getAppointmentTime() {
            return Optional.ofNullable(appointmentTime);
        }

        public void setAppointmentNotes(AppointmentNotes notes) {
            this.notes = notes;
        }

        public Optional<AppointmentNotes> getAppointmentNotes() {
            return Optional.ofNullable(notes);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditAppointmentDescriptor otherEditAppointmentDescriptor)) {
                return false;
            }

            return Objects.equals(identityNumber, otherEditAppointmentDescriptor.identityNumber)
                    && Objects.equals(appointmentTime, otherEditAppointmentDescriptor.appointmentTime)
                    && Objects.equals(notes, otherEditAppointmentDescriptor.notes);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("identityNumber", identityNumber)
                    .add("appointmentTime", appointmentTime)
                    .add("notes", notes)
                    .toString();
        }
    }
}

