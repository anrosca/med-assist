package inc.evil.medassist.file.facade;

import inc.evil.medassist.file.web.CreateFileRecordRequest;
import inc.evil.medassist.file.web.FileRecordResponse;

import java.util.List;

public interface FileRecordFacade {
    FileRecordResponse create(CreateFileRecordRequest request);

    FileRecordResponse findById(String id);

    List<FileRecordResponse> findByPatientId(String id);

    void deleteById(String id);
}
