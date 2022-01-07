import {Component, OnInit, ViewChild} from '@angular/core';
import {ChartComponent} from 'ng-apexcharts';
import {
    ApexNonAxisChartSeries,
    ApexResponsive,
    ApexChart,
    ApexPlotOptions,
    ApexLegend,
    ApexTitleSubtitle,
    ApexAxisChartSeries,
    ApexDataLabels,
    ApexXAxis,
    ApexStroke,
    ApexGrid,
    ApexYAxis,
    ApexTooltip,
    ApexFill
} from 'ng-apexcharts';
import {PatientService} from "../../core/services/patient.service";
import {MatTableDataSource} from "@angular/material/table";
import {NotificationService} from "../../core/services/notification.service";
import {DoctorService} from "../../core/services/doctor.service";
import {AppointmentService} from "../../core/services/appointment.service";

@Component({
    selector: 'app-statistics-view',
    templateUrl: './statistics-view.component.html',
    styleUrls: ['./statistics-view.component.css']
})
export class StatisticsViewComponent implements OnInit {

    @ViewChild('patientsChart') patientsChart: ChartComponent;
    @ViewChild('doctorsChart') doctorsChart: ChartComponent;
    @ViewChild('appointmentsChart') appointmentsChart: ChartComponent;
    @ViewChild('operationsChart') operationsChart: ChartComponent;
    @ViewChild('appointmentsPerMonthChart') appointmentsPerMonthChart: ChartComponent;
    @ViewChild('newPatientsPerMonthChart') newPatientsPerMonthChart: ChartComponent;

    public patientsChartOptions: Partial<NonAxisChartOptions>;
    public doctorsChartOptions: Partial<NonAxisChartOptions>;
    public appointmentsChartOptions: Partial<NonAxisChartOptions>;
    public operationsChartOptions: Partial<AxisChartOptions>;
    public appointmentsPerMonthChartOptions: Partial<AxisChartOptions>;
    public newPatientsPerMonthChartOptions: Partial<AxisChartOptions>;

    initCharts(): void {
        this.initAppointmentsPerMonthChart();
        this.initOperationsChart();
        this.initPatientsChart();
        this.initDoctorsChart();
        this.initAppointmentsChart();
        this.initNewPatientsPerMonthChart();
    }

    private initNewPatientsPerMonthChart() {
        this.patientsService.countPatientsCreatedPerMonth()
            .subscribe(result => {
                    this.newPatientsPerMonthChartOptions = {
                        series: [
                            {
                                name: 'New Patients',
                                data: Object.values(result)
                            },
                        ],
                        chart: {
                            events: {
                                mounted: (chart) => {
                                    chart.windowResizeHandler();
                                }
                            },
                            type: 'bar',
                            height: 500
                        },
                        plotOptions: {
                            bar: {
                                horizontal: false,
                                columnWidth: '55%',
                            }
                        },
                        dataLabels: {
                            enabled: false
                        },
                        stroke: {
                            show: true,
                            width: 2,
                            colors: ['transparent']
                        },
                        xaxis: {
                            categories: Object.keys(result)
                        },
                        yaxis: {
                            title: {
                                text: '$ (thousands)'
                            }
                        },
                        fill: {
                            opacity: 1
                        },
                        tooltip: {
                            y: {
                                formatter: function (val) {
                                    return '$ ' + val + ' thousands';
                                }
                            }
                        }
                    };
                },
                error => {
                    const resMessage = error.error.messages || error.message || error.error.message || error.toString();
                    this.notificationService.openSnackBar(resMessage);
                });
    }

    private initAppointmentsChart() {
        this.appointmentsService.countAllAppointments()
            .subscribe(count => {
                    this.appointmentsChartOptions = {
                        series: [count],
                        chart: {
                            events: {
                                mounted: (chart) => {
                                    chart.windowResizeHandler();
                                }
                            },
                            height: 300,
                            type: 'radialBar'
                        },
                        plotOptions: {
                            radialBar: {
                                dataLabels: {
                                    name: {
                                        fontSize: '22px'
                                    },
                                    value: {
                                        fontSize: '22px'
                                    },
                                    total: {
                                        show: true,
                                        label: 'Total',
                                        formatter: function (w) {
                                            return '' + count;
                                        }
                                    }
                                }
                            }
                        },
                        labels: ['Appointments']
                    };
                },
                error => {
                    const resMessage = error.error.messages || error.message || error.error.message || error.toString();
                    this.notificationService.openSnackBar(resMessage);
                });
    }

    private initDoctorsChart() {
        this.doctorsService.countSpecialties()
            .subscribe(result => {
                    this.doctorsChartOptions = {
                        series: Object.values(result),
                        chart: {
                            events: {
                                mounted: (chart) => {
                                    chart.windowResizeHandler();
                                }
                            },
                            height: 300,
                            type: 'radialBar'
                        },
                        plotOptions: {
                            radialBar: {
                                offsetY: 0,
                                startAngle: 0,
                                endAngle: 270,
                                hollow: {
                                    margin: 5,
                                    size: '30%',
                                    background: 'transparent',
                                    image: undefined
                                },
                                dataLabels: {
                                    name: {
                                        show: false
                                    },
                                    value: {
                                        fontSize: '22px',
                                        show: true,
                                        formatter(val: number): string {
                                            return val + '';
                                        }
                                    },
                                    total: {
                                        show: true,
                                        label: 'Total',
                                        formatter: function (w) {
                                            return Object.values(result).reduce((a, b) => a + b, 0);
                                        }
                                    }
                                }
                            }
                        },
                        labels: Object.keys(result),
                        legend: {
                            show: true,
                            floating: true,
                            fontSize: '12px',
                            position: 'left',
                            offsetX: 50,
                            offsetY: 10,
                            labels: {
                                useSeriesColors: true
                            },
                            formatter: function (seriesName, opts) {
                                return seriesName + ':  ' + opts.w.globals.series[opts.seriesIndex];
                            },
                            itemMargin: {
                                horizontal: 3
                            }
                        },
                        responsive: [
                            {
                                breakpoint: 480,
                                options: {
                                    legend: {
                                        show: false
                                    }
                                }
                            }
                        ]
                    };
                },
                error => {
                    const resMessage = error.error.messages || error.message || error.error.message || error.toString();
                    this.notificationService.openSnackBar(resMessage);
                });
    }

