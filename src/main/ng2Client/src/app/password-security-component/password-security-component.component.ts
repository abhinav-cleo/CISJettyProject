import {Component, OnInit} from '@angular/core';
import {Response} from "@angular/http";
import {DashboardDataService} from "../dashboard-data.service";

@Component({
    selector: 'app-password-security-component',
    templateUrl: './password-security-component.component.html',
    styleUrls: ['./password-security-component.component.css']
})
export class PasswordSecurityComponentComponent implements OnInit {

    public componentTitle: string = "";
    public createNewComponent = "";
    public resources: any[] = [];
    public dataLoaded: boolean = false;
    public dataKeys: string[] = [];
    public name: string = "";
    public showForm: boolean = false;
    public permissionsFetched: boolean = false;
    public rolePermissions: any = [];
    public fetchedPermissions: string = "";
    public selectedRole: string = "";
    public selectedAsset: string = "";
    public selectedAction: string = "";
    public roles: any = [];
    public actions: any = [];
    public assets: any = [];

    toggleUserForm() {
        this.showForm = !this.showForm;
        this.name = "";
        return this.showForm;
    }

    constructor(private _backend: DashboardDataService) {
        this.componentTitle = "CIS User Permissions Management";
        this.createNewComponent = "New CIS User Permissions";
        this.permissionsFetched = false;
        this.rolePermissions = [];
        this.fetchedPermissions = "";
        this.selectedRole = "";
        this.selectedAction = "";
        this.selectedAsset = "";
        this.roles = [];
        this.assets = [];
        this.actions = [];
    }

    onChangeRole(newValue) {
        console.log(newValue);
        this.selectedRole = newValue;
    }

    onChangeAsset(newValue) {
        console.log(newValue);
        this.selectedAsset = newValue;
    }

    onChangeAction(newValue) {
        console.log(newValue);
        this.selectedAction = newValue;
    }

    private getData() {
        this.resources = [];
        this.dataKeys = [];
        this.rolePermissions = [];
        return this._backend.getAllRoles().subscribe(
            (data: Response) => {
                console.log('success', 'Data is been fetched from the API Successfully', 'Data is been fetched from the API Successfully');
                this.resources = JSON.parse(data['_body']);
                this.resources = this.resources.reverse();
                this.getAllUserRoles();
            },
            error => {
                console.log('error', 'Data reading to the API failed', 'Data reading to the API failed');
            },
            () => console.log('Reading Data complete')
        );
    }

    private initiateRolePermissions() {
        var data = this.resources;
        for (var i = 0; i < data.length; i++) {
            var key = data[i];
            var value = "";
            this.rolePermissions.push({key, value});
        }
    }

    writeData() {
        var data = {
            role: "",
            asset: "",
            action: ""
        }
        data.role = this.selectedRole;
        data.asset = this.selectedAsset;
        data.action = this.selectedAction;
        this.showForm = false;
        this._backend.assignActionPermissionsOnAssetToRole(data).subscribe(
            (data: Response) => {
                console.log("Permissions for Actions over Assets is successfully assigned to Roles");
                this.getData();
            },
            error => {
                console.log("Permissions for Actions over Assets is Failed");
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

    fetchRolePermissions(param) {
        this.initiateRolePermissions();
        this.fetchedPermissions = "";
        console.log(param);
        this._backend.getRolePermissions(param).subscribe(
            (data: Response) => {
                console.log("User Role Permissions Fetched Successfully");
                this.permissionsFetched = true;
                this.fetchedPermissions = JSON.parse(data['_body']);
                console.log(this.fetchedPermissions);
                for (var i = 0; i < this.rolePermissions.length; i++) {
                    if (this.rolePermissions[i].key == param) {
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
            () => console.log('User API Execution Completed')
        );
    }

    getAllUserRoles(){
        this._backend.getAllRoles().subscribe(
            (data: Response) => {
                console.log("user Role Fetched successfully");
                this.roles = JSON.parse(data['_body']);
                this.getAllUserAssets();
            },
            error => {
                console.log("user Role Fetching failed");
                this.roles = [];
            },
            () => {}
        );
    }


    getAllUserAssets(){
        this._backend.getAssets().subscribe(
            (data: Response) => {
                console.log("user Role Fetched successfully");
                this.assets = JSON.parse(data['_body']).assets;
                this.getAllUserActions();
            },
            error => {
                console.log("user Role Fetching failed");
                this.assets = [];
            },
            () => {}
        );
    }

    getAllUserActions(){
        this._backend.getActions().subscribe(
            (data: Response) => {
                console.log("user Role Fetched successfully");
                this.actions = JSON.parse(data['_body']).actions;
                this.dataLoaded = true;
            },
            error => {
                console.log("user Role Fetching failed");
                this.actions = [];
                this.dataLoaded = true;
            },
            () => {}
        );
    }

    ngOnInit() {
        this.getData();
        this.permissionsFetched = false;
        this.rolePermissions = [];
        this.fetchedPermissions = "";
        this.selectedRole = "";
        this.selectedAction = "";
        this.selectedAsset = "";
        this.roles = [];
        this.assets = [];
        this.actions = [];
    }

}
