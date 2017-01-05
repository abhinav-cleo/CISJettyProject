import {DashboardDataService} from "./dashboard-data.service";
import {Response} from "@angular/http";
import {Component} from "@angular/core";
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
    devices = ['Stock', 'VlTransfers'];
    selectedDevice = 'Stock';
    constructor(private _backend: DashboardDataService) {
        this.dataLoaded = false;
    }

    onChange(newValue) {
        console.log(newValue);
        this.selectedDevice = newValue;
        this.getData();
    }

    ngOnInit() {
        this.dataLoaded = false;
        this.read = this.getData();
    }

    private getData() {
        this.resources = [];
        this.dataKeys = [];
        var url = '';
        if(this.selectedDevice == 'Stock'){
            url = 'http://localhost:8680/rest/cis/info?table='+this.selectedDevice;
        }else{
            url = 'http://10.20.101.250:8680/rest/transfers/data?table='+this.selectedDevice
        }
        return this._backend.readData(url).subscribe(
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

    ngOnDestroy() {
        this.read.unsubscribe();
    }
}
