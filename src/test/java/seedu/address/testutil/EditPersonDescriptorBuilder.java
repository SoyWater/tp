package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.model.person.Address;
import seedu.address.model.person.AlcoholicRecord;
import seedu.address.model.person.Allergy;
import seedu.address.model.person.BloodType;
import seedu.address.model.person.DateOfBirth;
import seedu.address.model.person.Email;
import seedu.address.model.person.EmergencyContact;
import seedu.address.model.person.Gender;
import seedu.address.model.person.IdentityNumber;
import seedu.address.model.person.Medicine;
import seedu.address.model.person.Name;
import seedu.address.model.person.PastMedicalHistory;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.SmokingRecord;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building EditPersonDescriptor objects.
 */
public class EditPersonDescriptorBuilder {

    private EditPersonDescriptor descriptor;

    public EditPersonDescriptorBuilder() {
        descriptor = new EditPersonDescriptor();
    }

    public EditPersonDescriptorBuilder(EditPersonDescriptor descriptor) {
        this.descriptor = new EditPersonDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditPersonDescriptor} with fields containing {@code person}'s details
     */
    public EditPersonDescriptorBuilder(Person person) {
        descriptor = new EditPersonDescriptor();
        descriptor.setName(person.getName());
        descriptor.setIdentityNumber(person.getIdentityNumber());
        descriptor.setPhone(person.getPhone());
        descriptor.setEmail(person.getEmail());
        descriptor.setAddress(person.getAddress());
        descriptor.setEmergencyContact(person.getEmergencyContact());
        descriptor.setTags(person.getTags());
        descriptor.setAllergies(person.getAllergies());
        descriptor.setMedicines(person.getMedicines());
        descriptor.setDateOfBirth(person.getDateOfBirth());
        descriptor.setSmokingRecord(person.getSmokingRecord());
        descriptor.setPastMedicalHistory(person.getPastMedicalHistory());
        descriptor.setDateOfBirth(person.getDateOfBirth());
    }

    /**
     * Sets the {@code Name} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code IdentityNumber} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withIdentityNumber(String id) {
        descriptor.setIdentityNumber(new IdentityNumber(id));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withEmail(String email) {
        descriptor.setEmail(new Email(email));
        return this;
    }

    /**
     * Sets the {@code DateOfBirth} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withDateOfBirth(String dob) {
        descriptor.setDateOfBirth(new DateOfBirth(dob));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withAddress(String address) {
        descriptor.setAddress(new Address(address));
        return this;
    }

    /**
     * Sets the {@code EmergencyContact} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withEmergencyContact(String emergencyContact) {
        descriptor.setEmergencyContact(new EmergencyContact(emergencyContact));
        return this;
    }


    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditPersonDescriptor}
     * that we are building.
     */
    public EditPersonDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    /**
     * Parses the {@code medicines} into a {@code Set<Tag>} and set it to the {@code EditPersonDescriptor}
     * that we are building.
     */
    public EditPersonDescriptorBuilder withMedicines(String... medicines) {
        Set<Medicine> medicinesSet = Stream.of(medicines).map(Medicine::new).collect(Collectors.toSet());
        descriptor.setMedicines(medicinesSet);
        return this;
    }

    /**
     * Parses the {@code allergies} into a {@code Set<Allergy>} and set it to the {@code EditPersonDescriptor}
     * that we are building.
     */
    public EditPersonDescriptorBuilder withAllergies(String... allergies) {
        Set<Allergy> allerySet = Stream.of(allergies).map(Allergy::new).collect(Collectors.toSet());
        descriptor.setAllergies(allerySet);
        return this;
    }


    /**
     * Sets the {@code bloodType} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withBloodType(String bloodType) {
        descriptor.setBloodType(new BloodType(bloodType));
        return this;
    }
    /**
     * Sets the {@code alcoholicRecord} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withAlcoholicRecord(String alcoholicRecord) {
        descriptor.setAlcoholicRecord(new AlcoholicRecord(alcoholicRecord));
        return this;
    }

    /**
     * Sets the {@code gender} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withGender(String gender) {
        descriptor.setGender(new Gender(gender));
        return this;
    }

    /**
     * Sets the {@code SmokingRecord} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withSmokingRecord(String smokingRecord) {
        descriptor.setSmokingRecord(new SmokingRecord(smokingRecord));
        return this;
    }

    /**
     * Sets the {@code PastMedicalHistory} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withPastMedicalHistory(String pastMedicalHistory) {
        descriptor.setPastMedicalHistory(new PastMedicalHistory(pastMedicalHistory));
        return this;
    }

    public EditPersonDescriptor build() {
        return descriptor;
    }
}
