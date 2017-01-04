/**
 * Created by abhinavnathgupta on 03/01/17.
 */
import {ModuleWithProviders} from "@angular/core";
import {Routes, RouterModule} from "@angular/router";
import {Dashboard} from "./dashboard";
import {LoginComponent} from "./login/login.component";
import {MainComponentComponent} from "./main-component/main-component.component";
import {AdminComponentComponent} from "./admin-component/admin-component.component";
import {UserComponentComponent} from "./user-component/user-component.component";
import {DatasourceComponentComponent} from "./datasource-component/datasource-component.component";
import {SchedulingComponentComponent} from "./scheduling-component/scheduling-component.component";
import {SecurityComponentComponent} from "./security-component/security-component.component";
import {AppuserSecurityComponentComponent} from "./appuser-security-component/appuser-security-component.component";
import {UserrolesSecurityComponentComponent} from "./userroles-security-component/userroles-security-component.component";
import {MenuoptionsSecurityComponentComponent} from "./menuoptions-security-component/menuoptions-security-component.component";
import {AssignmenuoptionsSecurityComponentComponent} from "./assignmenuoptions-security-component/assignmenuoptions-security-component.component";
import {ApplicationSecurityComponentComponent} from "./application-security-component/application-security-component.component";
import {PasswordSecurityComponentComponent} from "./password-security-component/password-security-component.component";

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
        component:MainComponentComponent,
        children:[
            {
                path: '',
                component:AdminComponentComponent,
                children: [
                    {
                        path: '',
                        component: DatasourceComponentComponent
                    },
                    {
                        path: 'scheduling',
                        component: SchedulingComponentComponent
                    },
                    {
                        path: 'security',
                        component: SecurityComponentComponent,
                        children: [
                            {
                                path: '',
                                component: Dashboard
                            },
                            {
                                path: 'manageapplicationusers',
                                component: AppuserSecurityComponentComponent
                            },
                            {
                                path: 'assignuserroles',
                                component: UserrolesSecurityComponentComponent
                            },
                            {
                                path: 'managemenuoptions',
                                component: MenuoptionsSecurityComponentComponent
                            },
                            {
                                path: 'assignmenuoptionstoroles',
                                component: AssignmenuoptionsSecurityComponentComponent
                            },
                            {
                                path: 'manageapplicationsecurity',
                                component: ApplicationSecurityComponentComponent
                            },
                            {
                                path: 'managepasswords',
                                component: PasswordSecurityComponentComponent
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