    private initPatientsChart() {
        this.patientsService.countAgeCategories()
            .subscribe(result => {
                    this.patientsChartOptions = {
                        series: Object.values(result),
                        chart: {
                            events: {
                                mounted: (chart) => {
                                    chart.windowResizeHandler();
                                }
                            },
                            height: 300,
                            type: 'radialBar'
                        },
                        plotOptions: {
                            radialBar: {
                                offsetY: 0,
                                startAngle: 0,
                                endAngle: 270,
                                hollow: {
                                    margin: 5,
                                    size: '30%',
                                    background: 'transparent',
                                    image: undefined
                                },
                                dataLabels: {
                                    name: {
                                        show: false
                                    },
                                    value: {
                                        fontSize: '22px',
                                        show: true,
                                        formatter(val: number): string {
                                            return val + '';
                                        }
                                    },
                                    total: {
                                        show: true,
                                        label: 'Total',
                                        formatter: function (w) {
                                            return Object.values(result).reduce((a, b) => a + b, 0);
                                        }
                                    }
                                }
                            }
                        },
                        labels: Object.keys(result).map(e => e + ' years'),
                        legend: {
                            show: true,
                            floating: true,
                            fontSize: '16px',
                            position: 'left',
                            offsetX: 50,
                            offsetY: 10,
                            labels: {
                                useSeriesColors: true
                            },
                            formatter: function (seriesName, opts) {
                                return seriesName + ':  ' + opts.w.globals.series[opts.seriesIndex];
                            },
                            itemMargin: {
                                horizontal: 3
                            }
                        },
                        responsive: [
                            {
                                breakpoint: 480,
                                options: {
                                    legend: {
                                        show: false
                                    }
                                }
                            }
                        ]
                    };
                },
                error => {
                    const resMessage = error.error.messages || error.message || error.error.message || error.toString();
                    this.notificationService.openSnackBar(resMessage);
                });
    }

    private initOperationsChart() {
        this.appointmentsService.countOperations()
            .subscribe(result => {
                    const data = [];
                    Object.keys(result).forEach(function (key) {
                        data.push({x: key, y: result[key]});
                    });
                    this.operationsChartOptions = {
                        series: [
                            {
                                data: data
                            }
                        ],

                        chart: {
                            height: 300,
                            type: 'treemap'
                        },
                        title: {
                            text: 'Frequency'
                        }
                    };
                },
                error => {
                    const resMessage = error.error.messages || error.message || error.error.message || error.toString();
                    this.notificationService.openSnackBar(resMessage);
                });
    }

    private initAppointmentsPerMonthChart() {
        this.appointmentsService.countAppointmentsPerMonth()
            .subscribe(result => {
                    this.appointmentsPerMonthChartOptions = {
                        series: [
                            {
                                name: 'Appointments',
                                data: Object.values(result)
                            }
                        ],
                        chart: {
                            height: 500,
                            type: 'line',
                            zoom: {
                                enabled: false
                            }
                        },
                        dataLabels: {
                            enabled: false
                        },
                        stroke: {
                            curve: 'straight'
                        },
                        title: {
                            text: 'Appointment trends by Month',
                            align: 'left'
                        },
                        grid: {
                            row: {
                                colors: ['#f3f3f3', 'transparent'], // takes an array which will be repeated on columns
                                opacity: 0.5
                            }
                        },
                        xaxis: {
                            categories: Object.keys(result)
                        }
                    };
                },
                error => {
                    const resMessage = error.error.messages || error.message || error.error.message || error.toString();
                    this.notificationService.openSnackBar(resMessage);
                });
    }

    constructor(private patientsService: PatientService,
                private doctorsService: DoctorService,
                private appointmentsService: AppointmentService,
                private notificationService: NotificationService) {
        this.initCharts();
    }

    ngOnInit(): void {
    }

    tabChanged() {
        this.initCharts();
    }
}

export interface AxisChartOptions {
    series: ApexAxisChartSeries;
    chart: ApexChart;
    labels: string[];
    colors: string[];
    legend: ApexLegend;
    plotOptions: ApexPlotOptions;
    title: ApexTitleSubtitle;
    dataLabels: ApexDataLabels;
    grid: ApexGrid;
    stroke: ApexStroke;
    xaxis: ApexXAxis;
    yaxis: ApexYAxis;
    fill: ApexFill;
    tooltip: ApexTooltip;
    responsive: ApexResponsive | ApexResponsive[];
}

export interface NonAxisChartOptions {
    series: ApexNonAxisChartSeries;
    chart: ApexChart;
    labels: string[];
    colors: string[];
    legend: ApexLegend;
    plotOptions: ApexPlotOptions;
    title: ApexTitleSubtitle;
    dataLabels: ApexDataLabels;
    responsive: ApexResponsive | ApexResponsive[];
}
