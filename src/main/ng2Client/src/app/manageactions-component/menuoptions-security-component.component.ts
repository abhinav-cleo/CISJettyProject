import { Component, OnInit } from '@angular/core';
import {Response} from "@angular/http";
import {DashboardDataService} from "../dashboard-data.service";

@Component({
  selector: 'app-menuoptions-security-component',
  templateUrl: './menuoptions-security-component.component.html',
  styleUrls: ['./menuoptions-security-component.component.css']
})
export class ManageActionsComponent implements OnInit {

  public componentTitle:string ="";
  public createNewComponent = "";
  public resources: any[] = [];
  public dataLoaded: boolean = false;
  public dataKeys: string[] = [];
  public name: string = "";
  public showForm:boolean =false;

  toggleUserForm(){
    this.showForm = !this.showForm;
    this.name = "";
    return this.showForm;
  }

  constructor(private _backend: DashboardDataService) {
    this.componentTitle = "CIS Action Management";
    this.createNewComponent = "New CIS Action";
  }


  private getData() {
    this.resources = [];
    this.dataKeys = [];
    return this._backend.getActions().subscribe(
        (data: Response) => {
          console.log(JSON.parse(data['_body']).actions);
          this.resources = JSON.parse(data['_body']).actions;
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

  writeData(){
    var data={
      action: "",
    }

    data.action = this.name;
    console.log(data);
    this.showForm = false;
    this._backend.createAction(data).subscribe(
        (data: Response) => {
          console.log("Action  created Successfully");
        },
        error => {
          console.log("Action creation failed");
        },
        () => console.log('User API Execution Completed')
    );
  }

  ngOnInit() {
    this.getData();
  }


}
