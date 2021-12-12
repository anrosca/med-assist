package inc.evil.medassist.common.exception;

public class NotFoundException extends RuntimeException{

    public NotFoundException(final Class<?> clazz, final String property, String propertyValue) {
        super(clazz.getSimpleName() + " with " + property + " equal to [" + propertyValue + "] could not be found!");
    }
}
