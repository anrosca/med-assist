import {Component, Input, OnInit, Pipe, PipeTransform, ViewChild} from '@angular/core';
import {Patient} from '../../../core/model/patient';
import {TeethService} from '../../../core/services/teeth.service';
import {NotificationService} from '../../../core/services/notification.service';
import {Tooth} from '../../../core/model/tooth';
import {DentalChart} from '../../../core/model/dental-chart';
import {MatSort} from '@angular/material/sort';
import {MatPaginator} from '@angular/material/paginator';
import {Treatment} from '../../../core/model/treatment';
import {TreatmentService} from '../../../core/services/treatment.service';
import {MatTableDataSource} from '@angular/material/table';
import {AddToothTreatmentDialog} from '../add-tooth-treatment-dialog/add-tooth-treatment-dialog';
import {MatDialog} from '@angular/material/dialog';

@Component({
    selector: 'app-dental-chart',
    templateUrl: './dental-chart.component.html',
    styleUrls: ['./dental-chart.component.css']
})
export class DentalChartComponent implements OnInit {

    @Input() patient: Patient;

    dentalChart: DentalChart;
    displayedColumns: string[] = ['description', 'doctor', 'teeth', 'price', 'createdAt'];
    dataSource;

    @ViewChild(MatSort, {static: true}) sort: MatSort;
    @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;

    currentTooth: Tooth = {
        extracted: false,
        code: 'n/a',
        scientificName: 'n/a',
        id: 'n/a',
        number: 'n/a',
        patientId: 'n/a',
        isSelected: false
    };

    constructor(private teethService: TeethService,
                private treatmentService: TreatmentService,
                private notificationService: NotificationService,
                private dialog: MatDialog) {

    }

    viewTooth(tooth: Tooth) {
        this.currentTooth = tooth;
        this.treatmentService.getTreatmentsByToothId(tooth.id)
            .subscribe(treatments => {
                    this.dataSource = new MatTableDataSource(treatments);
                    this.dataSource.sort = this.sort;
                    this.dataSource.paginator = this.paginator;
                    this.dataSource.filterPredicate = (data: any, filter) => {
                        const dataStr = JSON.stringify(data).toLowerCase();
                        return dataStr.indexOf(filter) != -1;
                    };
                },
                error => {
                    const resMessage = error.error.messages || error.message || error.error.message || error.toString();
                    this.notificationService.openSnackBar(resMessage);
                });


    }

    applyFilter(event: Event) {
        const filterValue = (event.target as HTMLInputElement).value;
        this.dataSource.filter = filterValue.trim().toLowerCase();

        if (this.dataSource.paginator) {
            this.dataSource.paginator.firstPage();
        }
    }

    ngOnInit(): void {
        this.teethService.getPatientTeeth(this.patient.id)
            .subscribe(teeth => {
                    const mappedTeeth = teeth as unknown as Tooth[];
                    this.dentalChart = {
                        UR1: mappedTeeth.filter((tooth) => tooth.code === 'UR1')[0],
                        UR2: mappedTeeth.filter((tooth) => tooth.code === 'UR2')[0],
                        UR3: mappedTeeth.filter((tooth) => tooth.code === 'UR3')[0],
                        UR4: mappedTeeth.filter((tooth) => tooth.code === 'UR4')[0],
                        UR5: mappedTeeth.filter((tooth) => tooth.code === 'UR5')[0],
                        UR6: mappedTeeth.filter((tooth) => tooth.code === 'UR6')[0],
                        UR7: mappedTeeth.filter((tooth) => tooth.code === 'UR7')[0],
                        UR8: mappedTeeth.filter((tooth) => tooth.code === 'UR8')[0],
                        UL1: mappedTeeth.filter((tooth) => tooth.code === 'UL1')[0],
                        UL2: mappedTeeth.filter((tooth) => tooth.code === 'UL2')[0],
                        UL3: mappedTeeth.filter((tooth) => tooth.code === 'UL3')[0],
                        UL4: mappedTeeth.filter((tooth) => tooth.code === 'UL4')[0],
                        UL5: mappedTeeth.filter((tooth) => tooth.code === 'UL5')[0],
                        UL6: mappedTeeth.filter((tooth) => tooth.code === 'UL6')[0],
                        UL7: mappedTeeth.filter((tooth) => tooth.code === 'UL7')[0],
                        UL8: mappedTeeth.filter((tooth) => tooth.code === 'UL8')[0],
                        LL1: mappedTeeth.filter((tooth) => tooth.code === 'LL1')[0],
                        LL2: mappedTeeth.filter((tooth) => tooth.code === 'LL2')[0],
                        LL3: mappedTeeth.filter((tooth) => tooth.code === 'LL3')[0],
                        LL4: mappedTeeth.filter((tooth) => tooth.code === 'LL4')[0],
                        LL5: mappedTeeth.filter((tooth) => tooth.code === 'LL5')[0],
                        LL6: mappedTeeth.filter((tooth) => tooth.code === 'LL6')[0],
                        LL7: mappedTeeth.filter((tooth) => tooth.code === 'LL7')[0],
                        LL8: mappedTeeth.filter((tooth) => tooth.code === 'LL8')[0],
                        LR1: mappedTeeth.filter((tooth) => tooth.code === 'LR1')[0],
                        LR2: mappedTeeth.filter((tooth) => tooth.code === 'LR2')[0],
                        LR3: mappedTeeth.filter((tooth) => tooth.code === 'LR3')[0],
                        LR4: mappedTeeth.filter((tooth) => tooth.code === 'LR4')[0],
                        LR5: mappedTeeth.filter((tooth) => tooth.code === 'LR5')[0],
                        LR6: mappedTeeth.filter((tooth) => tooth.code === 'LR6')[0],
                        LR7: mappedTeeth.filter((tooth) => tooth.code === 'LR7')[0],
                        LR8: mappedTeeth.filter((tooth) => tooth.code === 'LR8')[0]
                    };
                    this.currentTooth = this.dentalChart.UL1;
                },
                error => {
                    const resMessage = error.error.messages || error.message || error.error.message || error.toString();
                    this.notificationService.openSnackBar(resMessage);
                });
    }

    openAddTreatmentDialog() {
        const dialogRef = this.dialog.open(AddToothTreatmentDialog, {
            width: 'auto',
            disableClose: true,
            data: {treatment: {}, patient: this.patient, tooth: this.currentTooth}
        });

        dialogRef.afterClosed().subscribe(result => {
            if (result.status === 'Submitted') {
                this.treatmentService.createTreatment({
                    doctorId: result.treatment.doctorId,
                    description: result.treatment.description,
                    patientId: this.patient.id,
                    price: result.treatment.price,
                    teethIds: [this.currentTooth.id]
                }).subscribe(() => {
                    this.viewTooth(this.currentTooth);
                }, error => {
                    console.log(error);
                    const resMessage = error.error.messages || error.message || error.error.message || error.toString();
                    this.notificationService.openSnackBar(resMessage);
                });
            }
        });
    }
}

@Pipe({name: 'teethPrinter'})
export class TeethPrinter implements PipeTransform {
    transform(input: Tooth[]): any {
        return input.map(value => ' ' + value.code);
    }
}

