package inc.evil.medassist.file.facade;

import inc.evil.medassist.file.model.FileRecord;
import inc.evil.medassist.file.service.FileRecordService;
import inc.evil.medassist.file.web.CreateFileRecordRequest;
import inc.evil.medassist.patient.model.Patient;
import inc.evil.medassist.patient.service.PatientService;
import inc.evil.medassist.file.web.FileRecordResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
class FileRecordFacadeImpl implements FileRecordFacade {

    private final FileRecordService fileRecordService;
    private final PatientService patientService;

    @Override
    @Transactional
    public FileRecordResponse create(final CreateFileRecordRequest request) {
        final FileRecord fileRecord = toFileRecord(request);
        return FileRecordResponse.from(fileRecordService.create(fileRecord));
    }

    @Override
    public FileRecordResponse findById(String id){
        return FileRecordResponse.from(fileRecordService.findById(id));
    }

    @Override
    public List<FileRecordResponse> findByPatientId(String id){
        return fileRecordService.findByPatientId(id).stream()
                .map(FileRecordResponse::from)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(final String id) {
        fileRecordService.deleteById(id);
    }

    private FileRecord toFileRecord(final CreateFileRecordRequest request) {
        final String name = StringUtils.cleanPath(Objects.requireNonNull(request.getFile().getOriginalFilename()));
        try {
            final Patient patient = patientService.findById(request.getPatientId());
            final FileRecord fileRecord = FileRecord.builder()
                    .name(name)
                    .data(request.getFile().getBytes())
                    .type(request.getFile().getContentType())
                    .patient(patient).build();
            patient.addFileRecord(fileRecord);
            return fileRecord;
        } catch (IOException e) {
            throw new InvalidFileException("Could not process file " + name);
        }
    }


}
