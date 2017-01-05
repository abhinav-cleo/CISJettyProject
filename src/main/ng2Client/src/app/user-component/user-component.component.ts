import {Component, OnInit} from '@angular/core';
import {DashboardDataService} from "../dashboard-data.service";
import {Response} from "@angular/http";

@Component({
    selector: 'app-user-component',
    templateUrl: './user-component.component.html',
    styleUrls: ['./user-component.component.css']
})
export class UserComponentComponent implements OnInit {
    public componentTitle: string = "";
    public createNewComponent = "";
    public resources: any[] = [];
    public dataLoaded: boolean = false;
    public dataKeys: string[] = [];
    public password: string = "";
    public username: string = "";
    public showForm: boolean = false;

    toggleUserForm() {
        this.showForm = !this.showForm;
        this.username = "";
        this.password = "";
        return this.showForm;
    }

    constructor(private _backend: DashboardDataService) {
        this.componentTitle = "CIS Users Management";
        this.createNewComponent = "New User";
    }


    private getData() {
        this.resources = [];
        this.dataKeys = [];
        return this._backend.getUsers().subscribe(
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
            username: "",
            password: "",
        }

        data.username = this.username;
        data.password = this.password;
        console.log(data);
        this.showForm = false;
        this._backend.createUser(data).subscribe(
            (data: Response) => {
                console.log("User created Successfully");
            },
            error => {
                console.log("user creation failed");
            },
            () => console.log('User API Execution Completed')
        );
    }

    ngOnInit() {
        this.getData();
    }

}
