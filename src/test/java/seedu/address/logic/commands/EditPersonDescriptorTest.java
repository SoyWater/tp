package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ALCOHOLIC_RECORD_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ALLERGY_POLLEN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BLOOD_TYPE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_OF_BIRTH_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMERGENCY_CONTACT_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GENDER_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ID_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MEDICINE_ANTIDEPRESSANT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PAST_MEDICAL_HISTORY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SMOKING_RECORD_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.testutil.EditPersonDescriptorBuilder;

public class EditPersonDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditPersonDescriptor descriptorWithSameValues = new EditPersonDescriptor(DESC_AMY);
        assertTrue(DESC_AMY.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_AMY.equals(DESC_AMY));

        // null -> returns false
        assertFalse(DESC_AMY.equals(null));

        // different types -> returns false
        assertFalse(DESC_AMY.equals(5));

        // different values -> returns false
        assertFalse(DESC_AMY.equals(DESC_BOB));

        // different name -> returns false
        EditPersonDescriptor editedAmy = new EditPersonDescriptorBuilder(DESC_AMY).withName(VALID_NAME_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different phone -> returns false
        editedAmy = new EditPersonDescriptorBuilder(DESC_AMY).withPhone(VALID_PHONE_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different date of birth -> returns false
        editedAmy = new EditPersonDescriptorBuilder(DESC_AMY).withDateOfBirth(VALID_DATE_OF_BIRTH_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different email -> returns false
        editedAmy = new EditPersonDescriptorBuilder(DESC_AMY).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different address -> returns false
        editedAmy = new EditPersonDescriptorBuilder(DESC_AMY).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different tags -> returns false
        editedAmy = new EditPersonDescriptorBuilder(DESC_AMY).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different identity number -> returns false
        editedAmy = new EditPersonDescriptorBuilder(DESC_AMY).withIdentityNumber(VALID_ID_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different emergency contact -> returns false
        editedAmy = new EditPersonDescriptorBuilder(DESC_AMY).withEmergencyContact(VALID_EMERGENCY_CONTACT_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different blood type -> returns false
        editedAmy = new EditPersonDescriptorBuilder(DESC_AMY).withBloodType(VALID_BLOOD_TYPE_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different alcoholic record -> returns false
        editedAmy = new EditPersonDescriptorBuilder(DESC_AMY).withAlcoholicRecord(VALID_ALCOHOLIC_RECORD_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different gender -> returns false
        editedAmy = new EditPersonDescriptorBuilder(DESC_AMY).withGender(VALID_GENDER_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different smoking record -> returns false
        editedAmy = new EditPersonDescriptorBuilder(DESC_AMY).withSmokingRecord(VALID_SMOKING_RECORD_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different past medical history -> returns false
        editedAmy = new EditPersonDescriptorBuilder(DESC_AMY).withPastMedicalHistory(VALID_PAST_MEDICAL_HISTORY_BOB)
                .build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different allergies -> returns false
        editedAmy = new EditPersonDescriptorBuilder(DESC_AMY).withAllergies(VALID_ALLERGY_POLLEN).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different medicines -> returns false
        editedAmy = new EditPersonDescriptorBuilder(DESC_AMY).withMedicines(VALID_MEDICINE_ANTIDEPRESSANT).build();
        assertFalse(DESC_AMY.equals(editedAmy));
    }

    @Test
    public void toStringMethod() {
        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();
        String expected = EditPersonDescriptor.class.getCanonicalName() + "{name="
                + editPersonDescriptor.getIdentityNumber().orElse(null) + ", identityNumber="
                + editPersonDescriptor.getName().orElse(null) + ", phone="
                + editPersonDescriptor.getPhone().orElse(null) + ", email="
                + editPersonDescriptor.getEmail().orElse(null) + ", address="
                + editPersonDescriptor.getEmergencyContact().orElse(null) + ", emergencyContact="
                + editPersonDescriptor.getAddress().orElse(null) + ", tags="
                + editPersonDescriptor.getTags().orElse(null) + ", dateOfBirth="
                + editPersonDescriptor.getDateOfBirth().orElse(null) + ", bloodType="
                + editPersonDescriptor.getBloodType().orElse(null) + ", alcoholicRecord="
                + editPersonDescriptor.getAlcoholicRecord().orElse(null) + ", gender="
                + editPersonDescriptor.getGender().orElse(null) + ", value="
                + editPersonDescriptor.getSmokingRecord().orElse(null) + ", allergies="
                + editPersonDescriptor.getAllergies().orElse(null) + ", medicines="
                + editPersonDescriptor.getMedicines().orElse(null) + ", pastMedicalHistory="
                + editPersonDescriptor.getPastMedicalHistory().orElse(null) + "}";
        assertTrue(expected.equals(editPersonDescriptor.toString()));
    }

    @Test
    public void isAnyFieldEdited_noFieldsEdited_returnsFalse() {
        EditPersonDescriptor descriptor = new EditPersonDescriptor();
        assertFalse(descriptor.isAnyFieldEdited());
    }

    @Test
    public void isAnyFieldEdited_someFieldEdited_returnsTrue() {
        EditPersonDescriptor descriptor = new EditPersonDescriptor();
        descriptor.setName(new seedu.address.model.person.Name("Alice"));
        assertTrue(descriptor.isAnyFieldEdited());
    }

    @Test
    public void getOptionalFields_emptyWhenNotSet() {
        EditPersonDescriptor descriptor = new EditPersonDescriptor();
        assertFalse(descriptor.getTags().isPresent());
        assertFalse(descriptor.getAllergies().isPresent());
        assertFalse(descriptor.getMedicines().isPresent());
    }
}
