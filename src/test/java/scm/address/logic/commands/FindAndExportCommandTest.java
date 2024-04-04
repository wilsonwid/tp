package scm.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static scm.address.testutil.TypicalPersons.ALICE;
import static scm.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import scm.address.logic.commands.exceptions.CommandException;
import scm.address.model.AddressBook;
import scm.address.model.Model;
import scm.address.model.ModelManager;
import scm.address.model.ScheduleList;
import scm.address.model.UserPrefs;
import scm.address.model.person.Person;

public class FindAndExportCommandTest {
    @TempDir
    Path testFolder;

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new ScheduleList());

    @Test
    public void execute_validTagAndFilename_success() throws Exception {
        Path filePath = testFolder.resolve("output.json");
        File file = new File(filePath.toString());
        FindAndExportCommand command = new FindAndExportCommand("friends", null, null, file);

        CommandResult result = command.execute(model);
        assertTrue(Files.exists(filePath), "The file was not created.");
    }

    @Test
    public void execute_exportAsJsonSuccessful() throws Exception {
        Model model = new ModelManager();
        model.addPerson(ALICE);

        String tag = "friends";
        String name = ALICE.getName().toString();
        String address = ALICE.getAddress().toString();
        Path tempFile = Files.createTempFile("testExport", ".json");
        String filename = tempFile.toString();

        FindAndExportCommand command = new FindAndExportCommand(tag, name, address, new File(filename));
        CommandResult result = command.execute(model);
        assertTrue(Files.exists(tempFile));
        Files.deleteIfExists(tempFile);
    }

    @Test
    public void execute_exportAsCsvSuccessful() throws Exception {
        Model model = new ModelManager();
        model.addPerson(ALICE);

        String tag = "friends";
        String name = ALICE.getName().toString();
        String address = ALICE.getAddress().toString();
        Path tempFile = Files.createTempFile("testExport", ".csv");
        String filename = tempFile.toString();

        FindAndExportCommand command = new FindAndExportCommand(tag, name, address, new File(filename));
        CommandResult result = command.execute(model);
        assertTrue(Files.exists(tempFile));
        Files.deleteIfExists(tempFile);
    }

    @Test
    public void execute_emptyFilteredList_failure() throws Exception {
        Model emptyModel = new ModelManager(new AddressBook(),
                new UserPrefs(), new ScheduleList());
        Path tempFile = Files.createTempFile("testExport", ".txt");
        String filename = tempFile.toString();
        FindAndExportCommand command = new FindAndExportCommand(ALICE.getTags().toString(),
                ALICE.getName().toString(), ALICE.getAddress().toString(), new File(filename));
        assertThrows(CommandException.class, () -> command.execute(emptyModel));
    }

    @Test
    public void execute_ioException_failure() throws IOException {
        Path tempFile = Files.createTempFile("testExport", ".txt");
        String filename = tempFile.toString();
        FindAndExportCommand command = new FindAndExportCommandStub(ALICE.getTags().toString(),
                ALICE.getName().toString(), ALICE.getAddress().toString(), filename);
        assertThrows(CommandException.class, () -> command.execute(model));
    }

    private class FindAndExportCommandStub extends FindAndExportCommand {
        /**
         * Constructs a FindAndExportCommand to find and export users' information.
         *
         * @param tag      The tag by which users are filtered.
         * @param name     The name substring by which users are further filtered. Can be {@code null}.
         * @param address  The address substring by which users are further filtered. Can be {@code null}.
         * @param filename The name of the file to which the filtered users are exported.
         */
        public FindAndExportCommandStub(String tag, String name, String address, String filename) {
            super(tag, name, address, new File(filename));
        }

        private void exportData(List<Person> users, String fileName) throws IOException {
            throw new IOException();
        }
    }
}
