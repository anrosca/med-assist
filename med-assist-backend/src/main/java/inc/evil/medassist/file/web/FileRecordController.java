package inc.evil.medassist.file.web;

import inc.evil.medassist.file.facade.FileRecordFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/files")
@RequiredArgsConstructor
public class FileRecordController {

    private final FileRecordFacade fileRecordFacade;

    @PostMapping
    public ResponseEntity<FileRecordResponse> uploadFile(@RequestParam(value = "file") MultipartFile file,
                                                         @RequestParam String patientId) {
        final CreateFileRecordRequest request = new CreateFileRecordRequest(patientId, file);
        final FileRecordResponse fileRecordResponse = fileRecordFacade.create(request);
        return ResponseEntity.status(HttpStatus.OK).body(fileRecordResponse);
    }

    @GetMapping(params = "patientId")
    public ResponseEntity<List<FileRecordResponse>> getFilesByPatientId(@RequestParam String patientId) {
        final List<FileRecordResponse> fileRecordResponses = fileRecordFacade.findByPatientId(patientId);
        return ResponseEntity.ok().body(fileRecordResponses);
    }

    @GetMapping("{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable String id) {
        final FileRecordResponse fileRecordResponse = fileRecordFacade.findById(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + fileRecordResponse.getName() + "\"")
                .body(fileRecordResponse.getData());
    }

    @DeleteMapping("{id}")
    public void deleteFile(@PathVariable String id) {
        fileRecordFacade.deleteById(id);
    }
}
