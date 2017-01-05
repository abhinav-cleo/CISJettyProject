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
    public permissionsFetched: boolean = false;
    public rolePermissions:any = [];
    public fetchedPermissions: any = [];

    toggleUserForm() {
        this.showForm = !this.showForm;
        this.username = "";
        this.password = "";
        return this.showForm;
    }

    constructor(private _backend: DashboardDataService) {
        this.componentTitle = "CIS Users Management";
        this.createNewComponent = "New User";
        this.username = "";
        this.password = "";
        this.permissionsFetched = false;
        this.rolePermissions = [];
        this.fetchedPermissions = [];
    }


    private getData() {
        this.resources = [];
        this.dataKeys = [];
        return this._backend.getUsers().subscribe(
            (data: Response) => {
                this.resources = JSON.parse(data['_body']);
                this.resources = this.resources.reverse();
                this.dataLoaded = true;
            },
            error => {
                console.log('error', 'Data reading to the API failed', 'Data reading to the API failed');
                this.dataLoaded = true;
            },
            () => {}
        );
    }

    writeData() {
        var data = {
            username: "",
            password: "",
        }

        data.username = this.username;
        data.password = this.password;
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


    userRemove(param) {
        console.log(param);
        this._backend.removeUser(param).subscribe(
            (data: Response) => {
                console.log("User Removed Successfully");
            },
            error => {
                console.log("user Removal failed");
            },
            () => console.log('User API Execution Completed')
        );
    }

    private initiateRolePermissions() {
        var data = this.resources;
        for (var i = 0; i < data.length; i++) {
            var key = data[i];
            var value = "";
            this.rolePermissions.push({key, value});
        }
        console.log(this.rolePermissions);
    }

    fetchUserRoles(param){
        this.initiateRolePermissions();
        this.fetchedPermissions = [];
        this._backend.getUserRoles(param).subscribe(
            (data: Response) => {
                this.permissionsFetched = true;
                this.fetchedPermissions = JSON.parse(data['_body']);
                for(var i = 0; i < this.rolePermissions.length; i++) {
                    if(this.rolePermissions[i].key == param) {
                        this.rolePermissions[i].value = this.fetchedPermissions;
                    }
                    else {
                        continue;
                    }
                }
            },
            error => {
                console.log("user Role Permissions Fetching failed");
                this.permissionsFetched = false;
                this.rolePermissions = "";
            },
            () => {}
        );
    }

    ngOnInit() {
        this.username = "";
        this.password = "";
        this.permissionsFetched = false;
        this.rolePermissions = [];
        this.fetchedPermissions = [];
        this.getData();
    }

}
