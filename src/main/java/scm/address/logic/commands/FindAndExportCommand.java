package scm.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static scm.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static scm.address.logic.parser.CliSyntax.PREFIX_FILENAME;
import static scm.address.logic.parser.CliSyntax.PREFIX_NAME;
import static scm.address.model.file.FileFormat.CSV_FILE;
import static scm.address.model.file.FileFormat.JSON_FILE;
import static scm.address.model.file.FileFormat.getFileFormat;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;

import scm.address.commons.exceptions.IllegalValueException;
import scm.address.logic.commands.exceptions.CommandException;
import scm.address.model.AddressBook;
import scm.address.model.Model;
import scm.address.model.person.Person;
import scm.address.storage.JsonAddressBookStorage;

/**
 * Represents a command to find users based on specified criteria and export their information.
 * The command allows filtering of users by tags, name, and address, and exports the
 * filtered list to a specified file, in a specified format.
 */
public class FindAndExportCommand extends Command {

    public static final String COMMAND_WORD = "find_and_export";
    public static final String DEFAULT_DATA_DIR = "data/";
    public static final String DEFAULT_FILEPATH = "defaultfilename.json";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Exports the users filtered by a tag "
            + "and other optional parameters.\n"
            + "Parameters: "
            + "TAG "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_FILENAME + "FILENAME]\n"
            + "Example: " + COMMAND_WORD + " "
            + "cs2103t "
            + PREFIX_NAME + "john "
            + PREFIX_ADDRESS + "olive street 42 "
            + PREFIX_FILENAME + "output1.json";
    public static final String FILE_NOT_WRITABLE_MESSAGE = "File exists but is not writable: ";
    public static final String MESSAGE_UNSUPPORTED_FILE_FORMAT = "Unsupported file format: ";
    private final String tag;
    private final String name;
    private final String address;
    private final File file;

    /**
     * Constructs a FindAndExportCommand to find and export users' information.
     *
     * @param tag The tag by which users are filtered.
     * @param name The name substring by which users are further filtered. Can be {@code null}.
     * @param address The address substring by which users are further filtered. Can be {@code null}.
     * @param file The file to which the filtered users are exported.
     */
    public FindAndExportCommand(String tag, String name, String address, File file) {
        this.tag = tag;
        this.name = name;
        this.address = address;
        this.file = file;
    }

    public String getTag() {
        return tag;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public File getFile() {
        return file;
    }


    @Override
    public CommandResult execute(Model model) throws CommandException {
        Predicate<Person> predicate = createPredicateForFiltering(tag, name, address);
        model.updateFilteredPersonList(predicate);

        List<Person> filteredList = model.getFilteredPersonList();

        if (filteredList.isEmpty()) {
            throw new CommandException("No users found.");
        }

        try {
            exportData(filteredList, this.file);
            return new CommandResult(String.format("Export successful to [%s].", this.file.getName()));
        } catch (IOException | IllegalValueException e) {
            throw new CommandException("Error exporting data: " + e.getMessage());
        }
    }

    private Predicate<Person> createPredicateForFiltering(String tag, String name, String address) {
        Predicate<Person> predicate = person -> true;

        if (tag != null && !tag.isBlank()) {
            predicate = predicate.and(person -> person.getTags().stream().anyMatch(t -> t.tagName.equals(tag)));
        }
        if (name != null && !name.isBlank()) {
            predicate = predicate.and(person -> person.getName().fullName.toLowerCase().contains(name.toLowerCase()));
        }
        if (address != null && !address.isBlank()) {
            predicate = predicate.and(person -> person.getAddress().value
                    .toLowerCase().contains(address.toLowerCase()));
        }

        return predicate;
    }

    private void exportData(List<Person> users, File file) throws IOException, IllegalValueException {
        String fileFormat = getFileFormat(file);
        switch (fileFormat) {
        case JSON_FILE:
            exportDataAsJson(users, file);
            break;
        case CSV_FILE:
            exportDataAsCsv(users, file);
            break;
        default:
            throw new IOException(MESSAGE_UNSUPPORTED_FILE_FORMAT + fileFormat);
        }
    }

    private void exportDataAsJson(List<Person> users, File file) throws IOException {
        requireNonNull(users);
        requireNonNull(file);
        Path filePath = file.toPath();
        JsonAddressBookStorage jsonAddressBookStorage = new JsonAddressBookStorage(filePath);
        AddressBook addressBook = new AddressBook();
        for (Person p : users) {
            addressBook.addPerson(p);
        }
        jsonAddressBookStorage.saveAddressBook(addressBook);
    }

    private void exportDataAsCsv(List<Person> users, File file) throws IOException {
        if (file.exists() && !file.canWrite()) {
            throw new IOException(FILE_NOT_WRITABLE_MESSAGE + file.getAbsolutePath());
        }
        StringBuilder csv = new StringBuilder();
        csv.append("Name,Phone,Email,Address,Tags\n");
        for (Person user : users) {
            csv.append(user.getName().fullName).append(",");
            csv.append(user.getPhone().value).append(",");
            csv.append(user.getEmail().value).append(",");
            csv.append('\"' + user.getAddress().value).append("\",");
            csv.append(user.getTags().stream().map(tag -> tag.tagName).reduce((a, b) -> a + " | " + b).orElse(""));
            csv.append("\n");
        }
        Files.write(file.toPath(), csv.toString().getBytes());
    }
}
