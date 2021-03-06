import {Component, OnInit} from '@angular/core';
import {DashboardDataService} from "../dashboard-data.service";
import {Response} from "@angular/http";
import {AlertServiceService} from "../alert-service.service";

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
    public allUserRoles: any = [];
    public selectedRole: string = "admin";

    toggleUserForm() {
        this.showForm = !this.showForm;
        this.username = "";
        this.password = "";
        return this.showForm;
    }

    constructor(private _backend: DashboardDataService, private alertService: AlertServiceService) {
        this.componentTitle = "CIS Users Management";
        this.createNewComponent = "New User";
        this.username = "";
        this.password = "";
        this.permissionsFetched = false;
        this.rolePermissions = [];
        this.fetchedPermissions = [];
        this.allUserRoles = [];
        this.selectedRole = "admin";
    }


    onChange(newValue) {
        console.log(newValue);
        this.selectedRole = newValue;
    }

    private getData() {
        this.resources = [];
        this.dataKeys = [];
        return this._backend.getUsers().subscribe(
            (data: Response) => {
                this.resources = JSON.parse(data['_body']);
                this.resources = this.resources.reverse();
                this.alertService.success("All Users are Fetched Successfully");
                this.getAllUserRoles();
            },
            error => {
                this.alertService.error(JSON.parse(error['_body']).ERROR);
                console.log('Data reading to the API failed');

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
                this.alertService.success("User created Successfully")
                console.log("User created Successfully");
                this.setRoleForUser();
            },
            error => {
                this.alertService.success("user creation failed");
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
                this.getData();
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

    getAllUserRoles(){
        this._backend.getAllRoles().subscribe(
            (data: Response) => {
                console.log("user Role Fetched successfully");
                this.allUserRoles = JSON.parse(data['_body']);
                this.dataLoaded = true;
            },
            error => {
                console.log("user Role Fetching failed");
                this.allUserRoles = [];
                this.dataLoaded = true;
            },
            () => {}
        );
    }

    setRoleForUser(){
        var param: any = {
            user: "",
            role: ""
        };
        param.user = this.username;
        param.role = this.selectedRole;
        this._backend.assignRoleToUser(param).subscribe(
            (data: Response) => {
                console.log("user Role Updated successfully ");
                this.getData();
            },
            error => {
                console.log("user Role update failed");
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
        this.allUserRoles = [];
        this.selectedRole = "admin";
        this.getData();
    }
}
