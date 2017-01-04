import {BrowserModule} from "@angular/platform-browser";
import {NgModule} from "@angular/core";
import {FormsModule} from "@angular/forms";
import {HttpModule} from "@angular/http";
import {AppComponent} from "./app.component";
import {Dashboard} from "./dashboard";
import {DashboardDataService} from "./dashboard-data.service";
import {HelloWorld} from "./helloWorld.component";
import { LoginComponent } from './login/login.component';
import { LoginRouteGuardComponent } from './login-route-guard/login-route-guard.component';
import {RouterModule, RouterOutletMap} from "@angular/router";
import {routing} from './app.routes';
import { MainComponentComponent } from './main-component/main-component.component';
import { AdminComponentComponent } from './admin-component/admin-component.component';
import { UserComponentComponent } from './user-component/user-component.component';
import { DatasourceComponentComponent } from './datasource-component/datasource-component.component';
import { SchedulingComponentComponent } from './scheduling-component/scheduling-component.component';
import { SecurityComponentComponent } from './security-component/security-component.component';
import { AppuserSecurityComponentComponent } from './appuser-security-component/appuser-security-component.component';
import { UserrolesSecurityComponentComponent } from './userroles-security-component/userroles-security-component.component';
import { MenuoptionsSecurityComponentComponent } from './menuoptions-security-component/menuoptions-security-component.component';
import { AssignmenuoptionsSecurityComponentComponent } from './assignmenuoptions-security-component/assignmenuoptions-security-component.component';
import { ApplicationSecurityComponentComponent } from './application-security-component/application-security-component.component';
import { PasswordSecurityComponentComponent } from './password-security-component/password-security-component.component';
@NgModule({
    declarations: [
        AppComponent,
        Dashboard,
        HelloWorld,
        LoginComponent,
        MainComponentComponent,
        AdminComponentComponent,
        UserComponentComponent,
        DatasourceComponentComponent,
        SchedulingComponentComponent,
        SecurityComponentComponent,
        AppuserSecurityComponentComponent,
        UserrolesSecurityComponentComponent,
        MenuoptionsSecurityComponentComponent,
        AssignmenuoptionsSecurityComponentComponent,
        ApplicationSecurityComponentComponent,
        PasswordSecurityComponentComponent,
    ],
    imports: [
        BrowserModule,
        FormsModule,
        HttpModule,
        RouterModule,
        routing
    ],
    providers: [
        DashboardDataService,
        LoginRouteGuardComponent,
        RouterOutletMap
    ],
    bootstrap: [AppComponent]
})
export class AppModule {
}
