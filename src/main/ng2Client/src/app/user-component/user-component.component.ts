import {Component, OnInit} from '@angular/core';
import {DashboardDataService} from "../dashboard-data.service";
import {Response} from "@angular/http";

@Component({
    selector: 'app-user-component',
    templateUrl: './user-component.component.html',
    styleUrls: ['./user-component.component.css']
})
export class UserComponentComponent implements OnInit {
    public componentTitle:string ="";
    public createNewComponent = "";
    public resources: any[] = [];
    public dataLoaded: boolean = false;
    public dataKeys: string[] = [];
    public name: string = "";
    public email: string = "";
    public username: string = "";
    public showForm:boolean =false;

    toggleUserForm(){
        this.showForm = !this.showForm;
        this.name = "";
        this.username = "";
        this.email = "";
        return this.showForm;
    }

    constructor(private _backend: DashboardDataService) {
        this.componentTitle = "CIS User Record Management";
        this.createNewComponent = "New CIS User";
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
            },
            error => {
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


    writeData(){
        var data={
            name: "",
            email: "",
            username: ""
        }

        data.name = this.name;
        data.username = this.username;
        data.email = this.email;
        console.log(data);
        this.showForm = false;
        this._backend.writeFactoryData(data).subscribe(
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
