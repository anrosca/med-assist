package inc.evil.medassist.common.jpa;

import inc.evil.medassist.common.entity.Identifiable;
import org.hibernate.Session;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public abstract class AbstractEqualityCheckTest<T extends Identifiable<? extends Serializable>> extends
        AbstractJpaTest {

    protected void assertEqualityConsistency(Class<T> clazz, T entity) {
        Set<T> tuples = new HashSet<>();

        assertFalse(tuples.contains(entity));
        tuples.add(entity);
        assertTrue(tuples.contains(entity));

        doInJPA(entityManager -> {
            entityManager.persist(entity);
            entityManager.flush();
            assertTrue(
                    tuples.contains(entity),
                    "The entity is not found in the Set after it's persisted."
            );
        });

        assertTrue(tuples.contains(entity));

        doInJPA(entityManager -> {
            T _entity = entityManager.merge(entity);
            assertTrue(
                    tuples.contains(_entity),
                    "The entity is not found in the Set after it's merged."
            );
        });

        doInJPA(entityManager -> {
            entityManager.unwrap(Session.class).update(entity);
            assertTrue(
                    tuples.contains(entity),
                    "The entity is not found in the Set after it's reattached."
            );
        });

        doInJPA(entityManager -> {
            T _entity = entityManager.find(clazz, entity.getId());
            assertTrue(
                    tuples.contains(_entity),
                    "The entity is not found in the Set after it's loaded in a different Persistence Context."
            );
        });

        doInJPA(entityManager -> {
            T _entity = entityManager.getReference(clazz, entity.getId());
            assertTrue(
                    tuples.contains(_entity),
                    "The entity is not in the Set found after it's loaded as a proxy in a different Persistence Context."
            );
        });

        doInJPA(entityManager -> {
            T entityProxy = entityManager.getReference(
                    clazz,
                    entity.getId()
            );
            assertEquals(entity, entityProxy, "The entity is not equal with the entity proxy.");
        });

        T deletedEntity = doInJPA(entityManager -> {
            T _entity = entityManager.getReference(
                    clazz,
                    entity.getId()
            );
            entityManager.remove(_entity);
            return _entity;
        });

        assertTrue(
                tuples.contains(deletedEntity),
                "The entity is not found in the Set even after it's deleted."
        );
    }
}
