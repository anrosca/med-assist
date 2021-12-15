package inc.evil.medassist.doctor.repository;

import inc.evil.medassist.doctor.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, String> {
    Optional<Doctor> findByUsername(String username);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select d from Doctor d where d.id = :id")
    Doctor findByIdAndLock(@Param("id") String id);

    @Query("select d from Doctor d join fetch d.authorities where d.id = :id")
    Optional<Doctor> findByIdWithAuthorities(@Param("id") String id);

    @Query("select d from Doctor d join fetch d.authorities")
    List<Doctor> findAllWithAuthorities();
}
