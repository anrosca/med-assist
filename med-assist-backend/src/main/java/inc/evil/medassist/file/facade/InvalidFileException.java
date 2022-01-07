package inc.evil.medassist.file.facade;

public class InvalidFileException extends RuntimeException {
    public InvalidFileException(final String message) {
        super(message);
    }
}
