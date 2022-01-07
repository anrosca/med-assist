package inc.evil.medassist.file.web;

import inc.evil.medassist.file.model.FileRecord;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Data
@Builder
public class FileRecordResponse {
    private String id;
    private String patientId;
    private String name;
    private String url;
    private String type;
    private byte[] data;
    private long size;

    public static FileRecordResponse from(FileRecord fileRecord) {
        String fileDownloadUri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/v1/files/")
                .path(fileRecord.getId())
                .toUriString();

        return FileRecordResponse.builder()
                .id(fileRecord.getId())
                .name(fileRecord.getName())
                .size(fileRecord.getData().length)
                .url(fileDownloadUri)
                .data(fileRecord.getData())
                .type(fileRecord.getType())
                .patientId(fileRecord.getPatient().getId())
                .build();
    }
}
