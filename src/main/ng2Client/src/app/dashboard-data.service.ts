import {Injectable} from "@angular/core";
import {Http} from "@angular/http";
import "rxjs/add/operator/map";
import {Observable} from "rxjs";
@Injectable()
export class DashboardDataService {
    public base_url = "";
    constructor(private http: Http) {
        //this.base_url = "http://localhost:8680";
        this.base_url = "http://ec2-35-167-20-157.us-west-2.compute.amazonaws.com:8680";
    }

    readData(param) {
        return this.http.get(param)
            .map(response => response);
    }

    login(params) {
        return this.http
            .get(this.base_url + "/rest/authService/login/" + params.id + "/" + params.password + "/")
            .map(response => {
                return response
            });
    }

    readFactoryData() {
        var url = "https://api.github.com/users"; // TODO: dummy service, to be changed as per requirements
        return this.http.get(url)
            .map(response => response);
    }

    writeFactoryData(data) {
        var url = this.base_url + "/rest/cis/writedata"; // TODO: dummy service, to be changed as per requirements
        return this.http.get(url)
            .map(response => response);
    }

    authData() {
        return true
    }

    getUsers() {
        var url = this.base_url + "/rest/authService/users"; // TODO: dummy service, to be changed as per requirements
        return this.http.get(url)
            .map(response => response);
    }

    createUser(param) {
        var url = this.base_url + "/rest/authService/createUser/" + param.username + "/" + param.password; // TODO: dummy service, to be changed as per requirements
        return this.http.get(url)
            .map(response => response);
    }

    removeUser(param) {
        var url = this.base_url+ "/rest/authService/removeUser/" + param; // TODO: dummy service, to be changed as per requirements
        return this.http.get(url)
            .map(response => response);
    }

    getAllRoles() {
        var url = this.base_url + "/rest/authService/roles"; // TODO: dummy service, to be changed as per requirements
        return this.http.get(url)
            .map(response => response);
    }

    getUserRoles(param) {
        var url = this.base_url + "/rest/authService/roles/" + param; // TODO: dummy service, to be changed as per requirements
        return this.http.get(url)
            .map(response => response);
    }

    getRolePermissions(param) {
        var url = this.base_url + "/rest/authService/permissions/" + param; // TODO: dummy service, to be changed as per requirements
        return this.http.get(url)
            .map(response => response);
    }

    createRole(param) {
        var url = this.base_url + "/rest/authService/addRole/" + param.role; // TODO: dummy service, to be changed as per requirements
        return this.http.get(url)
            .map(response => response);
    }

    deleteRole(param) {
        var url = this.base_url + "/rest/authService/deleteRole/" + param; // TODO: dummy service, to be changed as per requirements
        return this.http.get(url)
            .map(response => response);
    }

    removeRole(param) {
        var url = this.base_url + "/rest/authService/removeRole/" + param; // TODO: dummy service, to be changed as per requirements
        return this.http.get(url)
            .map(response => response);
    }

    assignRoleToUser(param) {
        console.log("COnsoloing API Log " + param.user + "/" + param.role);
        var url = this.base_url + "/rest/authService/assignRole/" + param.user + "/" + param.role; // TODO: dummy service, to be changed as per requirements
        return this.http.get(url)
            .map(response => response);
    }

    assignActionPermissionsOnAssetToRole(param) {
        var url = this.base_url + "/rest/authService/addPermission/" + param.role + "/" + param.asset + "/" + param.action; // TODO: dummy service, to be changed as per requirements
        return this.http.get(url)
            .map(response => response);
    }

    createAsset(param) {
        var url = this.base_url + "/rest/authService/addAsset/" + param.asset; // TODO: dummy service, to be changed as per requirements
        return this.http.get(url)
            .map(response => response);
    }

    getAssets() {
        var url = this.base_url + "/rest/authService/getAssets"; // TODO: dummy service, to be changed as per requirements
        return this.http.get(url)
            .map(response => response);
    }

    createAction(param) {
        var url = this.base_url + "/rest/authService/addAction/" + param.action; // TODO: dummy service, to be changed as per requirements
        return this.http.get(url)
            .map(response => response);
    }

    getActions() {
        var url = this.base_url + "/rest/authService/getActions"; // TODO: dummy service, to be changed as per requirements
        return this.http.get(url)
            .map(response => response);
    }

}
