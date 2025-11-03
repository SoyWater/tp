package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.AddCommand.OLDER_THAN_100_YEARS_WARNING;
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
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
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
 * Edits the details of an existing person in the address book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the person identified "
            + "by the index number used in the displayed person list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_IDENTITY_NUMBER + "IDENTITY_NUMBER] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_EMERGENCY_CONTACT + "EMERGENCY_CONTACT] "
            + "[" + PREFIX_DATE_OF_BIRTH + "DATE OF BIRTH] "
            + "[" + PREFIX_BLOOD_TYPE + "BLOOD TYPE] "
            + "[" + PREFIX_ALCOHOLIC_RECORD + "ALCOHOLIC RECORD] "
            + "[" + PREFIX_GENDER + "GENDER] "
            + "[" + PREFIX_SMOKING_RECORD + "SMOKING RECORD] "
            + "[" + PREFIX_PAST_MEDICAL_HISTORY + "PAST MEDICAL HISTORY] "
            + "[" + PREFIX_TAG + "TAG] "
            + "[" + PREFIX_ALLERGY + "ALLERGY] "
            + "[" + PREFIX_MEDICINE + "MEDICINE]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com" + "\n"
            + "Notes: \\ is preserved for internal usage and should not be used in any field other than prefix.";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Person: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private final Index index;
    private final EditPersonDescriptor editPersonDescriptor;

    /**
     * @param index of the person in the filtered person list to edit
     * @param editPersonDescriptor details to edit the person with
     */
    public EditCommand(Index index, EditPersonDescriptor editPersonDescriptor) {
        requireNonNull(index);
        requireNonNull(editPersonDescriptor);

        this.index = index;
        this.editPersonDescriptor = new EditPersonDescriptor(editPersonDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = createEditedPerson(personToEdit, editPersonDescriptor);

        if (!personToEdit.isSamePerson(editedPerson) && model.hasPerson(editedPerson)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.setPerson(personToEdit, editedPerson);
        model.setViewedPerson(editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        String returnString = String.format(MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedPerson));
        if (editedPerson.getDateOfBirth().isOlderThanAge(100)) {
            returnString = OLDER_THAN_100_YEARS_WARNING + returnString;
        }
        return new CommandResult(returnString);
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Person createEditedPerson(Person personToEdit, EditPersonDescriptor editPersonDescriptor) {
        assert personToEdit != null;
        assert editPersonDescriptor != null;

        Name updatedName = editPersonDescriptor.getName().orElse(personToEdit.getName());
        IdentityNumber updatedIdentityNumber = editPersonDescriptor.getIdentityNumber()
                .orElse(personToEdit.getIdentityNumber());
        Phone updatedPhone = editPersonDescriptor.getPhone().orElse(personToEdit.getPhone());
        Email updatedEmail = editPersonDescriptor.getEmail().orElse(personToEdit.getEmail());
        Address updatedAddress = editPersonDescriptor.getAddress().orElse(personToEdit.getAddress());
        EmergencyContact updatedEmergencyContact = editPersonDescriptor.getEmergencyContact()
                .orElse(personToEdit.getEmergencyContact());
        DateOfBirth updatedDateOfBirth = editPersonDescriptor.getDateOfBirth().orElse(personToEdit.getDateOfBirth());
        Set<Tag> updatedTags = editPersonDescriptor.getTags().orElse(personToEdit.getTags());
        Set<Allergy> updatedAllergies = editPersonDescriptor.getAllergies().orElse(personToEdit.getAllergies());
        Set<Medicine> updatedMedicines = editPersonDescriptor.getMedicines().orElse(personToEdit.getMedicines());
        SmokingRecord updatedSmokingRecord = editPersonDescriptor.getSmokingRecord()
                .orElse(personToEdit.getSmokingRecord());
        BloodType updatedBloodType = editPersonDescriptor.getBloodType().orElse(personToEdit.getBloodType());
        AlcoholicRecord updatedAlcoholicRecord = editPersonDescriptor.getAlcoholicRecord()
                .orElse(personToEdit.getAlcoholicRecord());
        Gender updatedGender = editPersonDescriptor.getGender().orElse(personToEdit.getGender());
        PastMedicalHistory updatedPastMedicalHistory = editPersonDescriptor.getPastMedicalHistory()
                .orElse(personToEdit.getPastMedicalHistory());

        return new Person(updatedName, updatedIdentityNumber, updatedPhone, updatedEmail,
                updatedAddress, updatedEmergencyContact, updatedTags, updatedDateOfBirth, updatedBloodType,
                updatedAlcoholicRecord, updatedGender, updatedSmokingRecord, updatedAllergies,
                updatedPastMedicalHistory, updatedMedicines);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        EditCommand otherEditCommand = (EditCommand) other;
        return index.equals(otherEditCommand.index)
                && editPersonDescriptor.equals(otherEditCommand.editPersonDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("editPersonDescriptor", editPersonDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditPersonDescriptor {
        private Name name;
        private IdentityNumber identityNumber;
        private Phone phone;
        private Email email;
        private Address address;
        private EmergencyContact emergencyContact;
        private Set<Tag> tags;
        private Set<Medicine> medicines;
        private Set<Allergy> allergies;
        private DateOfBirth dateOfBirth;
        private BloodType bloodType;
        private AlcoholicRecord alcoholicRecord;
        private Gender gender;
        private SmokingRecord smokingRecord;
        private PastMedicalHistory pastMedicalHistory;

        public EditPersonDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditPersonDescriptor(EditPersonDescriptor toCopy) {
            setName(toCopy.name);
            setIdentityNumber(toCopy.identityNumber);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setAddress(toCopy.address);
            setEmergencyContact(toCopy.emergencyContact);
            setTags(toCopy.tags);
            setAllergies(toCopy.allergies);
            setBloodType(toCopy.bloodType);
            setDateOfBirth(toCopy.dateOfBirth);
            setAlcoholicRecord(toCopy.alcoholicRecord);
            setDateOfBirth(toCopy.dateOfBirth);
            setGender(toCopy.gender);
            setSmokingRecord(toCopy.smokingRecord);
            setPastMedicalHistory(toCopy.pastMedicalHistory);
            setMedicines(toCopy.medicines);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, identityNumber, phone, email, address, emergencyContact, tags,
                    allergies, dateOfBirth, bloodType, alcoholicRecord, gender, smokingRecord, pastMedicalHistory,
                    medicines);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setIdentityNumber(IdentityNumber identityNumber) {
            this.identityNumber = identityNumber;
        }

        public Optional<IdentityNumber> getIdentityNumber() {
            return Optional.ofNullable(identityNumber);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        public void setEmergencyContact(EmergencyContact emergencyContact) {
            this.emergencyContact = emergencyContact;
        }

        public Optional<EmergencyContact> getEmergencyContact() {
            return Optional.ofNullable(emergencyContact);
        }

        public void setDateOfBirth(DateOfBirth dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
        }

        public Optional<DateOfBirth> getDateOfBirth() {
            return Optional.ofNullable(dateOfBirth);
        }

        public void setBloodType(BloodType bloodType) {
            this.bloodType = bloodType;
        }

        public Optional<BloodType> getBloodType() {
            return Optional.ofNullable(bloodType);
        }

        public void setAlcoholicRecord(AlcoholicRecord alcoholicRecord) {
            this.alcoholicRecord = alcoholicRecord;
        }
        public Optional<AlcoholicRecord> getAlcoholicRecord() {
            return Optional.ofNullable(alcoholicRecord);
        }

        public void setGender(Gender gender) {
            this.gender = gender;
        }

        public Optional<Gender> getGender() {
            return Optional.ofNullable(gender);
        }

        public void setSmokingRecord(SmokingRecord smokingRecord) {
            this.smokingRecord = smokingRecord;
        }

        public Optional<SmokingRecord> getSmokingRecord() {
            return Optional.ofNullable(smokingRecord);
        }

        public void setPastMedicalHistory(PastMedicalHistory pastMedicalHistory) {
            this.pastMedicalHistory = pastMedicalHistory;
        }

        public Optional<PastMedicalHistory> getPastMedicalHistory() {
            return Optional.ofNullable(pastMedicalHistory);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        /**
         * Sets {@code allergies} to this object's {@code allergies}.
         * A defensive copy of {@code allergies} is used internally.
         */
        public void setAllergies(Set<Allergy> allergies) {
            this.allergies = (allergies != null) ? new HashSet<>(allergies) : null;
        }

        /**
         * Returns an unmodifiable allergy set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code allergies} is null.
         */
        public Optional<Set<Allergy>> getAllergies() {
            return (allergies != null) ? Optional.of(Collections.unmodifiableSet(allergies)) : Optional.empty();
        }

        /**
         * Sets {@code medicines} to this object's {@code medicines}.
         * A defensive copy of {@code medicines} is used internally.
         */
        public void setMedicines(Set<Medicine> medicines) {
            this.medicines = (medicines != null) ? new HashSet<>(medicines) : null;
        }

        /**
         * Returns an unmodifiable medicine set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code medicines} is null.
         */
        public Optional<Set<Medicine>> getMedicines() {
            return (medicines != null) ? Optional.of(Collections.unmodifiableSet(medicines)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditPersonDescriptor)) {
                return false;
            }

            EditPersonDescriptor otherEditPersonDescriptor = (EditPersonDescriptor) other;
            return Objects.equals(name, otherEditPersonDescriptor.name)
                    && Objects.equals(identityNumber, otherEditPersonDescriptor.identityNumber)
                    && Objects.equals(phone, otherEditPersonDescriptor.phone)
                    && Objects.equals(email, otherEditPersonDescriptor.email)
                    && Objects.equals(address, otherEditPersonDescriptor.address)
                    && Objects.equals(emergencyContact, otherEditPersonDescriptor.emergencyContact)
                    && Objects.equals(tags, otherEditPersonDescriptor.tags)
                    && Objects.equals(dateOfBirth, otherEditPersonDescriptor.dateOfBirth)
                    && Objects.equals(bloodType, otherEditPersonDescriptor.bloodType)
                    && Objects.equals(alcoholicRecord, otherEditPersonDescriptor.alcoholicRecord)
                    && Objects.equals(gender, otherEditPersonDescriptor.gender)
                    && Objects.equals(smokingRecord, otherEditPersonDescriptor.smokingRecord)
                    && Objects.equals(allergies, otherEditPersonDescriptor.allergies)
                    && Objects.equals(medicines, otherEditPersonDescriptor.medicines)
                    && Objects.equals(pastMedicalHistory, otherEditPersonDescriptor.pastMedicalHistory);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("name", name)
                    .add("identityNumber", identityNumber)
                    .add("phone", phone)
                    .add("email", email)
                    .add("address", address)
                    .add("emergencyContact", emergencyContact)
                    .add("tags", tags)
                    .add("dateOfBirth", dateOfBirth)
                    .add("bloodType", bloodType)
                    .add("alcoholicRecord", alcoholicRecord)
                    .add("gender", gender)
                    .add("value", smokingRecord)
                    .add("allergies", allergies)
                    .add("medicines", medicines)
                    .add("pastMedicalHistory", pastMedicalHistory)
                    .toString();
        }
    }
}
