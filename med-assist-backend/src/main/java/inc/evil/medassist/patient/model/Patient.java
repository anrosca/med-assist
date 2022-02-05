package inc.evil.medassist.patient.model;

import inc.evil.medassist.common.entity.AbstractEntity;
import inc.evil.medassist.file.model.FileRecord;
import inc.evil.medassist.teeth.model.Tooth;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "patients")
@SuperBuilder
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Patient extends AbstractEntity {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String source;
    private LocalDate birthDate;

    @Column(insertable = false)
    private boolean isDeleted;

    @Builder.Default
    @OneToMany(mappedBy = "patient",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tooth> teeth = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "patient",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FileRecord> fileRecords = new ArrayList<>();

    public void addFileRecord(FileRecord fileRecord){
        fileRecord.setPatient(this);
        this.fileRecords.add(fileRecord);
    }

    public void removeFileRecord(FileRecord fileRecord){
        fileRecord.setPatient(null);
        this.fileRecords.remove(fileRecord);
    }

    public Patient mergeWith(Patient otherPatient) {
        return Patient.builder()
                .id(id)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .teeth(teeth)
                .fileRecords(fileRecords)
                .firstName(otherPatient.getFirstName() != null ? otherPatient.getFirstName() : firstName)
                .lastName(otherPatient.getLastName() != null ? otherPatient.getLastName() : lastName)
                .birthDate(otherPatient.getBirthDate() != null ? otherPatient.getBirthDate() : birthDate)
                .phoneNumber(otherPatient.getPhoneNumber() != null ? otherPatient.getPhoneNumber() : phoneNumber)
                .source(otherPatient.getSource() != null ? otherPatient.getSource() : source)
                .build();
    }
}
