import {Inject, Injectable, LOCALE_ID} from "@angular/core";
import {CalendarEvent, CalendarEventTitleFormatter} from "angular-calendar";
import {formatDate} from "@angular/common";

@Injectable()
export class CustomEventTitleFormatter extends CalendarEventTitleFormatter {
    constructor(@Inject(LOCALE_ID) private locale: string) {
        super();
    }

    // you can override any of the methods defined in the parent class

    month(event: CalendarEvent): string {
        return `<b>${formatDate(event.start, 'h:mm a', this.locale)}</b> 
${event.title} [ Patient: ${event.meta.patient.firstName} ${event.meta.patient.lastName}, Doctor: ${event.meta.doctor.firstName} ${event.meta.doctor.lastName} ]`;
    }

    week(event: CalendarEvent): string {
        return `<b>${formatDate(event.start, 'h:mm a', this.locale)}</b> 
${event.title} [ Patient: ${event.meta.patient.firstName} ${event.meta.patient.lastName}, Doctor: ${event.meta.doctor.firstName} ${event.meta.doctor.lastName} ]`;
    }

    day(event: CalendarEvent): string {
        return `<b>${formatDate(event.start, 'h:mm a', this.locale)}</b> 
${event.title} [ Patient: ${event.meta.patient.firstName} ${event.meta.patient.lastName}, Doctor: ${event.meta.doctor.firstName} ${event.meta.doctor.lastName} ]`;
    }
}
