import {AfterViewInit, Component, Inject, OnInit, ViewChild} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from '@angular/material/dialog';
import {TreatmentService} from '../../core/services/treatment.service';
import {NotificationService} from '../../core/services/notification.service';
import {MatTableDataSource} from '@angular/material/table';
import {MatSort} from '@angular/material/sort';
import {MatPaginator} from '@angular/material/paginator';
import {AddTreatmentDialog} from './add-treatment-dialog/add-treatment-dialog';
import {FileRecord} from '../../core/model/file-record';
import {FileRecordService} from '../../core/services/file-record.service';
import {PatientService} from '../../core/services/patient.service';
import {Subject} from 'rxjs';
import {DentalChartComponent} from './dental-chart/dental-chart.component';
import {FormControl, FormGroup, ValidationErrors, ValidatorFn, Validators} from '@angular/forms';

@Component({
    selector: 'app-view-patient',
    templateUrl: './view-patient-dialog.html',
    styleUrls: ['./view-patient-dialog.css']
})
export class ViewPatientDialog implements OnInit {
    constructor(public dialogRef: MatDialogRef<ViewPatientDialog>,
                @Inject(MAT_DIALOG_DATA) public data: ViewPatientDialogData,
                private treatmentService: TreatmentService,
                private notificationService: NotificationService,
                private fileRecordService: FileRecordService,
                private patientService: PatientService,
                public dialog: MatDialog) {
    }

    treatmentsDisplayedColumns: string[] = ['description', 'doctor', 'teeth', 'price', 'createdAt'];
    treatmentsDataSource;
    @ViewChild(MatSort, {static: true}) treatmentsSort: MatSort;
    @ViewChild(MatPaginator, {static: true}) treatmentsPaginator: MatPaginator;
    @ViewChild(DentalChartComponent, {static: true}) dentalChartComponent: DentalChartComponent;
    refresh = new Subject<void>();
    files: File[] = [];
    fileRecords: FileRecord[] = [];
    isEditable = false;
    form: FormGroup;
    patientRequiredFields = ['firstName', 'lastName', 'phoneNumber', 'birthDate', 'source'];

    onUpload(event) {
        event.addedFiles.forEach(f => {
            this.fileRecordService.upload(f, this.data.patient.id)
                .subscribe(() => {
                        this.initFiles();
                    },
                    error => {
                        const resMessage = error.error.messages || error.message || error.error.message || error.toString();
                        this.notificationService.openSnackBar(resMessage);
                    });
        });
    }

    onRemove(file: FileRecord) {
        this.fileRecordService.deleteById(file.id)
            .subscribe(() => {
                    this.initFiles();
                },
                error => {
                    const resMessage = error.error.messages || error.message || error.error.message || error.toString();
                    this.notificationService.openSnackBar(resMessage);
                });
    }

    download(file: FileRecord) {
        this.fileRecordService.download(file.url)
            .subscribe(data => this.downloadFile(data, file),
                error => {
                    const resMessage = error.error.messages || error.message || error.error.message || error.toString();
                    this.notificationService.openSnackBar(resMessage);
                });
        console.log(file);
    }

    downloadFile(data: Blob, file: FileRecord) {
        const blob = new Blob([data], {type: file.type});
        const url = window.URL.createObjectURL(blob);
        window.open(url);
        const anchor = document.createElement('a');
        anchor.download = file.name;
        anchor.href = url;
        anchor.click();
    }

    ngOnInit(): void {
        this.initFiles();
        this.setUpValidationRules();

        this.treatmentService.getTreatmentsByPatientId(this.data.patient.id)
            .subscribe(treatments => {
                    this.treatmentsDataSource = new MatTableDataSource(treatments);
                    this.treatmentsDataSource.sort = this.treatmentsSort;
                    this.treatmentsDataSource.paginator = this.treatmentsPaginator;
                    this.treatmentsDataSource.filterPredicate = (data: any, filter) => {
                        const dataStr = JSON.stringify(data).toLowerCase();
                        return dataStr.indexOf(filter) != -1;
                    };
                },
                error => {
                    const resMessage = error.error.messages || error.message || error.error.message || error.toString();
                    this.notificationService.openSnackBar(resMessage);
                });
    }

    private initFiles() {
        this.fileRecordService.getByPatientId(this.data.patient.id)
            .subscribe(fileRecords => {
                    this.fileRecords = fileRecords;
                },
                error => {
                    const resMessage = error.error.messages || error.message || error.error.message || error.toString();
                    this.notificationService.openSnackBar(resMessage);
                });
    }

    applyTreatmentsFilter(event: Event) {
        const filterValue = (event.target as HTMLInputElement).value;
        this.treatmentsDataSource.filter = filterValue.trim().toLowerCase();

        if (this.treatmentsDataSource.paginator) {
            this.treatmentsDataSource.paginator.firstPage();
        }
    }

    openAddTreatmentDialog() {
        const dialogRef = this.dialog.open(AddTreatmentDialog, {
            width: 'auto',
            disableClose: true,
            data: {treatment: {}, patient: this.data.patient}
        });

        dialogRef.afterClosed().subscribe(result => {
            console.log('The dialog was closed');
            if (result.status === 'Submitted') {
                this.treatmentService.createTreatment({
                    doctorId: result.treatment.doctorId,
                    description: result.treatment.description,
                    patientId: this.data.patient.id,
                    price: result.treatment.price,
                    treatedTeeth: result.treatment.treatedTeeth
                }).subscribe(() => {
                    this.ngOnInit();
                    this.dentalChartComponent.ngOnInit();
                }, error => {
                    console.log(error);
                    const resMessage = error.error.messages || error.message || error.error.message || error.toString();
                    this.notificationService.openSnackBar(resMessage);
                });
            }
        });
    }

    onSave() {
        this.patientService.updatePatient(this.data.patient).subscribe(() => {
            this.notificationService.openSnackBar('Patient successfully updated');
            this.ngOnInit();
        }, error => {
            console.log(error);
            const resMessage = error.error.messages || error.message || error.error.message || error.toString();
            this.notificationService.openSnackBar(resMessage);
        });
        this.isEditable = false;
        this.clearValidators();
    }

    private clearValidators() {
        this.patientRequiredFields.forEach(fieldName => this.form.get(fieldName).clearValidators());
    }

    onEdit() {
        this.isEditable = true;
        this.setUpValidatorForRequiredFields();
    }

    private setUpValidatorForRequiredFields() {
        this.patientRequiredFields.forEach(fieldName => this.form.get(fieldName).setValidators(Validators.required));
    }

    private setUpValidationRules() {
        this.form = new FormGroup({
            firstName: new FormControl(this.data.patient.firstName),
            lastName: new FormControl(this.data.patient.lastName),
            phoneNumber: new FormControl(this.data.patient.phoneNumber),
            birthDate: new FormControl(this.data.patient.birthDate),
            source: new FormControl(this.data.patient.source),
        });
        this.form.get('firstName').valueChanges
            .subscribe(val => { this.data.patient.firstName = val; });
        this.form.get('lastName').valueChanges
            .subscribe(val => { this.data.patient.lastName = val; });
        this.form.get('phoneNumber').valueChanges
            .subscribe(val => { this.data.patient.phoneNumber = val; });
        this.form.get('birthDate').valueChanges
            .subscribe(val => { this.data.patient.birthDate = val; });
        this.form.get('source').valueChanges
            .subscribe(val => { this.data.patient.source = val; });
    }
}

export interface ViewPatientDialogData {
    patient: any;
}
