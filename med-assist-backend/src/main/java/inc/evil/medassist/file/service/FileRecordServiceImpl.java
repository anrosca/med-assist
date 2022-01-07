package inc.evil.medassist.file.service;

import inc.evil.medassist.common.exception.NotFoundException;
import inc.evil.medassist.file.model.FileRecord;
import inc.evil.medassist.file.repository.FileRecordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
class FileRecordServiceImpl implements FileRecordService {
    private  final FileRecordRepository fileRecordRepository;

    @Override
    @Transactional
    public FileRecord create(FileRecord fileRecord){
        return fileRecordRepository.save(fileRecord);
    }

    @Override
    @Transactional(readOnly = true)
    public FileRecord findById(String id){
        return fileRecordRepository.findById(id).orElseThrow(() -> new NotFoundException(FileRecord.class, "id", id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<FileRecord> findByPatientId(final String id) {
        return fileRecordRepository.findByPatientId(id);
    }

    @Override
    @Transactional
    public void deleteById(final String id) {
        final FileRecord fileRecord = findById(id);
        fileRecordRepository.delete(fileRecord);
    }

}
