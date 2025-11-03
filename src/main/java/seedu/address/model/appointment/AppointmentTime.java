package seedu.address.model.appointment;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.List;

import seedu.address.model.util.DateTimeParserUtil;

/**
 * Represents a specific date and time for an appointment.
 * Provides functionality to validate, parse, and format the date-time information.
 */
public class AppointmentTime {

    public static final String MESSAGE_FORMAT_CONSTRAINTS =
            "Appointment time should be of the following formats: "
                    + "DD-MM-YYYY HH:mm, DD/MM/YYYY HH:mm or DD.MM.YYYY HH:mm";

    public static final String MESSAGE_VALID_DATE_CONSTRAINT = "Appointment time should be a valid date and time: ";

    public static final String VALIDATION_REGEX =
            "^(\\d{1,2})([-/.])(\\d{1,2})\\2(?!0000)(\\d{4}) (\\d{2}):(\\d{2})$";

    public static final List<DateTimeFormatter> FORMATTERS = List.of(
            DateTimeFormatter.ofPattern("d-M-uuuu HH:mm").withResolverStyle(ResolverStyle.STRICT),
            DateTimeFormatter.ofPattern("d/M/uuuu HH:mm").withResolverStyle(ResolverStyle.STRICT),
            DateTimeFormatter.ofPattern("d.M.uuuu HH:mm").withResolverStyle(ResolverStyle.STRICT)
    );

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    private final LocalDateTime dateTime;

    /**
     * Constructs an {@code AppointmentTime} object with the specified date-time string.
     * Validates and parses the date-time string to a {@code LocalDateTime} object.
     *
     * @param dateTime The string representing the appointment time. It must adhere to one of the supported
     *                 formats: DD-MM-YYYY HH:mm, DD/MM/YYYY HH:mm, or DD.MM.YYYY HH:mm. Must not be null.
     * @throws NullPointerException If {@code dateTime} is null.
     * @throws IllegalArgumentException If {@code dateTime} does not meet the format constraints.
     */
    public AppointmentTime(String dateTime) {
        requireNonNull(dateTime);
        checkArgument(isValidDateTimeFormat(dateTime), MESSAGE_FORMAT_CONSTRAINTS);
        checkArgument(isValidDateTime(dateTime), MESSAGE_VALID_DATE_CONSTRAINT + dateTime);
        this.dateTime = DateTimeParserUtil.parseDateTime(dateTime, FORMATTERS);
    }

    /**
     * Returns true if a given string is a valid dateTime.
     */
    public static boolean isValidDateTimeFormat(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    public static boolean isValidDateTime(String test) {
        return DateTimeParserUtil.isValidDateTime(test, FORMATTERS);
    }

    /**
     * Determines if the current {@code AppointmentTime} is before the specified {@code LocalDateTime}.
     *
     * @param other The {@code LocalDateTime} to compare with the current {@code AppointmentTime}.
     *              Must not be null.
     * @return {@code true} if the current {@code AppointmentTime} is before the specified {@code LocalDateTime};
     *         {@code false} otherwise.
     */
    public boolean isBefore(LocalDateTime other) {
        return this.dateTime.isBefore(other);
    }

    /**
     * Determines if the current {@code AppointmentTime} is after the specified {@code LocalDateTime}.
     *
     * @param other The {@code LocalDateTime} to compare with the current {@code AppointmentTime}.
     *              Must not be null.
     * @return {@code true} if the current {@code AppointmentTime} is after the specified {@code LocalDateTime};
     *         {@code false} otherwise.
     */
    public boolean isAfter(LocalDateTime other) {
        return this.dateTime.isAfter(other);
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    /**
     * Returns a string representation of the {@code AppointmentTime} object.
     * The string is formatted based on the predefined date-time format.
     *
     * @return A formatted string representation of the appointment time.
     */
    @Override
    public String toString() {
        return DateTimeParserUtil.formatDateTime(dateTime, DATE_TIME_FORMATTER);
    }

    /**
     * Compares this {@code AppointmentTime} object with another object to determine if they are equal.
     * Two {@code AppointmentTime} objects are considered equal if their {@code dateTime} values are the same.
     *
     * @param other The object to compare this {@code AppointmentTime} object with.
     *              It may be null or an object of any type.
     * @return {@code true} if the provided object is the same as this {@code AppointmentTime}
     *         object or if it is an instance of {@code AppointmentTime} with the same {@code dateTime};
     *         {@code false} otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AppointmentTime)) {
            return false;
        }

        return dateTime.equals(((AppointmentTime) other).dateTime);
    }

    /**
     * Generates a hash code for the {@code AppointmentTime} object based on its {@code dateTime} field.
     *
     * @return An integer hash code value for the object, determined by the hash code of its {@code dateTime} field.
     */
    @Override
    public int hashCode() {
        return dateTime.hashCode();
    }
}

