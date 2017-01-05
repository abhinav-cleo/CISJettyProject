/**
 * Created by abhinavnathgupta on 03/01/17.
 */
import {ModuleWithProviders} from "@angular/core";
import {Routes, RouterModule} from "@angular/router";
import {DashboardComponent} from "./dashboard";
import {LoginComponent} from "./login/login.component";
import {MainComponent} from "./main-component/main-component.component";
import {AdminComponent} from "./admin-component/admin-component.component";
import {UserComponentComponent} from "./user-component/user-component.component";
import {DatasourceComponent} from "./datasource-component/datasource-component.component";
import {SchedulingComponent} from "./scheduling-component/scheduling-component.component";
import {SecurityComponent} from "./security-component/security-component.component";
import {AppuserSecurityComponent} from "./appuser-security-component/appuser-security-component.component";
import {UserrolesSecurityComponent} from "./userroles-security-component/userroles-security-component.component";
import {ManageActionsComponent} from "./manageactions-component/menuoptions-security-component.component";
import {ManageAssetsComponent} from "./manageassets-component/assignmenuoptions-security-component.component";

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
        path: 'main',
        component:MainComponent,
        children:[
            {
                path: 'admin',
                component:AdminComponent,
                children: [
                    {
                        path: 'datasource',
                        component: DatasourceComponent
                    },
                    {
                        path: 'scheduling',
                        component: SchedulingComponent
                    },
                    {
                        path: 'security',
                        component: SecurityComponent,
                        children: [
                            {
                                path: 'dashboard',
                                component: DashboardComponent
                            },
                            {
                                path: 'manageapplicationusers',
                                component: AppuserSecurityComponent
                            },
                            {
                                path: 'assignuserroles',
                                component: UserrolesSecurityComponent
                            },
                            {
                                path: 'manageactions',
                                component: ManageActionsComponent
                            },
                            {
                                path: 'manageassets',
                                component: ManageAssetsComponent
                            }
                        ]
                    }
                ]
            },
            {
                path: 'user',
                component: UserComponentComponent
            }
        ]
    }
];

export const appRoutingProviders: any[] = [];

export const routing: ModuleWithProviders = RouterModule.forRoot(routes);