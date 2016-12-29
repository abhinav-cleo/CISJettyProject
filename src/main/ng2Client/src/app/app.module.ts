import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { AppComponent } from './app.component';
import {Dashboard} from "./dashboard";
import {DashboardDataService} from "./dashboard-data.service";
import {HelloWorld} from "./helloWorld.component";

@NgModule({
  declarations: [
    AppComponent,
    Dashboard,
    HelloWorld
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule
  ],
  providers: [DashboardDataService],
  bootstrap: [AppComponent]
})
export class AppModule { }
