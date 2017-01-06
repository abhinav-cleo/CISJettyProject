import {DashboardDataService} from "./dashboard-data.service";
import {Response} from "@angular/http";
import {Component} from "@angular/core";
import {AlertServiceService} from "./alert-service.service";
@Component({
    selector: 'dashboard',
    templateUrl: './dashboard.html'
})
export class DashboardComponent {
    public read: any;
    public resources: any[] = [];
    public dataLoaded: boolean = false;
    public dataKeys: string[] = [];
    public tableName: string = "STOCK";
    public base_url = "";
    devices = ['Stock', 'EventsTable'];
    selectedDevice = 'Stock';
    constructor(private _backend: DashboardDataService, private alertService: AlertServiceService) {
        this.dataLoaded = false;
        this.base_url = "http://localhost:8680";
    }

    onChange(newValue) {
        this.selectedDevice = newValue;
        this.getData();
    }

    ngOnInit() {
        this.base_url = "http://localhost:8680";
        this.dataLoaded = false;
        this.read = this.getData();
    }

    private getData() {
        this.selectedDevice = this.base_url +"/rest/awsService/getEventsFromDB?table=";
        var dbResources  = {
            EventsTable : this.base_url +"/rest/awsService/getEventsFromDB?table="
        }
        this.resources = [];
        this.dataKeys = [];

        var url = this.selectedDevice+ "EventsTable";
        return this._backend.readData(url).subscribe(
            (data: Response) => {
                this.resources = JSON.parse(data['_body']);
                this.dataKeys = this.generateKeys(this.resources[0]);
                this.alertService.success("Data Fetched Successfully");
                this.dataLoaded = true;
            },
            error => {
                this.alertService.error(error._body);
                this.dataLoaded = true;
            },
            () => {}
        );;
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

    ngOnDestroy() {
        this.read.unsubscribe();
    }
}
