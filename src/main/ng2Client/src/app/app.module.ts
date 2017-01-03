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
@NgModule({
    declarations: [
        AppComponent,
        Dashboard,
        HelloWorld,
        LoginComponent,
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
