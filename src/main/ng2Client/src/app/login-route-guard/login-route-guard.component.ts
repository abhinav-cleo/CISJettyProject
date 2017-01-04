import {Injectable} from "@angular/core";
import {Router} from "@angular/router";
import {DashboardDataService} from "../dashboard-data.service";

@Injectable()
export class LoginRouteGuardComponent{

  constructor(private loginService: DashboardDataService, private router: Router) { }
    canActivate() {
        console.info('route changed');
        if(this.loginService.authData()){
            return this.loginService.authData();
        }
        else{
            this.router.navigate(['/login']);
            return false;
        }
    }

}
