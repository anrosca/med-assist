package inc.evil.medassist.user.repository;

import inc.evil.medassist.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    @Query("select u from User u join fetch u.authorities where u.username = :username")
    Optional<User> findByUsername(@Param("username") String username);

    @Query("select u from User u join fetch u.authorities where u.id = :id")
    Optional<User> findByIdWithAuthorities(String id);

    @Query("select u from User u join fetch u.authorities")
    List<User> findAllWithAuthorities();
}
