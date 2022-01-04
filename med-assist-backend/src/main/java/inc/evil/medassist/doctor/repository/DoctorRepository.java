package inc.evil.medassist.doctor.repository;

import inc.evil.medassist.doctor.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, String> {
    @Query("select d From Doctor d where d.username = lower(:username) and d.enabled = true")
    Optional<Doctor> findEnabledByUsername(@Param("username") String username);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select d from Doctor d where d.id = :id and d.enabled = true")
    Doctor findEnabledByIdAndLock(@Param("id") String id);

    @Query("select d from Doctor d join fetch d.authorities where d.id = :id and d.enabled = true")
    Optional<Doctor> findEnabledByIdWithAuthorities(@Param("id") String id);

    @Query("select d from Doctor d join fetch d.authorities where d.enabled = true")
    List<Doctor> findAllEnabledWithAuthorities();
}
