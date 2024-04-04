package scm.address.model.file;

import static java.util.Objects.requireNonNull;

import java.io.File;

import scm.address.commons.exceptions.IllegalValueException;

/**
 * Represents the file format of a file.
 */
public class FileFormat {
    public static final String JSON_FILE = "json";
    public static final String CSV_FILE = "csv";
    public static final String MESSAGE_INVALID_FILE_FORMAT = "Invalid file format!";
    public static final String MESSAGE_UNSUPPORTED_FILE_FORMAT = "Unsupported file format!";


    /**
     * Returns the file format of the {@code file}. Throws an {@code IllegalValueException} if the file format is not
     * found, or is unsupported.
     *
     * @param file A File.
     * @return The file format of the {@code file}.
     * @throws IllegalValueException If the file format is not found, or is unsupported.
     */
    public static String getFileFormat(File file) throws IllegalValueException {
        requireNonNull(file);
        String fileName = file.getName();
        int index = fileName.lastIndexOf(".");
        if (index == -1) {
            throw new IllegalValueException(MESSAGE_INVALID_FILE_FORMAT);
        }

        String tempEnd = fileName.substring(index + 1).toLowerCase();
        switch (tempEnd) {
        case "json":
            return JSON_FILE;
        case "csv":
            return CSV_FILE;
        default:
            throw new IllegalValueException(MESSAGE_UNSUPPORTED_FILE_FORMAT);
        }
    }
}
