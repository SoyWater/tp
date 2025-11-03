package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ALCOHOLIC_RECORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ALLERGY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BLOOD_TYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_OF_BIRTH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMERGENCY_CONTACT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GENDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_IDENTITY_NUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEDICINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PAST_MEDICAL_HISTORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SMOKING_RECORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.person.IdentityNumber;
import seedu.address.model.person.NameOrIdContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.testutil.EditPersonDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_ID_AMY = "A60";
    public static final String VALID_ID_BOB = "B69";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_EMERGENCY_CONTACT_AMY = "[mother] 123456789";
    public static final String VALID_EMERGENCY_CONTACT_BOB = "[father] +65 29571924";
    public static final String VALID_DATE_OF_BIRTH_AMY = "01-01-1990";
    public static final String VALID_DATE_OF_BIRTH_BOB = "02-02-1992";
    public static final String VALID_ALLERGY_NUTS = "nuts";
    public static final String VALID_ALLERGY_POLLEN = "pollen";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";
    public static final String VALID_MEDICINE_ANTIDEPRESSANT = "antidepressant";
    public static final String VALID_BLOOD_TYPE = "AB";
    public static final String VALID_BLOOD_TYPE_BOB = "A+";
    public static final String VALID_ALCOHOLIC_RECORD_AMY = "Social drinker";
    public static final String VALID_ALCOHOLIC_RECORD_BOB = "Never";
    public static final String VALID_GENDER_AMY = "F";
    public static final String VALID_GENDER_BOB = "M";
    public static final String VALID_SMOKING_RECORD_AMY = "Non-smoker";
    public static final String VALID_SMOKING_RECORD_BOB = "Occasional smoker";
    public static final String VALID_PAST_MEDICAL_HISTORY_AMY = "Diabetes";
    public static final String VALID_PAST_MEDICAL_HISTORY_BOB = "Hypertension";

    public static final String VALID_APPOINTMENT_TIME_AMY = "01-01-2025 10:00";
    public static final String VALID_APPOINTMENT_TIME_BOB = "02-02-2025 11:00";
    public static final String VALID_APPOINTMENT_NOTES_AMY = "Amy's appointment";
    public static final String VALID_APPOINTMENT_NOTES_BOB = "Bob's appointment";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String IDENTITY_NUMBER_DESC_AMY = " " + PREFIX_IDENTITY_NUMBER + VALID_ID_AMY;
    public static final String IDENTITY_NUMBER_DESC_BOB = " " + PREFIX_IDENTITY_NUMBER + VALID_ID_BOB;
    public static final String PAST_MEDICAL_HISTORY_DESC_AMY = " " + PREFIX_PAST_MEDICAL_HISTORY
            + VALID_PAST_MEDICAL_HISTORY_AMY;
    public static final String PAST_MEDICAL_HISTORY_DESC_BOB = " " + PREFIX_PAST_MEDICAL_HISTORY
            + VALID_PAST_MEDICAL_HISTORY_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + VALID_ADDRESS_BOB;
    public static final String EMERGENCY_CONTACT_DESC_AMY = " " + PREFIX_EMERGENCY_CONTACT
            + VALID_EMERGENCY_CONTACT_AMY;
    public static final String EMERGENCY_CONTACT_DESC_BOB = " " + PREFIX_EMERGENCY_CONTACT
            + VALID_EMERGENCY_CONTACT_BOB;
    public static final String DATE_OF_BIRTH_DESC_AMY = " " + PREFIX_DATE_OF_BIRTH + VALID_DATE_OF_BIRTH_AMY;
    public static final String DATE_OF_BIRTH_DESC_BOB = " " + PREFIX_DATE_OF_BIRTH + VALID_DATE_OF_BIRTH_BOB;
    public static final String ALLERGY_DESC_NUTS = " " + PREFIX_ALLERGY + VALID_ALLERGY_NUTS;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;
    public static final String MEDICINE_DESC_ANTIDEPRESSANT = " " + PREFIX_MEDICINE + VALID_MEDICINE_ANTIDEPRESSANT;
    public static final String BLOOD_TYPE_DESC = " " + PREFIX_BLOOD_TYPE + VALID_BLOOD_TYPE;
    public static final String ALCOHOLIC_RECORD_DESC_AMY = " " + PREFIX_ALCOHOLIC_RECORD + VALID_ALCOHOLIC_RECORD_AMY;
    public static final String ALCOHOLIC_RECORD_DESC_BOB = " " + PREFIX_ALCOHOLIC_RECORD + VALID_ALCOHOLIC_RECORD_BOB;
    public static final String GENDER_DESC_AMY = " " + PREFIX_GENDER + VALID_GENDER_AMY;
    public static final String GENDER_DESC_BOB = " " + PREFIX_GENDER + VALID_GENDER_BOB;
    public static final String SMOKING_RECORD_DESC_AMY = " " + PREFIX_SMOKING_RECORD + VALID_SMOKING_RECORD_AMY;
    public static final String SMOKING_RECORD_DESC_BOB = " " + PREFIX_SMOKING_RECORD + VALID_SMOKING_RECORD_BOB;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME; // empty string not allowed in names
    public static final String INVALID_IDENTITY_NUMBER_DESC = " "
            + PREFIX_IDENTITY_NUMBER + "John Doe"; // whitespace not allowed in identity number
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "9 1 1"; // less than consecutive 2 digits
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_EMERGENCY_CONTACT_DESC = " " + PREFIX_EMERGENCY_CONTACT
            + "[mom] $12345"; // $ not allowed in the phone number
    public static final String INVALID_DATE_OF_BIRTH_DESC = " " + PREFIX_DATE_OF_BIRTH + "41-01-2000"; // invalid date
    public static final String INVALID_ALCOHOLIC_RECORD_DESC = " "
            + PREFIX_ALCOHOLIC_RECORD; // empty string not allowed for alcoholic record
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + " "; // empty string not allowed in tags
    public static final String INVALID_GENDER_DESC = " " + PREFIX_GENDER; // empty string not allowed for gender
    public static final String INVALID_ALLERGY_DESC = " " + PREFIX_ALLERGY; // empty string invalid for allergy
    public static final String INVALID_MEDICINE_DESC = " " + PREFIX_MEDICINE; // empty string invalid medicine
    public static final String INVALID_BLOOD_TYPE_DESC = " " + PREFIX_BLOOD_TYPE; // empty string invalid blood type
    public static final String INVALID_SMOKING_RECORD_DESC = " "
            + PREFIX_SMOKING_RECORD; // empty string not allowed for smoking record

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditPersonDescriptor DESC_AMY;
    public static final EditCommand.EditPersonDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY).withIdentityNumber(VALID_ID_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withEmergencyContact(VALID_EMERGENCY_CONTACT_AMY).withGender(VALID_GENDER_AMY)
                .withTags(VALID_TAG_FRIEND).withBloodType(VALID_BLOOD_TYPE)
                .withAlcoholicRecord(VALID_ALCOHOLIC_RECORD_AMY).withSmokingRecord(VALID_SMOKING_RECORD_AMY)
                .withAllergies(VALID_ALLERGY_NUTS)
                .withPastMedicalHistory(VALID_PAST_MEDICAL_HISTORY_AMY).build();
        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withIdentityNumber(VALID_ID_BOB).withEmergencyContact(VALID_EMERGENCY_CONTACT_BOB)
                .withGender(VALID_GENDER_BOB)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).withBloodType(VALID_BLOOD_TYPE)
                .withAlcoholicRecord(VALID_ALCOHOLIC_RECORD_BOB).withSmokingRecord(VALID_SMOKING_RECORD_BOB)
                .withAllergies(VALID_ALLERGY_NUTS)
                .withPastMedicalHistory(VALID_PAST_MEDICAL_HISTORY_BOB).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult}
     * <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
                                            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to
     * {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
                                            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book, filtered person list and selected person in
     * {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        List<Person> expectedFilteredList = new ArrayList<>(actualModel.getFilteredPersonList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedAddressBook, actualModel.getAddressBook());
        assertEquals(expectedFilteredList, actualModel.getFilteredPersonList());
    }

    /**
     * Updates {@code model}'s filtered person list to show only the person at the given
     * {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showPersonAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredPersonList().size());

        Person person = model.getFilteredPersonList().get(targetIndex.getZeroBased());
        final String[] splitName = person.getName().fullName.split("\\s+");
        model.updateFilteredPersonList(new NameOrIdContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredPersonList().size());
    }

    /**
     * Updates {@code model}'s filtered appointment list to show only the appointment with the given
     * {@code patientId} in the
     * {@code model}'s address book.
     */
    public static void showAppointmentWithId(Model model, IdentityNumber patientId) {
        model.updateFilteredAppointmentList(a -> a.getPatientId().equals(patientId));
        assertEquals(1, model.getFilteredAppointmentList().size());
    }

}
