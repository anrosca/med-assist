import {Component, OnInit, ViewChild} from '@angular/core';
import {MatSort} from "@angular/material/sort";
import {MatPaginator} from "@angular/material/paginator";
import {TreatmentService} from "../../core/services/treatment.service";
import {NotificationService} from "../../core/services/notification.service";
import {MatTableDataSource} from "@angular/material/table";
import {Title} from "@angular/platform-browser";

@Component({
    selector: 'app-treatments-list',
    templateUrl: './treatments-list.component.html',
    styleUrls: ['./treatments-list.component.css']
})
export class TreatmentsListComponent implements OnInit {

    displayedColumns: string[] = ['patient', 'description', 'doctor', 'teeth', 'price', 'createdAt'];
    dataSource;
    @ViewChild(MatSort, {static: true}) sort: MatSort;
    @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;

    constructor(private titleService: Title,
                private treatmentService: TreatmentService,
                private notificationService: NotificationService) {
    }

    ngOnInit(): void {
        this.titleService.setTitle('med-assist-client - Treatments');

        this.treatmentService.getAllTreatments()
            .subscribe(treatments => {
                console.log(treatments)
                    this.dataSource = new MatTableDataSource(treatments);
                    this.dataSource.sort = this.sort;
                    this.dataSource.paginator = this.paginator;
                    this.dataSource.filterPredicate = (data: any, filter) => {
                        const dataStr = JSON.stringify(data).toLowerCase();
                        return dataStr.indexOf(filter) != -1;
                    }
                },
                error => {
                    const resMessage = error.error.messages || error.message || error.error.message || error.toString();
                    this.notificationService.openSnackBar(resMessage);
                });
    }

    applyTreatmentsFilter(event: Event) {
        const filterValue = (event.target as HTMLInputElement).value;
        this.dataSource.filter = filterValue.trim().toLowerCase();

        if (this.dataSource.paginator) {
            this.dataSource.paginator.firstPage();
        }
    }

}
