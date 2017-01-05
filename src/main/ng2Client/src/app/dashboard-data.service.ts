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
            .get("http://localhost:8680/rest/authService/login/"+ params.id+"/"+ params.password+"/")
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

    getUsers(){
        var url = "http://localhost:8680/rest/authService/users"; // TODO: dummy service, to be changed as per requirements
        return Observable.interval(5000)
            .switchMap(() => this.http.get(url))
            .map(response => response);
    }

    createUser(param){
        var url = "http://localhost:8680/rest/authService/createUser/"+param.username+"/"+param.password; // TODO: dummy service, to be changed as per requirements
        return this.http.get(url)
            .map(response => response);
    }

    removeUser(param){
        var url = "http://localhost:8680/rest/authService/removeUser/"+param; // TODO: dummy service, to be changed as per requirements
        return this.http.get(url)
            .map(response => response);
    }

    getAllRoles(){
        var url = "http://localhost:8680/rest/authService/roles"; // TODO: dummy service, to be changed as per requirements
        return Observable.interval(5000)
            .switchMap(() => this.http.get(url))
            .map(response => response);
    }

    getUserRoles(param){
        var url = "http://localhost:8680/rest/authService/roles/"+param; // TODO: dummy service, to be changed as per requirements
        return this.http.get(url)
            .map(response => response);
    }

    getRolePermissions(param){
        var url = "http://localhost:8680/rest/authService/permissions/"+param; // TODO: dummy service, to be changed as per requirements
        return this.http.get(url)
            .map(response => response);
    }

    createRole(param){
        var url = "http://localhost:8680/rest/authService/addRole/"+param.role; // TODO: dummy service, to be changed as per requirements
        return this.http.get(url)
            .map(response => response);
    }

    deleteRole(param){
        var url = "http://localhost:8680/rest/authService/deleteRole/"+param; // TODO: dummy service, to be changed as per requirements
        return this.http.get(url)
            .map(response => response);
    }

    removeRole(param){
        var url = "http://localhost:8680/rest/authService/removeRole/"+param; // TODO: dummy service, to be changed as per requirements
        return this.http.get(url)
            .map(response => response);
    }

    assignRoleToUser(param){
        console.log("COnsoloing API Log " + param.user + "/" + param.role);
        var url = "http://localhost:8680/rest/authService/assignRole/"+param.user+"/"+param.role; // TODO: dummy service, to be changed as per requirements
        return this.http.get(url)
            .map(response => response);
    }

    assignActionPermissionsOnAssetToRole(param){
        var url = "http://localhost:8680/rest/authService/addPermission/"+param.role+"/"+param.asset+"/"+param.action; // TODO: dummy service, to be changed as per requirements
        return this.http.get(url)
            .map(response => response);
    }

    createAsset(param){
        var url = "http://localhost:8680/rest/authService/addAsset/"+param.asset; // TODO: dummy service, to be changed as per requirements
        return this.http.get(url)
            .map(response => response);
    }

    getAssets(){
        var url = "http://localhost:8680/rest/authService/getAssets"; // TODO: dummy service, to be changed as per requirements
        return Observable.interval(5000)
            .switchMap(() => this.http.get(url))
            .map(response => response);
    }

    createAction(param){
        var url = "http://localhost:8680/rest/authService/addAction/"+param.action; // TODO: dummy service, to be changed as per requirements
        return this.http.get(url)
            .map(response => response);
    }

    getActions(){
        var url = "http://localhost:8680/rest/authService/getActions"; // TODO: dummy service, to be changed as per requirements
        return Observable.interval(5000)
            .switchMap(() => this.http.get(url))
            .map(response => response);
    }

}
