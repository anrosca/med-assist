package inc.evil.medassist.common.jpa;

import javax.persistence.EntityManager;
import java.util.function.Function;

/**
 * @author Vlad Mihalcea
 */
@FunctionalInterface
public interface JPATransactionFunction<T> extends Function<EntityManager, T> {
    default void beforeTransactionCompletion() {
    }
    default void afterTransactionCompletion() {
    }
}
