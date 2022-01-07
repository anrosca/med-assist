package inc.evil.medassist.file.service;

import inc.evil.medassist.file.model.FileRecord;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface FileRecordService {
    FileRecord create(FileRecord fileRecord);

    FileRecord findById(String id);

    List<FileRecord> findByPatientId(String id);

    void deleteById(String id);
}
