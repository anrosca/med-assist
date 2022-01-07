package inc.evil.medassist.file.repository;

import inc.evil.medassist.file.model.FileRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRecordRepository extends JpaRepository<FileRecord, String> {
    List<FileRecord> findByPatientId(String patientId);
}
