import {Component, OnInit} from '@angular/core';
import {Response} from "@angular/http";
import {DashboardDataService} from "../dashboard-data.service";
import {AlertServiceService} from "../alert-service.service";

@Component({
    selector: 'app-assignmenuoptions-security-component',
    templateUrl: './assignmenuoptions-security-component.component.html',
    styleUrls: ['./assignmenuoptions-security-component.component.css']
})
export class ManageAssetsComponent implements OnInit {

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

    constructor(private _backend: DashboardDataService, private alertService: AlertServiceService) {
        this.dataLoaded = false;
        this.componentTitle = "CIS Assets Management";
        this.createNewComponent = "New CIS Asset";
    }


    private getData() {
        this.resources = [];
        this.dataKeys = [];
        return this._backend.getAssets().subscribe(
            (data: Response) => {
                this.resources = JSON.parse(data['_body']).assets;
                this.resources = this.resources.reverse();
                this.dataLoaded = true;
                this.alertService.success("User Asset Created Successfully");
            },
            error => {
                this.alertService.error("Error in creating User Asset");
                console.log('error', 'Data reading to the API failed', 'Data reading to the API failed');
                this.dataLoaded = true;
            },
            () => console.log('Reading Data complete')
        );
    }

    writeData() {
        var data = {
            asset: "",
        }

        data.asset = this.name;
        console.log(data);
        this.showForm = false;
        this._backend.createAsset(data).subscribe(
            (data: Response) => {
                this.alertService.success("Asset created Successfully");
                console.log("Asset created Successfully");
                this.getData();
            },
            error => {
                this.alertService.error("Asset creation failed");
                console.log("Asset creation failed");
            },
            () => console.log('User API Execution Completed')
        );
    }

    ngOnInit() {
        this.dataLoaded = false;
        this.getData();
    }


}
