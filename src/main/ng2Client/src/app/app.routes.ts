/**
 * Created by abhinavnathgupta on 03/01/17.
 */
import {ModuleWithProviders} from "@angular/core";
import {Routes, RouterModule} from "@angular/router";
import {Dashboard} from "./dashboard";
import {LoginComponent} from "./login/login.component";
import {LoginRouteGuardComponent} from "./login-route-guard/login-route-guard.component";

export const routes: Routes = [
    {
        path: '',
        redirectTo: '/login',
        pathMatch: 'full'
    },
    {
        path:'login',
        component: LoginComponent,
    },
    {
        path: 'dashboard',
        component:Dashboard,
    }
];

export const appRoutingProviders: any[] = [];

export const routing: ModuleWithProviders = RouterModule.forRoot(routes);