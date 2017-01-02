import { Injectable } from '@angular/core';
import {Http} from "@angular/http";
import "rxjs/add/operator/map";
import {Observable} from "rxjs";
@Injectable()
export class DashboardDataService {

  constructor(private http: Http) { }
  readData(param) {
      return Observable.interval(5000)
          .flatMap(() => this.http.get(param))
          .map(response => response);
  }
}
