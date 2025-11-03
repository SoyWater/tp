package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
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

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Adds a person to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a person to the address book. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_IDENTITY_NUMBER + "IDENTITY_NUMBER "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_ADDRESS + "ADDRESS "
            + PREFIX_EMERGENCY_CONTACT + "EMERGENCY CONTACT "
            + PREFIX_DATE_OF_BIRTH + "DATE OF BIRTH "
            + PREFIX_BLOOD_TYPE + "BLOOD TYPE "
            + "[" + PREFIX_ALCOHOLIC_RECORD + "ALCOHOLIC RECORD] "
            + PREFIX_GENDER + "GENDER "
            + "[" + PREFIX_SMOKING_RECORD + "SMOKING RECORD] "
            + "[" + PREFIX_PAST_MEDICAL_HISTORY + "PAST MEDICAL HISTORY] "
            + "[" + PREFIX_TAG + "TAG]"
            + "[" + PREFIX_ALLERGY + "ALLERGY]"
            + "[" + PREFIX_MEDICINE + "MEDICINE]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_IDENTITY_NUMBER + "A91234567 "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
            + PREFIX_EMERGENCY_CONTACT + "[Mother] +6591234567 "
            + PREFIX_BLOOD_TYPE + "AB "
            + PREFIX_ALCOHOLIC_RECORD + "Social drinker "
            + PREFIX_GENDER + "M "
            + PREFIX_DATE_OF_BIRTH + "01-01-2000 "
            + PREFIX_SMOKING_RECORD + "Heavy smoker "
            + PREFIX_ALLERGY + "nuts "
            + PREFIX_TAG + "priorityHigh "
            + PREFIX_TAG + "diabetesFollowUp "
            + PREFIX_PAST_MEDICAL_HISTORY + "Diabetes "
            + PREFIX_MEDICINE + "100mg painkillers/day "
            + PREFIX_MEDICINE + "100mg Panadol/day" + "\n"
            + "Note: \\ is preserved for internal usage and should not be used in any field other than prefix.";

    public static final String OLDER_THAN_100_YEARS_WARNING =
            "WARNING: The age of the person is older than 100 years.\n";
    public static final String MESSAGE_SUCCESS = "New person added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "A person with this ID already exists";

    private final Person toAdd;
    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddCommand(Person person) {
        requireNonNull(person);
        toAdd = person;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasPerson(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.addPerson(toAdd);
        model.setViewedPerson(toAdd);
        String returnString = String.format(MESSAGE_SUCCESS, Messages.format(toAdd));
        if (toAdd.getDateOfBirth().isOlderThanAge(100)) {
            returnString = OLDER_THAN_100_YEARS_WARNING + returnString;
        }
        return new CommandResult(returnString);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddCommand)) {
            return false;
        }

        AddCommand otherAddCommand = (AddCommand) other;
        return toAdd.equals(otherAddCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
