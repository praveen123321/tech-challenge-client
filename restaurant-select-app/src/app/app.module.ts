import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { FormsModule } from '@angular/forms';
import { SignupComponent } from './signup/signup.component';
import { LoginService } from './services/login.service';
import { HttpClientModule } from '@angular/common/http';
import { RestaurantPickerComponent } from './restaurant-picker/restaurant-picker.component';
import { RestaurantPickerService } from './services/restaurant-picker.service';


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    SignupComponent,
    RestaurantPickerComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [LoginService, RestaurantPickerService],
  bootstrap: [AppComponent]
})
export class AppModule { }
