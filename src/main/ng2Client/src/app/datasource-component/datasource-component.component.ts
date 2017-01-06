import { Component, OnInit } from '@angular/core';
import {Response} from "@angular/http";
import {DashboardDataService} from "../dashboard-data.service";
import {AlertServiceService} from "../alert-service.service";

@Component({
  selector: 'app-datasource-component',
  templateUrl: './datasource-component.component.html',
  styleUrls: ['./datasource-component.component.css']
})
export class DatasourceComponent implements OnInit {

  public componentTitle:string ="";
  public createNewComponent = "";
  public resources: any[] = [];
  public dataLoaded: boolean = false;
  public dataKeys: string[] = [];
  public name: string = "";
  public url: string = "";
  public showForm:boolean =false;

  toggleUserForm(){
    this.showForm = !this.showForm;
    this.name = "";
    this.url = "";
    return this.showForm;
  }

  constructor(private _backend: DashboardDataService, private alertService: AlertServiceService) {
    this.componentTitle = "CIS DataSource Management";
    this.createNewComponent = "New CIS DataSource";
  }


  private getData() {
    this.resources = [];
    this.dataKeys = [];
    return this._backend.readFactoryData().subscribe(
        (data: Response) => {
          console.log('success', 'Data is been fetched from the API Successfully', 'Data is been fetched from the API Successfully');
          this.resources = JSON.parse(data['_body']);
          this.dataKeys = this.generateKeys(this.resources[0]);
          this.alertService.success("DataSource Fetched Successfully");
          this.dataLoaded = true;
        },
        error => {
          this.alertService.error("DataSource Fetching Failed");
          console.log('Data reading to the API failed');
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
      url: ""
    }

    data.name = this.name;
    data.url = this.url;
    console.log(data);
    this.showForm = false;
    this._backend.writeFactoryData(data).subscribe(
        (data: Response) => {
          this.alertService.success("DataSource Created Successfully");
            console.log("DataSource created Successfully");
        },
        error => {
          this.alertService.error("DataSource Creation Successfully");
            console.log("Datasource creation failed");
        },
        () => console.log('User API Execution Completed')
    );
  }

  ngOnInit() {
    this.getData();
  }


}
