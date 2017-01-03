import { Injectable } from '@angular/core';
import {Http} from "@angular/http";
import "rxjs/add/operator/map";
import {Observable} from "rxjs";
@Injectable()
export class DashboardDataService {

  constructor(private http: Http) { }
  readData(param) {
      return Observable.interval(5000)
          .switchMap(() => this.http.get(param))
          .map(response => response);
  }
    login(params) {
        return this.http
            .post("http://localhost:8680/rest/cis/login", params)
            .map(response => {
                return response
            });
    }

    authData(){
        return true
    }

}
