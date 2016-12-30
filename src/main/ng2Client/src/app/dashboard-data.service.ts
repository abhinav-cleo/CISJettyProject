import { Injectable } from '@angular/core';
import {Http} from "@angular/http";
import "rxjs/add/operator/map";
@Injectable()
export class DashboardDataService {

  constructor(private http: Http) { }
  readData(param) {
    return this.http
      .get(param)
      .map(response => {
        return response
      });
  }
}
