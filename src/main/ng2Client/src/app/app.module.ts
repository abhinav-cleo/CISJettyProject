import {BrowserModule} from "@angular/platform-browser";
import {NgModule} from "@angular/core";
import {FormsModule} from "@angular/forms";
import {HttpModule} from "@angular/http";
import {AppComponent} from "./app.component";
import {DashboardComponent} from "./dashboard";
import {DashboardDataService} from "./dashboard-data.service";
import {HelloWorld} from "./helloWorld.component";
import { LoginComponent } from './login/login.component';
import { LoginRouteGuardComponent } from './login-route-guard/login-route-guard.component';
import {RouterModule, RouterOutletMap} from "@angular/router";
import {routing} from './app.routes';
import { MainComponent } from './main-component/main-component.component';
import { AdminComponent } from './admin-component/admin-component.component';
import { UserComponentComponent } from './user-component/user-component.component';
import { DatasourceComponent } from './datasource-component/datasource-component.component';
import { SchedulingComponent } from './scheduling-component/scheduling-component.component';
import { SecurityComponent } from './security-component/security-component.component';
import { AppuserSecurityComponent } from './appuser-security-component/appuser-security-component.component';
import { UserrolesSecurityComponent } from './userroles-security-component/userroles-security-component.component';
import { ManageActionsComponent } from './manageactions-component/menuoptions-security-component.component';
import { ManageAssetsComponent } from './manageassets-component/assignmenuoptions-security-component.component';
import { ApplicationSecurityComponentComponent } from './application-security-component/application-security-component.component';
import { PasswordSecurityComponentComponent } from './password-security-component/password-security-component.component';
import { AlertComponentComponent } from './alert-component/alert-component.component';
import {AlertServiceService} from "./alert-service.service";
@NgModule({
    declarations: [
        AppComponent,
        DashboardComponent,
        HelloWorld,
        LoginComponent,
        MainComponent,
        AdminComponent,
        UserComponentComponent,
        DatasourceComponent,
        SchedulingComponent,
        SecurityComponent,
        AppuserSecurityComponent,
        UserrolesSecurityComponent,
        ManageActionsComponent,
        ManageAssetsComponent,
        ApplicationSecurityComponentComponent,
        PasswordSecurityComponentComponent,
        AlertComponentComponent,
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
        RouterOutletMap,
        AlertServiceService
    ],
    bootstrap: [AppComponent]
})
export class AppModule {
}
