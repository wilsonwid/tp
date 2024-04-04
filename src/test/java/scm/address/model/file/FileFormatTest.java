package scm.address.model.file;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import org.junit.jupiter.api.Test;

import scm.address.commons.exceptions.IllegalValueException;

public class FileFormatTest {

    private static final String VALID_FILENAME_JSON = "hello.json";
    private static final String VALID_FILENAME_CSV = "hello.csv";
    private static final String INVALID_FILENAME = "hello.xyz";

    private static final String NO_FILE_FORMAT_FILENAME = "hello";

    @Test
    public void test_getFileFormatJson_success() throws IllegalValueException {
        File file = new File(VALID_FILENAME_JSON);
        assertEquals(FileFormat.JSON_FILE, FileFormat.getFileFormat(file));
    }

    @Test
    public void test_getFileFormatCsv_success() throws IllegalValueException {
        File file = new File(VALID_FILENAME_CSV);
        assertEquals(FileFormat.CSV_FILE, FileFormat.getFileFormat(file));
    }

    @Test
    public void test_getFileFormatInvalidFormat_failure() {
        File file = new File(INVALID_FILENAME);
        try {
            FileFormat.getFileFormat(file);
        } catch (IllegalValueException e) {
            assertEquals(FileFormat.MESSAGE_UNSUPPORTED_FILE_FORMAT, e.getMessage());
        }
    }

    @Test
    public void test_getFileFormatNoExtension_failure() {
        File file = new File(NO_FILE_FORMAT_FILENAME);
        try {
            FileFormat.getFileFormat(file);
        } catch (IllegalValueException e) {
            assertEquals(FileFormat.MESSAGE_INVALID_FILE_FORMAT, e.getMessage());
        }
    }

    @Test
    public void test_getFileFormatNullFile_failure() {
        try {
            FileFormat.getFileFormat(null);
        } catch (Exception e) {
            assertFalse(e instanceof IllegalValueException);
            assertTrue(e instanceof NullPointerException);
        }
    }

}
