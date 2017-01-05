import {Component, OnInit} from '@angular/core';
import {DashboardDataService} from "../dashboard-data.service";
import {Response} from "@angular/http";

@Component({
    selector: 'app-userroles-security-component',
    templateUrl: './userroles-security-component.component.html',
    styleUrls: ['./userroles-security-component.component.css']
})
export class UserrolesSecurityComponent implements OnInit {

    public componentTitle: string = "";
    public createNewComponent = "";
    public resources: any[] = [];
    public dataLoaded: boolean = false;
    public dataKeys: string[] = [];
    public name: string = "";
    public showForm: boolean = false;

    toggleUserForm() {
        this.showForm = !this.showForm;
        this.name = "";
        return this.showForm;
    }

    constructor(private _backend: DashboardDataService) {
        this.componentTitle = "CIS User Roles Management";
        this.createNewComponent = "New CIS User Role";
    }


    private getData() {
        this.resources = [];
        this.dataKeys = [];
        return this._backend.getAllRoles().subscribe(
            (data: Response) => {
                console.log('success', 'Data is been fetched from the API Successfully', 'Data is been fetched from the API Successfully');
                this.resources = JSON.parse(data['_body']);
                this.resources = this.resources.reverse();
                this.dataLoaded = true;
            },
            error => {
                console.log('error', 'Data reading to the API failed', 'Data reading to the API failed');
                this.dataLoaded = true;
            },
            () => console.log('Reading Data complete')
        );
    }

    writeData() {
        var data = {
            role: "",
        }

        data.role = this.name;
        this.showForm = false;
        this._backend.createRole(data).subscribe(
            (data: Response) => {
                console.log("User Role Successfully");
            },
            error => {
                console.log("user Role failed");
            },
            () => console.log('User API Execution Completed')
        );
    }

    roleRemove(param) {
        console.log(param);
        this._backend.removeRole(param).subscribe(
            (data: Response) => {
                console.log("User Role Removed Successfully");
            },
            error => {
                console.log("user Role Removal failed");
            },
            () => console.log('User API Execution Completed')
        );
    }

    roleDelete(param) {
        console.log(param);
        this._backend.deleteRole(param).subscribe(
            (data: Response) => {
                console.log("User Role Removed Successfully");
            },
            error => {
                console.log("user Role Removal failed");
            },
            () => console.log('User API Execution Completed')
        );
    }


    ngOnInit() {
        this.getData();
    }

}
