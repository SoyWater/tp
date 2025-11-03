package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

public class DateOfBirthTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DateOfBirth(null));
    }

    @Test
    public void constructor_invalidDateOfBirth_throwsIllegalArgumentException() {
        String invalidDob = "not-a-date";
        assertThrows(IllegalArgumentException.class, () -> new DateOfBirth(invalidDob));
    }

    @Test
    public void isValidDateFormat() {
        // null date of birth
        assertThrows(NullPointerException.class, () -> DateOfBirth.isValidDateFormat(null));

        // invalid format
        assertFalse(DateOfBirth.isValidDateFormat("")); // empty string
        assertFalse(DateOfBirth.isValidDateFormat(" ")); // spaces only
        assertFalse(DateOfBirth.isValidDateFormat("not-a-date"));
        assertFalse(DateOfBirth.isValidDateFormat("2020-02-30")); // general invalid format

        // invalid month, date and year should have the same behavior
        assertFalse(DateOfBirth.isValidDateFormat("31/1444/1999"));
        assertFalse(DateOfBirth.isValidDateFormat("31-12/19999")); // different delimiters

        // valid format
        assertTrue(DateOfBirth.isValidDateFormat("31-99-1999"));
        assertTrue(DateOfBirth.isValidDateFormat("1/1/2000"));
        assertTrue(DateOfBirth.isValidDateFormat("30.50.2000"));
    }

    @Test
    public void isValidDate() {
        // null date of birth
        assertThrows(NullPointerException.class, () -> DateOfBirth.isValidDateOfBirth(null));

        // invalid date
        assertFalse(DateOfBirth.isValidDateOfBirth("not-a-date"));
        assertFalse(DateOfBirth.isValidDateOfBirth("2020-02-30")); // invalid date
        assertFalse(DateOfBirth.isValidDateOfBirth("29/02/2025")); // leap day
        assertFalse(DateOfBirth.isValidDateOfBirth("31/04/1999")); // invalid day

        // valid date
        assertTrue(DateOfBirth.isValidDateOfBirth("31-12-1999"));
        assertTrue(DateOfBirth.isValidDateOfBirth("01/01/2000"));
    }

    @Test
    public void isValidDateOfBirth() {
        // null date of birth
        assertThrows(NullPointerException.class, () -> DateOfBirth.isValidDateOfBirth(null));

        // invalid date
        assertFalse(DateOfBirth.isValidDateOfBirth("")); // empty string
        assertFalse(DateOfBirth.isValidDateOfBirth(" ")); // spaces only
        assertFalse(DateOfBirth.isValidDateOfBirth("not-a-date"));
        assertFalse(DateOfBirth.isValidDateOfBirth("2020-02-30")); // invalid date
        assertFalse(DateOfBirth.isValidDateOfBirth("31/14/1999")); // wrong month

        // future date
        LocalDate futureDate = LocalDate.now().plusDays(1);
        String futureDateString = futureDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        assertFalse(DateOfBirth.isValidDateOfBirth(futureDateString));

        // valid date of birth
        assertTrue(DateOfBirth.isValidDateOfBirth("31-12-1999"));
        assertTrue(DateOfBirth.isValidDateOfBirth("01-01-2000"));
    }

    @Test
    public void equals() {
        DateOfBirth dob = new DateOfBirth("01-01-2000");

        // same values -> returns true
        assertTrue(dob.equals(new DateOfBirth("01-01-2000")));

        // same object -> returns true
        assertTrue(dob.equals(dob));

        // null -> returns false
        assertFalse(dob.equals(null));

        // different types -> returns false
        assertFalse(dob.equals(5.0f));

        // different values -> returns false
        assertFalse(dob.equals(new DateOfBirth("31-12-1999")));
    }

    @Test
    public void hashCode_sameValue_equals() {
        DateOfBirth dob1 = new DateOfBirth("01-01-2000");
        DateOfBirth dob2 = new DateOfBirth("01-01-2000");
        assertEquals(dob1.hashCode(), dob2.hashCode());
    }

    @Test
    public void calculateAge_birthdayHasOccurredThisYear_correctAge() {
        long age = Math.round((Math.random() * 80 + 1));
        LocalDate dob = LocalDate.now().minusYears(age).minusMonths(1);
        DateOfBirth dateOfBirth = new DateOfBirth(dob.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        assertEquals(age, dateOfBirth.calculateAge());
    }

    @Test
    public void calculateAge_birthdayHasNotOccurredThisYear_correctAge() {
        long age = Math.round((Math.random() * 80 + 1));
        LocalDate dob = LocalDate.now().minusYears(age).plusMonths(1);
        DateOfBirth dateOfBirth = new DateOfBirth(dob.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        assertEquals(age - 1, dateOfBirth.calculateAge());
    }

    @Test
    public void calculateAge_birthdayToday_ageIsCorrect() {
        LocalDate today = LocalDate.now();
        DateOfBirth dateOfBirth = new DateOfBirth(today.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        assertEquals(0, dateOfBirth.calculateAge());
    }

    @Test
    public void isOlderThanAge_birthdayHasOccurredThisYear() {
        long age = Math.round((Math.random() * 80 + 1));
        LocalDate dob = LocalDate.now().minusYears(age).minusMonths(1);
        DateOfBirth dateOfBirth = new DateOfBirth(dob.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));

        assertTrue(dateOfBirth.isOlderThanAge((int) (age))); // Exact age
        assertTrue(dateOfBirth.isOlderThanAge((int) (age - 1))); // One year younger
        assertFalse(dateOfBirth.isOlderThanAge((int) (age + 1))); // One year older
    }

    @Test
    public void isOlderThanAge_birthdayHasNotOccurredThisYear() {
        long age = Math.round((Math.random() * 80 + 1));
        LocalDate dob = LocalDate.now().minusYears(age).plusMonths(1);
        DateOfBirth dateOfBirth = new DateOfBirth(dob.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));

        assertTrue(dateOfBirth.isOlderThanAge((int) (age - 1))); // Exact age
        assertTrue(dateOfBirth.isOlderThanAge((int) (age - 2))); // One year younger
        assertFalse(dateOfBirth.isOlderThanAge((int) age)); // One year older
    }

    @Test
    public void isOlderThanAge_birthdayToday() {
        LocalDate today = LocalDate.now();
        DateOfBirth dateOfBirth = new DateOfBirth(today.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));

        assertTrue(dateOfBirth.isOlderThanAge((int) (0))); // Exact age
        assertTrue(dateOfBirth.isOlderThanAge((int) (-1))); // One year younger
        assertFalse(dateOfBirth.isOlderThanAge((int) 1)); // One year older
    }
}
