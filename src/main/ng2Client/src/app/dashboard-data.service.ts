import {Injectable} from '@angular/core';
import {Http} from "@angular/http";
import "rxjs/add/operator/map";
import {Observable} from "rxjs";
@Injectable()
export class DashboardDataService {

    constructor(private http: Http) {
    }

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

    readFactoryData() {
        var url = "https://api.github.com/users"; // TODO: dummy service, to be changed as per requirements
        return Observable.interval(5000)
            .switchMap(() => this.http.get(url))
            .map(response => response);
    }

    writeFactoryData(data) {
        var url = "http://localhost:8680/rest/cis/writedata"; // TODO: dummy service, to be changed as per requirements
        return Observable.interval(5000)
            .switchMap(() => this.http.post(url,data))
            .map(response => response);
    }

    authData() {
        return true
    }

}
