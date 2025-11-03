package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.List;

import seedu.address.model.util.DateParserUtil;

/**
 * Represents a person's date of birth in the system.
 * Guarantees: immutable; is valid as declared in {@link #isValidDateFormat(String)}.
 */
public class DateOfBirth {

    public static final String MESSAGE_FORMAT_CONSTRAINTS =
            "Date of birth should be of the following formats: "
                    + "DD-MM-YYYY, DD/MM/YYYY or DD.MM.YYYY";

    public static final String MESSAGE_PAST_DATE_CONSTRAINTS = "Date of birth should not be a future date.";

    public static final String MESSAGE_INVALID_DATE_CONSTRAINTS = "Date of birth should be a valid date: ";

    /**
     * A list of {@code DateTimeFormatter} objects that define the supported date formats.
     * The formats include:
     * - "dd-MM-yyyy": Dates with dashes as separators (e.g., 01-01-2023).
     * - "dd/MM/yyyy": Dates with slashes as separators (e.g., 01/01/2023).
     * - "dd.MM.yyyy": Dates with dots as separators (e.g., 01.01.2023).
     * These formatters are used to parse and validate user-provided date inputs
     * and ensure compatibility with the system's date handling logic.
     */
    public static final List<DateTimeFormatter> FORMATTERS = List.of(
        DateTimeFormatter.ofPattern("d-M-uuuu").withResolverStyle(ResolverStyle.STRICT),
        DateTimeFormatter.ofPattern("d/M/uuuu").withResolverStyle(ResolverStyle.STRICT),
        DateTimeFormatter.ofPattern("d.M.uuuu").withResolverStyle(ResolverStyle.STRICT)
    );

    /**
     * A {@code DateTimeFormatter} that specifies the pattern for date formatting and parsing.
     * The pattern used is "dd-MM-yyyy", which represents a date in the format: day-month-year.
     * Ensures consistency in handling date formats across the application.
     */
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    /**
     * A regular expression that defines the validation rules for date strings.
     * The rules are as follows:
     * - The date must be in the format "DD-MM-YYYY", "DD/MM/YYYY", or "DD.MM.YYYY".
     * - The day (DD) must be a valid number ranging from 00 to 31.
     * - The month (MM) must be a valid number ranging from 00 to 12.
     * - The year (YYYY) must be a four-digit number.
     * - The separator between day, month, and year can be "-", "/", or ".".
     * These rules ensure the date is formatted correctly and can be validated against the system's requirements.
     */ /*
     * Dates: 00 to 31
     * Separators: -, /, or .
     * Months: 00 to 12
     * Separators: -, /, or .
     * Years: Four digits
     */
    public static final String VALIDATION_REGEX = "^(\\d{1,2})([-/.])(\\d{1,2})\\2(?!0000)(\\d{4})$";

    public final LocalDate dateOfBirth;

    /**
     * Constructs a {@code DateOfBirth}.
     * Validates and parses the input date of the birth string, ensuring it conforms to the required format.
     *
     * @param dateOfBirth A string representing the date of birth, which must conform
     *                    to the accepted date formats and validation rules.
     *                    Must not be null or invalid.
     * @throws IllegalArgumentException If the provided {@code dateOfBirth} string
     *                                  does not pass validation.
     */
    public DateOfBirth(String dateOfBirth) {
        requireNonNull(dateOfBirth);
        checkArgument(isValidDateFormat(dateOfBirth), MESSAGE_FORMAT_CONSTRAINTS);
        checkArgument(isValidDate(dateOfBirth), MESSAGE_INVALID_DATE_CONSTRAINTS + dateOfBirth);
        checkArgument(isValidDateOfBirth(dateOfBirth), MESSAGE_PAST_DATE_CONSTRAINTS);
        this.dateOfBirth = DateParserUtil.parseDate(dateOfBirth, FORMATTERS);
    }

    /**
     * Returns true if a given string is a valid date format.
     */
    public static boolean isValidDateFormat(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    public static boolean isValidDate(String test) {
        return DateParserUtil.isValidDate(test, FORMATTERS);
    }

    /**
     * Validates if a given string represents a valid date of birth.
     * The date must be in an acceptable format and must not be after the current date.
     *
     * @param test The date of birth string to validate. Must not be null
     */
    public static boolean isValidDateOfBirth(String test) {
        return DateParserUtil.isBeforeOrEqualToday(test, FORMATTERS);
    }


    @Override
    public String toString() {
        return DateParserUtil.formatDate(dateOfBirth, DATE_FORMATTER);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DateOfBirth otherDate)) {
            return false;
        }

        return dateOfBirth.equals(otherDate.dateOfBirth);
    }

    /**
     * Calculates the age based on the date of birth and the current date.
     *
     * @return The calculated age as an integer.
     */
    public int calculateAge() {
        LocalDate currentDate = LocalDate.now();
        int age = currentDate.getYear() - dateOfBirth.getYear();

        // If the birthday hasn't occurred yet this year, subtract one from the age
        if (currentDate.getDayOfYear() < dateOfBirth.getDayOfYear()) {
            age--;
        }

        return age;
    }

    @Override
    public int hashCode() {
        return dateOfBirth.hashCode();
    }

}
