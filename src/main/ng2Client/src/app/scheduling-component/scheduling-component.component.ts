import {Component, OnInit} from '@angular/core';
import {DashboardDataService} from "../dashboard-data.service";
import {Response} from "@angular/http";
import {AlertServiceService} from "../alert-service.service";

@Component({
    selector: 'app-scheduling-component',
    templateUrl: './scheduling-component.component.html',
    styleUrls: ['./scheduling-component.component.css']
})
export class SchedulingComponent implements OnInit {

    public componentTitle: string = "";
    public createNewComponent = "";
    public resources: any[] = [];
    public dataLoaded: boolean = false;
    public dataKeys: string[] = [];
    public name: string = "";
    public email: string = "";
    public showForm: boolean = false;

    toggleUserForm() {
        this.showForm = !this.showForm;
        this.name = "";
        this.email = "";
        return this.showForm;
    }

    constructor(private _backend: DashboardDataService, private alertService: AlertServiceService) {
        this.componentTitle = "CIS Schedule Management";
        this.createNewComponent = "New Service Schedule";
    }


    private getData() {
        this.resources = [];
        this.dataKeys = [];
        return this._backend.readFactoryData().subscribe(
            (data: Response) => {
                console.log('success', 'Data is been fetched from the API Successfully', 'Data is been fetched from the API Successfully');
                this.resources = JSON.parse(data['_body']);
                this.dataKeys = this.generateKeys(this.resources[0]);
                this.dataLoaded = true;
                this.alertService.success("All Schedules are Fetched Successfully");
            },
            error => {
                this.alertService.error("Schedules Loading Failed");
                console.log('error', 'Data reading to the API failed', 'Data reading to the API failed');
                this.dataLoaded = true;
            },
            () => console.log('Reading Data complete')
        );
    }

    generateKeys(obj): string[] {
        let keys: string[] = [];
        for (var prop in obj) {
            if (obj.hasOwnProperty(prop))
                keys.push(prop);
        }
        return keys;
    }

    getValues(obj): string[] {
        let values: string[] = [];
        for (var prop in obj) {
            if (obj.hasOwnProperty(prop))
                values.push(obj[prop]); // value
        }
        return values
    }


    writeData() {
        var data = {
            name: "",
            email: ""
        }

        data.name = this.name;
        data.email = this.email;
        console.log(data);
        this.showForm = false;
        this._backend.writeFactoryData(data).subscribe(
            (data: Response) => {
                this.alertService.success("Schedule created Successfully");
                console.log("Schedule created Successfully");
            },
            error => {
                this.alertService.error("Schedule creation failed");
                console.log("Schedule creation failed");
            },
            () => console.log('User API Execution Completed')
        );
    }

    ngOnInit() {
        this.getData();
    }

}
