import {Component, OnInit} from '@angular/core';
import {AlertServiceService} from "../alert-service.service";

@Component({
    //moduleId: module.id,
    selector: 'alert',
    templateUrl: './alert-component.component.html',
    styleUrls: ['./alert-component.component.css']
})
export class AlertComponentComponent implements OnInit {
    message: any;

    constructor(private alertService: AlertServiceService) {
    }

    ngOnInit() {
        this.alertService.getMessage().subscribe(message => {
            this.message = message;
        });
    }

}
