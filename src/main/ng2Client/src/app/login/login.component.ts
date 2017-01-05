import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";
import {Response} from "@angular/http";
import {DashboardDataService} from "../dashboard-data.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  private user: string;
  private password: string;

  constructor(private router: Router, private _backend: DashboardDataService) { }

  ngOnInit() {
  }

    signed() {
        var params = {
            "id":this.user,
            "password": this.password
        };
        this._backend.login(params).subscribe(
            (data:Response) => {
                if(data){
                  this.router.navigate(['/main']);
                }
                else {
                    this.router.navigate(['/login']);
                }
            },
            error => {
                this.router.navigate(['/login']);
            },
            () => console.log('Logging in Complete')
        );
    }
}
